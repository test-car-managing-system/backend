package com.testcar.car.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** Jpa 엔티티 관련 변경감지를 활성화하는 설정 빈 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
