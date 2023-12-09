package com.testcar.car.domains.car;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.exception.ErrorCode;
import com.testcar.car.domains.car.model.RegisterCarRequest;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import com.testcar.car.domains.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    /** 차량을 id로 조회합니다. */
    public Car findById(Long id) {
        return carRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_NOT_FOUND));
    }

    /** 차량을 이름으로 조회합니다. */
    public Car findByName(String name) {
        return carRepository
                .findByNameAndDeletedFalse(name)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAR_NOT_FOUND));
    }

    /** 차량을 조건에 맞게 조회합니다. */
    public Page<Car> findAllPageByCondition(CarFilterCondition condition, Pageable pageable) {
        return carRepository.findAllPageByCondition(condition, pageable);
    }

    /** 새로운 차량을 등록합니다. */
    public Car register(RegisterCarRequest request) {
        final Car car = createEntity(request);
        return carRepository.save(car);
    }

    /** 차량 정보를 업데이트 합니다. */
    public Car updateById(Long carId, RegisterCarRequest request) {
        final Car car = this.findById(carId);
        if (!car.getName().equals(request.getName())) {
            validateNameNotDuplicated(request.getName());
        }
        final Car updateMember = createEntity(request);
        car.update(updateMember);
        return carRepository.save(car);
    }

    /** 차량을 삭제 처리 합니다. (soft delete) */
    public Car deleteById(Long carId) {
        final Car car = this.findById(carId);
        car.delete();
        return carRepository.save(car);
    }

    /** 영속되지 않은 차량 엔티티를 생성합니다. */
    private Car createEntity(RegisterCarRequest request) {
        return Car.builder()
                .name(request.getName())
                .type(request.getType())
                .displacement(request.getDisplacement())
                .build();
    }

    /** 차량명 중복을 검사합니다. */
    private void validateNameNotDuplicated(String name) {
        if (carRepository.existsByNameAndDeletedFalse(name)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_CAR_NAME);
        }
    }
}
