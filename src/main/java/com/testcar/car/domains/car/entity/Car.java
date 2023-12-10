package com.testcar.car.domains.car.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.carStock.entity.CarStock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    // 배기량
    @Column(nullable = false)
    private Double displacement;

    // 차종
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    // 차량 재고
    @OneToMany(mappedBy = "car")
    private final List<CarStock> carStocks = new ArrayList<>();

    @Builder
    public Car(String name, Double displacement, Type type) {
        this.name = name;
        this.displacement = displacement;
        this.type = type;
    }

    public Car update(Car car) {
        this.name = car.getName();
        this.displacement = car.getDisplacement();
        this.type = car.getType();
        return this;
    }
}
