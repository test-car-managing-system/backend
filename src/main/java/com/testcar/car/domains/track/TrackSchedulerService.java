package com.testcar.car.domains.track;


import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.repository.TrackRepository;
import com.testcar.car.infra.kakao.KakaoGeocodingService;
import com.testcar.car.infra.kakao.model.KakaoGeocodingResponse;
import com.testcar.car.infra.weather.WeatherService;
import com.testcar.car.infra.weather.model.WeatherRequest;
import com.testcar.car.infra.weather.model.WeatherResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TrackSchedulerService {
    private final KakaoGeocodingService kakaoGeocodingService;
    private final WeatherService weatherService;
    private final TrackRepository trackRepository;

    /** 매 시간 시험장의 좌표가 존재하면 기상 정보를 업데이트 한다. */
    @Scheduled(cron = "0 0 * * * ?")
    public void updateAllWeather() {
        final List<Track> tracks = trackRepository.findAllByDeletedFalse();
        tracks.forEach(this::updateGeoLocationIfExists);
        tracks.forEach(this::updateWeatherIfGeoLocationExists);
        trackRepository.saveAll(tracks);
    }

    /** 시험장 위치를 기반으로 좌표를 가져옵니다 */
    private void updateWeatherIfGeoLocationExists(Track track) {
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
