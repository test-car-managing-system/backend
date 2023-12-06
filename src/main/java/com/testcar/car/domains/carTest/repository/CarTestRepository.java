package com.testcar.car.domains.carTest.repository;


import com.testcar.car.domains.carTest.entity.CarTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTestRepository extends JpaRepository<CarTest, Long>, CarTestCustomRepository {}
