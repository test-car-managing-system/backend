package com.testcar.car.domains.trackReservation;

import static com.testcar.car.common.Constant.DAY_AFTER_TOMORROW;
import static com.testcar.car.common.Constant.TOMORROW;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_DATE;
import static com.testcar.car.common.Constant.YESTERDAY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.request.TrackRequestFactory;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import com.testcar.car.domains.trackReservation.repository.TrackReservationSlotRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackReservationSlotServiceTest {
    @Mock private TrackReservationSlotRepository trackReservationSlotRepository;
    @InjectMocks private TrackReservationSlotService trackReservationSlotService;

    private static Track track;
    private static TrackReservation trackReservation;
    private static Set<TrackReservationSlot> trackReservationSlots;
    private static Set<TrackReservationSlot> anotherTrackReservationSlots;
    private static final Long trackReservationId = 1L;

    @BeforeAll
    public static void setUp() {
        track = TrackEntityFactory.createTrack();
        trackReservation = TrackEntityFactory.createTrackReservation();
        trackReservationSlots = TrackEntityFactory.createTrackReservationSlotSet(trackReservation);
        anotherTrackReservationSlots =
                TrackEntityFactory.createAnotherTrackReservationSlotSet(trackReservation);
    }

    @Test
    void 시험장_ID와_날짜로_해당날짜에_시험장에_예약된_모든_슬롯_엔티티를_가져온다() {
        // given
        final LocalDate date = LocalDate.now();
        when(trackReservationSlotRepository.findAllByTrackIdAndDate(track.getId(), date))
                .thenReturn(trackReservationSlots);

        // when
        final Set<TrackReservationSlot> result =
                trackReservationSlotService.findAllByTrackIdAndDate(track.getId(), date);

        // then
        assertEquals(trackReservationSlots, result);
        verify(trackReservationSlotRepository).findAllByTrackIdAndDate(track.getId(), date);
    }

    @Test
    void 시험장_정보로_해당_시험장의_예약_슬롯을_할당하고_DB에_저장한다() {
        // given
        final List<TrackReservationSlot> slots =
                List.of(TrackEntityFactory.createTrackReservationSlot());
        final TrackReservationRequest request = TrackRequestFactory.createTrackReservationRequest();
        when(trackReservationSlotRepository.findAllByTrackIdAndDate(
                        track.getId(), request.getDate()))
                .thenReturn(Set.of());
        when(trackReservationSlotRepository.saveAll(anyCollection())).thenReturn(slots);

        // when
        trackReservationSlotService.reserve(track, trackReservation, request);

        // then
        verify(trackReservationSlotRepository).saveAll(anyCollection());
    }

    @Test
    void 시험장_정보로_해당_시험장의_예약_슬롯을_비교하고_겹치는_시간이_없다면_DB에_저장한다() {
        // given
        final List<TrackReservationSlot> anotherTrackReservationSlot =
                List.of(TrackEntityFactory.createAnotherTrackReservationSlot(trackReservation));
        final TrackReservationRequest request = TrackRequestFactory.createAnotherTrackReservationRequest();
        when(trackReservationSlotRepository.findAllByTrackIdAndDate(
                        track.getId(), request.getDate()))
                .thenReturn(trackReservationSlots);
        when(trackReservationSlotRepository.saveAll(anyCollection())).thenReturn(anotherTrackReservationSlot);

        // when
        List<TrackReservationSlot> reserve = trackReservationSlotService.reserve(track, trackReservation, request);

        // then
        verify(trackReservationSlotRepository).saveAll(anyCollection());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidTrackReservationSlots")
    void 유효하지_않은_슬롯으로_예약을_요청하면_예외가_발생한다(
            String testName, LocalDate date, List<ReservationSlotVo> slots) {
        final TrackReservationRequest request =
                TrackRequestFactory.createTrackReservationRequest(date, slots);

        assertThrows(
                BadRequestException.class,
                () -> trackReservationSlotService.reserve(track, trackReservation, request));
        then(trackReservationSlotRepository).shouldHaveNoMoreInteractions();
    }

    private static Stream<Arguments> getInvalidTrackReservationSlots() {
        final Arguments[] testArguments = {
            Arguments.of("슬롯이 null 인 경우", TRACK_RESERVATION_DATE, null),
            Arguments.of("슬롯이 비어있는 경우", TRACK_RESERVATION_DATE, List.of()),
            Arguments.of(
                    "슬롯 시간이 현재보다 이전인 경우",
                    YESTERDAY.toLocalDate(),
                    List.of(
                            TrackRequestFactory.createReservationSlotVo(
                                    YESTERDAY.toLocalDate(), 11, 12))),
            Arguments.of(
                    "날짜와 슬롯 시간이 다른 경우",
                    DAY_AFTER_TOMORROW.toLocalDate(),
                    List.of(
                            TrackRequestFactory.createReservationSlotVo(
                                    TOMORROW.withHour(11), TOMORROW.withHour(12)))),
            Arguments.of(
                    "시작시간이 null인 경우",
                    TRACK_RESERVATION_DATE,
                    List.of(
                            TrackRequestFactory.createReservationSlotVo(
                                    null, TOMORROW.withHour(12)))),
            Arguments.of(
                    "종료시간이 null 인 경우",
                    TRACK_RESERVATION_DATE,
                    List.of(
                            TrackRequestFactory.createReservationSlotVo(
                                    TOMORROW.withHour(11), null))),
            Arguments.of(
                    "종료시간이 시작시간보다 빠른 경우",
                    TRACK_RESERVATION_DATE,
                    List.of(
                            TrackRequestFactory.createReservationSlotVo(
                                    TOMORROW.withHour(12), TOMORROW.withHour(1))))
        };
        return Stream.of(testArguments);
    }

    @Test
    void 시험장의_이미_예약된_시간에는_예약할수_없다() {
        // given
        final TrackReservationRequest request = TrackRequestFactory.createTrackReservationRequest();
        when(trackReservationSlotRepository.findAllByTrackIdAndDate(
                        track.getId(), request.getDate()))
                .thenReturn(trackReservationSlots);

        // when, then
        assertThrows(
                BadRequestException.class,
                () -> {
                    trackReservationSlotService.reserve(track, trackReservation, request);
                });
        then(trackReservationSlotRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 시험장_예약_ID로_예약_슬롯을_모두_취소한다() {
        // given
        when(trackReservationSlotRepository.findAllByTrackReservationId(trackReservationId))
                .thenReturn(trackReservationSlots);
        when(trackReservationSlotRepository.saveAll(anyCollection()))
                .thenReturn(trackReservationSlots.stream().toList());

        // when
        List<TrackReservationSlot> deletedTrackReservationSlots =
                trackReservationSlotService.cancelByTrackReservationId(trackReservationId);

        // then
        verify(trackReservationSlotRepository).findAllByTrackReservationId(trackReservationId);
        verify(trackReservationSlotRepository).saveAll(anyCollection());
        deletedTrackReservationSlots.forEach(slot -> assertTrue(slot.getDeleted()));
    }
}
