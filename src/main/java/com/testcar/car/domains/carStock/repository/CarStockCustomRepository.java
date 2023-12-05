package com.testcar.car.domains.carStock.repository;


import com.testcar.car.domains.carStock.CarStock;
import com.testcar.car.domains.carStock.model.vo.CarStockFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarStockCustomRepository {
    Optional<CarStock> findById(Long id);

    Page<CarStock> findAllPageByCondition(CarStockFilterCondition condition, Pageable pageable);
}
