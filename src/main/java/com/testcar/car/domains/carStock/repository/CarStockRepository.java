package com.testcar.car.domains.carStock.repository;


import com.testcar.car.domains.carStock.CarStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarStockRepository
        extends JpaRepository<CarStock, Long>, CarStockCustomRepository {
    Optional<CarStock> findByIdAndDeletedFalse(Long id);

    boolean existsByStockNumberAndDeletedFalse(String stockNumber);
}
