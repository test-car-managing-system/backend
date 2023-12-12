package com.testcar.car.domains.car.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.CarEntityFactory;
import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    public void 차량을_생성한다() {
        // Given
        final String name = "아반떼";
        final Double displacement = 1.6;
        final Type type = Type.SEDAN;

        // When
        final Car car = Car.builder().name(name).displacement(displacement).type(type).build();

        // Then
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getDisplacement()).isEqualTo(displacement);
        assertThat(car.getType()).isEqualTo(type);
    }

    @Test
    public void 차량_정보를_변경한다() {
        // Given
        final Car car = CarEntityFactory.createCar();
        final Car updatedCar =
                Car.builder().name("소나타").displacement(1600.0).type(Type.SEDAN).build();

        // When
        car.update(updatedCar);

        // Then
        assertThat(car.getName()).isEqualTo(updatedCar.getName());
        assertThat(car.getDisplacement()).isEqualTo(updatedCar.getDisplacement());
        assertThat(car.getType()).isEqualTo(updatedCar.getType());
    }
}
