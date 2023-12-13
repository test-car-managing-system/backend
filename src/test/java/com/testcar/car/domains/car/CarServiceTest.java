package com.testcar.car.domains.car;

import static com.testcar.car.common.Constant.ANOTHER_CAR_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.model.RegisterCarRequest;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import com.testcar.car.domains.car.repository.CarRepository;
import com.testcar.car.domains.car.request.CarRequestFactory;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock private CarRepository carRepository;

    @InjectMocks private CarService carService;

    private static Car car;
    private static final Long carId = 1L;

    @BeforeAll
    public static void setUp() {
        car = CarEntityFactory.createCar();
    }

    @Test
    void 차량_ID로_차량_엔티티를_가져온다() {
        // given
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));

        // when
        Car result = carService.findById(carId);

        // then
        assertEquals(car, result);
        verify(carRepository).findByIdAndDeletedFalse(carId);
    }

    @Test
    void 차량_ID가_존재하지_않으면_오류가_발생한다() {
        // given
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    carService.findById(carId);
                });
    }

    @Test
    void 조건에_맞는_차량_페이지를_필터링하여_가져온다() {
        // given
        CarFilterCondition condition = new CarFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<Car> mockPage = mock(Page.class);
        when(carRepository.findAllPageByCondition(condition, pageable)).thenReturn(mockPage);

        // when
        Page<Car> result = carService.findAllPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(carRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 차량을_등록한다() {
        // given
        RegisterCarRequest request = CarRequestFactory.createRegisterCarRequest();
        given(carRepository.save(any(Car.class))).willReturn(car);

        // when
        Car newCar = carService.register(request);

        // then
        then(carRepository).should().save(any(Car.class));
        assertNotNull(newCar);
    }

    @Test
    void 차량정보를_수정한다() {
        // given
        RegisterCarRequest request = CarRequestFactory.createRegisterCarRequest();
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));
        given(carRepository.save(any(Car.class))).willReturn(car);

        // when
        Car newCar = carService.updateById(carId, request);

        // then
        verify(carRepository).findByIdAndDeletedFalse(carId);
        then(carRepository).should().save(any(Car.class));
        assertNotNull(newCar);
    }

    @Test
    void 이미_존재하는_이름의_차량으로_수정할수_없다() {
        // given
        RegisterCarRequest request = CarRequestFactory.createRegisterCarRequest(ANOTHER_CAR_NAME);
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));
        when(carRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(true);

        // when
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carService.updateById(carId, request);
                });
        then(carRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 차량을_삭제한다() {
        // given
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));
        given(carRepository.save(any(Car.class))).willReturn(car);

        // when
        Car deletedCar = carService.deleteById(carId);

        // then
        verify(carRepository).findByIdAndDeletedFalse(carId);
        then(carRepository).should().save(any(Car.class));
        assertNotNull(deletedCar);
        assertTrue(deletedCar.getDeleted());
    }
}
