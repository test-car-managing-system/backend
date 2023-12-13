package com.testcar.car.domains.car.entity;

import static com.testcar.car.common.Constant.ANOTHER_CAR_NAME;
import static com.testcar.car.common.Constant.CAR_DISPLACEMENT;
import static com.testcar.car.common.Constant.CAR_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.CarEntityFactory;
import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    public void 차량을_생성한다() {
        // given
        final String name = CAR_NAME;
        final Double displacement = CAR_DISPLACEMENT;
        final Type type = Type.SEDAN;

        // when
        final Car car = Car.builder().name(name).displacement(displacement).type(type).build();

        // then
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getDisplacement()).isEqualTo(displacement);
        assertThat(car.getType()).isEqualTo(type);
    }

    @Test
    public void 차량_정보를_변경한다() {
        // given
        final Car car = CarEntityFactory.createCar();
        final Car updatedCar =
                Car.builder().name(ANOTHER_CAR_NAME).displacement(1600.0).type(Type.SEDAN).build();

        // when
        car.update(updatedCar);

        // then
        assertThat(car.getName()).isEqualTo(updatedCar.getName());
        assertThat(car.getDisplacement()).isEqualTo(updatedCar.getDisplacement());
        assertThat(car.getType()).isEqualTo(updatedCar.getType());
    }
}
