package com.testcar.car.domains.carTest.model.vo;


import com.querydsl.core.annotations.QueryProjection;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.track.Track;
import lombok.Getter;

@Getter
public class CarTestDto {
    private final CarTest carTest;
    private final Track track;
    private final String memberName;
    private final String departmentName;
    private final String carName;
    private final String stockNumber;
    private final String updateMemberName;

    @QueryProjection
    public CarTestDto(
            CarTest carTest,
            Track track,
            String memberName,
            String updateMemberName,
            String departmentName,
            String carName,
            String stockNumber) {
        this.carTest = carTest;
        this.track = track;
        this.memberName = memberName;
        this.updateMemberName = updateMemberName;
        this.departmentName = departmentName;
        this.carName = carName;
        this.stockNumber = stockNumber;
    }
}
