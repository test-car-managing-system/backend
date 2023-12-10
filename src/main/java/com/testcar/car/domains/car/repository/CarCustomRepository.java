package com.testcar.car.domains.car.repository;


import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarCustomRepository {
    Optional<Car> findById(Long id);

    Optional<Car> findWithStocksById(Long id);

    Page<Car> findAllPageByCondition(CarFilterCondition condition, Pageable pageable);

    Page<Car> findAllWithStocksPageByCondition(CarFilterCondition condition, Pageable pageable);
}
