package com.testcar.car.domains.carTest;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carTest.exception.ErrorCode;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import com.testcar.car.domains.carTest.repository.CarTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarTestService {
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
}
