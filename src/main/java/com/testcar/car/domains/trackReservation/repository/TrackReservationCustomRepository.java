package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrackReservationCustomRepository {
    Optional<TrackReservation> findByMemberIdAndId(Long memberId, Long trackReservationId);

    List<TrackReservation> findAllByMemberIdAndCondition(
            Long memberId, TrackReservationFilterCondition condition);

    List<TrackReservation> findAllBySlotExpiredAtAndStatusReserved(LocalDateTime expiredAt);

    List<TrackReservation> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
