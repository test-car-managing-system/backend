package com.testcar.car.infra.weather.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/** PTY 값의 응답 값 파싱 */
@Getter
@AllArgsConstructor
public enum RainCode {
    NO_RAIN(0, "없음"),
    RAIN(1, "비"),
    RAIN_SNOW(2, "비/눈"),
    SNOW(3, "눈"),
    SHOWER(4, "소나기"),
    RAIN_SHOWER(5, "빗방울"),
    RAIN_SNOW_SHOWER(6, "빗방울떨어짐/눈날림"),
    SNOW_SHOWER(7, "눈날림"),
    ;
    private final int code;
    private final String description;

    public static RainCode of(int code) {
        for (RainCode rainCode : RainCode.values()) {
            if (rainCode.getCode() == code) {
                return rainCode;
            }
        }
        return NO_RAIN;
    }
}
