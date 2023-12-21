package com.testcar.car.domains.carReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarReservationResponse {
    @Schema(description = "차량 예약 ID", example = "1")
    private final Long id;

    @Schema(description = "차량명", example = "아반떼")
    private final String name;

    @Schema(description = "차량 재고번호", example = "2023010300001")
    private final String stockNumber;

    @DateFormat
    @Schema(description = "차량 대여 시작일", example = "2021-01-01")
    private final LocalDateTime startedAt;

    @DateFormat
    @Schema(description = "차량 대여 만료일", example = "2021-01-08")
    private final LocalDateTime expiredAt;

    @Schema(description = "대여 상태", example = "RESERVED", implementation = ReservationStatus.class)
    private final ReservationStatus status;

    public static CarReservationResponse from(CarReservationDto carReservationDto) {
        return CarReservationResponse.builder()
                .id(carReservationDto.getId())
                .name(carReservationDto.getCarName())
                .stockNumber(carReservationDto.getStockNumber())
                .startedAt(carReservationDto.getStartedAt())
                .expiredAt(carReservationDto.getExpiredAt())
                .status(carReservationDto.getStatus())
                .build();
    }

    public static CarReservationResponse from(CarReservation carReservation) {
        return CarReservationResponse.builder()
                .id(carReservation.getId())
                .name(carReservation.getCarStock().getCar().getName())
                .stockNumber(carReservation.getCarStock().getStockNumber())
                .startedAt(carReservation.getStartedAt())
                .expiredAt(carReservation.getExpiredAt())
                .status(carReservation.getStatus())
                .build();
    }
}
