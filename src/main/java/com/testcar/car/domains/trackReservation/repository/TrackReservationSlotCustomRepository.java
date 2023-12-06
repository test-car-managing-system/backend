package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TrackReservationSlotCustomRepository {
    Set<TrackReservationSlot> findAllByTrackIdAndDate(Long trackId, LocalDate date);

    boolean existsByTrackIdAndSlots(Long trackId, List<ReservationSlotVo> slots);
}
