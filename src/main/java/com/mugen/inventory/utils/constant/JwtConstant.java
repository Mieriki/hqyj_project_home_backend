package com.mugen.inventory.utils.constant;

/**
 * JWT token 相关常量
 */
public class JwtConstant {
    // JWT token 黑名单 key
    public static final String JWT_BLACK_LIST = "jwt:blacklist:";

    // JWT 强制退出 key
    public static final String JWT_FORCE_LOGOUT = "jwt:force_logout:";

    // JWT token 请求头 key
    public static final String JWT_TOKEN_HEADER = "Authorization";
}
