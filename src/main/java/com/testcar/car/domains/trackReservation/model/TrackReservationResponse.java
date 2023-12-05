package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.trackReservation.ReservationStatus;
import com.testcar.car.domains.trackReservation.TrackReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationResponse {
    @Schema(description = "시험장 예약 ID", example = "1")
    private Long id;

    @Schema(description = "시험장명", example = "서산주행시험장")
    private String name;

    @DateFormat
    @Schema(description = "예약일자", example = "2023-01-01")
    private LocalDateTime createdAt;

    @Schema(description = "예약상태", example = "RESERVED", implementation = ReservationStatus.class)
    private ReservationStatus status;

    public static TrackReservationResponse from(TrackReservation trackReservation) {
        return TrackReservationResponse.builder()
                .id(trackReservation.getId())
                .name(trackReservation.getTrack().getName())
                .createdAt(trackReservation.getCreatedAt())
                .status(trackReservation.getStatus())
                .build();
    }
}
