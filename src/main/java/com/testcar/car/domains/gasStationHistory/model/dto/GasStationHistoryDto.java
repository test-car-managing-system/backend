package com.testcar.car.domains.gasStationHistory.model.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GasStationHistoryDto {
    private final Long id;
    private final String name;
    private final String memberName;
    private final String carName;
    private final String stockNumber;
    private final String departmentName;
    private final LocalDateTime createdAt;
    private final Double amount;

    @QueryProjection
    public GasStationHistoryDto(
            GasStationHistory gasStationHistory,
            String memberName,
            String carName,
            String stockNumber,
            String departmentName) {
        this.id = gasStationHistory.getId();
        this.name = gasStationHistory.getGasStation().getName();
        this.memberName = memberName;
        this.carName = carName;
        this.stockNumber = stockNumber;
        this.departmentName = departmentName;
        this.createdAt = gasStationHistory.getCreatedAt();
        this.amount = gasStationHistory.getAmount();
    }
}
