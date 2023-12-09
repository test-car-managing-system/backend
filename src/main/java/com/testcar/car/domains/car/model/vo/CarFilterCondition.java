package com.testcar.car.domains.car.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.car.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarFilterCondition {
    @Schema(description = "차량명", example = "null")
    private String name;

    @Schema(description = "차종", example = "null", implementation = Type.class)
    private Type type;

    @DateFormat
    @Schema(description = "등록일자 시작일", example = "null")
    private LocalDate startDate;

    @DateFormat
    @Schema(description = "등록일자 종료일", example = "null")
    private LocalDate endDate;
}
