package com.testcar.car.domains.carReservation.repository;


import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarReservationCustomRepository {
    Page<CarReservationDto> findAllPageByCondition(
            CarReservationFilterCondition condition, Pageable pageable);
}
