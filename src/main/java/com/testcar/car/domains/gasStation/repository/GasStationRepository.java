package com.testcar.car.domains.gasStation.repository;


import com.testcar.car.domains.gasStation.entity.GasStation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    Optional<GasStation> findByIdAndDeletedFalse(Long id);

    Optional<GasStation> findByNameAndDeletedFalse(String name);

    List<GasStation> findAllByDeletedFalseOrderByCreatedAtDesc();

    List<GasStation> findAllByIdInAndDeletedFalse(List<Long> ids);

    boolean existsByNameAndDeletedFalse(String name);
}
