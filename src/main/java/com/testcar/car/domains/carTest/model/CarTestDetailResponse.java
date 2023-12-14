package com.testcar.car.domains.carTest.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.track.entity.Track;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarTestDetailResponse {
    @Schema(description = "주유 이력 ID", example = "1")
    private final Long id;

    @Schema(description = "시험장명", example = "서산주행시험장")
    private final String trackName;

    @Schema(description = "사용자명", example = "홍길동")
    private final String memberName;

    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private final String location;

    @Schema(description = "부서", example = "시스템관리팀")
    private final String departmentName;

    @Schema(description = "길이", example = "8")
    private final Double length;

    @Schema(description = "특성", example = "젖은 도로")
    private final String description;

    @Schema(description = "차량명", example = "아반떼")
    private final String carName;

    @Schema(description = "재고번호", example = "2023010100001")
    private final String stockNumber;

    @Schema(description = "주행결과", example = "이상없음")
    private final String result;

    @DateFormat
    @Schema(description = "시험일자", example = "2021-01-01")
    private final LocalDateTime performedAt;

    @Schema(description = "비고", example = "타이어 정비가 필요해보임")
    private final String memo;

    public static CarTestDetailResponse from(CarTestDto carTestDto) {
        final CarTest carTest = carTestDto.getCarTest();
        final Track track = carTestDto.getTrack();
        return CarTestDetailResponse.builder()
                .id(carTest.getId())
                .trackName(track.getName())
                .memberName(carTestDto.getMemberName())
                .location(track.getLocation())
                .departmentName(carTestDto.getDepartmentName())
                .length(track.getLength())
                .description(track.getDescription())
                .carName(carTestDto.getCarName())
                .stockNumber(carTestDto.getStockNumber())
                .result(carTest.getResult())
                .performedAt(carTest.getPerformedAt())
                .memo(carTest.getMemo())
                .build();
    }
}
