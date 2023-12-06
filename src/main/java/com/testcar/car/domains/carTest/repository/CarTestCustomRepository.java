package com.testcar.car.domains.carTest.repository;


import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarTestCustomRepository {
    Optional<CarTestDto> findDetailById(Long id);

    Page<CarTestDto> findAllPageByCondition(CarTestFilterCondition condition, Pageable pageable);
}
