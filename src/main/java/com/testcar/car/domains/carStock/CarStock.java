package com.testcar.car.domains.carStock;

import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.car.Car;
import com.testcar.car.domains.member.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "CarStock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarStock extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 차량
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId", nullable = false)
    private Car car;

    // 재고번호
    @Column(nullable = false, length = 12, unique = true)
    private String stockNumber;

    // 재고상태
    @Column(nullable = false)
    private StockStatus status;

    @Builder
    public CarStock(Car car, String stockNumber, StockStatus status) {
        this.car = car;
        this.stockNumber = stockNumber;
        this.status = status;
    }
}
