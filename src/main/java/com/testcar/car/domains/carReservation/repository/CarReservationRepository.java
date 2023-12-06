package com.testcar.car.domains.carReservation.repository;


import com.testcar.car.domains.carReservation.entity.CarReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReservationRepository
        extends JpaRepository<CarReservation, Long>, CarReservationCustomRepository {}
