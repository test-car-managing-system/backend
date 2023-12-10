package com.testcar.car.domains.car;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.exception.ErrorCode;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import com.testcar.car.domains.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TestCarService {
    private final CarRepository carRepository;

    /** 시험 차량을 조건에 맞게 조회합니다. */
    public Page<Car> findAllWithStocksPageByCondition(
            CarFilterCondition condition, Pageable pageable) {
        return carRepository.findAllWithStocksPageByCondition(condition, pageable);
    }

    /** 시험 차량을 id로 조회합니다. */
    public Car findById(Long id) {
        return carRepository
                .findWithStocksById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_NOT_FOUND));
    }
}
