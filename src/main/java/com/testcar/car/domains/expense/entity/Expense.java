package com.testcar.car.domains.expense.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.carStock.entity.CarStock;
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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Expense")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseEntity {
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

    // 차량재고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carStockId", nullable = true)
    private CarStock carStock;

    // 지출 내용
    @Column(nullable = false)
    private String description;

    // 지출 금액
    @Column(nullable = false)
    private Long amount;

    // 지출일시
    @Column(nullable = false)
    private LocalDate usedAt;

    @Builder
    public Expense(
            Member member, CarStock carStock, String description, Long amount, LocalDate usedAt) {
        this.member = member;
        this.updateMember = member;
        this.carStock = carStock;
        this.description = description;
        this.amount = amount;
        this.usedAt = usedAt;
    }

    public void updateCarStock(CarStock carStock) {
        this.carStock = carStock;
    }

    public void updateMemberBy(Member updateMember) {
        this.updateMember = updateMember;
    }

    public void update(String description, Long amount, LocalDate usedAt) {
        this.description = description;
        this.amount = amount;
        this.usedAt = usedAt;
    }
}
