package com.testcar.car.common.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/** application.yml 환경변수를 바인딩하여 등록하는 설정 빈 */
@Configuration
@ConfigurationPropertiesScan(basePackages = "com.testcar.car")
public class ConfigurationPropertiesConfig {}
