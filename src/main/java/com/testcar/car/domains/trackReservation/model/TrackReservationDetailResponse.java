package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationDetailResponse {
    @Schema(description = "시험장 예약 ID", example = "1")
    private Long id;

    @Schema(description = "예약자명", example = "홍길동")
    private String memberName;

    @Schema(description = "시험장명", example = "서산주행시험장")
    private String name;

    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private String location;

    @Schema(description = "시험장 특성", example = "평지")
    private String description;

    @Schema(description = "길이", example = "100.32")
    private Double length;

    @Schema(description = "예약상태", example = "RESERVED", implementation = ReservationStatus.class)
    private ReservationStatus status;

    @Schema(description = "예약시간")
    private List<ReservationSlotVo> reservationTime;

    public static TrackReservationDetailResponse from(
            Member member, TrackReservation trackReservation) {
        final Set<TrackReservationSlot> slots = trackReservation.getTrackReservationSlots();
        final List<ReservationSlotVo> slotsVo =
                slots.stream().map(ReservationSlotVo::from).toList();

        return TrackReservationDetailResponse.builder()
                .id(trackReservation.getId())
                .memberName(member.getName())
                .status(trackReservation.getStatus())
                .name(trackReservation.getTrack().getName())
                .location(trackReservation.getTrack().getLocation())
                .description(trackReservation.getTrack().getDescription())
                .length(trackReservation.getTrack().getLength())
                .status(trackReservation.getStatus())
                .reservationTime(slotsVo)
                .build();
    }
}
