package com.testcar.car.domains.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import com.testcar.car.domains.car.repository.CarRepository;
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
public class TestCarServiceTest {
    @Mock private CarRepository carRepository;

    @InjectMocks private TestCarService testCarService;

    private static Car car;
    private static final Long carId = 1L;

    @BeforeAll
    public static void setUp() {
        car = CarEntityFactory.createCar();
    }

    @Test
    void 차량_ID로_차량_엔티티를_가져온다() {
        // given
        when(carRepository.findWithStocksById(carId)).thenReturn(Optional.of(car));

        // when
        Car result = testCarService.findById(carId);

        // then
        assertEquals(car, result);
        verify(carRepository).findWithStocksById(carId);
    }

    @Test
    void 차량_ID가_존재하지_않으면_오류가_발생한다() {
        // given
        when(carRepository.findWithStocksById(carId)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    testCarService.findById(carId);
                });
    }

    @Test
    void 조건에_맞는_차량_페이지를_필터링하여_가져온다() {
        // given
        CarFilterCondition condition = new CarFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<Car> mockPage = mock(Page.class);
        when(carRepository.findAllWithStocksPageByCondition(condition, pageable))
                .thenReturn(mockPage);

        // when
        Page<Car> result = testCarService.findAllWithStocksPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(carRepository).findAllWithStocksPageByCondition(condition, pageable);
    }
}
