package com.testcar.car.domains.carReservation.request;


import com.testcar.car.domains.carReservation.model.CarReservationRequest;
import com.testcar.car.domains.carReservation.model.ReturnCarReservationRequest;
import java.util.List;

public class CarReservationRequestFactory {
    private CarReservationRequestFactory() {}

    public static CarReservationRequest createCarReservationRequest() {
        return CarReservationRequest.builder().carStockId(1L).build();
    }

    public static ReturnCarReservationRequest createReturnCarReservationRequest(List<Long> ids) {
        return ReturnCarReservationRequest.builder().carReservationIds(ids).build();
    }
}
