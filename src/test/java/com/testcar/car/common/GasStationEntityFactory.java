package com.testcar.car.common;

import static com.testcar.car.common.Constant.ANOTHER_GAS_STATION_NAME;
import static com.testcar.car.common.Constant.GAS_STATION_HISTORY_AMOUNT;
import static com.testcar.car.common.Constant.GAS_STATION_NAME;
import static com.testcar.car.common.Constant.NOW;

import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;

public class GasStationEntityFactory {
    private GasStationEntityFactory() {}

    public static GasStation createGasStation() {
        return GasStation.builder().name(GAS_STATION_NAME).build();
    }

    public static GasStation createAnotherGasStation() {
        return GasStation.builder().name(ANOTHER_GAS_STATION_NAME).build();
    }

    public static GasStationHistory createGasStationHistory() {
        return GasStationHistory.builder()
                .member(MemberEntityFactory.createMember())
                .gasStation(createGasStation())
                .carStock(CarEntityFactory.createCarStock())
                .amount(GAS_STATION_HISTORY_AMOUNT)
                .usedAt(NOW)
                .build();
    }
}
