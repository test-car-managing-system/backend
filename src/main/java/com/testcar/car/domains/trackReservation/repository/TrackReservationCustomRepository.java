package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.trackReservation.TrackReservation;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import java.util.List;
import java.util.Optional;

public interface TrackReservationCustomRepository {
    Optional<TrackReservation> findByMemberIdAndId(Long memberId, Long trackReservationId);

    List<TrackReservation> findAllByMemberIdAndCondition(
            Long memberId, TrackReservationFilterCondition condition);
}
