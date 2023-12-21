package com.testcar.car.domains.track;


import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.repository.TrackRepository;
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
    private final TrackService trackService;
    private final TrackRepository trackRepository;

    /** 매 시간 시험장의 좌표가 존재하면 기상 정보를 업데이트 한다. */
    @Scheduled(cron = "0 0 * * * ?")
    public void updateAllWeather() {
        final List<Track> tracks =
                trackRepository.findAllByLongitudeNotNullAndLatitudeNotNullAndDeletedFalse();
        tracks.forEach(trackService::updateWeatherIfGeoLocationExists);
        trackRepository.saveAll(tracks);
    }
}
