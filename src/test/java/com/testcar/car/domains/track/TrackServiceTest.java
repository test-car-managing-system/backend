package com.testcar.car.domains.track;

import static com.testcar.car.common.Constant.ANOTHER_TRACK_NAME;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import com.testcar.car.domains.track.repository.TrackRepository;
import com.testcar.car.domains.track.request.TrackRequestFactory;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
    @Mock private TrackRepository trackRepository;
    @InjectMocks private TrackService trackService;

    private static Track track;
    private static final Long trackId = 1L;
    private static List<Track> tracks;
    private static final List<Long> trackIds = List.of(1L, 2L);
    private static final String trackName = TRACK_NAME;

    @BeforeAll
    public static void setUp() {
        track = TrackEntityFactory.createTrack();
        tracks = List.of(TrackEntityFactory.createTrack(), TrackEntityFactory.createTrack());
    }

    @Test
    void 시험장_ID로_시험장_엔티티를_가져온다() {
        // given
        when(trackRepository.findByIdAndDeletedFalse(trackId)).thenReturn(Optional.of(track));

        // when
        final Track result = trackService.findById(trackId);

        // then
        assertEquals(track, result);
        verify(trackRepository).findByIdAndDeletedFalse(trackId);
    }

    @Test
    void 시험장_ID가_포함된_시험장이_DB에_존재하지_않으면_오류가_발생한다() {
        // given
        when(trackRepository.findByIdAndDeletedFalse(trackId)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    trackService.findById(trackId);
                });
    }

    @Test
    void 시험장_이름으로_시험장_엔티티를_가져온다() {
        // given
        when(trackRepository.findByNameAndDeletedFalse(trackName)).thenReturn(Optional.of(track));

        // when
        final Track result = trackService.findByName(trackName);

        // then
        assertEquals(track, result);
        verify(trackRepository).findByNameAndDeletedFalse(trackName);
    }

    @Test
    void 시험장_이름이_포함된_시험장이_DB에_존재하지_않으면_오류가_발생한다() {
        // given
        when(trackRepository.findByNameAndDeletedFalse(trackName)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    trackService.findByName(trackName);
                });
    }

    @Test
    void 시험장_id_리스트로_시험장_리스트를_가져온다() {
        // given
        when(trackRepository.findAllByIdInAndDeletedFalse(trackIds)).thenReturn(tracks);

        // when
        final List<Track> result = trackService.findAllByIdIn(trackIds);

        // then
        assertEquals(tracks, result);
        verify(trackRepository).findAllByIdInAndDeletedFalse(trackIds);
    }

    @Test
    void 시험장_id_리스트와_시험장_리스트의_결과_개수가_다르면_오류가_발생한다() {
        // given
        when(trackRepository.findAllByIdInAndDeletedFalse(trackIds)).thenReturn(List.of(track));

        // when
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    trackService.findAllByIdIn(trackIds);
                });

        // then
        verify(trackRepository).findAllByIdInAndDeletedFalse(trackIds);
    }

    @Test
    void 조건에_맞는_시험장_리스트를_가져온다() {
        // given
        final TrackFilterCondition condition = new TrackFilterCondition();
        when(trackRepository.findAllByCondition(condition)).thenReturn(tracks);

        // when
        final List<Track> result = trackService.findAllByCondition(condition);

        // then
        assertEquals(tracks, result);
        verify(trackRepository).findAllByCondition(condition);
    }

    @Test
    void 시험장을_등록한다() {
        // given
        final RegisterTrackRequest request = TrackRequestFactory.createRegisterTrackRequest();
        when(trackRepository.save(any(Track.class))).thenReturn(track);

        // when
        final Track result = trackService.register(request);

        // then
        assertEquals(track, result);
        verify(trackRepository).save(any(Track.class));
    }

    @Test
    void DB에_존재하는_시험장_이름과_같으면_등록할수_없다() {
        // given
        final RegisterTrackRequest request = TrackRequestFactory.createRegisterTrackRequest();
        when(trackRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(true);

        // when
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    trackService.register(request);
                });

        // then
        then(trackRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 시험장_이름과_바꾸려는_이름이_다르면_시험장명_중복검사와_함께_정보를_수정한다() {
        // given
        final RegisterTrackRequest request =
                TrackRequestFactory.createRegisterTrackRequest(ANOTHER_TRACK_NAME);
        when(trackRepository.findByIdAndDeletedFalse(trackId)).thenReturn(Optional.of(track));
        when(trackRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(false);
        when(trackRepository.save(any(Track.class))).thenReturn(track);

        // when
        final Track result = trackService.updateById(trackId, request);

        // then
        assertEquals(track, result);
        verify(trackRepository).findByIdAndDeletedFalse(trackId);
        verify(trackRepository).existsByNameAndDeletedFalse(request.getName());
        verify(trackRepository).save(any(Track.class));
    }

    @Test
    void 시험장_이름과_바꾸려는_이름이_같으면_중복검사를_하지않고_정보를_수정한다() {
        // given
        final RegisterTrackRequest request =
                TrackRequestFactory.createRegisterTrackRequest(TRACK_NAME);
        when(trackRepository.findByIdAndDeletedFalse(trackId)).thenReturn(Optional.of(track));
        when(trackRepository.save(any(Track.class))).thenReturn(track);

        // when
        final Track result = trackService.updateById(trackId, request);

        // then
        assertEquals(track, result);
        verify(trackRepository).findByIdAndDeletedFalse(trackId);
        verify(trackRepository).save(any(Track.class));
    }

    @Test
    void DB에_이미_존재하는_이름의_시험장으로_수정할수_없다() {
        // given
        final RegisterTrackRequest request =
                TrackRequestFactory.createRegisterTrackRequest(ANOTHER_TRACK_NAME);
        when(trackRepository.findByIdAndDeletedFalse(trackId)).thenReturn(Optional.of(track));
        when(trackRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(true);

        // when
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    trackService.updateById(trackId, request);
                });

        // then
        verify(trackRepository).findByIdAndDeletedFalse(trackId);
        then(trackRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void 시험장ID_리스트로_시험장을_모두_삭제한다() {
        // given
        final DeleteTrackRequest request = TrackRequestFactory.createDeleteTrackRequest(trackIds);
        when(trackRepository.findAllByIdInAndDeletedFalse(trackIds)).thenReturn(tracks);

        // when
        final List<Long> result = trackService.deleteAll(request);

        // then
        assertEquals(trackIds, result);
        verify(trackRepository).findAllByIdInAndDeletedFalse(trackIds);
    }
}
