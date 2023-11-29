package com.testcar.car.domains.carReservation;

import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.car.Car;
import com.testcar.car.domains.carStock.CarStock;
import com.testcar.car.domains.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "CarReservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarReservation extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    // 차량재고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carStockId", nullable = false)
    private CarStock carStock;

    // 대여시각
    @Column(nullable = false)
    private LocalDateTime reservedAt;

    // 대여상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder
    public CarReservation(Member member, CarStock carStock, LocalDateTime reservedAt, ReservationStatus status) {
        this.member = member;
        this.carStock = carStock;
        this.reservedAt = reservedAt;
        this.status = status;
    }
}
