package com.central.constant;

/**
 * Security 权限常量
 *
 * @author zlt
 */
public interface SecurityConstants {
    String PROJECT_LICENSE = "http://zlt2000.gitee.io/";

    /**
     * 用户信息分隔符
     */
    String USER_SPLIT = ":";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userid-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 密码模式
     */
    String PASSWORD = "password";

    /**
     * 刷新token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * oauth token
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validata/code";

    /**
     * 手机号的处理验证码的url前缀
     */
    String MOBILE_VALIDATE_CODE_URL_PREFIX = "/validata/smsCode";

    /**
     * 默认生成图形验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * 默认保存code的前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";

    /**
     * zlt公共前缀
     */
    String ZLT_PREFIX = "zlt:";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";

    /**
     * 登录页面
     */
    String LOGIN_PAGE = "/login.html";

    /**
     * 登录失败页面
     */
    String LOGIN_FAILURE_PAGE = LOGIN_PAGE + "?error";

    /**
     * 登出URL
     */
    String LOGOUT_URL = "/oauth/remove/token";

    /**
     * 默认token过期时间(1小时)
     */
    Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;

    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";

    /**
     * 默认账号类型
     */
    String DEF_ACCOUNT_TYPE = "admin";

    /**
     * 账号类型参数名
     */
    String ACCOUNT_TYPE_PARAM_NAME = "account_type";

    /**
     * 客户端ID
     */
    String CLIENT_ID = "client_id";

    /**
     * 用户信息
     */
    String DETAILS_USER = "user_info";

    /**
     * 用户ID
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名
     */
    String USERNAME = "username";

    /**
     * jwk set缓存前缀
     */
    String AUTHORIZATION_JWS_PREFIX_KEY = "authorization_jws";
}





