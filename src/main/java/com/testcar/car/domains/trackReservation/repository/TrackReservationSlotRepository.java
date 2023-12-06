package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackReservationSlotRepository
        extends JpaRepository<TrackReservationSlot, Long>, TrackReservationSlotCustomRepository {}
