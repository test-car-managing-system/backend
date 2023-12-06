package com.testcar.car.domains.gasStation;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStation.exception.ErrorCode;
import com.testcar.car.domains.gasStation.repository.GasStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GasStationService {
    private final GasStationRepository gasStationRepository;

    /** 주유소를 id로 조회합니다. */
    public GasStation findById(Long id) {
        return gasStationRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAS_STATION_NOT_FOUND));
    }
}
