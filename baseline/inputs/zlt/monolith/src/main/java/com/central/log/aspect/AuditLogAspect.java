package com.central.log.aspect;

import com.central.log.annotation.AuditLog;
import com.central.log.model.Audit;
import com.central.log.properties.AuditLogProperties;
import com.central.log.service.IAuditService;
import com.central.security.LoginAppUser;
import com.central.security.LoginUserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 审计日志切面
 *
 * @author zlt
 * @date 2020/2/3
 */
@Slf4j
@Aspect
@Component
public class AuditLogAspect {
    @Value("${spring.application.name}")
    private String applicationName;

    private final AuditLogProperties auditLogProperties;
    private final IAuditService auditService;

    public AuditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        this.auditLogProperties = auditLogProperties;
        this.auditService = auditService;
    }

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Before("@within(auditLog) || @annotation(auditLog)")
    public void beforeMethod(JoinPoint joinPoint, AuditLog auditLog) {
        if (auditLogProperties.getEnabled()) {
            if (auditService == null) {
                log.warn("AuditLogAspect - auditService is null");
                return;
            }
            if (auditLog == null) {
                auditLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AuditLog.class);
            }
            Audit audit = getAudit(auditLog, joinPoint);
            auditService.save(audit);
        }
    }

    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }

    private Audit getAudit(AuditLog auditLog, JoinPoint joinPoint) {
        Audit audit = new Audit();
        audit.setTimestamp(LocalDateTime.now());
        audit.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        audit.setClassName(methodSignature.getDeclaringTypeName());
        audit.setMethodName(methodSignature.getName());

        String userId = "";
        String userName = "";
        String clientId = "webApp";
        try {
            LoginAppUser user = LoginUserContextHolder.getUser();
            if (user != null) {
                userId = user.getId() != null ? user.getId().toString() : "";
                userName = user.getUsername() != null ? user.getUsername() : "";
            }
        } catch (Exception ignored) {
        }
        audit.setUserId(userId);
        audit.setUserName(userName);
        audit.setClientId(clientId);

        String operation = auditLog.operation();
        if (operation.contains("#")) {
            Object[] args = joinPoint.getArgs();
            operation = getValBySpEL(operation, methodSignature, args);
        }
        audit.setOperation(operation);

        return audit;
    }
}
