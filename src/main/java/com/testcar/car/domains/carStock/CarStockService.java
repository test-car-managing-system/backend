package com.testcar.car.domains.carStock;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.CarService;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.exception.ErrorCode;
import com.testcar.car.domains.carStock.model.DeleteCarStockRequest;
import com.testcar.car.domains.carStock.model.RegisterCarStockRequest;
import com.testcar.car.domains.carStock.model.UpdateCarStockRequest;
import com.testcar.car.domains.carStock.model.vo.CarStockFilterCondition;
import com.testcar.car.domains.carStock.repository.CarStockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarStockService {
    private final CarService carService;
    private final CarStockRepository carStockRepository;

    /** 차량 재고를 재고 번호로 조회합니다. */
    public CarStock findByStockNumber(String stockNumber) {
        return carStockRepository
                .findByStockNumberAndDeletedFalse(stockNumber)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_STOCK_NOT_FOUND));
    }

    /** 차량 재고를 id로 조회합니다. */
    public CarStock findById(Long id) {
        return carStockRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_STOCK_NOT_FOUND));
    }

    /** 차량 재고 리스트를 id 리스트로 조회합니다. */
    public List<CarStock> findAllByIdIn(List<Long> ids) {
        List<CarStock> stocks = carStockRepository.findAllByIdInAndDeletedFalse(ids);
        if (stocks.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.CAR_STOCK_NOT_FOUND);
        }
        return stocks;
    }

    /** 재고를 조건에 맞게 조회합니다. */
    public Page<CarStock> findAllPageByCondition(
            CarStockFilterCondition condition, Pageable pageable) {
        return carStockRepository.findAllPageByCondition(condition, pageable);
    }

    /** 새로운 재고를 등록합니다. */
    public CarStock register(RegisterCarStockRequest request) {
        validateStockNumberNotDuplicated(request.getStockNumber());
        final CarStock car = createEntity(request);
        return carStockRepository.save(car);
    }

    /** 재고 정보를 업데이트 합니다. */
    public CarStock updateById(Long carStockId, UpdateCarStockRequest request) {
        final CarStock carStock = this.findById(carStockId);
        if (!carStock.getStockNumber().equals(request.getStockNumber())) {
            validateStockNumberNotDuplicated(request.getStockNumber());
        }
        carStock.updateStockNumber(request.getStockNumber());
        carStock.updateStatus(request.getStatus());
        return carStockRepository.save(carStock);
    }

    /** 예약 중인 재고를 모두 반납합니다. */
    public List<CarStock> returnCarStocks(List<CarStock> carStocks) {
        carStocks.forEach(
                carStock -> {
                    carStock.updateStatus(StockStatus.AVAILABLE);
                    validateCarStockReserved(carStock);
                });
        return carStockRepository.saveAll(carStocks);
    }

    /** 재고를 삭제 처리 합니다. (soft delete) */
    public List<Long> deleteAll(DeleteCarStockRequest request) {
        final List<CarStock> stocks = this.findAllByIdIn(request.getIds());
        stocks.forEach(CarStock::delete);
        carStockRepository.saveAll(stocks);
        return request.getIds();
    }

    /** 영속되지 않은 재고 엔티티를 생성합니다. */
    private CarStock createEntity(RegisterCarStockRequest request) {
        final Car car = carService.findById(request.getCarId());
        return CarStock.builder()
                .car(car)
                .stockNumber(request.getStockNumber())
                .status(request.getStatus())
                .build();
    }

    /** 차량명 중복을 검사합니다. */
    private void validateStockNumberNotDuplicated(String stockNumber) {
        if (carStockRepository.existsByStockNumberAndDeletedFalse(stockNumber)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_STOCK_NUMBER);
        }
    }

    private void validateCarStockReserved(CarStock carStock) {
        if (carStock.getStatus() != StockStatus.RESERVED) {
            throw new BadRequestException(ErrorCode.CAR_STOCK_NOT_RESERVED);
        }
    }
}
