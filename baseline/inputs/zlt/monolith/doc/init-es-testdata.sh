#!/bin/bash
# =========================================================================
# ZLT 微服务平台 - 单体版 Elasticsearch 测试数据初始化脚本
# 用于初始化首页访问统计的测试数据
# 
# 使用方法:
#   ./init-es-testdata.sh [ES_HOST]
#   例如: ./init-es-testdata.sh localhost:9200
# =========================================================================

ES_HOST=${1:-localhost:9200}

echo "======================================"
echo "ES 测试数据初始化脚本"
echo "ES 地址: $ES_HOST"
echo "======================================"

# 等待 ES 就绪
echo "检查 ES 是否就绪..."
for i in {1..30}; do
  if curl -s "http://$ES_HOST/_cluster/health" > /dev/null 2>&1; then
    echo "ES 已就绪!"
    break
  fi
  echo "等待 ES 启动... ($i/30)"
  sleep 2
done

# 检查 point-log 是否已有数据
EXISTING_POINT=$(curl -s "http://$ES_HOST/_cat/indices/point-log-*" 2>/dev/null | wc -l)
if [ "$EXISTING_POINT" -gt 0 ]; then
  echo "point-log-* 索引已存在，跳过访问统计数据初始化"
  echo "如需重新初始化，请先删除索引: curl -X DELETE http://$ES_HOST/point-log-*"
  SKIP_POINT_LOG=true
else
  SKIP_POINT_LOG=false
fi

echo ""

# ====== point-log 访问统计数据初始化 ======
if [ "$SKIP_POINT_LOG" = "false" ]; then
echo "开始创建访问统计测试数据..."

# 获取日期
TODAY=$(date +%Y.%m.%d)
TODAY_ISO=$(date +%Y-%m-%d)

# 创建今天的索引
echo "创建索引 point-log-$TODAY..."
curl -s -X PUT "http://$ES_HOST/point-log-$TODAY" -H "Content-Type: application/json" -d '{
  "mappings": {
    "properties": {
      "timestamp": { "type": "date" },
      "appName": { "type": "keyword" },
      "id": { "type": "keyword" },
      "type": { "type": "keyword" },
      "ip": { "type": "keyword" },
      "browser": { "type": "keyword" },
      "operatingSystem": { "type": "keyword" }
    }
  }
}'
echo ""

# 插入今天的测试数据
CURRENT_HOUR=$(date +%H)
echo "插入今天的测试数据 (50条)..."
for i in {1..50}; do
  # 确保有些数据在最近1小时内
  if [ $i -le 15 ]; then
    HOUR=$CURRENT_HOUR
  else
    HOUR=$(printf "%02d" $((RANDOM % 24)))
  fi
  MIN=$(printf "%02d" $((RANDOM % 60)))
  SEC=$(printf "%02d" $((RANDOM % 60)))
  
  # 随机浏览器
  BROWSERS=("CHROME" "FIREFOX" "SAFARI" "EDGE")
  BROWSER=${BROWSERS[$((RANDOM % 4))]}
  
  # 随机操作系统
  OS_LIST=("WINDOWS" "MAC_OS_X" "LINUX" "ANDROID" "IOS")
  OS=${OS_LIST[$((RANDOM % 5))]}
  
  # 随机 IP
  IP="192.168.$((RANDOM % 256)).$((RANDOM % 256))"
  
  curl -s -X POST "http://$ES_HOST/point-log-$TODAY/_doc" -H "Content-Type: application/json" -d "{
    \"timestamp\": \"${TODAY_ISO}T${HOUR}:${MIN}:${SEC}.000+08:00\",
    \"appName\": \"zlt-monolith\",
    \"id\": \"1\",
    \"type\": \"request-statistics\",
    \"ip\": \"$IP\",
    \"browser\": \"$BROWSER\",
    \"operatingSystem\": \"$OS\"
  }" > /dev/null
done
echo "完成!"

