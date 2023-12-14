package com.testcar.car.domains.gasStationHistory;

import static com.testcar.car.common.Constant.ANOTHER_CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.GAS_STATION_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.DtoFactory;
import com.testcar.car.common.GasStationEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.gasStation.GasStationService;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.model.RegisterGasStationHistoryRequest;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import com.testcar.car.domains.gasStationHistory.repository.GasStationHistoryRepository;
import com.testcar.car.domains.gasStationHistory.request.GasStationHistoryRequestFactory;
import com.testcar.car.domains.member.entity.Member;
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
public class GasStationHistoryServiceTest {
    @Mock private CarStockService carStockService;
    @Mock private GasStationService gasStationService;
    @Mock private GasStationHistoryRepository gasStationHistoryRepository;
    @InjectMocks private GasStationHistoryService gasStationHistoryService;

    private static Member member;
    private static CarStock carStock;
    private static CarStock anotherCarStock;
    private static GasStation gasStation;
    private static GasStation anotherGasStation;
    private static GasStationHistory gasStationHistory;
    private static GasStationHistoryDto gasStationHistoryDto;
    private static final Long gasStationHistoryId = 1L;
    private static final String gasStationName = GAS_STATION_NAME;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        carStock = CarEntityFactory.createCarStock();
        anotherCarStock =
                CarEntityFactory.createCarStockBuilder()
                        .stockNumber(ANOTHER_CAR_STOCK_NUMBER)
                        .build();
        gasStation = GasStationEntityFactory.createGasStation();
        anotherGasStation = GasStationEntityFactory.createAnotherGasStation();
        gasStationHistory = GasStationEntityFactory.createGasStationHistory();
        gasStationHistoryDto = DtoFactory.createGasStationHistoryDto(gasStationHistory);
    }

    @Test
    void 조건에_맞는_주유_이력을_DB에서_조회한다() {
        // given
        final GasStationHistoryFilterCondition condition = new GasStationHistoryFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<GasStationHistoryDto> mockPage = mock(Page.class);
        when(gasStationHistoryRepository.findAllPageByCondition(condition, pageable))
                .thenReturn(mockPage);

        // when
        final Page<GasStationHistoryDto> result =
                gasStationHistoryService.findAllByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(gasStationHistoryRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 주유_이력을_ID로_조회한다() {
        // given
        when(gasStationHistoryRepository.findDetailById(gasStationHistoryId))
                .thenReturn(Optional.of(gasStationHistoryDto));

        // when
        final GasStationHistoryDto result = gasStationHistoryService.findById(gasStationHistoryId);

        // then
        assertEquals(gasStationHistoryDto, result);
        verify(gasStationHistoryRepository).findDetailById(gasStationHistoryId);
    }

    @Test
    void DB에_해당_ID의_주유이력이_없다면_오류가_발생한다() {
        // given
        when(gasStationHistoryRepository.findDetailById(gasStationHistoryId))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationHistoryService.findById(gasStationHistoryId);
                });
    }

    @Test
    void 새로운_주유이력을_등록한다() {
        // given
        final RegisterGasStationHistoryRequest request =
                GasStationHistoryRequestFactory.createRegisterGasStationHistoryRequest();
        when(gasStationHistoryRepository.save(any(GasStationHistory.class)))
                .thenReturn(gasStationHistory);
        when(gasStationService.findByName(gasStationName)).thenReturn(gasStation);
        when(carStockService.findByStockNumber(request.getStockNumber())).thenReturn(carStock);

        // when
        final GasStationHistory result = gasStationHistoryService.register(member, request);

        // then
        assertEquals(gasStationHistory, result);
        verify(gasStationService).findByName(gasStationName);
        verify(carStockService).findByStockNumber(request.getStockNumber());
        verify(gasStationHistoryRepository).save(any(GasStationHistory.class));
    }

    @Test
    void 주유이력_등록시_해당_이름의_주유소가_없다면_오류가_발생한다() {
        // given
        final RegisterGasStationHistoryRequest request =
                GasStationHistoryRequestFactory.createRegisterGasStationHistoryRequest();
        when(gasStationService.findByName(gasStationName)).thenThrow(NotFoundException.class);

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationHistoryService.register(member, request);
                });
    }

    @Test
    void 주유이력_등록시_해당_재고번호의_차량재고가_없다면_오류가_발생한다() {
        // given
        final RegisterGasStationHistoryRequest request =
                GasStationHistoryRequestFactory.createRegisterGasStationHistoryRequest();
        when(gasStationService.findByName(gasStationName)).thenReturn(gasStation);
        when(carStockService.findByStockNumber(request.getStockNumber()))
                .thenThrow(NotFoundException.class);

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationHistoryService.register(member, request);
                });
    }

    @Test
    void 주유이력_수정시_재고번호나_주유소명이_바뀌면_DB에서_엔티티를_조회하고_수정한다() {
        // given
        final RegisterGasStationHistoryRequest request =
                GasStationHistoryRequestFactory.createAnotherRegisterGasStationHistoryRequest();
        when(gasStationHistoryRepository.findDetailById(gasStationHistoryId))
                .thenReturn(Optional.of(gasStationHistoryDto));
        when(gasStationService.findByName(request.getGasStationName()))
                .thenReturn(anotherGasStation);
        when(carStockService.findByStockNumber(request.getStockNumber()))
                .thenReturn(anotherCarStock);
        when(gasStationHistoryRepository.save(any(GasStationHistory.class)))
                .thenReturn(gasStationHistory);

        // when
        final GasStationHistory result =
                gasStationHistoryService.update(member, gasStationHistoryId, request);

        // then
        assertEquals(gasStationHistory, result);
        verify(gasStationHistoryRepository).findDetailById(gasStationHistoryId);
        verify(gasStationService).findByName(request.getGasStationName());
        verify(carStockService).findByStockNumber(request.getStockNumber());
        verify(gasStationHistoryRepository).save(any(GasStationHistory.class));
    }

    @Test
    void 주유이력_수정시_재고번호나_주유소명이_같으면_나머지_정보만_수정한다() {
        // given
        final RegisterGasStationHistoryRequest request =
                GasStationHistoryRequestFactory.createRegisterGasStationHistoryRequest();
        when(gasStationHistoryRepository.findDetailById(gasStationHistoryId))
                .thenReturn(Optional.of(gasStationHistoryDto));
        when(gasStationHistoryRepository.save(any(GasStationHistory.class)))
                .thenReturn(gasStationHistory);

        // when
        final GasStationHistory result =
                gasStationHistoryService.update(member, gasStationHistoryId, request);

        // then
        assertEquals(gasStationHistory, result);
        verify(gasStationHistoryRepository).findDetailById(gasStationHistoryId);
        verifyNoInteractions(gasStationService);
        verifyNoInteractions(carStockService);
        verify(gasStationHistoryRepository).save(any(GasStationHistory.class));
    }

    @Test
    void 주유이력을_삭제한다() {
        // given
        when(gasStationHistoryRepository.findDetailById(gasStationHistoryId))
                .thenReturn(Optional.of(gasStationHistoryDto));
        when(gasStationHistoryRepository.save(any(GasStationHistory.class)))
                .thenReturn(gasStationHistory);

        // when
        final GasStationHistory result =
                gasStationHistoryService.delete(member, gasStationHistoryId);

        // then
        assertEquals(gasStationHistory, result);
        assertTrue(result.getDeleted());
        verify(gasStationHistoryRepository).findDetailById(gasStationHistoryId);
        verify(gasStationHistoryRepository).save(any(GasStationHistory.class));
    }
}
