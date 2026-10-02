package com.central.log.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.Setter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Elasticsearch HTTP Appender
 * 直接通过 HTTP 将日志写入 Elasticsearch。
 * 本 appender 设计为被 AsyncAppender 包裹使用，append 方法同步发送，
 * 由外层 AsyncAppender 负责异步调度，避免多层异步导致的线程生命周期问题。
 *
 * @author zlt
 */
@Setter
public class ElasticsearchAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String esUrl = "http://127.0.0.1:9200";
    private String indexPattern = "sys-log-%d{yyyy.MM.dd}";
    private String appName = "unknown";
    private String serverIp = "0.0.0.0";
    private String serverPort = "8080";

    private static final String AUDIT_LOGGER_NAME = "com.central.log.service.impl.LoggerAuditServiceImpl";
    private static final int AUDIT_FIELD_COUNT = 8;

    @Override
    protected void append(ILoggingEvent event) {
        try {
            String json = buildJson(event);
            sendToEs(json);
        } catch (Exception e) {
            addError("Failed to send log to ES", e);
        }
    }

    private String buildJson(ILoggingEvent event) {
        if (AUDIT_LOGGER_NAME.equals(event.getLoggerName())) {
            return buildAuditJson(event);
        }
        return buildGenericJson(event);
    }

    /**
     * 审计日志：将管道分隔的 message 解析为结构化字段
     * 格式：{时间}|{应用名}|{类名}|{方法名}|{用户id}|{用户名}|{租户id}|{操作信息}
     */
    private String buildAuditJson(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        String[] parts = message.split("\\|", AUDIT_FIELD_COUNT);

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sb.append("\"timestamp\":\"").append(sdf.format(new Date(event.getTimeStamp()))).append("\"");

        if (parts.length >= AUDIT_FIELD_COUNT) {
            sb.append(",\"applicationName\":\"").append(escapeJson(parts[1])).append("\"");
            sb.append(",\"className\":\"").append(escapeJson(parts[2])).append("\"");
            sb.append(",\"methodName\":\"").append(escapeJson(parts[3])).append("\"");
            sb.append(",\"userId\":\"").append(escapeJson(parts[4])).append("\"");
            sb.append(",\"userName\":\"").append(escapeJson(parts[5])).append("\"");
            sb.append(",\"clientId\":\"").append(escapeJson(parts[6])).append("\"");
            sb.append(",\"operation\":\"").append(escapeJson(parts[7])).append("\"");
        } else {
            sb.append(",\"message\":\"").append(escapeJson(message)).append("\"");
        }

        sb.append("}");
        return sb.toString();
    }

    private String buildGenericJson(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sb.append("\"timestamp\":\"").append(sdf.format(new Date(event.getTimeStamp()))).append("\",");

        sb.append("\"logLevel\":\"").append(event.getLevel().toString()).append("\",");
        sb.append("\"message\":\"").append(escapeJson(event.getFormattedMessage())).append("\",");
        sb.append("\"classname\":\"").append(event.getLoggerName()).append("\",");
        sb.append("\"threadName\":\"").append(escapeJson(event.getThreadName())).append("\",");
        sb.append("\"appName\":\"").append(appName).append("\",");
        sb.append("\"serverIp\":\"").append(serverIp).append("\",");
        sb.append("\"serverPort\":\"").append(serverPort).append("\",");

        String traceId = event.getMDCPropertyMap().get("traceId");
        String spanId = event.getMDCPropertyMap().get("spanId");
        sb.append("\"traceId\":\"").append(traceId != null ? traceId : "").append("\",");
        sb.append("\"spanId\":\"").append(spanId != null ? spanId : "").append("\"");

        IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy != null) {
            String stackTrace = ThrowableProxyUtil.asString(throwableProxy);
            sb.append(",\"stackTrace\":\"").append(escapeJson(stackTrace)).append("\"");
        }

        sb.append("}");
        return sb.toString();
    }

    private void sendToEs(String json) {
        HttpURLConnection conn = null;
        try {
            String baseUrl = esUrl;
            if (!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://")) {
                baseUrl = "http://" + baseUrl;
            }
            String indexName = getIndexName();
            String urlStr = baseUrl + "/" + indexName + "/_doc";

            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                addWarn("ES returned status: " + responseCode);
            }
        } catch (Exception e) {
            // 静默处理，避免日志风暴
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String getIndexName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String datePart = sdf.format(new Date());
        if (indexPattern != null && !indexPattern.isEmpty()) {
            return indexPattern.replaceAll("%d\\{[^}]*}", datePart);
        }
        return "sys-log-" + datePart;
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

