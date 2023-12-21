package com.testcar.car.infra.weather.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    @Schema(description = "날씨", example = "맑음")
    private String weather;

    @Schema(description = "기온", example = "10")
    private Integer temperature;
}
