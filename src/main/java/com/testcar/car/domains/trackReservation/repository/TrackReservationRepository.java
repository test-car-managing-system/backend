package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.track.repository.TrackCustomRepository;
import com.testcar.car.domains.trackReservation.TrackReservation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackReservationRepository
        extends JpaRepository<TrackReservation, Long>, TrackCustomRepository {
    Optional<TrackReservation> findByIdAndDeletedFalse(Long id);
}
