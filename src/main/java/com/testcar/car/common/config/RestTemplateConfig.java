package com.testcar.car.common.config;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // HTTP get,post 요청을 날릴때 일정한 형식에 맞춰주는 template
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .requestFactory(
                        () ->
                                new BufferingClientHttpRequestFactory(
                                        new SimpleClientHttpRequestFactory()))
                .additionalMessageConverters(new StringHttpMessageConverter(UTF_8))
                .build();
    }
}
