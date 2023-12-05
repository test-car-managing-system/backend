package com.testcar.car.domains.trackReservation;


import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.TrackService;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.repository.TrackReservationRepository;
import com.testcar.car.domains.trackReservation.repository.TrackReservationSlotRepository;
import java.time.LocalDate;
import java.util.Set;
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
    private final TrackReservationSlotRepository trackReservationSlotRepository;

    /** 해당 시험장의 예약된 슬롯을 시험장 id로 조회합니다. */
    public Set<TrackReservationSlot> findAllByTrackIdAndDate(Long trackId, LocalDate date) {
        return trackReservationSlotRepository.findAllByTrackIdAndDate(trackId, date);
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
