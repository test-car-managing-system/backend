package com.testcar.car.common;

import static com.testcar.car.common.Constant.CAR_DISPLACEMENT;
import static com.testcar.car.common.Constant.CAR_NAME;
import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.CAR_TEST_RESULT;
import static com.testcar.car.common.Constant.CAR_TYPE;
import static com.testcar.car.common.Constant.EXPIRED_AT;
import static com.testcar.car.common.Constant.STARTED_AT;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Car.CarBuilder;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.CarReservation.CarReservationBuilder;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.CarStock.CarStockBuilder;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.entity.CarTest.CarTestBuilder;

public class CarEntityFactory {
    private CarEntityFactory() {}

    public static Car createCar() {
        return createCarBuilder().build();
    }

    public static CarBuilder createCarBuilder() {
        return Car.builder().name(CAR_NAME).displacement(CAR_DISPLACEMENT).type(CAR_TYPE);
    }

    public static CarStock createCarStock() {
        return createCarStockBuilder().build();
    }

    public static CarStockBuilder createCarStockBuilder() {
        return CarStock.builder().car(createCar()).stockNumber(CAR_STOCK_NUMBER);
    }

    public static CarReservation createCarReservation() {
        return createCarReservationBuilder().build();
    }

    public static CarReservationBuilder createCarReservationBuilder() {
        return CarReservation.builder()
                .member(MemberEntityFactory.createMember())
                .carStock(createCarStock())
                .startedAt(STARTED_AT)
                .expiredAt(EXPIRED_AT)
                .status(ReservationStatus.RESERVED);
    }

    public static CarTest createCarTest() {
        return createCarTestBuilder().build();
    }

    public static CarTestBuilder createCarTestBuilder() {
        return CarTest.builder()
                .member(MemberEntityFactory.createMember())
                .carStock(createCarStock())
                .performedAt(STARTED_AT)
                .result(CAR_TEST_RESULT);
    }
}
