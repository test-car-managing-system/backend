package com.testcar.car.domains.trackReservation;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Role;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.model.TrackReservationDetailResponse;
import com.testcar.car.domains.trackReservation.model.TrackReservationResponse;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks/reservations")
@RequiredArgsConstructor
public class TrackReservationController {
    private final TrackReservationService trackReservationService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 내 시험장 예약 이력 조회", description = "나의 시험장 예약 이력을 조건에 맞게 조회합니다.")
    public List<TrackReservationResponse> getTrackReservationsByCondition(
            @AuthMember Member member, TrackReservationFilterCondition condition) {
        final List<TrackReservation> trackReservations =
                trackReservationService.findAllByMemberAndCondition(member, condition);
        return trackReservations.stream().map(TrackReservationResponse::from).toList();
    }

    @GetMapping("/{trackReservationId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 내 시험장 예약 이력 상세 조회", description = "시험장 예약 이력 상세 정보를 조회합니다.")
    public TrackReservationDetailResponse getTrackReservationsByCondition(
            @AuthMember Member member, @PathVariable Long trackReservationId) {
        final TrackReservation trackReservation =
                trackReservationService.findByMemberAndId(member, trackReservationId);
        return TrackReservationDetailResponse.from(member, trackReservation);
    }
}
