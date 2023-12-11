package com.testcar.car.domains.carTest;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.carTest.entity.CarTest;
import com.testcar.car.domains.carTest.model.CarTestRequest;
import com.testcar.car.domains.carTest.model.CarTestResponse;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험 수행 이력 등록", description = "시험 수행 이력을 등록합니다.")
    public CarTestResponse register(
            @AuthMember Member member, @Valid @RequestBody CarTestRequest request) {
        final CarTest carTest = carTestService.register(member, request);
        return CarTestResponse.from(carTest);
    }

    @PatchMapping("/{carTestId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험 수행 이력 수정", description = "시험 수행 이력을 수정합니다.")
    public CarTestResponse update(
            @AuthMember Member member,
            @PathVariable Long carTestId,
            @Valid @RequestBody CarTestRequest request) {
        final CarTestDto carTestDto = carTestService.update(member, carTestId, request);
        return CarTestResponse.from(carTestDto);
    }

    @DeleteMapping("/{carTestId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험장 관리] 시험 수행 이력 삭제", description = "시험 수행 이력을 삭제합니다.")
    public Long delete(@AuthMember Member member, @PathVariable Long carTestId) {
        carTestService.delete(member, carTestId);
        return carTestId;
    }
}
