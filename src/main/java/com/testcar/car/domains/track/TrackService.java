package com.testcar.car.domains.track;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.track.exception.ErrorCode;
import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import com.testcar.car.domains.track.repository.TrackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    /** 시험장을 id로 조회합니다. */
    public Track findById(Long id) {
        return trackRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRACK_NOT_FOUND));
    }

    /** 시험장을 이름으로 조회합니다. */
    public Track findByName(String name) {
        return trackRepository
                .findByNameAndDeletedFalse(name)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRACK_NOT_FOUND));
    }

    /** 시험장 리스트를 id 리스트로 조회합니다. */
    public List<Track> findAllByIdIn(List<Long> ids) {
        List<Track> tracks = trackRepository.findAllByIdInAndDeletedFalse(ids);
        if (tracks.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.TRACK_NOT_FOUND);
        }
        return tracks;
    }

    /** 시험장을 id로 조회합니다. */
    public List<Track> findAllByCondition(TrackFilterCondition condition) {
        return trackRepository.findAllByCondition(condition);
    }

    /** 새로운 시험장을 등록합니다. */
    public Track register(RegisterTrackRequest request) {
        validateNameNotDuplicated(request.getName());
        final Track car = createEntity(request);
        return trackRepository.save(car);
    }

    /** 시험장 정보를 업데이트 합니다. */
    public Track updateById(Long trackId, RegisterTrackRequest request) {
        Track track = this.findById(trackId);
        if (!track.getName().equals(request.getName())) {
            validateNameNotDuplicated(request.getName());
        }
        final Track updateTrack = this.createEntity(request);
        track.update(updateTrack);
        return trackRepository.save(track);
    }

    /** 시험장을 삭제 처리 합니다. (soft delete) */
    public List<Long> deleteAll(DeleteTrackRequest request) {
        final List<Track> tracks = this.findAllByIdIn(request.getIds());
        tracks.forEach(Track::delete);
        return request.getIds();
    }

    /** 영속되지 않은 시험장 엔티티를 생성합니다. */
    private Track createEntity(RegisterTrackRequest request) {
        return Track.builder()
                .name(request.getName())
                .location(request.getLocation())
                .description(request.getDescription())
                .length(request.getLength())
                .build();
    }

    /** 시험장명 중복을 검사합니다. */
    private void validateNameNotDuplicated(String name) {
        if (trackRepository.existsByNameAndDeletedFalse(name)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_TRACK_NAME);
        }
    }
}
