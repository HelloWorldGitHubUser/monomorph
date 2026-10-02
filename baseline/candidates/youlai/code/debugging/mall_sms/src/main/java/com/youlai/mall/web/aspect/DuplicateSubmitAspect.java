package com.youlai.mall.web.aspect;

import cn.hutool.core.util.StrUtil;
import com.youlai.mall.result.ResultCode;
import com.youlai.mall.security.util.SecurityUtils;
import com.youlai.mall.web.annotation.PreventDuplicateResubmit;
import com.youlai.mall.web.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 防重复提交切面
 *
 * @author haoxr
 * @since 2023/05/09
 */
@Aspect
@Component
@Slf4j
public class DuplicateSubmitAspect {

    private final ConcurrentHashMap<String, Long> submitRecordMap = new ConcurrentHashMap<>();

    private static final String RESUBMIT_LOCK_PREFIX = "LOCK:RESUBMIT:";

    /**
     * 防重复提交切点
     */
    @Pointcut("@annotation(preventDuplicateResubmit)")
    public void preventDuplicateSubmitPointCut(PreventDuplicateResubmit preventDuplicateResubmit) {
        log.info("定义防重复提交切点");
    }

    @Around("preventDuplicateSubmitPointCut(preventDuplicateResubmit)")
    public Object doAround(ProceedingJoinPoint pjp, PreventDuplicateResubmit preventDuplicateResubmit) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String jti = SecurityUtils.getJti();
        if (StrUtil.isNotBlank(jti)) {
            String resubmitLockKey = RESUBMIT_LOCK_PREFIX + jti + ":" + request.getMethod() + "-" + request.getRequestURI();
            int expire = preventDuplicateResubmit.expire(); // 防重提交锁过期时间（秒）
            long now = System.currentTimeMillis();
            long expireTime = now + expire * 1000L;
            
            // 使用compute()保证原子性
            Long existingExpireTime = submitRecordMap.compute(resubmitLockKey, (k, oldTime) -> {
                if (oldTime != null && oldTime > now) {
                    return oldTime; // 时间窗口内，保持旧值
                }
                return expireTime; // 记录新的提交时间
            });
            
            // 判断是否重复提交
            if (existingExpireTime != null && !existingExpireTime.equals(expireTime)) {
                throw new BizException(ResultCode.REPEAT_SUBMIT_ERROR); // 抛出重复提交提示信息
            }
        }
        return pjp.proceed();
    }

    /**
     * 定期清理过期记录（防止内存泄漏）
     */
    @Scheduled(fixedRate = 60000)
    public void cleanExpiredRecords() {
        long now = System.currentTimeMillis();
        submitRecordMap.entrySet().removeIf(entry -> entry.getValue() < now);
    }

}

