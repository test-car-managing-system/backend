package com.testcar.car.infra.weather.util;


import com.testcar.car.infra.weather.model.Category;
import com.testcar.car.infra.weather.model.Items.Item;
import com.testcar.car.infra.weather.model.SkyCode;
import java.util.List;

public class WeatherItemUtil {
    private WeatherItemUtil() {}

    public static String getWeather(List<Item> items) {
        return items.stream()
                .filter(item -> item.getCategory() == Category.PTY)
                .findFirst()
                .map(Item::getForecastValue)
                .map(Integer::parseInt)
                .map(SkyCode::of)
                .map(SkyCode::getDescription)
                .orElse(null);
    }

    public static Integer getTemperature(List<Item> items) {
        return items.stream()
                .filter(item -> item.getCategory() == Category.TMP)
                .findFirst()
                .map(Item::getForecastValue)
                .map(Integer::parseInt)
                .orElse(null);
    }
}
