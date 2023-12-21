package com.testcar.car.infra.weather.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    @Schema(description = "예보 데이터")
    private List<Item> item;

    @Getter
    @ToString
    @NoArgsConstructor
    public static class Item {
        @Schema(description = "일자")
        private String baseDate;

        @Schema(description = "시간")
        private String baseTime;

        @Schema(description = "예보 타입", implementation = Category.class)
        @JsonProperty("category")
        private Category category;

        @Schema(description = "예보 일자")
        @JsonProperty("fcstDate")
        private String forecastDate;

        @Schema(description = "예보 시간")
        @JsonProperty("fcstTime")
        private String forecastTime;

        @Schema(description = "예보 값")
        @JsonProperty("fcstValue")
        private String forecastValue;

        @Schema(description = "예보 x좌표")
        @JsonProperty("nx")
        private String x;

        @Schema(description = "예보 y좌표")
        @JsonProperty("ny")
        private String y;
    }
}
