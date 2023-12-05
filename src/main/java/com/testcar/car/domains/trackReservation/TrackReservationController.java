package com.testcar.car.domains.trackReservation;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.member.Role;
import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.TrackService;
import com.testcar.car.domains.track.model.TrackResponse;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackReservationController {
    private final TrackService trackService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험장 예약 이력 조회", description = "시험장 예약 이력을 조건에 맞게 조회합니다.")
    public List<TrackResponse> getTrackReservationsByCondition(TrackFilterCondition condition) {
        final List<Track> tracks = trackService.findAllByCondition(condition);
        return tracks.stream().map(TrackResponse::from).toList();
    }
}
