package com.testcar.car.domains.carReservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.DtoFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carReservation.model.CarReservationRequest;
import com.testcar.car.domains.carReservation.model.ReturnCarReservationRequest;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationCountVo;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import com.testcar.car.domains.carReservation.repository.CarReservationRepository;
import com.testcar.car.domains.carReservation.request.CarReservationRequestFactory;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.repository.CarStockRepository;
import com.testcar.car.domains.member.entity.Member;
import java.util.List;
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
public class CarReservationServiceTest {
    @Mock private CarStockService carStockService;
    @Mock private CarStockRepository carStockRepository;
    @Mock private CarReservationRepository carReservationRepository;

    @InjectMocks private CarReservationService carReservationService;

    private static Member member;
    private static Car car;
    private static CarStock carStock;
    private static List<CarReservation> carReservations;
    private static final List<Long> carReservationIds = List.of(1L, 2L);

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        car = CarEntityFactory.createCar();
        carStock = CarEntityFactory.createCarStock();
        carReservations =
                List.of(
                        CarEntityFactory.createCarReservation(),
                        CarEntityFactory.createAnotherCarReservation());
    }

    @Test
    void 조건에_맞는_시험차량_예약정보_페이지를_필터링하여_가져온다() {
        // given
        final CarReservationFilterCondition condition = new CarReservationFilterCondition();
        final Pageable pageable = mock(Pageable.class);
        final Page<CarReservationDto> mockPage = mock(Page.class);
        when(carReservationRepository.findAllPageByCondition(condition, pageable))
                .thenReturn(mockPage);

        // when
        Page<CarReservationDto> result =
                carReservationService.findAllPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(carReservationRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 시험장_예약순위_리스트를_가져온다() {
        // given
        List<CarReservationDto> mockReservations =
                List.of(
                        DtoFactory.createCarReservationDto(carReservations.get(0)),
                        DtoFactory.createCarReservationDto(carReservations.get(1)),
                        DtoFactory.createCarReservationDto(carReservations.get(1)));
        when(carReservationRepository.findAllByCreatedAtBetween(any(), any()))
                .thenReturn(mockReservations);

        // when
        final List<CarReservationCountVo> result = carReservationService.findAllByLast7DaysRank();

        // then
        assertEquals(result.size(), 2L); // 같은 종류의 시험장은 묶여야 한다.
        assertEquals(result.get(0).getCount(), 2L); // 묶인 시험장의 개수가 많을수록 먼저 나와야 한다.
        verify(carReservationRepository).findAllByCreatedAtBetween(any(), any());
    }

    @Test
    void 자신의_시험차량_예약ID_리스트로_시험차량_예약_리스트를_가져온다() {
        // given
        when(carReservationRepository.findAllWithCarStockByIdInAndMemberId(
                        carReservationIds, member.getId()))
                .thenReturn(carReservations);

        // when
        List<CarReservation> result =
                carReservationService.findAllByMemberAndIds(member, carReservationIds);

        // then
        assertEquals(carReservations, result);
        verify(carReservationRepository)
                .findAllWithCarStockByIdInAndMemberId(carReservationIds, member.getId());
    }

    @Test
    void 자신의_시험차량_예약ID_리스트와_결과_개수가_일치하지_않으면_오류가_발생한다() {
        // given
        final List<Long> carReservationIds = List.of(1L);
        when(carReservationRepository.findAllWithCarStockByIdInAndMemberId(
                        carReservationIds, member.getId()))
                .thenReturn(carReservations);

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    carReservationService.findAllByMemberAndIds(member, carReservationIds);
                });
    }

    @Test
    void 시험차량을_예약한다() {
        // given
        final CarReservation carReservation = carReservations.get(0);
        final CarReservationRequest request =
                CarReservationRequestFactory.createCarReservationRequest();
        given(carReservationRepository.save(any(CarReservation.class))).willReturn(carReservation);
        when(carStockService.findById(request.getCarStockId())).thenReturn(carStock);

        // when
        CarReservation newCarReservation = carReservationService.reserve(member, request);

        // then
        then(carReservationRepository).should().save(any(CarReservation.class));
        then(carStockService).should().findById(any(Long.class));
        assertNotNull(newCarReservation);
    }

    @Test
    void 예약가능_상태가_아닌_시험차량은_예약할수_없다() {
        // given
        final CarStock unavailableCarStock =
                CarEntityFactory.createCarStockBuilder().status(StockStatus.RESERVED).build();
        final CarReservationRequest request =
                CarReservationRequestFactory.createCarReservationRequest();
        when(carStockService.findById(request.getCarStockId())).thenReturn(unavailableCarStock);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carReservationService.reserve(member, request);
                });
        then(carStockService).should().findById(any(Long.class));
        then(carReservationRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 예약한_시험차량을_반납한다() {
        // given
        final ReturnCarReservationRequest request =
                CarReservationRequestFactory.createReturnCarReservationRequest(carReservationIds);
        given(carReservationRepository.saveAll(anyCollection())).willReturn(carReservations);
        given(
                        carReservationRepository.findAllWithCarStockByIdInAndMemberId(
                                carReservationIds, member.getId()))
                .willReturn(carReservations);

        // when
        List<CarReservation> result = carReservationService.returnCarReservation(member, request);

        // then
        then(carReservationRepository).should().saveAll(anyCollection());
        then(carReservationRepository)
                .should()
                .findAllWithCarStockByIdInAndMemberId(
                        request.getCarReservationIds(), member.getId());
        assertEquals(carReservations, result);
    }

    @Test
    void 이미_반납한_시험차량은_다시_반납할수_없다() {
        // given
        final CarReservation reservedCarReservation1 =
                CarEntityFactory.createCarReservationBuilder()
                        .status(ReservationStatus.RETURNED)
                        .build();
        final CarReservation reservedCarReservation2 =
                CarEntityFactory.createCarReservationBuilder()
                        .status(ReservationStatus.RETURNED)
                        .build();
        List<Long> ids = List.of(1L, 2L);
        final ReturnCarReservationRequest request =
                CarReservationRequestFactory.createReturnCarReservationRequest(ids);
        final List<CarReservation> reservedCarReservations =
                List.of(reservedCarReservation1, reservedCarReservation2);
        given(carReservationRepository.findAllWithCarStockByIdInAndMemberId(ids, member.getId()))
                .willReturn(reservedCarReservations);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    carReservationService.returnCarReservation(member, request);
                });
        then(carReservationRepository)
                .should()
                .findAllWithCarStockByIdInAndMemberId(
                        request.getCarReservationIds(), member.getId());
        then(carReservationRepository).shouldHaveNoMoreInteractions();
    }
}
