package com.testcar.car.infra.weather.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastApiResponse {
    @Schema(description = "응답")
    private Response response;

    @Getter
    @NoArgsConstructor
    public static class Response {
        @Schema(description = "응답 헤더")
        private Header header;

        @Schema(description = "응답 바디")
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    public static class Header {
        @Schema(description = "결과 코드", example = "00")
        private String resultCode;

        @Schema(description = "결과 메시지", example = "NORMAL_SERVICE")
        @JsonProperty("resultMsg")
        private String resultMessage;
    }

    @Getter
    @NoArgsConstructor
    public static class Body {
        @Schema(description = "데이터 유형", example = "JSON")
        private String dataType;

        @Schema(description = "날씨 데이터")
        private Items items;
    }
}
