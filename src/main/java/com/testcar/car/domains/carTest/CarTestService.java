package com.testcar.car.domains.carTest;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.exception.ErrorCode;
import com.testcar.car.domains.carTest.model.CarTestRequest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import com.testcar.car.domains.carTest.repository.CarTestRepository;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarTestService {
    private final CarStockService carStockService;
    private final TrackService trackService;
    private final CarTestRepository carTestRepository;

    /** 차량 시험 결과를 id로 조회합니다. */
    public CarTestDto findById(Long id) {
        return carTestRepository
                .findDetailById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_TEST_NOT_FOUND));
    }

    /** 차량 시험 결과를 조건에 맞게 조회합니다. */
    public Page<CarTestDto> findAllPageByCondition(
            CarTestFilterCondition condition, Pageable pageable) {
        return carTestRepository.findAllPageByCondition(condition, pageable);
    }

    /** 차량 시험 결과를 등록합니다. */
    public CarTest register(Member member, CarTestRequest request) {
        final CarStock carStock = carStockService.findById(request.getCarStockId());
        final Track track = trackService.findById(request.getTrackId());
        final CarTest carTest =
                CarTest.builder()
                        .carStock(carStock)
                        .track(track)
                        .member(member)
                        .performedAt(request.getPerformedAt())
                        .result(request.getResult())
                        .memo(request.getMemo())
                        .build();
        return carTestRepository.save(carTest);
    }

    /** 차량 시험 결과를 삭제합니다. */
    public CarTest deleteById(Long id) {
        final CarTest carTest =
                carTestRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_TEST_NOT_FOUND));
        carTest.delete();
        return carTest;
    }
}
