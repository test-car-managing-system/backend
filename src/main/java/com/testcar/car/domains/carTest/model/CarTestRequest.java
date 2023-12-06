package com.testcar.car.domains.carTest.model;


import com.testcar.car.common.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CarTestRequest {
    @Schema(description = "시험장 Id", example = "1")
    private Long trackId;

    @Schema(description = "차량재고 Id", example = "1")
    private Long carStockId;

    @DateTimeFormat
    @Schema(description = "시험일자", example = "2021-01-01 10:00:00")
    private LocalDateTime performedAt;

    @Schema(description = "주행결과", example = "이상없음")
    private String result;

    @Schema(description = "비고", example = "타이어 정비가 필요해보임")
    private String memo;
}
