package com.testcar.car.infra.kakao;


import com.testcar.car.common.util.RestTemplateHandler;
import com.testcar.car.infra.kakao.model.KakaoGeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoGeocodingService {
    private static final String KAKAO_GEOCODING_URL =
            "https://dapi.kakao.com/v2/local/search/address.json";
    private final KakaoProperty kakaoProperty;
    private final RestTemplateHandler restTemplateHandler;

    public KakaoGeocodingResponse geocoding(String address) {
        final String apiKey = kakaoProperty.getApiKey();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        final String url =
                UriComponentsBuilder.fromHttpUrl(KAKAO_GEOCODING_URL)
                        .queryParam("query", address)
                        .build()
                        .toUriString();
        return restTemplateHandler.get(url, headers, KakaoGeocodingResponse.class);
    }
}