# 创建过去6天的数据
for day in {1..6}; do
  # 兼容 macOS 和 Linux
  if date -v-1d > /dev/null 2>&1; then
    # macOS
    PAST_DATE=$(date -v-${day}d +%Y.%m.%d)
    PAST_DATE_ISO=$(date -v-${day}d +%Y-%m-%d)
  else
    # Linux
    PAST_DATE=$(date -d "$day days ago" +%Y.%m.%d)
    PAST_DATE_ISO=$(date -d "$day days ago" +%Y-%m-%d)
  fi
  
  echo "创建索引 point-log-$PAST_DATE..."
  curl -s -X PUT "http://$ES_HOST/point-log-$PAST_DATE" -H "Content-Type: application/json" -d '{
    "mappings": {
      "properties": {
        "timestamp": { "type": "date" },
        "appName": { "type": "keyword" },
        "id": { "type": "keyword" },
        "type": { "type": "keyword" },
        "ip": { "type": "keyword" },
        "browser": { "type": "keyword" },
        "operatingSystem": { "type": "keyword" }
      }
    }
  }' 2>/dev/null
  
  # 随机 20-50 条数据
  COUNT=$((20 + RANDOM % 31))
  echo "插入 $COUNT 条测试数据..."
  for i in $(seq 1 $COUNT); do
    HOUR=$(printf "%02d" $((RANDOM % 24)))
    MIN=$(printf "%02d" $((RANDOM % 60)))
    SEC=$(printf "%02d" $((RANDOM % 60)))
    
    BROWSERS=("CHROME" "FIREFOX" "SAFARI" "EDGE")
    BROWSER=${BROWSERS[$((RANDOM % 4))]}
    
    OS_LIST=("WINDOWS" "MAC_OS_X" "LINUX" "ANDROID" "IOS")
    OS=${OS_LIST[$((RANDOM % 5))]}
    
    IP="192.168.$((RANDOM % 256)).$((RANDOM % 256))"
    
    curl -s -X POST "http://$ES_HOST/point-log-$PAST_DATE/_doc" -H "Content-Type: application/json" -d "{
      \"timestamp\": \"${PAST_DATE_ISO}T${HOUR}:${MIN}:${SEC}.000+08:00\",
      \"appName\": \"zlt-monolith\",
      \"id\": \"1\",
      \"type\": \"request-statistics\",
      \"ip\": \"$IP\",
      \"browser\": \"$BROWSER\",
      \"operatingSystem\": \"$OS\"
    }" > /dev/null
  done
done

# 刷新索引
echo ""
echo "刷新 point-log 索引..."
curl -s -X POST "http://$ES_HOST/point-log-*/_refresh" > /dev/null
fi
# ====== point-log 初始化结束 ======

echo ""
echo "======================================"
echo "初始化 sys_user 索引 (用户搜索)"
echo "======================================"

# 检查 sys_user 是否已存在
EXISTING_USER=$(curl -s "http://$ES_HOST/_cat/indices/sys_user" 2>/dev/null | wc -l)
if [ "$EXISTING_USER" -gt 0 ]; then
  echo "sys_user 索引已存在，跳过初始化"
  echo "如需重新初始化，请先删除索引: curl -X DELETE http://$ES_HOST/sys_user"
else

# 创建用户索引
curl -s -X PUT "http://$ES_HOST/sys_user" -H "Content-Type: application/json" -d '{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "username": { "type": "text", "fields": { "keyword": { "type": "keyword" } } },
      "nickname": { "type": "text", "fields": { "keyword": { "type": "keyword" } } },
      "mobile": { "type": "keyword" },
      "sex": { "type": "integer" },
      "enabled": { "type": "boolean" },
      "type": { "type": "keyword" },
      "company": { "type": "text", "fields": { "keyword": { "type": "keyword" } } },
      "isDel": { "type": "boolean" },
      "createTime": { "type": "date", "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis" },
      "updateTime": { "type": "date", "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis" }
    }
  }
}' 2>/dev/null
echo ""

# 同步默认用户数据（与 init-database.sql 保持一致）
echo "同步用户数据..."
curl -s -X POST "http://$ES_HOST/sys_user/_doc/1" -H "Content-Type: application/json" -d '{
  "id": 1, "username": "admin", "nickname": "管理员", "mobile": "18888888888",
  "sex": 0, "enabled": true, "type": "BACKEND", "company": "ENGJ",
  "isDel": false,
  "createTime": "2017-11-17 16:56:59", "updateTime": "2019-01-08 17:05:47"
}' > /dev/null

curl -s -X POST "http://$ES_HOST/sys_user/_doc/2" -H "Content-Type: application/json" -d '{
  "id": 2, "username": "user", "nickname": "体验用户", "mobile": "18888888887",
  "sex": 1, "enabled": true, "type": "BACKEND", "company": "ENGJ",
  "isDel": false,
  "createTime": "2017-11-17 16:56:59"
}' > /dev/null

curl -s -X POST "http://$ES_HOST/sys_user/_doc/3" -H "Content-Type: application/json" -d '{
  "id": 3, "username": "test", "nickname": "测试账户", "mobile": "13851539156",
  "sex": 0, "enabled": true, "type": "BACKEND", "company": "ENGJ",
  "isDel": false,
  "createTime": "2017-11-17 16:56:59", "updateTime": "2018-09-07 03:27:40"
}' > /dev/null

curl -s -X POST "http://$ES_HOST/sys_user/_refresh" > /dev/null
echo "用户数据同步完成!"
fi
# ====== sys_user 初始化结束 ======

echo ""
echo "======================================"
echo "初始化完成! 索引列表:"
echo "======================================"
curl -s "http://$ES_HOST/_cat/indices?v"
echo ""
echo "测试 API:"
echo "  访问统计: curl http://localhost:8080/api-log/requestStat"
echo "  用户搜索: curl http://localhost:8080/api-user/users/search?queryStr=admin"

