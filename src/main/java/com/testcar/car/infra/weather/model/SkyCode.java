package com.testcar.car.infra.weather.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/** SKY 값의 응답 값 파싱 */
@Getter
@AllArgsConstructor
public enum SkyCode {
    SUNNY(1, "맑음"),
    LITTLE_CLOUDY(2, "구름 조금"),
    CLOUDY(3, "구름 많음"),
    CLOUDY_RAIN(4, "흐림"),
    ;
    private final int code;
    private final String description;

    public static SkyCode of(int code) {
        for (SkyCode skyCode : SkyCode.values()) {
            if (skyCode.getCode() == code) {
                return skyCode;
            }
        }
        return SUNNY;
    }
}
