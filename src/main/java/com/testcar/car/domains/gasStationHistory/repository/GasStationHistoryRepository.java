package com.testcar.car.domains.gasStationHistory.repository;


import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasStationHistoryRepository
        extends JpaRepository<GasStationHistory, Long>, GasStationHistoryCustomRepository {}
