package com.testcar.car.common;

import static com.testcar.car.common.CarEntityFactory.createCarStock;
import static com.testcar.car.common.CarEntityFactory.createCarTest;
import static com.testcar.car.common.TrackEntityFactory.createTrack;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.member.Member;

public class DtoFactory {
    private DtoFactory() {}

    public static CarTestDto createCarTestDto() {
        final Member member = MemberEntityFactory.createMember();
        final Department department = member.getDepartment();
        final CarStock carStock = createCarStock();
        final Car car = carStock.getCar();
        return new CarTestDto(
                createCarTest(),
                createTrack(),
                member.getName(),
                member.getName(),
                department.getName(),
                car.getName(),
                carStock.getStockNumber());
    }

    public static GasStationHistoryDto createGasStationHistoryDto() {
        final GasStationHistory gasStationHistory =
                GasStationEntityFactory.createGasStationHistory();
        final Member member = MemberEntityFactory.createMember();
        final Department department = member.getDepartment();
        final CarStock carStock = createCarStock();
        final Car car = carStock.getCar();
        return new GasStationHistoryDto(
                gasStationHistory,
                member.getName(),
                member.getName(),
                car.getName(),
                carStock.getStockNumber(),
                department.getName());
    }
}
