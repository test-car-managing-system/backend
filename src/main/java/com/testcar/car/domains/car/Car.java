package com.testcar.car.domains.car;


import com.testcar.car.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Car")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 차량명
    @Column(length = 50, nullable = false)
    private String name;

    // 출시일자
    @Column(nullable = false)
    private LocalDateTime releasedAt;

    // 차종
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public Car(String name, LocalDateTime releasedAt, Type type) {
        this.name = name;
        this.releasedAt = releasedAt;
        this.type = type;
    }
}
