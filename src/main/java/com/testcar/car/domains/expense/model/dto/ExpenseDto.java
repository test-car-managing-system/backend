package com.testcar.car.domains.expense.model.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.testcar.car.domains.expense.entity.Expense;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ExpenseDto {
    private final Expense expense;
    private final Long id;
    private final String memberName;
    private final String updateMemberName;
    private final String departmentName;
    private final String carName;
    private final String stockNumber;
    private final String description;
    private final LocalDate usedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long amount;

    @QueryProjection
    public ExpenseDto(
            Expense expense,
            String memberName,
            String updateMemberName,
            String carName,
            String stockNumber,
            String departmentName) {
        this.expense = expense;
        this.id = expense.getId();
        this.memberName = memberName;
        this.updateMemberName = updateMemberName;
        this.carName = carName;
        this.stockNumber = stockNumber;
        this.departmentName = departmentName;
        this.description = expense.getDescription();
        this.usedAt = expense.getUsedAt();
        this.createdAt = expense.getCreatedAt();
        this.updatedAt = expense.getUpdatedAt();
        this.amount = expense.getAmount();
    }
}
