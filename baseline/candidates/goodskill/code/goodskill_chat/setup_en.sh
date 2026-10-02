#!/bin/bash

# ============================================================
# GoodsKill Single Instance Monolith - Environment Setup Script
# Purpose: Start required dependency services and initialize database
# After completion, manually run: mvn spring-boot:run or java -jar target/goodskill-mono.jar
# ============================================================

set -e

# Color definitions
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Path configuration
MONO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Use monolith-specific simplified SQL (removed Seata-related gs_sys and seata_server databases)
SQL_FILE="${MONO_DIR}/seckill-mono.sql"

# Required services for single instance monolith (based on application.yml configuration)
REQUIRED_SERVICES="mysql redis rabbitmq mongo"
# Optional services
OPTIONAL_SERVICES="elasticsearch"

echo -e "${BLUE}============================================${NC}"
echo -e "${BLUE}   GoodsKill Single Instance Monolith - Environment Setup${NC}"
echo -e "${BLUE}============================================${NC}"
echo ""
echo -e "Required services: ${GREEN}MySQL, Redis, RabbitMQ, MongoDB${NC}"
echo -e "Optional services: ${YELLOW}Elasticsearch${NC}"
echo ""

# ============================================================
# Step 1: Check and configure /etc/hosts
# ============================================================
echo -e "${YELLOW}[Step 1/5] Checking /etc/hosts configuration...${NC}"

HOSTS_ENTRIES="mysql redis mongo rabbitmq elasticsearch"
MISSING_HOSTS=""

for host in $HOSTS_ENTRIES; do
    if ! grep -q "127.0.0.1.*\b$host\b" /etc/hosts 2>/dev/null; then
        MISSING_HOSTS="$MISSING_HOSTS $host"
    fi
done

if [ -n "$MISSING_HOSTS" ]; then
    echo -e "${YELLOW}  Missing hosts entries:${NC}$MISSING_HOSTS"
    echo -e "${YELLOW}  Please enter password to add hosts configuration:${NC}"
    echo "127.0.0.1 mysql redis mongo rabbitmq elasticsearch" | sudo tee -a /etc/hosts > /dev/null
    echo -e "${GREEN}  ✓ Hosts configuration added${NC}"
else
    echo -e "${GREEN}  ✓ Hosts configuration already exists${NC}"
fi

# ============================================================
# Step 2: Clean up and start Docker services
# ============================================================
echo ""
echo -e "${YELLOW}[Step 2/5] Starting Docker services...${NC}"

# Stop potentially existing old containers (avoid conflicts)
echo -e "  Cleaning up potentially existing old containers..."
for service in mysql redis rabbitmq mongo elasticsearch zookeeper kafka nacos seata-server minio kafkamanager; do
    if docker ps -a --format '{{.Names}}' | grep -q "^${service}$"; then
        docker rm -f "$service" > /dev/null 2>&1 || true
    fi
done
echo -e "${GREEN}  ✓ Old containers cleaned up${NC}"

# Create Docker network (if not exists)
if ! docker network ls --format '{{.Name}}' | grep -q "^goodskill_net$"; then
    docker network create goodskill_net > /dev/null 2>&1 || true
fi

# Start MySQL
echo -e "  Starting MySQL..."
docker run -d --name mysql \
    --network goodskill_net \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD=Password123 \
    -e TZ=Asia/Shanghai \
    mysql:8.0.29 > /dev/null 2>&1
echo -e "${GREEN}  ✓ MySQL started${NC}"

# Start Redis
echo -e "  Starting Redis..."
docker run -d --name redis \
    --network goodskill_net \
    -p 6379:6379 \
    redis:latest \
    redis-server --requirepass 123456 > /dev/null 2>&1
echo -e "${GREEN}  ✓ Redis started${NC}"

# Start RabbitMQ
echo -e "  Starting RabbitMQ..."
docker run -d --name rabbitmq \
    --network goodskill_net \
    -p 5672:5672 \
    -p 15672:15672 \
    rabbitmq:3-management > /dev/null 2>&1
