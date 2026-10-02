# ============================================================
#  Ecommerce Monolith - Infrastructure Setup Script
#  Usage: .\scripts\setup.ps1
# ============================================================

$ErrorActionPreference = "Stop"
$ROOT = Split-Path -Parent $PSScriptRoot

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Ecommerce Monolith - Setup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# ----------------------------------------------------------
#  Step 1: Start Docker containers
# ----------------------------------------------------------
Write-Host ""
Write-Host "[Step 1] Starting Docker containers..." -ForegroundColor Yellow
Set-Location $ROOT
docker-compose up -d

Write-Host "Waiting 15 seconds for databases to initialize..." -ForegroundColor DarkGray
Start-Sleep -Seconds 15

# ----------------------------------------------------------
#  Step 2: Create the MySQL database
# ----------------------------------------------------------
Write-Host ""
Write-Host "[Step 2] Creating MySQL database: ecommerce..." -ForegroundColor Yellow
docker exec -i $(docker-compose ps -q mysql) mysql -uroot -p12042003 -e "CREATE DATABASE IF NOT EXISTS ecommerce DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>$null

# Seed roles table
Write-Host "  Seeding roles table in ecommerce..."
$rolesSql = @"
USE ecommerce;
CREATE TABLE IF NOT EXISTS roles (id BIGINT AUTO_INCREMENT PRIMARY KEY, roleName VARCHAR(60) NOT NULL UNIQUE);
INSERT IGNORE INTO roles (roleName) VALUES ('USER'), ('PM'), ('ADMIN');
"@
docker exec -i $(docker-compose ps -q mysql) mysql -uroot -p12042003 -e $rolesSql 2>$null

# ----------------------------------------------------------
#  Step 3: Create the PostgreSQL database
# ----------------------------------------------------------
Write-Host ""
Write-Host "[Step 3] Creating PostgreSQL database: ecommerce..." -ForegroundColor Yellow
$postgresDatabaseExists = docker exec -i $(docker-compose ps -q postgres) psql -U admin -tAc "SELECT 1 FROM pg_database WHERE datname='ecommerce'"
if (-not $postgresDatabaseExists) {
    docker exec -i $(docker-compose ps -q postgres) psql -U admin -c "CREATE DATABASE ecommerce;" 2>$null
}

# ----------------------------------------------------------
#  Step 4: Verify connectivity
# ----------------------------------------------------------
Write-Host ""
Write-Host "[Step 4] Verifying connectivity..." -ForegroundColor Yellow

try {
    $null = Invoke-WebRequest -Uri "http://localhost:9200" -TimeoutSec 5
    Write-Host "  Elasticsearch: OK" -ForegroundColor Green
} catch {
    Write-Host "  Elasticsearch: NOT READY (may need more time)" -ForegroundColor Red
}

try {
    $null = [System.Net.Sockets.TcpClient]::new("localhost", 3307)
    Write-Host "  MySQL (3307): OK" -ForegroundColor Green
} catch {
    Write-Host "  MySQL (3307): NOT READY" -ForegroundColor Red
}

try {
    $null = [System.Net.Sockets.TcpClient]::new("localhost", 5432)
    Write-Host "  PostgreSQL (5432): OK" -ForegroundColor Green
} catch {
    Write-Host "  PostgreSQL (5432): NOT READY" -ForegroundColor Red
}

try {
    $null = [System.Net.Sockets.TcpClient]::new("localhost", 27017)
    Write-Host "  MongoDB (27017): OK" -ForegroundColor Green
} catch {
    Write-Host "  MongoDB (27017): NOT READY" -ForegroundColor Red
}

# ----------------------------------------------------------
#  Done
# ----------------------------------------------------------
Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Setup Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor White
Write-Host "  1. Start the application:" -ForegroundColor White
Write-Host "     cd $ROOT" -ForegroundColor DarkGray
Write-Host "     mvn spring-boot:run" -ForegroundColor DarkGray
Write-Host ""
Write-Host "  2. Or run from IDE:" -ForegroundColor White
Write-Host "     Run EcommerceApplication.main()" -ForegroundColor DarkGray
Write-Host ""
Write-Host "  3. Access Swagger UI:" -ForegroundColor White
Write-Host "     http://localhost:8080/swagger-ui.html" -ForegroundColor DarkGray
Write-Host ""
Write-Host "  4. Run E2E tests (after app is running):" -ForegroundColor White
Write-Host "     .\scripts\e2e-test.ps1" -ForegroundColor DarkGray
