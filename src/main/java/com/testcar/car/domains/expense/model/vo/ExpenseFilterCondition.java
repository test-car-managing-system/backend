package com.testcar.car.domains.expense.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseFilterCondition {
    @Schema(description = "사용자명", example = "null")
    private String memberName;

    @Schema(description = "소속부서", example = "null")
    private String departmentName;

    @Schema(description = "차량명", example = "null")
    private String carName;

    @Schema(description = "재고번호", example = "null")
    private String stockNumber;

    @Schema(description = "지출내용", example = "null")
    private String description;

    @DateFormat
    @Schema(description = "지출일자 시작일", example = "null")
    private LocalDate startDate;

    @DateFormat
    @Schema(description = "지출일자 종료일", example = "null")
    private LocalDate endDate;
}
