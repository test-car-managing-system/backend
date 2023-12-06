package com.testcar.car.domains.gasStationHistory.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GasStationHistoryFilterCondition {
    @Schema(description = "주유소명", example = "null")
    private String name;

    @Schema(description = "사용자명", example = "null")
    private String memberName;

    @Schema(description = "차량명", example = "null")
    private String carName;

    @Schema(description = "재고번호", example = "null")
    private String stockNumber;

    @Schema(description = "소속부서", example = "null")
    private String departmentName;

    @DateFormat
    @Schema(description = "주유일자 시작일", example = "null")
    private LocalDate startDate;

    @DateFormat
    @Schema(description = "주유일자 종료일", example = "null")
    private LocalDate endDate;
}
