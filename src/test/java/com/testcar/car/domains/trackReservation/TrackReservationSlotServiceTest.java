package com.testcar.car.domains.trackReservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.testcar.car.domains.trackReservation.repository.TrackReservationSlotRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private static final Long trackReservationId = 1L;

    @BeforeAll
    public static void setUp() {
        track = TrackEntityFactory.createTrack();
        trackReservation = TrackEntityFactory.createTrackReservation();
        trackReservationSlots = TrackEntityFactory.createTrackReservationSlotSet();
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
        final TrackReservationRequest request = TrackRequestFactory.createTrackReservationRequest();
        final List<TrackReservationSlot> trackReservationSlots =
                List.of(TrackEntityFactory.createTrackReservationSlot());
        when(trackReservationSlotRepository.saveAll(anyCollection()))
                .thenReturn(trackReservationSlots);

        // when
        trackReservationSlotService.reserve(track, trackReservation, request);

        // then
        verify(trackReservationSlotRepository).saveAll(anyCollection());
    }

    @Test
    void 예약요청시간과_마감시간이_현재시간보다_이전이면_시험장을_예약할수_없다() {
        // given
        final TrackReservationRequest request =
                TrackRequestFactory.createInvalidTrackReservationRequest();

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    trackReservationSlotService.reserve(track, trackReservation, request);
                });
        then(trackReservationSlotRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 시험장의_이미_예약된_시간에는_예약할수_없다() {
        // given
        final TrackReservationRequest request = TrackRequestFactory.createTrackReservationRequest();
        when(trackReservationSlotRepository.findAllByTrackIdAndDate(
                        track.getId(), request.getDate()))
                .thenReturn(trackReservationSlots);

        // when, then
        Assertions.assertThrows(
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
