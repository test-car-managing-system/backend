package com.testcar.car.common;

import static com.testcar.car.common.Constant.GAS_STATION_NAME;

import com.testcar.car.domains.gasStation.entity.GasStation;

public class GasStationEntityFactory {
    private GasStationEntityFactory() {}

    public static GasStation createGasStation() {
        return GasStation.builder().name(GAS_STATION_NAME).build();
    }

    public static GasStation createGasStation(String name) {
        return GasStation.builder().name(name).build();
    }
}
