package com.xwwx.douyin.common.core.constant;

/**
 * @author: 可乐罐
 * @date: 2021/12/28 20:16
 * @description:token常量
 */
public class TokenConstants {
    /**
     * 令牌自定义标识
     */
    public static final String TOKEN = "token";
    /**
     * system令牌前缀
     */
    public static final String SYSTEM_PREFIX = "system-token:";
    /**
     * app令牌前缀
     */
    public static final String APP_PREFIX = "app-token:";
    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz123456";
    /**
     * 用户标识
     */
    public static final String USER_TYPE = "user_type";

    /**
     * token
     */
    public static final String TOKEN_USER = "token_user";

    /**
     * token有效时长10天
     */
    public static final Long TOKEN_TIME = 10 * 24 * 60 * 60 * 1000L;
}
