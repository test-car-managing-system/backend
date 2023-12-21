package com.testcar.car.infra.weather;


import com.testcar.car.common.util.RestTemplateHandler;
import com.testcar.car.infra.weather.model.Items.Item;
import com.testcar.car.infra.weather.model.WeatherForecastApiResponse;
import com.testcar.car.infra.weather.model.WeatherRequest;
import com.testcar.car.infra.weather.model.WeatherResponse;
import com.testcar.car.infra.weather.util.WeatherItemUtil;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private static final String HOST = "apis.data.go.kr";
    private static final String PATH = "/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String TIME_FORMAT = "HHmm";
    private static final DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
    private static final List<Integer> timeSlots = Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23);

    private final WeatherProperty weatherProperty;
    private final RestTemplateHandler restTemplateHandler;

    public WeatherResponse getWeatherForecast(WeatherRequest request) {
        final URI encodedUri = getEncodedUri(request.getX(), request.getY());
        final WeatherForecastApiResponse response =
                restTemplateHandler.get(encodedUri, WeatherForecastApiResponse.class);
        final List<Item> items = response.getResponse().getBody().getItems().getItem();

        final LocalDateTime now = LocalDateTime.now().withMinute(0).withNano(0);
        final String time = now.format(DateTimeFormatter.ofPattern(TIME_FORMAT));

        final List<Item> currentTimeItems =
                items.stream().filter(item -> item.getForecastTime().equals(time)).toList();
        final Integer temperature = WeatherItemUtil.getTemperature(currentTimeItems);
        final String weather = WeatherItemUtil.getWeather(currentTimeItems);

        return new WeatherResponse(weather, temperature);
    }

    private URI getEncodedUri(int x, int y) {
        final LocalDateTime forecastTime = getForecastTime(LocalDateTime.now());
        final String date = forecastTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        final String time = forecastTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        factory.setEncodingMode(EncodingMode.NONE);
        final String url =
                factory.builder()
                        .scheme("https")
                        .host(HOST)
                        .path(PATH)
                        .queryParam("serviceKey", weatherProperty.getApiKey())
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 1000)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", date)
                        .queryParam("base_time", time)
                        .queryParam("nx", x)
                        .queryParam("ny", y)
                        .build()
                        .toString();
        try {
            return new URI(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getForecastTime(LocalDateTime currentTime) {
        int currentHour = currentTime.getHour();
        int previousSlot =
                timeSlots.stream().filter(hour -> hour <= currentHour).findFirst().orElse(23);
        // 현재 시간이 가장 작은 시간대보다 작다면, 전날의 23:00을 선택
        if (previousSlot == 23 && currentHour < timeSlots.get(timeSlots.size() - 1)) {
            currentTime = currentTime.minusDays(1);
        }
        return currentTime.withHour(previousSlot).withMinute(0).withSecond(0).withNano(0);
    }
}
