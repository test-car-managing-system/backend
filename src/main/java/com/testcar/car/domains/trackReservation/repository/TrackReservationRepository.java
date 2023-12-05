package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.trackReservation.TrackReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackReservationRepository
        extends JpaRepository<TrackReservation, Long>, TrackReservationCustomRepository {}
