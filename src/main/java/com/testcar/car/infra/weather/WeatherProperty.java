package com.testcar.car.infra.weather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "weather")
public class WeatherProperty {
    private String apiKey;
}
