#!/bin/bash

# ====================================================
# ZLT Microservices Platform - Monolith Version Startup Script
# ====================================================

set -e

# Color definitions
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Project root directory
BASE_DIR=$(cd "$(dirname "$0")" && pwd)
APP_NAME="zlt-monolith"
JAR_NAME="microservices-platform-monolith-6.0.0.jar"

# Configuration parameters - modify as needed
MYSQL_HOST="${MYSQL_HOST:-127.0.0.1}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
REDIS_HOST="${REDIS_HOST:-127.0.0.1}"
REDIS_PORT="${REDIS_PORT:-6379}"

# Application port
APP_PORT=8080

# JVM options
JVM_OPTS="${JVM_OPTS:--Xms256m -Xmx512m}"

# Log directory
LOG_DIR="${BASE_DIR}/logs"
mkdir -p "${LOG_DIR}"

# Print functions
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_title() {
    echo -e "${CYAN}"
    echo "=============================================="
    echo "  ZLT Microservices Platform - Monolith"
    echo "=============================================="
    echo -e "${NC}"
}

# Show help information
show_help() {
    print_title
    echo "
Usage:
  ./start.sh [command] [options]

Commands:
  build         Build the project
  start         Start the application
  stop          Stop the application
  restart       Restart the application
  status        Check status
  logs          View logs
  docker-up     Start Docker infrastructure (MySQL + Redis)
  docker-down   Stop Docker infrastructure
  docker-full   Start full Docker environment (with MinIO + ES)
  init-db       Initialize database
  init-es       Initialize ES test data (access statistics)

Options:
  -p, --profile   Spring Profile: dev/test/prod (default: dev)
  -d, --daemon    Run in background
  -h, --help      Show help information

Examples:
  ./start.sh docker-up          # Start MySQL + Redis
  ./start.sh init-db            # Initialize database
  ./start.sh build              # Build project
  ./start.sh start -d           # Start in background
  ./start.sh start -p prod      # Start in production mode
  ./start.sh status             # Check status
  ./start.sh logs               # View logs
  ./start.sh stop               # Stop application
"
}

# Check Java environment
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed, please install JDK 17+"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 17 ]; then
        print_error "Java version too low, JDK 17+ required, current version: $JAVA_VERSION"
        exit 1
    fi
    print_success "Java environment check passed (JDK $JAVA_VERSION)"
}

# Check Maven environment
check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed"
        exit 1
    fi
    print_success "Maven environment check passed"
}

# Check Docker environment
check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed"
        exit 1
    fi
    if ! docker info &> /dev/null; then
        print_error "Docker is not running"
        exit 1
    fi
    print_success "Docker environment check passed"
}

# Build project
build_project() {
    print_info "Starting project build..."
    cd "${BASE_DIR}"
    mvn clean package -DskipTests
    print_success "Project build completed"
    print_info "JAR file: target/${JAR_NAME}"
}

# Start Docker infrastructure
docker_up() {
    check_docker
    print_info "Starting Docker infrastructure (MySQL + Redis)..."
    cd "${BASE_DIR}"
    docker compose up -d mysql redis
    
    print_info "Waiting for services to start..."
    sleep 5
    
    show_docker_status
}

# Start full Docker environment
docker_full() {
    check_docker
    print_info "Starting full Docker environment..."
    cd "${BASE_DIR}"
    docker compose --profile full up -d
    
    print_info "Waiting for services to start..."
    sleep 10
    
    show_docker_status
}

# Stop Docker infrastructure
docker_down() {
    check_docker
    print_info "Stopping Docker infrastructure..."
    cd "${BASE_DIR}"
    docker compose --profile full down
    print_success "Docker infrastructure stopped"
}

# Initialize database
init_db() {
    print_info "Initializing database..."
    
    local sql_file="${BASE_DIR}/doc/init-database.sql"
    if [ ! -f "$sql_file" ]; then
        print_error "Database initialization file not found: $sql_file"
        exit 1
    fi
    
    # Execute SQL using docker exec
    if docker ps | grep -q "zlt-monolith-mysql"; then
        docker exec -i zlt-monolith-mysql mysql -uroot -p1q2w3e4r < "$sql_file"
        print_success "Database initialization completed"
    else
        # Local MySQL
        print_info "Please manually execute the following command to initialize the database:"
        echo "  mysql -h ${MYSQL_HOST} -P ${MYSQL_PORT} -uroot -p < doc/init-database.sql"
    fi
}

# Initialize ES test data
init_es() {
    print_info "Initializing ES test data (access statistics)..."
    
    local es_script="${BASE_DIR}/doc/init-es-testdata.sh"
    if [ ! -f "$es_script" ]; then
        print_error "ES initialization script not found: $es_script"
        exit 1
    fi
    
    # Check if ES is running
    if ! curl -s "http://localhost:9200/_cluster/health" > /dev/null 2>&1; then
        print_warning "Elasticsearch is not running, please start it first: ./start.sh docker-full"
        exit 1
    fi
    
    bash "$es_script" localhost:9200
    print_success "ES test data initialization completed"
}

