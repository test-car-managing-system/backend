package com.testcar.car.domains.car.repository;


import com.testcar.car.domains.car.entity.Car;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>, CarCustomRepository {
    Optional<Car> findByIdAndDeletedFalse(Long id);

    Optional<Car> findByNameAndDeletedFalse(String name);

    boolean existsByNameAndDeletedFalse(String name);
}
