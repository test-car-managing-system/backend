package com.testcar.car.domains.gasStationHistory; // package com.testcar.car.domains.gasStation;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.gasStationHistory.exception.ErrorCode;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import com.testcar.car.domains.gasStationHistory.repository.GasStationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GasStationHistoryService {
    private final GasStationHistoryRepository gasStationHistoryRepository;

    /** 조건에 맞는 주유 이력을 모두 조회합니다. */
    public Page<GasStationHistoryDto> findAllByCondition(
            GasStationHistoryFilterCondition condition, Pageable pageable) {
        return gasStationHistoryRepository.findAllPageByCondition(condition, pageable);
    }

    /** 주유 이력 상세 정보를 id로 조회합니다. */
    public GasStationHistoryDto findById(Long id) {
        return gasStationHistoryRepository
                .findDetailById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAS_STATION_HISTORY_NOT_FOUND));
    }
}
