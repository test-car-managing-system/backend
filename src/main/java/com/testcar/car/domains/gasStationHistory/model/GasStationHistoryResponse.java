package com.testcar.car.domains.gasStationHistory.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GasStationHistoryResponse {
    @Schema(description = "주유 이력 ID", example = "1")
    private final Long id;

    @Schema(description = "주유소명", example = "서산주유소A")
    private final String name;

    @Schema(description = "사용자명", example = "홍길동")
    private final String memberName;

    @Schema(description = "차량명", example = "아반떼")
    private final String carName;

    @Schema(description = "재고번호", example = "2023010100001")
    private final String stockNumber;

    @Schema(description = "소속부서", example = "시스템관리팀")
    private final String departmentName;

    @DateFormat
    @Schema(description = "주유일자", example = "2021-01-01")
    private final LocalDateTime usedAt;

    @Schema(description = "주유량", example = "100.33")
    private final Double amount;

    @DateTimeFormat
    @Schema(description = "등록일시", example = "2021-01-01 12:00:01")
    private final LocalDateTime createdAt;

    @DateTimeFormat
    @Schema(description = "수정일시", example = "2021-01-01 12:00:01")
    private final LocalDateTime updatedAt;

    @Schema(description = "수정인", example = "홍동길")
    private final String updateMemberName;

    public static GasStationHistoryResponse from(GasStationHistoryDto gasStationHistoryDto) {
        return GasStationHistoryResponse.builder()
                .id(gasStationHistoryDto.getId())
                .name(gasStationHistoryDto.getName())
                .memberName(gasStationHistoryDto.getMemberName())
                .carName(gasStationHistoryDto.getCarName())
                .stockNumber(gasStationHistoryDto.getStockNumber())
                .departmentName(gasStationHistoryDto.getDepartmentName())
                .usedAt(gasStationHistoryDto.getUsedAt())
                .amount(gasStationHistoryDto.getAmount())
                .createdAt(gasStationHistoryDto.getCreatedAt())
                .updatedAt(gasStationHistoryDto.getUpdatedAt())
                .updateMemberName(gasStationHistoryDto.getUpdateMemberName())
                .build();
    }

    public static GasStationHistoryResponse from(GasStationHistory gasStationHistory) {
        return GasStationHistoryResponse.builder()
                .id(gasStationHistory.getId())
                .name(gasStationHistory.getGasStation().getName())
                .memberName(gasStationHistory.getMember().getName())
                .carName(gasStationHistory.getCarStock().getCar().getName())
                .stockNumber(gasStationHistory.getCarStock().getStockNumber())
                .departmentName(gasStationHistory.getMember().getDepartment().getName())
                .usedAt(gasStationHistory.getUsedAt())
                .amount(gasStationHistory.getAmount())
                .createdAt(gasStationHistory.getCreatedAt())
                .updatedAt(gasStationHistory.getUpdatedAt())
                .updateMemberName(gasStationHistory.getUpdateMember().getName())
                .build();
    }
}
