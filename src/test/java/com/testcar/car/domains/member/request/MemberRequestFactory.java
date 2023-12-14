package com.testcar.car.domains.member.request;

import static com.testcar.car.common.Constant.ANOTHER_MEMBER_EMAIL;
import static com.testcar.car.common.Constant.ANOTHER_MEMBER_NAME;
import static com.testcar.car.common.Constant.MEMBER_EMAIL;
import static com.testcar.car.common.Constant.MEMBER_NAME;
import static com.testcar.car.common.Constant.MEMBER_PASSWORD;

import com.testcar.car.domains.member.entity.Role;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import com.testcar.car.domains.member.model.UpdateMemberRequest;

public class MemberRequestFactory {
    private MemberRequestFactory() {}

    public static RegisterMemberRequest createMemberRequest() {
        return RegisterMemberRequest.builder()
                .departmentId(1L)
                .name(MEMBER_NAME)
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .role(Role.ADMIN)
                .build();
    }

    public static UpdateMemberRequest createUpdateMemberRequest() {
        return UpdateMemberRequest.builder()
                .departmentId(2L)
                .name(ANOTHER_MEMBER_NAME)
                .email(ANOTHER_MEMBER_EMAIL)
                .role(Role.ADMIN)
                .build();
    }

    public static UpdateMemberRequest createInvalidUpdateMemberRequest() {
        return UpdateMemberRequest.builder()
                .departmentId(2L)
                .name(ANOTHER_MEMBER_NAME)
                .email(MEMBER_EMAIL)
                .role(Role.ADMIN)
                .build();
    }
}
