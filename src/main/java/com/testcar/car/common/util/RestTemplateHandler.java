package com.testcar.car.common.util;


import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateHandler {
    private final RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public <T> T get(URI uri, HttpHeaders headers, Class<T> responseType) {
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // ResponseEntity로부터 바로 body를 반환하고 예외 처리
        final ResponseEntity<T> responseEntity =
                restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, responseType);
        return Optional.ofNullable(responseEntity.getBody()).orElseThrow();
    }
}
