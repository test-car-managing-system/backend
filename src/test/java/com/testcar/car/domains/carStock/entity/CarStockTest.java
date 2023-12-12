package com.testcar.car.domains.carStock.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarStockTest {
    private Car car;
    private CarStock carStock;

    @BeforeEach
    public void setUp() {
        car = Car.builder().name("아반떼").displacement(1.6).type(Type.SEDAN).build();
        carStock = CarStock.builder().car(car).stockNumber("123456789012").status(StockStatus.AVAILABLE).build();
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
    public void carStockUpdateStockNumberTest() {
        // Given
        final CarStock carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();
        final String newStockNumber = "123456789013";

        // When
        carStock.updateStockNumber(newStockNumber);

        // Then
        assertThat(carStock.getStockNumber()).isEqualTo(newStockNumber);
    }

    @Test
    public void carStockUpdateStatusTest() {
        // Given
        final CarStock carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();
        final StockStatus newStatus = StockStatus.INSPECTION;

        // When
        carStock.updateStatus(newStatus);

        // Then
        assertThat(carStock.getStatus()).isEqualTo(newStatus);
    }
}
