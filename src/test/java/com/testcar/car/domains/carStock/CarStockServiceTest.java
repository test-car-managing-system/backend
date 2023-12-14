package com.testcar.car.domains.carStock;

import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.CarService;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.model.DeleteCarStockRequest;
import com.testcar.car.domains.carStock.model.RegisterCarStockRequest;
import com.testcar.car.domains.carStock.model.UpdateCarStockRequest;
import com.testcar.car.domains.carStock.model.vo.CarStockFilterCondition;
import com.testcar.car.domains.carStock.repository.CarStockRepository;
import com.testcar.car.domains.carStock.request.CarStockRequestFactory;
import com.testcar.car.domains.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CarStockServiceTest {
    @Mock private CarService carService;
    @Mock private CarStockRepository carStockRepository;

    @InjectMocks private CarStockService carStockService;

    private static Member member;
    private static Car car;
    private static CarStock carStock;
    private static final Long carStockId = 1L;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        car = CarEntityFactory.createCar();
        carStock = CarEntityFactory.createCarStock();
    }

    @Test
    void 차량_재고번호로_차량재고_엔티티를_가져온다() {
        // given
        final String stockNumber = CAR_STOCK_NUMBER;
        when(carStockRepository.findByStockNumberAndDeletedFalse(stockNumber))
                .thenReturn(Optional.of(carStock));

        // when
        CarStock result = carStockService.findByStockNumber(stockNumber);

        // then
        assertEquals(carStock, result);
        verify(carStockRepository).findByStockNumberAndDeletedFalse(stockNumber);
    }

    @Test
    void 차량_재고번호가_존재하지_않으면_오류가_발생한다() {
        // given
        final String stockNumber = CAR_STOCK_NUMBER;
        when(carStockRepository.findByStockNumberAndDeletedFalse(stockNumber))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    carStockService.findByStockNumber(stockNumber);
                });
    }

    @Test
    void 차량_재고ID로_차량재고_엔티티를_가져온다() {
        // given
        when(carStockRepository.findByIdAndDeletedFalse(carStockId))
                .thenReturn(Optional.of(carStock));

        // when
        CarStock result = carStockService.findById(carStockId);

        // then
        assertEquals(carStock, result);
        verify(carStockRepository).findByIdAndDeletedFalse(carStockId);
    }

    @Test
    void 차량_재고_ID가_존재하지_않으면_오류가_발생한다() {
        // given
        when(carStockRepository.findByIdAndDeletedFalse(carStockId)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    carStockService.findById(carStockId);
                });
    }

    @Test
    void 차량_재고ID_리스트로_차량재고_리스트를_가져온다() {
        // given
        final CarStock carStock1 = CarEntityFactory.createCarStock();
        final CarStock carStock2 = CarEntityFactory.createCarStock();
        final CarStock carStock3 = CarEntityFactory.createCarStock();
        final List<CarStock> carStocks = List.of(carStock1, carStock2, carStock3);
        final List<Long> carStockIds = List.of(1L, 2L, 3L);
        when(carStockRepository.findAllByIdInAndDeletedFalse(carStockIds)).thenReturn(carStocks);

        // when
        List<CarStock> result = carStockService.findAllByIdIn(carStockIds);

        // then
        assertEquals(carStocks, result);
        verify(carStockRepository).findAllByIdInAndDeletedFalse(carStockIds);
    }

    @Test
    void 차량_재고ID_리스트와_결과_개수가_일치하지_않으면_오류가_발생한다() {
        // given
        final CarStock carStock1 = CarEntityFactory.createCarStock();
        final CarStock carStock2 = CarEntityFactory.createCarStock();
        final List<CarStock> fewerStocks = List.of(carStock1, carStock2);
        final List<Long> carStockIds = List.of(1L, 2L, 3L);
        when(carStockRepository.findAllByIdInAndDeletedFalse(carStockIds)).thenReturn(fewerStocks);

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    carStockService.findAllByIdIn(carStockIds);
                });
    }

    @Test
    void 조건에_맞는_차량재고_페이지를_필터링하여_가져온다() {
        // given
        CarStockFilterCondition condition = new CarStockFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<CarStock> mockPage = mock(Page.class);
        when(carStockRepository.findAllPageByCondition(condition, pageable)).thenReturn(mockPage);

        // when
        Page<CarStock> result = carStockService.findAllPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(carStockRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 차량재고를_등록한다() {
        // given
        RegisterCarStockRequest request = CarStockRequestFactory.createRegisterCarStockRequest();
        given(carStockRepository.save(any(CarStock.class))).willReturn(carStock);

        // when
        CarStock newCarStock = carStockService.register(request);

        // then
        then(carStockRepository).should().save(any(CarStock.class));
        assertNotNull(newCarStock);
    }

    @Test
    void 이미_등록된_재고번호로는_등록할수_없다() {
        // given
        RegisterCarStockRequest request = CarStockRequestFactory.createRegisterCarStockRequest();
        given(carStockRepository.existsByStockNumberAndDeletedFalse(request.getStockNumber()))
                .willReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carStockService.register(request);
                });
        then(carStockRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 차량재고를_수정한다() {
        // given
        UpdateCarStockRequest request = CarStockRequestFactory.createUpdateCarStockRequest();
        when(carStockRepository.findByIdAndDeletedFalse(carStockId))
                .thenReturn(Optional.of(carStock));
        given(carStockRepository.save(any(CarStock.class))).willReturn(carStock);

        // when
        CarStock newCarStock = carStockService.updateById(carStockId, request);

        // then
        verify(carStockRepository).findByIdAndDeletedFalse(carStockId);
        then(carStockRepository).should().save(any(CarStock.class));
        assertNotNull(newCarStock);
    }

    @Test
    void 이미_등록된_재고번호로는_수정할수_없다() {
        // given
        UpdateCarStockRequest request = CarStockRequestFactory.createUpdateCarStockRequest();
        when(carStockRepository.findByIdAndDeletedFalse(carStockId))
                .thenReturn(Optional.of(carStock));
        given(carStockRepository.existsByStockNumberAndDeletedFalse(request.getStockNumber()))
                .willReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carStockService.updateById(carStockId, request);
                });
        then(carStockRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 예약중인_차량재고를_모두_반납한다() {
        // given
        final CarStock carStock1 =
                CarEntityFactory.createCarStockBuilder().status(StockStatus.RESERVED).build();
        final CarStock carStock2 =
                CarEntityFactory.createCarStockBuilder().status(StockStatus.RESERVED).build();
        final List<CarStock> carStocks = List.of(carStock1, carStock2);

        // when
        final List<CarStock> returnedCarStocks = carStockService.returnCarStocks(carStocks);

        // then
        returnedCarStocks.forEach(
                carStock -> assertEquals(StockStatus.AVAILABLE, carStock.getStatus()));
        verify(carStockRepository).saveAll(carStocks);
    }

    @ParameterizedTest
    @EnumSource(
            value = StockStatus.class,
            names = {"AVAILABLE", "UNAVAILABLE", "INSPECTION"})
    void 예약중이지_않은_차량재고는_반납할수_없다(StockStatus status) {
        // given
        final CarStock carStock = CarEntityFactory.createCarStockBuilder().status(status).build();
        final List<CarStock> carStocks = List.of(carStock);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carStockService.returnCarStocks(carStocks);
                });
        then(carStockRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 차량재고를_삭제한다() {
        // given
        final CarStock carStock1 = CarEntityFactory.createCarStock();
        final CarStock carStock2 = CarEntityFactory.createCarStock();
        final CarStock carStock3 = CarEntityFactory.createCarStock();
        final List<CarStock> carStocks = List.of(carStock1, carStock2, carStock3);
        DeleteCarStockRequest request = CarStockRequestFactory.createDeleteCarStockRequest();
        when(carStockRepository.findAllByIdInAndDeletedFalse(request.getIds()))
                .thenReturn(carStocks);

        // when
        List<Long> deletedIds = carStockService.deleteAll(request);

        // then
        verify(carStockRepository).findAllByIdInAndDeletedFalse(request.getIds());
        assertEquals(carStock1.getDeleted(), true);
        assertEquals(carStock2.getDeleted(), true);
        assertEquals(carStock3.getDeleted(), true);
        assertEquals(deletedIds, request.getIds());
    }
}
