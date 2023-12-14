package com.testcar.car.domains.auth.request;

import static com.testcar.car.common.Constant.ANOTHER_MEMBER_PASSWORD;
import static com.testcar.car.common.Constant.MEMBER_EMAIL;
import static com.testcar.car.common.Constant.MEMBER_PASSWORD;

import com.testcar.car.domains.auth.model.LoginRequest;

public class AuthRequestFactory {
    private AuthRequestFactory() {}

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder().email(MEMBER_EMAIL).password(MEMBER_PASSWORD).build();
    }

    public static LoginRequest createInvalidLoginRequest() {
        return LoginRequest.builder().email(MEMBER_EMAIL).password(ANOTHER_MEMBER_PASSWORD).build();
    }
}
