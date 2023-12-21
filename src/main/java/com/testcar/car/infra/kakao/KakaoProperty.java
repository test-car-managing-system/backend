package com.testcar.car.infra.kakao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperty {
    private String apiKey;
}
