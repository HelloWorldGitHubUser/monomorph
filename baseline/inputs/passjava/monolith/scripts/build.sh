#!/bin/bash

# PassJava 单体应用构建脚本
# 使用方法: ./scripts/build.sh

set -e

echo "=========================================="
echo "  PassJava Monolith 构建脚本"
echo "=========================================="

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 项目根目录
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_ROOT"

echo -e "${YELLOW}项目目录: $PROJECT_ROOT${NC}"

# 构建 passjava-monolith
echo -e "\n${GREEN}[1/2] 构建 passjava-monolith...${NC}"
cd passjava-monolith
mvn clean package -DskipTests
echo -e "${GREEN}✓ passjava-monolith 构建完成${NC}"

# 构建 renren-fast
echo -e "\n${GREEN}[2/2] 构建 renren-fast...${NC}"
cd ../renren-fast
mvn clean package -DskipTests
echo -e "${GREEN}✓ renren-fast 构建完成${NC}"

echo -e "\n${GREEN}=========================================="
echo "  所有模块构建完成！"
echo "==========================================${NC}"
echo ""
echo "下一步操作:"
echo "  1. 启动所有服务:     docker-compose up -d"
echo "  2. 仅启动核心服务:   docker-compose -f docker-compose-simple.yml up -d"
echo "  3. 仅启动基础设施:   docker-compose -f docker-compose-infra.yml up -d"





