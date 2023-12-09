package com.testcar.car.domains.carStock.repository;


import com.testcar.car.domains.carStock.entity.CarStock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarStockRepository
        extends JpaRepository<CarStock, Long>, CarStockCustomRepository {
    Optional<CarStock> findByIdAndDeletedFalse(Long id);
    List<CarStock> findAllByIdInAndDeletedFalse(List<Long> ids);
    boolean existsByStockNumberAndDeletedFalse(String stockNumber);
}
