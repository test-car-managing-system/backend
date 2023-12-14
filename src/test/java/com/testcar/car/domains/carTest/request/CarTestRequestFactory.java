package com.testcar.car.domains.carTest.request;

import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.TRACK_NAME;

import com.testcar.car.domains.carTest.model.CarTestRequest;
import java.time.LocalDate;

public class CarTestRequestFactory {
    private CarTestRequestFactory() {}

    public static CarTestRequest createCarTestRequest() {
        return CarTestRequest.builder()
                .trackName(TRACK_NAME)
                .stockNumber(CAR_STOCK_NUMBER)
                .performedAt(LocalDate.now())
                .result("이상없음")
                .memo("타이어 정비가 필요해보임")
                .build();
    }

    public static CarTestRequest createCarTestRequest(String trackName, String stockNumber) {
        return CarTestRequest.builder()
                .trackName(trackName)
                .stockNumber(stockNumber)
                .performedAt(LocalDate.now())
                .result("이상없음")
                .memo("타이어 정비가 필요해보임")
                .build();
    }
}
