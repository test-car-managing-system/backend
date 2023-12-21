package com.testcar.car.common.util;


import com.testcar.car.common.exception.ErrorCode;
import com.testcar.car.common.exception.InternalServerException;
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
    public <T> T get(String url, HttpHeaders headers, Class<T> responseType) {
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        // ResponseEntity로부터 바로 body를 반환하고 예외 처리
        final ResponseEntity<T> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    public <T> T get(URI uri, Class<T> responseType) {
        // ResponseEntity로부터 바로 body를 반환하고 예외 처리
        final ResponseEntity<T> responseEntity = restTemplate.getForEntity(uri, responseType);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
