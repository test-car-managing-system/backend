package com.testcar.car.domains.carTest.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.track.entity.Track;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarTestResponse {
    @Schema(description = "주유 이력 ID", example = "1")
    private final Long id;

    @Schema(description = "시험장명", example = "서산주행시험장")
    private final String trackName;

    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private final String location;

    @Schema(description = "특성", example = "젖은 도로")
    private final String description;

    @Schema(description = "사용자명", example = "홍길동")
    private final String memberName;

    @Schema(description = "수정인", example = "홍동길")
    private final String updateMemberName;

    @Schema(description = "길이", example = "123.2")
    private final Double length;

    @Schema(description = "부서", example = "시스템관리팀")
    private final String departmentName;

    @Schema(description = "차량명", example = "아반떼")
    private final String carName;

    @Schema(description = "재고번호", example = "2023010100001")
    private final String stockNumber;

    @DateFormat
    @Schema(description = "시험일자", example = "2021-01-01")
    private final LocalDateTime performedAt;

    @Schema(description = "수행결과", example = "통과")
    private final String result;

    @Schema(description = "비고", example = "차량 점검 필요")
    private final String memo;

    @DateTimeFormat
    @Schema(description = "등록일자", example = "2021-01-01 22:33:11")
    private final LocalDateTime createdAt;

    @DateTimeFormat
    @Schema(description = "수정일자", example = "2021-01-01 22:33:11")
    private final LocalDateTime updatedAt;

    public static CarTestResponse from(CarTestDto carTestDto) {
        final CarTest carTest = carTestDto.getCarTest();
        final Track track = carTestDto.getTrack();
        return CarTestResponse.builder()
                .id(carTest.getId())
                .trackName(track.getName())
                .location(track.getLocation())
                .description(track.getDescription())
                .memberName(carTestDto.getMemberName())
                .updateMemberName(carTestDto.getUpdateMemberName())
                .length(track.getLength())
                .departmentName(carTestDto.getDepartmentName())
                .carName(carTestDto.getCarName())
                .stockNumber(carTestDto.getStockNumber())
                .performedAt(carTest.getPerformedAt())
                .result(carTest.getResult())
                .memo(carTest.getMemo())
                .createdAt(carTest.getCreatedAt())
                .updatedAt(carTest.getUpdatedAt())
                .build();
    }

    public static CarTestResponse from(CarTest carTest) {
        final Track track = carTest.getTrack();
        final CarStock carStock = carTest.getCarStock();
        final Car car = carStock.getCar();
        final Member member = carTest.getMember();
        final Department department = member.getDepartment();
        final Member updateMember = carTest.getUpdateMember();

        return CarTestResponse.builder()
                .id(carTest.getId())
                .trackName(track.getName())
                .location(track.getLocation())
                .description(track.getDescription())
                .memberName(member.getName())
                .updateMemberName(updateMember.getName())
                .length(track.getLength())
                .departmentName(department.getName())
                .carName(car.getName())
                .stockNumber(carStock.getStockNumber())
                .performedAt(carTest.getPerformedAt())
                .result(carTest.getResult())
                .memo(carTest.getMemo())
                .createdAt(carTest.getCreatedAt())
                .updatedAt(carTest.getUpdatedAt())
                .build();
    }
}