# Start application
start_app() {
    local profile=${1:-dev}
    local daemon=${2:-false}
    
    print_info "Starting ${APP_NAME}..."
    print_info "Profile: ${profile}"
    
    # Find jar file
    local jar_file="${BASE_DIR}/target/${JAR_NAME}"
    if [ ! -f "$jar_file" ]; then
        # Try to find other jar
        jar_file=$(find "${BASE_DIR}/target" -name "*.jar" -type f 2>/dev/null | grep -v original | head -n 1)
    fi
    
    if [ -z "$jar_file" ] || [ ! -f "$jar_file" ]; then
        print_error "JAR file not found, please build first: ./start.sh build"
        exit 1
    fi
    
    # Check if port is already in use
    if lsof -i:${APP_PORT} > /dev/null 2>&1; then
        print_warning "Port ${APP_PORT} is already in use"
        print_info "Use ./start.sh stop to stop the existing process"
        exit 1
    fi
    
    # Build startup command
    local start_cmd="java ${JVM_OPTS} \
        -Dspring.profiles.active=${profile} \
        -Dserver.port=${APP_PORT} \
        -jar ${jar_file}"
    
    if [ "$daemon" = "true" ]; then
        # Start in background
        nohup $start_cmd > "${LOG_DIR}/${APP_NAME}.log" 2>&1 &
        local pid=$!
        echo $pid > "${LOG_DIR}/${APP_NAME}.pid"
        
        print_success "${APP_NAME} started (PID: $pid)"
        print_info "Log file: ${LOG_DIR}/${APP_NAME}.log"
        print_info "Access URL: http://localhost:${APP_PORT}"
    else
        # Start in foreground
        print_info "Starting in foreground... (Ctrl+C to stop)"
        print_info "Access URL: http://localhost:${APP_PORT}"
        $start_cmd
    fi
}

# Stop application
stop_app() {
    print_info "Stopping ${APP_NAME}..."
    
    local pid_file="${LOG_DIR}/${APP_NAME}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 $pid 2>/dev/null; then
            kill $pid
            print_success "${APP_NAME} stopped (PID: $pid)"
        else
            print_warning "Process $pid does not exist"
        fi
        rm -f "$pid_file"
    else
        # Try to find process by port
        local pid=$(lsof -t -i:${APP_PORT} 2>/dev/null)
        if [ -n "$pid" ]; then
            kill $pid
            print_success "${APP_NAME} stopped (PID: $pid)"
        else
            print_warning "${APP_NAME} is not running"
        fi
    fi
}

# Restart application
restart_app() {
    stop_app
    sleep 2
    start_app "$@"
}

# Show status
show_status() {
    print_title
    echo ""
    printf "%-25s %-10s %-15s\n" "Service" "Port" "Status"
    echo "------------------------------------------------"
    
    # Check application
    check_service_status "${APP_NAME}" ${APP_PORT}
    
    echo ""
    show_docker_status
    
    echo ""
    print_info "Access URLs:"
    echo "  - Application Home:    http://localhost:${APP_PORT}"
    echo "  - Swagger Docs:        http://localhost:${APP_PORT}/doc.html"
    echo "  - Health Check:        http://localhost:${APP_PORT}/actuator/health"
    echo ""
}

# Show Docker service status
show_docker_status() {
    printf "%-25s %-10s %-15s\n" "Docker Service" "Port" "Status"
    echo "------------------------------------------------"
    
    check_service_status "MySQL" 3306
    check_service_status "Redis" 6379
    check_service_status "MinIO" 9000
    check_service_status "Elasticsearch" 9200
}

check_service_status() {
    local name=$1
    local port=$2
    
    if lsof -i:${port} > /dev/null 2>&1; then
        printf "%-25s %-10s ${GREEN}%-15s${NC}\n" "$name" "$port" "Running"
    else
        printf "%-25s %-10s ${RED}%-15s${NC}\n" "$name" "$port" "Not Started"
    fi
}

# View logs
show_logs() {
    local log_file="${LOG_DIR}/${APP_NAME}.log"
    
    if [ -f "$log_file" ]; then
        print_info "Viewing logs: $log_file"
        tail -f "$log_file"
    else
        print_warning "Log file does not exist, application may not be running in background mode"
        print_info "Available log files:"
        ls -la "${LOG_DIR}"/*.log 2>/dev/null || echo "  None"
    fi
}

# Main function
main() {
    local cmd=$1
    local profile="dev"
    local daemon="false"
    
    # Parse arguments
    shift || true
    while [[ $# -gt 0 ]]; do
        case $1 in
            -p|--profile)
                profile="$2"
                shift 2
                ;;
            -d|--daemon)
                daemon="true"
                shift
                ;;
            -h|--help)
                show_help
                exit 0
                ;;
            *)
                shift
                ;;
        esac
    done
    
    case "$cmd" in
        build)
            check_java
            check_maven
            build_project
            ;;
        start)
            check_java
            start_app $profile $daemon
            ;;
        stop)
            stop_app
            ;;
        restart)
            check_java
            restart_app $profile $daemon
            ;;
        status)
            show_status
            ;;
        logs)
            show_logs
            ;;
        docker-up)
            docker_up
            ;;
        docker-down)
            docker_down
            ;;
        docker-full)
            docker_full
            ;;
        init-db)
            init_db
            ;;
        init-es)
            init_es
            ;;
        -h|--help|help|"")
            show_help
            ;;
        *)
            print_error "Unknown command: $cmd"
            show_help
            exit 1
            ;;
    esac
}

main "$@"

