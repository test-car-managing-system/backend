package com.testcar.car.domains.carTest.repository;


import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTestRepository
        extends JpaRepository<GasStationHistory, Long>, CarTestCustomRepository {}
