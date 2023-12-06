package com.testcar.car.domains.gasStationHistory.model;


import com.testcar.car.common.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RegisterGasStationHistoryRequest {
    @Schema(description = "주유소 Id", example = "1")
    private Long gasStationId;

    @Schema(description = "사용자 ID", example = "1")
    private Long memberId;

    @Schema(description = "차량 재고 ID", example = "1")
    private Long carStockId;

    @Schema(description = "주유량", example = "100.33")
    private Double amount;

    @DateTimeFormat
    @Schema(description = "주유일시", example = "2021-01-01 00:00:00")
    private LocalDateTime usedAt;
}
