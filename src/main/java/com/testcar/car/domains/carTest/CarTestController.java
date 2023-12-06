package com.testcar.car.domains.carTest;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.carTest.model.CarTestResponse;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[시험장 관리] 시험 수행 이력", description = "차량 시험 수행 결과를 관리합니다.")
@RestController
@RequestMapping("/cars/tests")
@RequiredArgsConstructor
public class CarTestController {
    private final CarTestService carTestService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(
            summary = "[시험장 관리] 시험 수행 이력 조회 필터",
            description = "조건에 맞는 모든 시험 수행 이력을 페이지네이션으로 조회합니다.")
    public PageResponse<CarTestResponse> getCarTestsByCondition(
            @ParameterObject @ModelAttribute CarTestFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<CarTestDto> carTests =
                carTestService.findAllPageByCondition(condition, pageable);
        return PageResponse.from(carTests.map(CarTestResponse::from));
    }

    @GetMapping("/{carTestId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험 수행 이력 상세", description = "시험 수행 이력을 id로 조회합니다.")
    public CarTestResponse getCarTestsByCondition(@PathVariable Long carTestId) {
        final CarTestDto carTest = carTestService.findById(carTestId);
        return CarTestResponse.from(carTest);
    }
}
