package com.testcar.car.common;


import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.member.Role;
import java.time.LocalDateTime;

public class Constant {
    private Constant() {}

    /** Member */
    public static final String MEMBER_EMAIL = "test@test.com";

    public static final String MEMBER_PASSWORD = "1234abcd@";
    public static final String MEMBER_NAME = "홍길동";
    public static final Role MEMBER_ROLE = Role.ADMIN;
    public static final String DEPARTMENT_NAME = "모비스시스템팀";

    /** Car */
    public static final String CAR_NAME = "아반떼";

    public static final String ANOTHER_CAR_NAME = "소나타";
    public static final double CAR_DISPLACEMENT = 1.6;
    public static final Type CAR_TYPE = Type.SEDAN;
    public static final String CAR_STOCK_NUMBER = "123456789012";
    public static final String ANOTHER_CAR_STOCK_NUMBER = "987654321098";
    public static final LocalDateTime STARTED_AT = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
    public static final LocalDateTime EXPIRED_AT = LocalDateTime.of(2021, 1, 8, 0, 0, 0);
    public static final String CAR_TEST_RESULT = "통과";

    /** Track */
    public static final String TRACK_NAME = "고속주행로";

    public static final String ANOTHER_TRACK_NAME = "경사로";
    public static final String TRACK_LOCATION = "서산주행시험장";
    public static final String TRACK_DESCRIPTION = "비탈길";
    public static final double TRACK_LENGTH = 1230.6;
}
