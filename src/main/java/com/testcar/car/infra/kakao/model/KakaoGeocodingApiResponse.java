package com.testcar.car.infra.kakao.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoGeocodingApiResponse {
    private List<Document> documents;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {
        @JsonProperty("address_name")
        private String addressName;

        private Double x;
        private Double y;
    }
}