echo -e "${GREEN}  ✓ RabbitMQ started${NC}"

# Start MongoDB
echo -e "  Starting MongoDB..."
docker run -d --name mongo \
    --network goodskill_net \
    -p 27017:27017 \
    mongo:6.0.7 > /dev/null 2>&1
echo -e "${GREEN}  ✓ MongoDB started${NC}"

# Ask whether to start Elasticsearch
echo ""
echo -e "${YELLOW}  Start Elasticsearch? (y/N)${NC}"
read -r START_ES
if [ "$START_ES" = "y" ] || [ "$START_ES" = "Y" ]; then
    echo -e "  Starting Elasticsearch..."
    docker run -d --name elasticsearch \
        --network goodskill_net \
        -p 9200:9200 \
        -p 9300:9300 \
        -e "discovery.type=single-node" \
        -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
        elasticsearch:7.16.2 > /dev/null 2>&1
    echo -e "${GREEN}  ✓ Elasticsearch started${NC}"
else
    echo -e "${YELLOW}  Skipping Elasticsearch${NC}"
fi

# ============================================================
# Step 3: Wait for services to be ready
# ============================================================
echo ""
echo -e "${YELLOW}[Step 3/5] Waiting for services to be ready...${NC}"

MAX_RETRIES=60

# Wait for MySQL to be ready
echo -e "  Waiting for MySQL to be ready..."
RETRY_COUNT=0
while ! docker exec mysql mysql -u root -pPassword123 -e "SELECT 1" > /dev/null 2>&1; do
    RETRY_COUNT=$((RETRY_COUNT + 1))
    if [ $RETRY_COUNT -ge $MAX_RETRIES ]; then
        echo -e "${RED}  ✗ MySQL startup timeout${NC}"
        exit 1
    fi
    printf "    Waiting... (%d/%d)\r" $RETRY_COUNT $MAX_RETRIES
    sleep 2
done
echo -e "${GREEN}  ✓ MySQL is ready              ${NC}"

# Wait for Redis to be ready
echo -e "  Waiting for Redis to be ready..."
RETRY_COUNT=0
while ! docker exec redis redis-cli -a 123456 ping 2>/dev/null | grep -q "PONG"; do
    RETRY_COUNT=$((RETRY_COUNT + 1))
    if [ $RETRY_COUNT -ge $MAX_RETRIES ]; then
        echo -e "${RED}  ✗ Redis startup timeout${NC}"
        exit 1
    fi
    sleep 1
done
echo -e "${GREEN}  ✓ Redis is ready${NC}"

# Wait for RabbitMQ to be ready
echo -e "  Waiting for RabbitMQ to be ready..."
RETRY_COUNT=0
while ! docker exec rabbitmq rabbitmqctl status > /dev/null 2>&1; do
    RETRY_COUNT=$((RETRY_COUNT + 1))
    if [ $RETRY_COUNT -ge $MAX_RETRIES ]; then
        echo -e "${RED}  ✗ RabbitMQ startup timeout${NC}"
        exit 1
    fi
    sleep 2
done
echo -e "${GREEN}  ✓ RabbitMQ is ready${NC}"

# Wait for MongoDB to be ready
echo -e "  Waiting for MongoDB to be ready..."
RETRY_COUNT=0
while ! docker exec mongo mongosh --eval "db.adminCommand('ping')" > /dev/null 2>&1; do
    RETRY_COUNT=$((RETRY_COUNT + 1))
    if [ $RETRY_COUNT -ge 30 ]; then
        echo -e "${YELLOW}  ⚠ MongoDB wait timeout, continuing...${NC}"
        break
    fi
    sleep 1
done
echo -e "${GREEN}  ✓ MongoDB is ready${NC}"

# ============================================================
# Step 4: Initialize database
# ============================================================
echo ""
echo -e "${YELLOW}[Step 4/5] Initializing database...${NC}"

if [ ! -f "$SQL_FILE" ]; then
    echo -e "${RED}  ✗ SQL file does not exist: $SQL_FILE${NC}"
    exit 1
fi

