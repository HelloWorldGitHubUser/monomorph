#!/bin/bash

# PassJava Docker 停止脚本
# 使用方法: ./scripts/stop.sh

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 项目根目录
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_ROOT"

echo "=========================================="
echo "  PassJava Docker 停止脚本"
echo "=========================================="

echo -e "${YELLOW}停止所有 PassJava 容器...${NC}"

# 停止所有 docker-compose 服务
docker-compose down 2>/dev/null || true
docker-compose -f docker-compose-simple.yml down 2>/dev/null || true
docker-compose -f docker-compose-infra.yml down 2>/dev/null || true

echo -e "${GREEN}✓ 所有服务已停止${NC}"
echo ""
echo "提示:"
echo "  - 删除数据卷: docker-compose down -v"
echo "  - 清理镜像:   docker system prune -f"





