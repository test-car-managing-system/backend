package com.testcar.car.domains.gasStation;

import static com.testcar.car.common.Constant.GAS_STATION_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.GasStationEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStation.model.DeleteGasStationRequest;
import com.testcar.car.domains.gasStation.model.RegisterGasStationRequest;
import com.testcar.car.domains.gasStation.model.UpdateGasStationRequest;
import com.testcar.car.domains.gasStation.repository.GasStationRepository;
import com.testcar.car.domains.gasStation.request.GasStationRequestFactory;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GasStationServiceTest {
    @Mock private GasStationRepository gasStationRepository;
    @InjectMocks private GasStationService gasStationService;

    private GasStation gasStation;
    private static final Long gasStationId = 1L;
    private static final String gasStationName = GAS_STATION_NAME;

    @BeforeEach
    public void setUp() {
        gasStation = GasStationEntityFactory.createGasStation();
    }

    @Test
    void 주유소를_이름으로_DB에서_조회한다() {
        // given
        when(gasStationRepository.findByNameAndDeletedFalse(gasStationName))
                .thenReturn(Optional.of(gasStation));

        // when
        final GasStation result = gasStationService.findByName(gasStationName);

        // then
        assertEquals(gasStation, result);
        verify(gasStationRepository).findByNameAndDeletedFalse(gasStationName);
    }

    @Test
    void DB에_해당_이름의_주유소가_없다면_오류가_발생한다() {
        // given
        when(gasStationRepository.findByNameAndDeletedFalse(gasStationName))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationService.findByName(gasStationName);
                });
    }

    @Test
    void 등록된_모든_주유소를_DB에서_조회한다() {
        // given
        when(gasStationRepository.findAllByDeletedFalse()).thenReturn(List.of(gasStation));

        // when
        final List<GasStation> result = gasStationService.findAll();

        // then
        assertEquals(List.of(gasStation), result);
        verify(gasStationRepository).findAllByDeletedFalse();
    }

    @Test
    void 주유소를_ID로_DB에서_조회한다() {
        // given
        when(gasStationRepository.findByIdAndDeletedFalse(gasStationId))
                .thenReturn(Optional.of(gasStation));

        // when
        final GasStation result = gasStationService.findById(gasStationId);

        // then
        assertEquals(gasStation, result);
        verify(gasStationRepository).findByIdAndDeletedFalse(gasStationId);
    }

    @Test
    void DB에_해당_ID의_주유소가_없다면_오류가_발생한다() {
        // given
        when(gasStationRepository.findByIdAndDeletedFalse(gasStationId))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationService.findById(gasStationId);
                });
    }

    @Test
    void 주유소ID_리스트에_포함된_주유소_리스트를_DB에서_조회한다() {
        // given
        final List<Long> gasStationIds = List.of(gasStationId);
        when(gasStationRepository.findAllByIdInAndDeletedFalse(gasStationIds))
                .thenReturn(List.of(gasStation));

        // when
        final List<GasStation> result = gasStationService.findAllByIdIn(gasStationIds);

        // then
        assertEquals(List.of(gasStation), result);
        verify(gasStationRepository).findAllByIdInAndDeletedFalse(gasStationIds);
    }

    @Test
    void 주유소ID_리스트와_주유소_결과_개수가_다르면_오류가_발생한다() {
        // given
        final List<Long> gasStationIds = List.of(gasStationId);
        when(gasStationRepository.findAllByIdInAndDeletedFalse(gasStationIds))
                .thenReturn(List.of());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    gasStationService.findAllByIdIn(gasStationIds);
                });
        verify(gasStationRepository).findAllByIdInAndDeletedFalse(gasStationIds);
    }

    @Test
    void 주유소를_등록한다() {
        // given
        final RegisterGasStationRequest request =
                GasStationRequestFactory.createRegisterGasStationRequest();
        when(gasStationRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(false);
        when(gasStationRepository.save(any(GasStation.class))).thenReturn(gasStation);

        // when
        final GasStation result = gasStationService.register(request);

        // then
        assertEquals(gasStation, result);
        verify(gasStationRepository).existsByNameAndDeletedFalse(request.getName());
        verify(gasStationRepository).save(any(GasStation.class));
    }

    @Test
    void 주유소_정보를_수정한다() {
        // given
        final UpdateGasStationRequest request =
                GasStationRequestFactory.createUpdateGasStationRequest();
        when(gasStationRepository.findByIdAndDeletedFalse(gasStationId))
                .thenReturn(Optional.of(gasStation));
        when(gasStationRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(false);
        when(gasStationRepository.save(any(GasStation.class))).thenReturn(gasStation);

        // when
        final GasStation result = gasStationService.update(gasStationId, request);

        // then
        assertEquals(gasStation, result);
        verify(gasStationRepository).findByIdAndDeletedFalse(gasStationId);
        verify(gasStationRepository).existsByNameAndDeletedFalse(request.getName());
        verify(gasStationRepository).save(any(GasStation.class));
    }

    @Test
    void 요청_주유소명과_현재_주유소명이_같다면_이름_중복을_검증하지_않는다() {
        // given
        final UpdateGasStationRequest request =
                GasStationRequestFactory.createUpdateGasStationRequest(GAS_STATION_NAME);
        when(gasStationRepository.findByIdAndDeletedFalse(gasStationId))
                .thenReturn(Optional.of(gasStation));
        when(gasStationRepository.save(any(GasStation.class))).thenReturn(gasStation);

        // when
        final GasStation result = gasStationService.update(gasStationId, request);

        // then
        assertEquals(gasStation, result);
        assertEquals(gasStation.getName(), result.getName());
        verify(gasStationRepository).findByIdAndDeletedFalse(gasStationId);
        verify(gasStationRepository).save(any(GasStation.class));
    }

    @Test
    void 이미_DB에_존재하는_이름의_주유소로는_수정할수_없다() {
        // given
        final UpdateGasStationRequest request =
                GasStationRequestFactory.createUpdateGasStationRequest();
        when(gasStationRepository.findByIdAndDeletedFalse(gasStationId))
                .thenReturn(Optional.of(gasStation));
        when(gasStationRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    gasStationService.update(gasStationId, request);
                });
        verify(gasStationRepository).findByIdAndDeletedFalse(gasStationId);
        verify(gasStationRepository).existsByNameAndDeletedFalse(request.getName());
    }

    @Test
    void 주유소ID_리스트에_포함된_주유소를_모두_삭제한다() {
        // given
        final List<Long> ids = List.of(gasStationId);
        final List<GasStation> gasStations = List.of(gasStation);
        final DeleteGasStationRequest request =
                GasStationRequestFactory.createDeleteGasStationRequest(ids);
        when(gasStationRepository.findAllByIdInAndDeletedFalse(ids)).thenReturn(gasStations);

        // when
        gasStationService.deleteAll(request);

        // then
        gasStations.forEach(gasStation -> assertTrue(gasStation.getDeleted()));
        verify(gasStationRepository).findAllByIdInAndDeletedFalse(ids);
        verify(gasStationRepository).saveAll(anyCollection());
    }
}
