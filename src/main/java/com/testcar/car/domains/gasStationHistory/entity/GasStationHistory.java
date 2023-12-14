package com.testcar.car.domains.gasStationHistory.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "GasStationHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GasStationHistory extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    // 수정인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updateMemberId", nullable = false)
    private Member updateMember;

    // 주유소
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gasStationId", nullable = true)
    private GasStation gasStation;

    // 차량재고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carStockId", nullable = false)
    private CarStock carStock;

    // 주유량
    @Column(nullable = false)
    private Double amount;

    // 주유일시
    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Builder
    public GasStationHistory(
            Member member,
            GasStation gasStation,
            CarStock carStock,
            Double amount,
            LocalDateTime usedAt) {
        this.member = member;
        this.updateMember = member;
        this.gasStation = gasStation;
        this.carStock = carStock;
        this.amount = amount;
        this.usedAt = usedAt;
    }

    public void updateGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public void updateCarStock(CarStock carStock) {
        this.carStock = carStock;
    }

    public void updateMemberBy(Member updateMember) {
        this.updateMember = updateMember;
    }

    public void update(Double amount, LocalDateTime usedAt) {
        this.amount = amount;
        this.usedAt = usedAt;
    }
}
