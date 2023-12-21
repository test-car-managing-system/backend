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
public class WeatherRequest {
    @Schema(description = "x축", example = "37")
    private Integer x;

    @Schema(description = "y축", example = "126")
    private Integer y;
}