# Check if database is already initialized
SECKILL_EXISTS=$(docker exec mysql mysql -u root -pPassword123 -N -e "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'seckill';" 2>/dev/null || echo "0")

# Create databases needed for monolith (only seckill and seckill_01, no Seata-related gs_sys and seata_server)
create_all_databases() {
    echo -e "  Creating databases..."
    docker exec mysql mysql -u root -pPassword123 -e "
        CREATE DATABASE IF NOT EXISTS seckill CHARACTER SET utf8mb4;
        CREATE DATABASE IF NOT EXISTS seckill_01 CHARACTER SET utf8mb4;
    "
    echo -e "${GREEN}  ✓ Databases created (seckill, seckill_01)${NC}"
    
    echo -e "  Copying and executing SQL script..."
    docker cp "$SQL_FILE" mysql:/tmp/seckill.sql
    # Execute SQL script (switch to seckill database first)
    docker exec mysql mysql -u root -pPassword123 seckill -e "source /tmp/seckill.sql"
    echo -e "${GREEN}  ✓ SQL script execution completed${NC}"
}

if [ "$SECKILL_EXISTS" = "1" ]; then
    echo -e "${YELLOW}  Database already exists, reinitialize? (y/N)${NC}"
    read -r REINIT
    if [ "$REINIT" != "y" ] && [ "$REINIT" != "Y" ]; then
        echo -e "${GREEN}  Skipping database initialization${NC}"
    else
        echo -e "  Reinitializing database..."
        docker exec mysql mysql -u root -pPassword123 -e "
            DROP DATABASE IF EXISTS seckill;
            DROP DATABASE IF EXISTS seckill_01;
        "
        create_all_databases
        echo -e "${GREEN}  ✓ Database reinitialized${NC}"
    fi
else
    echo -e "  Executing database initialization script..."
    create_all_databases
    echo -e "${GREEN}  ✓ Database initialization completed${NC}"
fi

# ============================================================
# Step 5: Verify environment
# ============================================================
echo ""
echo -e "${YELLOW}[Step 5/5] Verifying environment...${NC}"

echo -e "  Checking databases..."
DATABASES=$(docker exec mysql mysql -u root -pPassword123 -N -e "SHOW DATABASES LIKE 'seckill%';" 2>/dev/null)
echo -e "  Created databases:"
echo "$DATABASES" | while read db; do
    echo -e "    ${GREEN}✓${NC} $db"
done

echo ""
echo -e "  Docker service status:"
docker ps --format "table {{.Names}}\t{{.Status}}" | grep -E "mysql|redis|mongo|rabbitmq|elasticsearch" | while read line; do
    echo -e "    ${GREEN}✓${NC} $line"
done

# ============================================================
# Complete
# ============================================================
echo ""
echo -e "${BLUE}============================================${NC}"
echo -e "${GREEN}   ✓ Environment setup completed!${NC}"
echo -e "${BLUE}============================================${NC}"
echo ""
echo -e "Started services:"
echo -e "  ${GREEN}•${NC} MySQL      - localhost:3306 (root/Password123)"
echo -e "  ${GREEN}•${NC} Redis      - localhost:6379 (password: 123456)"
echo -e "  ${GREEN}•${NC} RabbitMQ   - localhost:5672 (management UI: http://localhost:15672)"
echo -e "  ${GREEN}•${NC} MongoDB    - localhost:27017"
if [ "$START_ES" = "y" ] || [ "$START_ES" = "Y" ]; then
    echo -e "  ${GREEN}•${NC} Elasticsearch - localhost:9200"
fi
echo ""
echo -e "You can now start the application:"
echo -e ""
echo -e "  ${YELLOW}cd $MONO_DIR${NC}"
echo -e "  ${YELLOW}mvn spring-boot:run${NC}"
echo -e ""
echo -e "After successful startup, visit:"
echo -e "  Health check: ${BLUE}http://localhost:8080/actuator/health${NC}"
echo -e "  Swagger:      ${BLUE}http://localhost:8080/swagger-ui.html${NC}"
echo ""

