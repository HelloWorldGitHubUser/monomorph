package io.gulimall.interceptor;

import io.gulimall.constant.AuthServerConstant;
import io.gulimall.security.LoginRequired;
import io.gulimall.vo.MemberResponseVo;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * 登录拦截器，统一保护购物车、订单、会员等需要登录的接口
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final List<String> SAFE_PATHS = Arrays.asList(
            "/login**",
            "/register",
            "/payed/**",
            "/order/order/infoByOrderSn/**",
            "/error",
            "/actuator/**",
            "/static/**",
            "/css/**",
            "/js/**",
            "/favicon.ico"
    );
    private static final List<String> PROTECTED_PATHS = Arrays.asList(
            "/cart/**",
            "/order/**",
            "/member/**",
            "/ware/wareinfo/fare/**",
            "/ware/waresku/**",
            "/coupon/**"
    );

    public static ThreadLocal<MemberResponseVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (isWhitelisted(requestURI)) {
            return true;
        }
        boolean needLogin = requiresLogin(handler, requestURI);
        if (!needLogin) {
            return true;
        }

        HttpSession session = request.getSession();
        MemberResponseVo memberResponseVo = (MemberResponseVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (memberResponseVo != null) {
            loginUser.set(memberResponseVo);
            return true;
        } else {
            session.setAttribute("msg", "请先登录");
            response.sendRedirect("/login.html");
            return false;
        }
    }

    private boolean isWhitelisted(String requestURI) {
        for (String pattern : SAFE_PATHS) {
            if (PATH_MATCHER.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresLogin(Object handler, String requestURI) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired methodAnno = handlerMethod.getMethodAnnotation(LoginRequired.class);
            LoginRequired classAnno = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
            if (methodAnno != null || classAnno != null) {
                return true;
            }
        }
        for (String pattern : PROTECTED_PATHS) {
            if (PATH_MATCHER.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        loginUser.remove();
    }
}
