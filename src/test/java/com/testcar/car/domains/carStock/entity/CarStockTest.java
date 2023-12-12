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
        // Given
        final String stockNumber = "123456789012";
        final StockStatus status = StockStatus.AVAILABLE;

        // When
        final CarStock newCarStock =
                CarStock.builder().car(car).stockNumber(stockNumber).status(status).build();
        // Then
        assertThat(newCarStock.getCar()).isEqualTo(car);
        assertThat(newCarStock.getStockNumber()).isEqualTo(stockNumber);
        assertThat(newCarStock.getStatus()).isEqualTo(status);
    }

    @Test
    public void 차량_재고의_재고번호를_변경한다() {
        // Given
        final String newStockNumber = "123456789013";

        // When
        carStock.updateStockNumber(newStockNumber);

        // Then
        assertThat(carStock.getStockNumber()).isEqualTo(newStockNumber);
    }

    @Test
    public void 차량_재고의_재고상태를_변경한다() {
        // Given
        final StockStatus newStatus = StockStatus.INSPECTION;

        // When
        carStock.updateStatus(newStatus);

        // Then
        assertThat(carStock.getStatus()).isEqualTo(newStatus);
    }
}
