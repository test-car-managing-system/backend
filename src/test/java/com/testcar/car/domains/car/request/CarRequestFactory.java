package com.testcar.car.domains.car.request;

import static com.testcar.car.common.Constant.CAR_DISPLACEMENT;
import static com.testcar.car.common.Constant.CAR_NAME;
import static com.testcar.car.common.Constant.CAR_TYPE;

import com.testcar.car.domains.car.model.RegisterCarRequest;

public class CarRequestFactory {
    private CarRequestFactory() {}

    public static RegisterCarRequest createRegisterCarRequest() {
        return RegisterCarRequest.builder()
                .name(CAR_NAME)
                .type(CAR_TYPE)
                .displacement(CAR_DISPLACEMENT)
                .build();
    }

    public static RegisterCarRequest createRegisterCarRequest(String name) {
        return RegisterCarRequest.builder()
                .name(name)
                .type(CAR_TYPE)
                .displacement(CAR_DISPLACEMENT)
                .build();
    }
}
