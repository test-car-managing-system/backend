package com.testcar.car.domains.gasStationHistory.model.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GasStationHistoryDto {
    private final GasStationHistory gasStationHistory;
    private final Long id;
    private final String name;
    private final String memberName;
    private final String updateMemberName;
    private final String carName;
    private final String stockNumber;
    private final String departmentName;
    private final LocalDateTime usedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Double amount;

    @QueryProjection
    public GasStationHistoryDto(
            GasStationHistory gasStationHistory,
            String memberName,
            String updateMemberName,
            String carName,
            String stockNumber,
            String departmentName) {
        this.gasStationHistory = gasStationHistory;
        this.id = gasStationHistory.getId();
        this.name = gasStationHistory.getGasStation().getName();
        this.memberName = memberName;
        this.updateMemberName = updateMemberName;
        this.carName = carName;
        this.stockNumber = stockNumber;
        this.departmentName = departmentName;
        this.usedAt = gasStationHistory.getUsedAt();
        this.createdAt = gasStationHistory.getCreatedAt();
        this.updatedAt = gasStationHistory.getUpdatedAt();
        this.amount = gasStationHistory.getAmount();
    }
}
