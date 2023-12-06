package com.testcar.car.domains.gasStationHistory.repository;


import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GasStationHistoryCustomRepository {
    Optional<GasStationHistoryDto> findDetailById(Long id);

    Page<GasStationHistoryDto> findAllPageByCondition(
            GasStationHistoryFilterCondition condition, Pageable pageable);
}
