package com.testcar.car.domains.trackReservation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.repository.TrackReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackReservationSchedulerServiceTest {
    @Mock private TrackReservationRepository trackReservationRepository;
    @InjectMocks private TrackReservationSchedulerService trackReservationSchedulerService;

    private TrackReservation todayTrackReservation;
    private TrackReservation tomorrowTrackReservation;
    private static final LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

    @BeforeEach
    public void setUp() {
        todayTrackReservation = mock(TrackReservation.class);
        TrackReservationSlot slot = mock(TrackReservationSlot.class);
        when(todayTrackReservation.getTrackReservationSlots()).thenReturn(Set.of(slot));
        when(slot.getExpiredAt()).thenReturn(now);
        doCallRealMethod().when(todayTrackReservation).completed();

        tomorrowTrackReservation = mock(TrackReservation.class);
    }

    @Test
    void 사용중인_시험장_마지막_슬롯의_마감시간이_도래하면_시험장을_반납한다() {
        // given
        List<TrackReservation> reservations =
                List.of(todayTrackReservation, tomorrowTrackReservation);
        when(trackReservationRepository.findAllBySlotExpiredAtAndStatusReserved(
                        any(LocalDateTime.class)))
                .thenReturn(reservations);
        when(trackReservationRepository.saveAll(reservations)).thenReturn(reservations);

        // when
        trackReservationSchedulerService.updateAllCompleted();

        // then
        verify(todayTrackReservation).completed();
        verify(tomorrowTrackReservation, never()).completed();
        verify(trackReservationRepository).findAllBySlotExpiredAtAndStatusReserved(now);
        verify(trackReservationRepository).saveAll(reservations);
    }
}
