package com.testcar.car.domains.gasStation.entity;

import static com.testcar.car.common.Constant.GAS_STATION_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.testcar.car.common.GasStationEntityFactory;
import org.junit.jupiter.api.Test;

public class GasStationTest {
    @Test
    public void 새로운_주유소를_생성한다() {
        // given
        final String name = GAS_STATION_NAME;

        // when
        final GasStation gasStation = GasStation.builder().name(name).build();

        // then
        assertNotNull(gasStation);
        assertThat(gasStation.getName()).isEqualTo(name);
    }

    @Test
    public void 주유소_이름을_변경한다() {
        // given
        final GasStation gasStation = GasStationEntityFactory.createGasStation();
        final String updatedName = "updatedName";

        // when
        gasStation.update(updatedName);

        // then
        assertThat(gasStation.getName()).isEqualTo(updatedName);
    }
}
