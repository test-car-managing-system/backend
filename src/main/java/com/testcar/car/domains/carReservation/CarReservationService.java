package com.testcar.car.domains.carReservation;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import com.testcar.car.common.annotation.DistributedLock;
import com.testcar.car.common.database.DistributedLockType;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carReservation.exception.ErrorCode;
import com.testcar.car.domains.carReservation.model.ReturnCarReservationRequest;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationCountVo;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import com.testcar.car.domains.carReservation.repository.CarReservationRepository;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.repository.CarStockRepository;
import com.testcar.car.domains.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private final CarStockRepository carStockRepository;
    private final CarReservationRepository carReservationRepository;

    /** 조건에 맞는 시험차량 대여 이력을 조회합니다. */
    public Page<CarReservationDto> findAllPageByCondition(
            CarReservationFilterCondition condition, Pageable pageable) {
        return carReservationRepository.findAllPageByCondition(condition, pageable);
    }

    /** 멤버 ID와 시험 차량 예약 ID 리스트를 모두 포함하는 예약 리스트를 조회합니다 */
    public List<CarReservation> findAllByMemberAndIds(Member member, List<Long> ids) {
        final List<CarReservation> carReservations =
                carReservationRepository.findAllWithCarStockByIdInAndMemberId(ids, member.getId());
        if (carReservations.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.CAR_RESERVATION_NOT_FOUND);
        }
        return carReservations;
    }

    /** 일주일 이내 대여된 차량 예약 순위를 조회합니다. */
    public List<CarReservationCountVo> findAllByLast7DaysRank() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime weekAgo = now.minusDays(7);
        List<CarReservationDto> reservations =
                carReservationRepository.findAllByCreatedAtBetween(weekAgo, now);
        final Map<String, Long> countMap =
                reservations.stream()
                        .map(CarReservationDto::getCarName)
                        .collect(groupingBy(Function.identity(), counting()));

        return countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(CarReservationCountVo::from)
                .toList();
    }

    /** 시험차량을 예약합니다. */
    @DistributedLock(type = DistributedLockType.CAR, identifier = "carStockId")
    public CarReservation reserve(Member member, Long carStockId) {
        final CarStock carStock = carStockService.findById(carStockId);
        validateCarStockAvailable(carStock);
        carStock.updateStatus(StockStatus.RESERVED);
        carStockRepository.save(carStock);
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiredAt = now.toLocalDate().plusDays(RESERVATION_DATE).atStartOfDay();
        final CarReservation carReservation =
                CarReservation.builder()
                        .member(member)
                        .carStock(carStock)
                        .status(ReservationStatus.RESERVED)
                        .startedAt(now)
                        .expiredAt(expiredAt)
                        .build();
        return carReservationRepository.save(carReservation);
    }

    /** 시험차량을 반납합니다. */
    public List<CarReservation> returnCarReservation(
            Member member, ReturnCarReservationRequest request) {
        final List<CarReservation> carReservations =
                this.findAllByMemberAndIds(member, request.getCarReservationIds());
        carReservations.forEach(
                carReservation -> {
                    validateCarReservationReserved(carReservation);
                    carReservation.updateReturn();
                });
        final List<CarStock> carStocks =
                carReservations.stream().map(CarReservation::getCarStock).toList();
        carStockService.returnCarStocks(carStocks);
        return carReservationRepository.saveAll(carReservations);
    }

    private void validateCarReservationReserved(CarReservation carReservation) {
        if (carReservation.getStatus() != ReservationStatus.RESERVED) {
            throw new BadRequestException(ErrorCode.CAR_RESERVATION_NOT_RESERVED);
        }
    }

    private void validateCarStockAvailable(CarStock carStock) {
        if (carStock.getStatus() != StockStatus.AVAILABLE) {
            throw new BadRequestException(ErrorCode.CAR_STOCK_NOT_AVAILABLE);
        }
    }
}
