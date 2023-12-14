package com.testcar.car.domains.carTest;

import static com.testcar.car.common.Constant.ANOTHER_CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.ANOTHER_TRACK_NAME;
import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.DtoFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.CarTestRequest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import com.testcar.car.domains.carTest.repository.CarTestRepository;
import com.testcar.car.domains.carTest.request.CarTestRequestFactory;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.track.TrackService;
import com.testcar.car.domains.track.entity.Track;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CarTestServiceTest {
    @Mock private CarStockService carStockService;

    @Mock private TrackService trackService;

    @Mock private CarTestRepository carTestRepository;

    @InjectMocks private CarTestService carTestService;

    private static Member member;
    private static CarStock carStock;
    private static Track track;
    private static CarTest carTest;
    private static CarTestDto carTestDto;
    private static final Long carTestId = 1L;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        carStock = CarEntityFactory.createCarStock();
        track = TrackEntityFactory.createTrack();
        carTest = CarEntityFactory.createCarTest();
        carTestDto = DtoFactory.createCarTestDto(carTest);
    }

    @Test
    void 시험_수행이력_ID로_수행이력_DTO를_가져온다() {
        // given
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.of(carTestDto));

        // when
        CarTestDto result = carTestService.findById(carTestId);

        // then
        assertEquals(carTestDto, result);
        verify(carTestRepository).findDetailById(carTestId);
    }

    @Test
    void 시험_수행이력_ID에_해당하는_정보가_없으면_예외가_발생한다() {
        // given
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NotFoundException.class, () -> carTestService.findById(carTestId));
    }

    @Test
    void 조건에_맞는_시험_수행이력_DTO_페이지를_필터링하여_가져온다() {
        // given
        CarTestFilterCondition condition = new CarTestFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<CarTestDto> mockPage = mock(Page.class);
        when(carTestRepository.findAllPageByCondition(condition, pageable)).thenReturn(mockPage);

        // when
        Page<CarTestDto> result = carTestService.findAllPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(carTestRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 시험_수행이력을_등록한다() {
        // given
        CarTestRequest request = CarTestRequestFactory.createCarTestRequest();
        when(trackService.findByName(request.getTrackName())).thenReturn(track);
        when(carStockService.findByStockNumber(request.getStockNumber())).thenReturn(carStock);
        given(carTestRepository.save(any(CarTest.class))).willReturn(carTestDto.getCarTest());

        // when
        CarTest newCarTestDto = carTestService.register(member, request);

        // then
        then(trackService).should().findByName(anyString());
        then(carStockService).should().findByStockNumber(anyString());
        then(carTestRepository).should().save(any(CarTest.class));
        assertNotNull(newCarTestDto);
    }

    @Test
    void 시험_수행이력의_정보를_수정한다() {
        // given
        CarTestRequest request =
                CarTestRequestFactory.createCarTestRequest(
                        ANOTHER_TRACK_NAME, ANOTHER_CAR_STOCK_NUMBER);
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.of(carTestDto));
        when(carStockService.findByStockNumber(anyString())).thenReturn(carStock);
        when(trackService.findByName(anyString())).thenReturn(track);

        // when
        CarTestDto newCarTestDto = carTestService.update(member, carTestId, request);

        // then
        verify(carTestRepository).findDetailById(carTestId);
        verify(carStockService).findByStockNumber(anyString());
        verify(trackService).findByName(anyString());
        verify(carTestRepository).save(any(CarTest.class));
        assertNotNull(newCarTestDto);
    }

    @Test
    void 수정_요청하는_시험_결과를_조회할수_없으면_예외가_발생한다() {
        // given
        CarTestRequest request = CarTestRequestFactory.createCarTestRequest();
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(
                NotFoundException.class, () -> carTestService.update(member, carTestId, request));
    }

    @Test
    void 수정_요청하는_시험_결과와_차량_재고번호가_같으면_차량_재고를_조회하지_않는다() {
        // given
        CarTestRequest request =
                CarTestRequestFactory.createCarTestRequest(ANOTHER_TRACK_NAME, CAR_STOCK_NUMBER);
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.of(carTestDto));
        when(trackService.findByName(anyString())).thenReturn(track);

        // when
        CarTestDto newCarTestDto = carTestService.update(member, carTestId, request);

        // then
        assertNotNull(newCarTestDto);
        assertEquals(carTestDto.getCarName(), newCarTestDto.getCarName());
        verify(carTestRepository).findDetailById(carTestId);
        verify(trackService).findByName(anyString());
        verify(carTestRepository).save(any(CarTest.class));
    }

    @Test
    void 수정_요청하는_시험_결과와_시험장_이름이_같으면_시험장을_조회하지_않는다() {
        // given
        CarTestRequest request =
                CarTestRequestFactory.createCarTestRequest(TRACK_NAME, ANOTHER_CAR_STOCK_NUMBER);
        when(carTestRepository.findDetailById(carTestId)).thenReturn(Optional.of(carTestDto));
        when(carStockService.findByStockNumber(anyString())).thenReturn(carStock);

        // when
        CarTestDto newCarTestDto = carTestService.update(member, carTestId, request);

        // then
        assertNotNull(newCarTestDto);
        assertEquals(carTestDto.getTrack(), newCarTestDto.getTrack());
        verify(carStockService).findByStockNumber(anyString());
        verify(carTestRepository).findDetailById(carTestId);
        verify(carTestRepository).save(any(CarTest.class));
    }

    @Test
    void 시험_수행이력을_삭제한다() {
        // given
        when(carTestRepository.findById(carTestId)).thenReturn(Optional.of(carTest));

        // when
        Long deletedId = carTestService.delete(member, carTestId);

        // then
        assertEquals(carTest.getDeleted(), true);
        assertEquals(carTest.getUpdateMember(), member);
        assertEquals(carTestId, deletedId);
        verify(carTestRepository).findById(carTestId);
    }

    @Test
    void 삭제할_시험_수행이력이_없다면_예외가_발생한다() {
        // given
        when(carTestRepository.findById(carTestId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NotFoundException.class, () -> carTestService.delete(member, carTestId));
    }
}
