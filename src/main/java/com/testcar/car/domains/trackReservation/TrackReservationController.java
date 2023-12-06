package com.testcar.car.domains.trackReservation;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Role;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.model.TrackReservationDetailResponse;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.TrackReservationResponse;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[시험장 관리]", description = "시험장 예약 API")
@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackReservationController {
    private final TrackReservationService trackReservationService;

    @GetMapping("/reservations")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 내 시험장 예약 이력 조회", description = "나의 시험장 예약 이력을 조건에 맞게 조회합니다.")
    public List<TrackReservationResponse> getTrackReservationsByCondition(
            @AuthMember Member member, TrackReservationFilterCondition condition) {
        final List<TrackReservation> trackReservations =
                trackReservationService.findAllByMemberAndCondition(member, condition);
        return trackReservations.stream().map(TrackReservationResponse::from).toList();
    }

    @GetMapping("/reservations/{trackReservationId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 내 시험장 예약 이력 상세 조회", description = "시험장 예약 이력 상세 정보를 조회합니다.")
    public TrackReservationDetailResponse getTrackReservationDetailById(
            @AuthMember Member member, @PathVariable Long trackReservationId) {
        final TrackReservation trackReservation =
                trackReservationService.findByMemberAndId(member, trackReservationId);
        return TrackReservationDetailResponse.from(member, trackReservation);
    }

    @PostMapping("/{trackId}/reserve")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험장 예약", description = "해당 시험장을 예약합니다.")
    public TrackReservationDetailResponse postTrackReservation(
            @AuthMember Member member,
            @PathVariable Long trackId,
            @Valid @RequestBody TrackReservationRequest request) {
        final TrackReservation trackReservation =
                trackReservationService.reserve(member, trackId, request);
        return TrackReservationDetailResponse.from(member, trackReservation);
    }

    @PatchMapping("/reservations/{trackReservationId}/cancel")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험장 예약 취소", description = "해당 시험장을 예약을 취소합니다.")
    public TrackReservationDetailResponse postTrackReservation(
            @AuthMember Member member, @PathVariable Long trackReservationId) {
        final TrackReservation trackReservation =
                trackReservationService.cancel(member, trackReservationId);
        return TrackReservationDetailResponse.from(member, trackReservation);
    }
}
