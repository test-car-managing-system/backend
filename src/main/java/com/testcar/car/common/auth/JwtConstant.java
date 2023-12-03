package com.testcar.car.common.auth;

public class JwtConstant {
    private JwtConstant() {}

    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7; // 7일
    public static final String CLAIM_KEY = "userId";
    public static final String HEADER_KEY = "type";
    public static final String HEADER_VALUE = "user";
}
