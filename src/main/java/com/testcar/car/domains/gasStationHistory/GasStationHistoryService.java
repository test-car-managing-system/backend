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
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GasStationHistoryService {
    private final MemberService memberService;
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
    public GasStationHistory register(RegisterGasStationHistoryRequest request) {
        final Member member = memberService.findById(request.getMemberId());
        final GasStation gasStation = gasStationService.findById(request.getGasStationId());
        final CarStock carStock = carStockService.findById(request.getCarStockId());
        final GasStationHistory gasStationHistory =
                GasStationHistory.builder()
                        .member(member)
                        .gasStation(gasStation)
                        .carStock(carStock)
                        .amount(request.getAmount())
                        .usedAt(request.getUsedAt())
                        .build();
        return gasStationHistoryRepository.save(gasStationHistory);
    }
}
