#!/bin/bash

# PassJava Docker 启动脚本
# 使用方法: ./scripts/start.sh [all|simple|infra]

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# 项目根目录
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_ROOT"

MODE=${1:-simple}

echo "=========================================="
echo "  PassJava Docker 启动脚本"
echo "=========================================="
echo -e "${YELLOW}启动模式: $MODE${NC}"

case $MODE in
  "all")
    echo -e "${GREEN}启动所有服务 (MySQL + Redis + ES + 后端 + 前端)...${NC}"
    docker-compose up -d --build
    ;;
  "simple")
    echo -e "${GREEN}启动核心服务 (MySQL + Redis + ES + 单体应用)...${NC}"
    docker-compose -f docker-compose-simple.yml up -d --build
    ;;
  "infra")
    echo -e "${GREEN}启动基础设施 (MySQL + Redis + ES)...${NC}"
    docker-compose -f docker-compose-infra.yml up -d
    ;;
  *)
    echo -e "${RED}未知模式: $MODE${NC}"
    echo "使用方法: ./scripts/start.sh [all|simple|infra]"
    echo "  all    - 启动所有服务 (包含前端)"
    echo "  simple - 启动核心服务 (默认)"
    echo "  infra  - 仅启动基础设施 (用于本地开发)"
    exit 1
    ;;
esac

echo ""
echo -e "${GREEN}=========================================="
echo "  服务启动中..."
echo "==========================================${NC}"
echo ""
echo -e "${BLUE}服务地址:${NC}"
echo "  - PassJava API:      http://localhost:8080"
echo "  - MySQL:             localhost:3306"
echo "  - Redis:             localhost:6379"
echo "  - Elasticsearch:     http://localhost:9200"

if [ "$MODE" = "all" ]; then
  echo "  - Renren Admin API:  http://localhost:8081"
  echo "  - 前端门户:           http://localhost:80"
fi

echo ""
echo -e "${YELLOW}查看日志: docker-compose logs -f${NC}"
echo -e "${YELLOW}停止服务: ./scripts/stop.sh${NC}"





