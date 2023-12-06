package com.testcar.car.domains.carTest.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
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

    @Schema(description = "사용자명", example = "홍길동")
    private final String memberName;

    @Schema(description = "부서", example = "시스템관리팀")
    private final String departmentName;

    @Schema(description = "차량명", example = "아반떼")
    private final String carName;

    @Schema(description = "재고번호", example = "2023010100001")
    private final String stockNumber;

    @DateFormat
    @Schema(description = "시험일자", example = "2021-01-01")
    private final LocalDateTime performedAt;

    public static CarTestResponse from(CarTestDto carTestDto) {
        final CarTest carTest = carTestDto.getCarTest();
        return CarTestResponse.builder()
                .id(carTest.getId())
                .trackName(carTestDto.getTrack().getName())
                .memberName(carTestDto.getMemberName())
                .carName(carTestDto.getCarName())
                .stockNumber(carTestDto.getStockNumber())
                .performedAt(carTest.getPerformedAt())
                .build();
    }
}
