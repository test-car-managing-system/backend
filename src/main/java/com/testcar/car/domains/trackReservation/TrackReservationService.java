package com.testcar.car.domains.trackReservation;

import static com.testcar.car.domains.trackReservation.exception.ErrorCode.TRACK_RESERVATION_NOT_FOUND;

import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.TrackService;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import com.testcar.car.domains.trackReservation.repository.TrackReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackReservationService {
    private final TrackService trackService;
    private final TrackReservationRepository trackReservationRepository;
    private final TrackReservationSlotService trackReservationSlotService;

    /** 시험장 예약 상세 정보를 조회합니다. */
    public TrackReservation findByMemberAndId(Member member, Long trackReservationId) {
        return trackReservationRepository
                .findByMemberIdAndId(member.getId(), trackReservationId)
                .orElseThrow(() -> new NotFoundException(TRACK_RESERVATION_NOT_FOUND));
    }

    /** 조건에 맞는 시험장 예약 정보를 모두 조회합니다. */
    public List<TrackReservation> findAllByMemberAndCondition(
            Member member, TrackReservationFilterCondition condition) {
        return trackReservationRepository.findAllByMemberIdAndCondition(member.getId(), condition);
    }

    /** 해당 시험장을 예약합니다. */
    public TrackReservation reserve(Member member, Long trackId, TrackReservationRequest request) {
        final Track track = trackService.findById(trackId);
        final TrackReservation trackReservation = this.createEntity(member, track);
        trackReservationRepository.save(trackReservation);
        trackReservationSlotService.reserve(track, trackReservation, request);
        return trackReservation;
    }

    /** 영속되지 않은 시험장 예약 엔티티를 생성합니다. */
    private TrackReservation createEntity(Member member, Track track) {
        return TrackReservation.builder()
                .member(member)
                .track(track)
                .status(ReservationStatus.RESERVED)
                .build();
    }
}
