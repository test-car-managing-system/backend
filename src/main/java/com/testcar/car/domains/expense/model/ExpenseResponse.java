package com.testcar.car.domains.expense.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.expense.entity.Expense;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpenseResponse {
    @Schema(description = "지출 ID", example = "1")
    private final Long id;

    @Schema(description = "사용자명", example = "홍길동")
    private final String memberName;

    @Schema(description = "소속부서", example = "시스템관리팀")
    private final String departmentName;

    @Schema(description = "차량명", example = "아반떼")
    private final String carName;

    @Schema(description = "재고번호", example = "2023010100001")
    private final String stockNumber;

    @Schema(description = "지출내용", example = "차량수선비")
    private final String description;

    @DateFormat
    @Schema(description = "지출일자", example = "2021-01-01")
    private final LocalDate usedAt;

    @Schema(description = "금액", example = "30000")
    private final Long amount;

    @DateTimeFormat
    @Schema(description = "등록일시", example = "2021-01-01 12:00:01")
    private final LocalDateTime createdAt;

    @DateTimeFormat
    @Schema(description = "수정일시", example = "2021-01-01 12:00:01")
    private final LocalDateTime updatedAt;

    @Schema(description = "수정인", example = "홍동길")
    private final String updateMemberName;

    public static ExpenseResponse from(ExpenseDto expenseDto) {
        return ExpenseResponse.builder()
                .id(expenseDto.getId())
                .memberName(expenseDto.getMemberName())
                .carName(expenseDto.getCarName())
                .stockNumber(expenseDto.getStockNumber())
                .description(expenseDto.getDescription())
                .departmentName(expenseDto.getDepartmentName())
                .usedAt(expenseDto.getUsedAt())
                .amount(expenseDto.getAmount())
                .createdAt(expenseDto.getCreatedAt())
                .updatedAt(expenseDto.getUpdatedAt())
                .updateMemberName(expenseDto.getUpdateMemberName())
                .build();
    }

    public static ExpenseResponse from(Expense expense) {
        final Member member = expense.getMember();
        final Member updateMember = expense.getUpdateMember();
        final Department department = member.getDepartment();
        final Optional<CarStock> carStock = Optional.ofNullable(expense.getCarStock());
        return ExpenseResponse.builder()
                .id(expense.getId())
                .memberName(member.getName())
                .carName(carStock.map(CarStock::getCar).map(Car::getName).orElse(null))
                .stockNumber(carStock.map(CarStock::getStockNumber).orElse(null))
                .description(expense.getDescription())
                .departmentName(department.getName())
                .usedAt(expense.getUsedAt())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .updateMemberName(updateMember.getName())
                .build();
    }
}
