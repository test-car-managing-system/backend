package com.testcar.car.domains.gasStationHistory; // package com.testcar.car.domains.gasStation;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.gasStation.GasStationService;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.exception.ErrorCode;
import com.testcar.car.domains.gasStationHistory.model.RegisterGasStationHistoryRequest;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import com.testcar.car.domains.gasStationHistory.repository.GasStationHistoryRepository;
import com.testcar.car.domains.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GasStationHistoryService {
    private final CarStockService carStockService;
    private final GasStationService gasStationService;
    private final GasStationHistoryRepository gasStationHistoryRepository;

    /** 조건에 맞는 주유 이력을 모두 조회합니다. */
    public Page<GasStationHistoryDto> findAllByCondition(
            GasStationHistoryFilterCondition condition, Pageable pageable) {
        return gasStationHistoryRepository.findAllPageByCondition(condition, pageable);
    }

    /** 주유 이력을 id로 조회합니다. */
    public GasStationHistoryDto findById(Long id) {
        return gasStationHistoryRepository
                .findDetailById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAS_STATION_HISTORY_NOT_FOUND));
    }

    /** 주유 이력을 등록합니다. */
    public GasStationHistory register(Member member, RegisterGasStationHistoryRequest request) {
        final GasStation gasStation = gasStationService.findByName(request.getGasStationName());
        final CarStock carStock = carStockService.findByStockNumber(request.getStockNumber());
        final GasStationHistory gasStationHistory =
                GasStationHistory.builder()
                        .member(member)
                        .gasStation(gasStation)
                        .carStock(carStock)
                        .amount(request.getAmount())
                        .usedAt(request.getUsedAt().atStartOfDay())
                        .build();
        return gasStationHistoryRepository.save(gasStationHistory);
    }

    /** 주유 이력을 수정합니다. */
    public GasStationHistory update(
            Member member, Long gasStationHistoryId, RegisterGasStationHistoryRequest request) {
        final GasStationHistoryDto gasStationHistoryDto = this.findById(gasStationHistoryId);
        final GasStationHistory gasStationHistory = gasStationHistoryDto.getGasStationHistory();

        if (!gasStationHistoryDto.getStockNumber().equals(request.getStockNumber())) {
            final CarStock carStock = carStockService.findByStockNumber(request.getStockNumber());
            gasStationHistory.updateCarStock(carStock);
        }
        if (!gasStationHistoryDto.getName().equals(request.getGasStationName())) {
            final GasStation gasStation = gasStationService.findByName(request.getGasStationName());
            gasStationHistory.updateGasStation(gasStation);
        }
        gasStationHistory.updateMemberBy(member);
        gasStationHistory.update(request.getAmount(), request.getUsedAt().atStartOfDay());
        return gasStationHistoryRepository.save(gasStationHistory);
    }

    /** 주유 이력을 삭제합니다. */
    public GasStationHistory delete(Member member, Long gasStationHistoryId) {
        final GasStationHistoryDto gasStationHistoryDto = this.findById(gasStationHistoryId);
        final GasStationHistory gasStationHistory = gasStationHistoryDto.getGasStationHistory();
        gasStationHistory.updateMemberBy(member);
        gasStationHistory.delete();
        return gasStationHistoryRepository.save(gasStationHistory);
    }
}
