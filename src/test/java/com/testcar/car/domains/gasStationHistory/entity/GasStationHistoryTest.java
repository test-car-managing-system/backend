package com.testcar.car.domains.gasStationHistory.entity;

import static com.testcar.car.common.Constant.ANOTHER_GAS_STATION_HISTORY_AMOUNT;
import static com.testcar.car.common.Constant.GAS_STATION_HISTORY_AMOUNT;
import static com.testcar.car.common.Constant.NOW;
import static com.testcar.car.common.Constant.YESTERDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.GasStationEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.member.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class GasStationHistoryTest {
    @Test
    public void 새로운_주유_이력를_생성한다() {
        // given
        final Member member = MemberEntityFactory.createMember();
        final GasStation gasStation = GasStationEntityFactory.createGasStation();
        final CarStock carStock = CarEntityFactory.createCarStock();
        final double amount = GAS_STATION_HISTORY_AMOUNT;
        final LocalDateTime usedAt = NOW;

        // when
        final GasStationHistory gasStationHistory =
                GasStationHistory.builder()
                        .member(member)
                        .gasStation(gasStation)
                        .carStock(carStock)
                        .amount(amount)
                        .usedAt(usedAt)
                        .build();

        // then
        assertNotNull(gasStationHistory);
        assertThat(gasStationHistory.getMember()).isEqualTo(member);
        assertThat(gasStationHistory.getGasStation()).isEqualTo(gasStation);
        assertThat(gasStationHistory.getCarStock()).isEqualTo(carStock);
        assertThat(gasStationHistory.getAmount()).isEqualTo(amount);
        assertThat(gasStationHistory.getUsedAt()).isEqualTo(usedAt);
    }

    @Test
    public void 주유_이력의_주유소_정보를_수정한다() {
        // given
        final GasStationHistory gasStationHistory =
                GasStationEntityFactory.createGasStationHistory();
        final GasStation gasStation = GasStationEntityFactory.createGasStation();

        // when
        gasStationHistory.updateGasStation(gasStation);

        // then
        assertThat(gasStationHistory.getGasStation()).isEqualTo(gasStation);
    }

    @Test
    public void 주유_이력의_차량_재고_정보를_수정한다() {
        // given
        final GasStationHistory gasStationHistory =
                GasStationEntityFactory.createGasStationHistory();
        final CarStock carStock = CarEntityFactory.createCarStock();

        // when
        gasStationHistory.updateCarStock(carStock);

        // then
        assertThat(gasStationHistory.getCarStock()).isEqualTo(carStock);
    }

    @Test
    public void 주유_이력의_수정인_정보를_수정한다() {
        // given
        final GasStationHistory gasStationHistory =
                GasStationEntityFactory.createGasStationHistory();
        final Member updateMember = MemberEntityFactory.createMember();

        // when
        gasStationHistory.updateMemberBy(updateMember);

        // then
        assertThat(gasStationHistory.getUpdateMember()).isEqualTo(updateMember);
    }

    @Test
    public void 주유_이력의_주유_정보를_수정한다() {
        // given
        final GasStationHistory gasStationHistory =
                GasStationEntityFactory.createGasStationHistory();
        final double amount = ANOTHER_GAS_STATION_HISTORY_AMOUNT;
        final LocalDateTime usedAt = YESTERDAY;

        // when
        gasStationHistory.update(amount, usedAt);

        // then
        assertThat(gasStationHistory.getAmount()).isEqualTo(amount);
        assertThat(gasStationHistory.getUsedAt()).isEqualTo(usedAt);
    }
}
