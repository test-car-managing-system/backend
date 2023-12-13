package com.testcar.car.common;


import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.member.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Constant {
    private Constant() {}

    /** Date */
    public static final LocalDateTime NOW =
            LocalDateTime.now().withHour(12).withMinute(0).withSecond(0).withNano(0);

    public static final LocalDateTime TOMORROW = NOW.plusDays(1L);
    public static final LocalDateTime YESTERDAY = NOW.minusDays(1L);

    /** Member */
    public static final String MEMBER_NAME = "홍길동";

    public static final String ANOTHER_MEMBER_NAME = "동길홍";
    public static final String MEMBER_EMAIL = "test@test.com";
    public static final String ANOTHER_MEMBER_EMAIL = "car@car.com";
    public static final String MEMBER_PASSWORD = "1234abcd@";
    public static final Role MEMBER_ROLE = Role.ADMIN;
    public static final String DEPARTMENT_NAME = "모비스시스템팀";

    /** Car */
    public static final String CAR_NAME = "아반떼";

    public static final String ANOTHER_CAR_NAME = "소나타";
    public static final double CAR_DISPLACEMENT = 1.6;
    public static final Type CAR_TYPE = Type.SEDAN;
    public static final String CAR_STOCK_NUMBER = "123456789012";
    public static final String ANOTHER_CAR_STOCK_NUMBER = "987654321098";
    public static final LocalDateTime STARTED_AT = NOW;
    public static final LocalDateTime EXPIRED_AT = NOW.plusDays(7L);
    public static final String CAR_TEST_RESULT = "통과";

    /** Track */
    public static final String TRACK_NAME = "고속주행로";

    public static final String ANOTHER_TRACK_NAME = "경사로";
    public static final String TRACK_LOCATION = "서산주행시험장";
    public static final String TRACK_DESCRIPTION = "비탈길";
    public static final double TRACK_LENGTH = 1230.6;
    public static final LocalDate TRACK_RESERVATION_DATE = TOMORROW.toLocalDate();
    public static final LocalDateTime TRACK_RESERVATION_SLOT_STARTED_AT = TOMORROW.withHour(11);
    public static final LocalDateTime TRACK_RESERVATION_SLOT_EXPIRED_AT = TOMORROW.withHour(12);

    /** Gas Station */
    public static final String GAS_STATION_NAME = "서산주유소A";

    public static final String ANOTHER_GAS_STATION_NAME = "아무주유소B";
}
