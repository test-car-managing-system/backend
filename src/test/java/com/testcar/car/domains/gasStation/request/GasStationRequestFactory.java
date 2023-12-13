package com.testcar.car.domains.gasStation.request;

import static com.testcar.car.common.Constant.ANOTHER_GAS_STATION_NAME;
import static com.testcar.car.common.Constant.GAS_STATION_NAME;

import com.testcar.car.domains.gasStation.model.DeleteGasStationRequest;
import com.testcar.car.domains.gasStation.model.RegisterGasStationRequest;
import com.testcar.car.domains.gasStation.model.UpdateGasStationRequest;
import java.util.List;

public class GasStationRequestFactory {
    private GasStationRequestFactory() {}

    public static RegisterGasStationRequest createRegisterGasStationRequest() {
        return RegisterGasStationRequest.builder().name(GAS_STATION_NAME).build();
    }

    public static UpdateGasStationRequest createUpdateGasStationRequest() {
        return UpdateGasStationRequest.builder().name(ANOTHER_GAS_STATION_NAME).build();
    }

    public static DeleteGasStationRequest createDeleteGasStationRequest(List<Long> ids) {
        return DeleteGasStationRequest.builder().ids(ids).build();
    }
}
