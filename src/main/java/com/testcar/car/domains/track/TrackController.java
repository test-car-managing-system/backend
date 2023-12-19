package com.testcar.car.domains.track;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.member.entity.Role;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.track.model.TrackResponse;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import com.testcar.car.infra.kakao.KakaoGeocodingService;
import com.testcar.car.infra.kakao.model.KakaoGeocodingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[시험장 관리]", description = "시험장 관리 API")
@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;
    private final KakaoGeocodingService kakaoGeocodingService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[예약] 시험장 조회", description = "시험장을 조건에 맞게 조회합니다.")
    public List<TrackResponse> getTracksByCondition(
            @ParameterObject TrackFilterCondition condition) {
        final List<Track> tracks = trackService.findAllByCondition(condition);
        return tracks.stream().map(TrackResponse::from).toList();
    }

    @GetMapping("/{trackId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[예약] 시험장 상세 정보", description = "시험장 상세 정보를 가져옵니다.")
    public TrackResponse getTrackById(@PathVariable Long trackId) {
        final Track track = trackService.findById(trackId);
        return TrackResponse.from(track);
    }

    @PostMapping("/register")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[시험장 관리] 시험장 등록", description = "(관리자) 새로운 시험장을 등록합니다.")
    public TrackResponse register(@Valid @RequestBody RegisterTrackRequest request) {
        final Track track = trackService.register(request);
        return TrackResponse.from(track);
    }

    @PatchMapping("/{trackId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[시험장 관리] 시험장 정보 수정", description = "(관리자) 시험장 정보를 수정합니다.")
    public TrackResponse update(
            @PathVariable Long trackId, @Valid @RequestBody RegisterTrackRequest request) {
        final Track track = trackService.updateById(trackId, request);
        return TrackResponse.from(track);
    }

    @DeleteMapping
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[시험장 관리] 시험장 삭제", description = "(관리자) 시험장을 삭제합니다.")
    public List<Long> delete(@RequestBody DeleteTrackRequest request) {
        return trackService.deleteAll(request);
    }

    @GetMapping("/geo")
    public KakaoGeocodingResponse getKakaoGeocoding(@RequestParam String address) {
        return kakaoGeocodingService.geocoding(address);
    }
}
