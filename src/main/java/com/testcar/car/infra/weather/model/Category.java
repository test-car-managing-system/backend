package com.testcar.car.infra.weather.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    POP("강수확률"),
    PTY("강수형태"),
    PCP("1시간 강수량"),
    REH("습도"),
    SNO("1시간 적설량"),
    SKY("하늘상태"),
    TMP("1시간 기온"),
    TMN("일 최저기온"),
    TMX("일 최고기온"),
    UUU("풍속(동서성분)"),
    VVV("풍속(남북성분)"),
    WAV("파고"),
    VEC("풍향"),
    WSD("풍속"),
    ;
    private final String description;
}
