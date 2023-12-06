package com.testcar.car.domains.carReservation;

import static com.testcar.car.domains.carStock.entity.StockStatus.AVAILABLE;

import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.exception.ErrorCode;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import com.testcar.car.domains.carReservation.repository.CarReservationRepository;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.member.Member;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationService {
    private static final int RESERVATION_DATE = 7;

    private final CarStockService carStockService;
    private final CarReservationRepository carReservationRepository;

    /** 조건에 맞는 시험차량 대여 이력을 조회합니다. */
    public Page<CarReservationDto> findAllPageByCondition(
            CarReservationFilterCondition condition, Pageable pageable) {
        return carReservationRepository.findAllPageByCondition(condition, pageable);
    }

    /** 시험차량을 예약합니다. */
    public CarReservation reserve(Member member, Long carStockId) {
        final CarStock carStock = carStockService.findById(carStockId);
        validateCarStockAvailable(carStock);
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiredAt = now.toLocalDate().plusDays(RESERVATION_DATE).atStartOfDay();
        final CarReservation carReservation =
                CarReservation.builder()
                        .member(member)
                        .carStock(carStock)
                        .startedAt(now)
                        .expiredAt(expiredAt)
                        .build();
        return carReservationRepository.save(carReservation);
    }

    public void validateCarStockAvailable(CarStock carStock) {
        if (carStock.getStatus() != AVAILABLE) {
            throw new BadRequestException(ErrorCode.CAR_STOCK_NOT_AVAILABLE);
        }
    }
}
