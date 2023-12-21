package com.testcar.car.domains.track;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.exception.ErrorCode;
import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import com.testcar.car.domains.track.repository.TrackRepository;
import com.testcar.car.infra.kakao.KakaoGeocodingService;
import com.testcar.car.infra.kakao.model.KakaoGeocodingResponse;
import com.testcar.car.infra.weather.WeatherService;
import com.testcar.car.infra.weather.model.WeatherRequest;
import com.testcar.car.infra.weather.model.WeatherResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final KakaoGeocodingService kakaoGeocodingService;
    private final WeatherService weatherService;

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
        Track track = createEntity(request);
        updateGeoLocationIfExists(track);
        updateWeatherIfGeoLocationExists(track);
        return trackRepository.save(track);
    }

    /** 시험장 정보를 업데이트 합니다. */
    public Track updateById(Long trackId, RegisterTrackRequest request) {
        Track track = this.findById(trackId);
        if (!track.getName().equals(request.getName())) {
            validateNameNotDuplicated(request.getName());
        }
        final Track updateTrack = this.createEntity(request);
        track.update(updateTrack);
        updateGeoLocationIfExists(track);
        updateWeatherIfGeoLocationExists(track);
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

    /** 시험장 위치를 기반으로 좌표를 가져옵니다 */
    public void updateWeatherIfGeoLocationExists(Track track) {
        final Double latitude = track.getLatitude();
        final Double longitude = track.getLongitude();
        if (latitude == null || longitude == null) {
            track.updateWeather(null, null);
            return;
        }

        try {
            final WeatherRequest request =
                    new WeatherRequest(latitude.intValue(), longitude.intValue());
            final WeatherResponse response = weatherService.getWeatherForecast(request);
            track.updateWeather(response.getWeather(), response.getTemperature());
        } catch (Exception e) {
            track.updateWeather(null, null);
            log.error("시험장 위치를 기반으로 날씨를 가져오는데 실패했습니다.", e);
        }
    }

    /** 시험장 위치를 기반으로 좌표를 가져옵니다 */
    private void updateGeoLocationIfExists(Track track) {
        try {
            final KakaoGeocodingResponse response =
                    kakaoGeocodingService.geocoding(track.getLocation());
            track.updateGeoLocation(response.getX(), response.getY());
        } catch (Exception e) {
            track.updateGeoLocation(null, null);
            log.error("시험장 위치를 기반으로 좌표를 가져오는데 실패했습니다.", e);
        }
    }
}
