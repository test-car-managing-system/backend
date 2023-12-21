package com.testcar.car.infra.weather.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    @Schema(description = "날씨", example = "맑음")
    private String weather;

    @Schema(description = "기온", example = "10")
    private Integer temperature;
}
