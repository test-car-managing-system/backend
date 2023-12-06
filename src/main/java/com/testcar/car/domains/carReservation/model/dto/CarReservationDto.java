package com.testcar.car.domains.carReservation.model.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CarReservationDto {
    private final Long id;
    private final String carName;
    private final String stockNumber;
    private final LocalDateTime startedAt;
    private final LocalDateTime expiredAt;
    private final ReservationStatus status;

    @QueryProjection
    public CarReservationDto(CarReservation carReservation, String carName, String stockNumber) {
        this.id = carReservation.getId();
        this.carName = carName;
        this.stockNumber = stockNumber;
        this.startedAt = carReservation.getStartedAt();
        this.expiredAt = carReservation.getExpiredAt();
        this.status = carReservation.getStatus();
    }
}
