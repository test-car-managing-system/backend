package com.testcar.car.domains.trackReservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.track.TrackService;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.request.TrackRequestFactory;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import com.testcar.car.domains.trackReservation.repository.TrackReservationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackReservationServiceTest {
    @Mock private TrackReservationRepository trackReservationRepository;
    @Mock private TrackService trackService;
    @Mock private TrackReservationSlotService trackReservationSlotService;
    @InjectMocks private TrackReservationService trackReservationService;

    private static Member member;
    private static Track track;
    private static TrackReservation trackReservation;
    private static List<TrackReservation> trackReservations;
    private static Set<TrackReservationSlot> trackReservationSlots;
    private static final Long trackReservationId = 1L;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        track = TrackEntityFactory.createTrack();
        trackReservation = TrackEntityFactory.createTrackReservation();
        trackReservations =
                List.of(
                        TrackEntityFactory.createTrackReservation(),
                        TrackEntityFactory.createTrackReservation());
        trackReservationSlots = TrackEntityFactory.createTrackReservationSlotSet();
    }

    @Test
    void 사용자와_시험장_예약_ID로_엔티티를_가져온다() {
        // given
        when(trackReservationRepository.findByMemberIdAndId(member.getId(), trackReservationId))
                .thenReturn(Optional.of(trackReservation));

        // when
        final TrackReservation result =
                trackReservationService.findByMemberAndId(member, trackReservationId);

        // then
        assertEquals(trackReservation, result);
        verify(trackReservationRepository).findByMemberIdAndId(member.getId(), trackReservationId);
    }

    @Test
    void 시험장예약_ID가_포함된_정보가_DB에_존재하지_않으면_오류가_발생한다() {
        // given
        when(trackReservationRepository.findByMemberIdAndId(member.getId(), trackReservationId))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    trackReservationService.findByMemberAndId(member, trackReservationId);
                });
    }

    @Test
    void 조건에_맞는_시험장예약_리스트를_가져온다() {
        // given
        final TrackReservationFilterCondition condition = new TrackReservationFilterCondition();
        when(trackReservationRepository.findAllByMemberIdAndCondition(member.getId(), condition))
                .thenReturn(trackReservations);

        // when
        final List<TrackReservation> result =
                trackReservationService.findAllByMemberAndCondition(member, condition);

        // then
        assertEquals(trackReservations, result);
        verify(trackReservationRepository).findAllByMemberIdAndCondition(member.getId(), condition);
    }

    @Test
    void 시험장의_특정_일자의_모든_예약슬롯을_조회한다() {
        // given
        final LocalDate date = LocalDate.now();
        when(trackReservationSlotService.findAllByTrackIdAndDate(track.getId(), date))
                .thenReturn(trackReservationSlots);

        // when
        final Set<TrackReservationSlot> result =
                trackReservationService.findUnavailableReservationSlots(track.getId(), date);

        // then
        assertEquals(trackReservationSlots, result);
        verify(trackReservationSlotService).findAllByTrackIdAndDate(track.getId(), date);
    }

    @Test
    void 시험장을_예약한다() {
        // given
        final TrackReservationRequest request = TrackRequestFactory.createTrackReservationRequest();
        when(trackService.findById(track.getId())).thenReturn(track);
        when(trackReservationRepository.save(any(TrackReservation.class)))
                .thenReturn(trackReservation);

        // when
        final TrackReservation result =
                trackReservationService.reserve(member, track.getId(), request);

        // then
        assertEquals(trackReservation, result);
        verify(trackService).findById(track.getId());
        verify(trackReservationRepository).save(any(TrackReservation.class));
        verify(trackReservationSlotService).reserve(track, trackReservation, request);
    }

    @ParameterizedTest
    @EnumSource(
            value = ReservationStatus.class,
            names = {"RESERVED", "USING"})
    void 시험장_예약을_취소_또는_반납한다(ReservationStatus status) {
        // given
        final TrackReservation cancelableTrackReservation =
                TrackEntityFactory.createTrackReservationBuilder().status(status).build();
        when(trackReservationRepository.findByMemberIdAndId(member.getId(), trackReservationId))
                .thenReturn(Optional.of(cancelableTrackReservation));
        when(trackReservationRepository.save(cancelableTrackReservation))
                .thenReturn(cancelableTrackReservation);

        // when
        final TrackReservation result = trackReservationService.cancel(member, trackReservationId);

        // then
        assertEquals(cancelableTrackReservation, result);
        verify(trackReservationRepository).findByMemberIdAndId(member.getId(), trackReservationId);
        verify(trackReservationRepository).save(cancelableTrackReservation);
    }

    @ParameterizedTest
    @EnumSource(
            value = ReservationStatus.class,
            names = {"CANCELED", "COMPLETED"})
    void 시험장을_반납했거나_이용완료_상태면_반납할수_없다(ReservationStatus status) {
        // given
        final TrackReservation notCancelableTrackReservation =
                TrackEntityFactory.createTrackReservationBuilder().status(status).build();
        when(trackReservationRepository.findByMemberIdAndId(member.getId(), trackReservationId))
                .thenReturn(Optional.of(notCancelableTrackReservation));

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    trackReservationService.cancel(member, trackReservationId);
                });
    }
}
