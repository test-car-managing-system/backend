package com.testcar.car.domains.gasStationHistory.request;

import static com.testcar.car.common.Constant.ANOTHER_CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.ANOTHER_GAS_STATION_HISTORY_AMOUNT;
import static com.testcar.car.common.Constant.ANOTHER_GAS_STATION_NAME;
import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.GAS_STATION_HISTORY_AMOUNT;
import static com.testcar.car.common.Constant.GAS_STATION_NAME;
import static com.testcar.car.common.Constant.NOW;
import static com.testcar.car.common.Constant.YESTERDAY;

import com.testcar.car.domains.gasStationHistory.model.RegisterGasStationHistoryRequest;

public class GasStationHistoryRequestFactory {
    private GasStationHistoryRequestFactory() {}

    public static RegisterGasStationHistoryRequest createRegisterGasStationHistoryRequest() {
        return RegisterGasStationHistoryRequest.builder()
                .gasStationName(GAS_STATION_NAME)
                .stockNumber(CAR_STOCK_NUMBER)
                .amount(GAS_STATION_HISTORY_AMOUNT)
                .usedAt(NOW.toLocalDate())
                .build();
    }

    public static RegisterGasStationHistoryRequest createAnotherRegisterGasStationHistoryRequest() {
        return RegisterGasStationHistoryRequest.builder()
                .gasStationName(ANOTHER_GAS_STATION_NAME)
                .stockNumber(ANOTHER_CAR_STOCK_NUMBER)
                .amount(ANOTHER_GAS_STATION_HISTORY_AMOUNT)
                .usedAt(YESTERDAY.toLocalDate())
                .build();
    }
}
