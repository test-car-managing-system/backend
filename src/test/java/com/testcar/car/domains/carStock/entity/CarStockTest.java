package com.testcar.car.domains.carStock.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarStockTest {
    private Car car;

    @BeforeEach
    public void setUp() {
        car = Car.builder().name("아반떼").displacement(1.6).type(Type.SEDAN).build();
    }

    @Test
    public void createCarStockTest() {
        // Given
        final String stockNumber = "123456789012";
        final StockStatus status = StockStatus.AVAILABLE;

        // When
        final CarStock carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();
        // Then
        assertThat(carStock.getCar()).isEqualTo(car);
        assertThat(carStock.getStockNumber()).isEqualTo(stockNumber);
        assertThat(carStock.getStatus()).isEqualTo(status);
    }

    @Test
    public void carStockUpdateTest() {
        // Given
        final CarStock carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();

        final CarStock updatedCarStock =
                CarStock.builder()
                        .stockNumber("123456789013")
                        .status(StockStatus.INSPECTION)
                        .build();

        // When
        carStock.updateStockNumber(updatedCarStock.getStockNumber());
        carStock.updateStatus(updatedCarStock.getStatus());

        // Then
        assertThat(carStock.getStockNumber()).isEqualTo(updatedCarStock.getStockNumber());
        assertThat(carStock.getStatus()).isEqualTo(updatedCarStock.getStatus());
    }
}
