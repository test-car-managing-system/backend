package com.testcar.car.domains.carStock.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.domains.car.entity.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CarStockTest {
    private static Car car;
    private static CarStock carStock;

    @BeforeAll
    public static void setUp() {
        car = CarEntityFactory.createCar();
        carStock = CarEntityFactory.createCarStock();
    }

    @Test
    public void 차량_재고를_생성한다() {
        // given
        final String stockNumber = "123456789012";
        final StockStatus status = StockStatus.AVAILABLE;

        // when
        final CarStock newCarStock =
                CarStock.builder().car(car).stockNumber(stockNumber).status(status).build();
        // then
        assertThat(newCarStock.getCar()).isEqualTo(car);
        assertThat(newCarStock.getStockNumber()).isEqualTo(stockNumber);
        assertThat(newCarStock.getStatus()).isEqualTo(status);
    }

    @Test
    public void 차량_재고의_재고번호를_변경한다() {
        // given
        final String newStockNumber = "123456789013";

        // when
        carStock.updateStockNumber(newStockNumber);

        // then
        assertThat(carStock.getStockNumber()).isEqualTo(newStockNumber);
    }

    @Test
    public void 차량_재고의_재고상태를_변경한다() {
        // given
        final StockStatus newStatus = StockStatus.INSPECTION;

        // when
        carStock.updateStatus(newStatus);

        // then
        assertThat(carStock.getStatus()).isEqualTo(newStatus);
    }
}
