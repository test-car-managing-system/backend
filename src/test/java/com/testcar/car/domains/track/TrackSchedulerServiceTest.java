package com.testcar.car.domains.track;

import static com.testcar.car.common.Constant.TRACK_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.repository.TrackRepository;
import com.testcar.car.infra.kakao.KakaoGeocodingService;
import com.testcar.car.infra.kakao.model.KakaoGeocodingResponse;
import com.testcar.car.infra.weather.WeatherService;
import com.testcar.car.infra.weather.model.WeatherResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackSchedulerServiceTest {
    @Mock private TrackRepository trackRepository;
    @Mock private KakaoGeocodingService kakaoGeocodingService;
    @Mock private WeatherService weatherService;
    @InjectMocks private TrackSchedulerService trackSchedulerService;

    private static Track track;
    private static final Long trackId = 1L;
    private static List<Track> tracks;
    private static final List<Long> trackIds = List.of(1L, 2L);
    private static final String trackName = TRACK_NAME;

    @BeforeAll
    public static void setUp() {
        track = TrackEntityFactory.createTrack();
        tracks = List.of(track);
    }

    @Test
    void 시험장의_위치정보로_좌표를_구할수_있으면_좌표와_날씨를_업데이트한다() {
        // given
        final KakaoGeocodingResponse kakaoGeocodingResponse =
                KakaoGeocodingResponse.builder().x(126.0).y(36.0).build();
        final WeatherResponse weatherResponse =
                WeatherResponse.builder().weather("맑음").temperature(20).build();
        when(trackRepository.findAllByDeletedFalse()).thenReturn(tracks);
        when(trackRepository.saveAll(any())).thenReturn(tracks);
        when(kakaoGeocodingService.geocoding(any())).thenReturn(kakaoGeocodingResponse);
        when(weatherService.getWeatherForecast(any())).thenReturn(weatherResponse);

        // when
        trackSchedulerService.updateAllWeather();

        // then
        assertEquals(kakaoGeocodingResponse.getX(), track.getLongitude());
        assertEquals(kakaoGeocodingResponse.getY(), track.getLatitude());
        assertEquals(weatherResponse.getWeather(), track.getWeather());
        assertEquals(weatherResponse.getTemperature(), track.getTemperature());
        verify(trackRepository).findAllByDeletedFalse();
        verify(trackRepository).saveAll(any());
        verify(kakaoGeocodingService).geocoding(any());
        verify(weatherService).getWeatherForecast(any());
    }

    @Test
    void 저장된_시험장의_좌표정보가_하나라도_null이면_날씨를_업데이트하지_않는다() {
        // given
        final KakaoGeocodingResponse kakaoGeocodingResponse =
                KakaoGeocodingResponse.builder().x(null).y(36.0).build();
        when(trackRepository.findAllByDeletedFalse()).thenReturn(tracks);
        when(trackRepository.saveAll(any())).thenReturn(tracks);
        when(kakaoGeocodingService.geocoding(any())).thenReturn(kakaoGeocodingResponse);

        // when
        trackSchedulerService.updateAllWeather();

        // then
        assertEquals(kakaoGeocodingResponse.getX(), track.getLongitude());
        assertEquals(kakaoGeocodingResponse.getY(), track.getLatitude());
        assertNull(track.getWeather());
        assertNull(track.getTemperature());
        verify(trackRepository).findAllByDeletedFalse();
        verify(trackRepository).saveAll(any());
        verify(kakaoGeocodingService).geocoding(any());
        then(weatherService).shouldHaveNoInteractions();
    }

    @Test
    void 좌표_외부_api에서_오류가_발생하면_좌표를_null로_바꾸고_날씨를_업데이트하지_않는다() {
        // given
        when(trackRepository.findAllByDeletedFalse()).thenReturn(tracks);
        when(trackRepository.saveAll(any())).thenReturn(tracks);
        when(kakaoGeocodingService.geocoding(any())).thenThrow(new RuntimeException());

        // when
        trackSchedulerService.updateAllWeather();

        // then
        assertNull(track.getLatitude());
        assertNull(track.getLongitude());
        assertNull(track.getWeather());
        assertNull(track.getTemperature());
        verify(trackRepository).findAllByDeletedFalse();
        verify(trackRepository).saveAll(any());
        verify(kakaoGeocodingService).geocoding(any());
        then(weatherService).shouldHaveNoInteractions();
    }

    @Test
    void 시험장의_위치정보로_좌표를_구할수_없으면_좌표를_null로_바꾸고_날씨를_업데이트하지_않는다() {
        // given
        final KakaoGeocodingResponse kakaoGeocodingResponse =
                KakaoGeocodingResponse.builder().x(null).y(null).build();
        when(trackRepository.findAllByDeletedFalse()).thenReturn(tracks);
        when(trackRepository.saveAll(any())).thenReturn(tracks);
        when(kakaoGeocodingService.geocoding(any())).thenReturn(kakaoGeocodingResponse);

        // when
        trackSchedulerService.updateAllWeather();

        // then
        assertNull(track.getLatitude());
        assertNull(track.getLongitude());
        assertNull(track.getWeather());
        assertNull(track.getTemperature());
        verify(trackRepository).findAllByDeletedFalse();
        verify(trackRepository).saveAll(any());
        verify(kakaoGeocodingService).geocoding(any());
        then(weatherService).shouldHaveNoInteractions();
    }

    @Test
    void 날씨_외부_api에서_오류가_발생하면_날씨를_null로_바꾼다() {
        // given
        final KakaoGeocodingResponse kakaoGeocodingResponse =
                KakaoGeocodingResponse.builder().x(126.0).y(36.0).build();
        when(trackRepository.findAllByDeletedFalse()).thenReturn(tracks);
        when(trackRepository.saveAll(any())).thenReturn(tracks);
        when(kakaoGeocodingService.geocoding(any())).thenReturn(kakaoGeocodingResponse);
        when(weatherService.getWeatherForecast(any())).thenThrow(new RuntimeException());

        // when
        trackSchedulerService.updateAllWeather();

        // then
        assertEquals(kakaoGeocodingResponse.getX(), track.getLongitude());
        assertEquals(kakaoGeocodingResponse.getY(), track.getLatitude());
        assertNull(track.getWeather());
        assertNull(track.getTemperature());
        verify(trackRepository).findAllByDeletedFalse();
        verify(trackRepository).saveAll(any());
        verify(kakaoGeocodingService).geocoding(any());
        verify(weatherService).getWeatherForecast(any());
    }
}
