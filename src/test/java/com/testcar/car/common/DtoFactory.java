package com.testcar.car.common;

import static com.testcar.car.common.CarEntityFactory.createCarStock;
import static com.testcar.car.common.TrackEntityFactory.createTrack;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.expense.entity.Expense;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.member.entity.Member;

public class DtoFactory {
    private DtoFactory() {}

    public static CarReservationDto createCarReservationDto(CarReservation carReservation) {
        final CarStock carStock = carReservation.getCarStock();
        final Car car = carStock.getCar();
        return new CarReservationDto(carReservation, car.getName(), carStock.getStockNumber());
    }

    public static CarTestDto createCarTestDto(CarTest carTest) {
        final Member member = MemberEntityFactory.createMember();
        final Department department = member.getDepartment();
        final CarStock carStock = createCarStock();
        final Car car = carStock.getCar();
        return new CarTestDto(
                carTest,
                createTrack(),
                member.getName(),
                member.getName(),
                department.getName(),
                car.getName(),
                carStock.getStockNumber());
    }

    public static GasStationHistoryDto createGasStationHistoryDto(
            GasStationHistory gasStationHistory) {
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

    public static ExpenseDto createExpenseDto(Expense expense) {
        final Member member = MemberEntityFactory.createMember();
        final Department department = member.getDepartment();
        final CarStock carStock = createCarStock();
        final Car car = carStock.getCar();
        return new ExpenseDto(
                expense,
                member.getName(),
                member.getName(),
                car.getName(),
                carStock.getStockNumber(),
                department.getName());
    }
}
