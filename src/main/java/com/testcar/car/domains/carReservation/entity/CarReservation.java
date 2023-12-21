package com.testcar.car.domains.carReservation.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.member.entity.Member;
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
import lombok.ToString;

@Getter
@ToString
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

    // 대여 시각
    @Column(nullable = false)
    private LocalDateTime startedAt;

    // 대여 만료시각
    @Column(nullable = false)
    private LocalDateTime expiredAt;

    // 대여상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder
    public CarReservation(
            Member member,
            CarStock carStock,
            LocalDateTime startedAt,
            LocalDateTime expiredAt,
            ReservationStatus status) {
        this.member = member;
        this.carStock = carStock;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.status = status;
    }

    public void updateReturn() {
        this.status = ReservationStatus.RETURNED;
        this.expiredAt = LocalDateTime.now();
    }
}
