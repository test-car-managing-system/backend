package com.testcar.car.domains.carTest.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
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
@Table(name = "CarTest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarTest extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    // 시험장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackId", nullable = false)
    private Track track;

    // 차량재고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carStockId", nullable = false)
    private CarStock carStock;

    // 수행시간
    @Column(nullable = false)
    private LocalDateTime performedAt;

    // 수행결과
    @Column(nullable = false, columnDefinition = "TEXT")
    private String result;

    // 비고
    @Column(columnDefinition = "TEXT")
    private String memo;

    @Builder
    public CarTest(
            Member member,
            Track track,
            CarStock carStock,
            LocalDateTime performedAt,
            String result,
            String memo) {
        this.member = member;
        this.track = track;
        this.carStock = carStock;
        this.performedAt = performedAt;
        this.result = result;
        this.memo = memo;
    }
}
