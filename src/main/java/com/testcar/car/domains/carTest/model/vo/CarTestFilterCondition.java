package com.testcar.car.domains.carTest.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarTestFilterCondition {
    @Schema(description = "시험장명", example = "null")
    private String trackName;

    @Schema(description = "차량명", example = "null")
    private String memberName;

    @Schema(description = "차량명", example = "null")
    private String carName;

    @Schema(description = "재고번호", example = "null")
    private String stockNumber;

    @Schema(description = "부서명", example = "null")
    private String departmentName;

    @DateFormat
    @Schema(description = "시험일자 시작일", example = "null")
    private LocalDate startDate;

    @DateFormat
    @Schema(description = "시험일자 종료일", example = "null")
    private LocalDate endDate;
}
