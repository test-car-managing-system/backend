package com.testcar.car.infra.kakao.model;


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
public class KakaoGeocodingResponse {
    private String addressName;
    private Double x;
    private Double y;

    public static KakaoGeocodingResponse empty() {
        return new KakaoGeocodingResponse();
    }

    public static KakaoGeocodingResponse from(KakaoGeocodingApiResponse response) {
        return response.getDocuments().stream()
                .findFirst()
                .map(KakaoGeocodingResponse::from)
                .orElse(KakaoGeocodingResponse.empty());
    }

    private static KakaoGeocodingResponse from(KakaoGeocodingApiResponse.Document document) {
        return KakaoGeocodingResponse.builder()
                .addressName(document.getAddressName())
                .x(document.getX())
                .y(document.getY())
                .build();
    }
}
