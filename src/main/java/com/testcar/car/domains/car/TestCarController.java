package com.testcar.car.domains.car;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.model.CarResponse;
import com.testcar.car.domains.car.model.RegisterCarRequest;
import com.testcar.car.domains.car.model.TestCarResponse;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
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

@Tag(name = "[시험차량 관리] ", description = "시험차량 관리 API")
@RestController
@RequestMapping("/test-cars")
@RequiredArgsConstructor
public class TestCarController {
    private final TestCarService testCarService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험차량 관리] 차량 조회 필터", description = "조건에 맞는 시험 차량을 페이지네이션으로 조회합니다.")
    public PageResponse<TestCarResponse> getTestCarsByCondition(
            @ParameterObject @ModelAttribute CarFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<Car> cars = testCarService.findAllWithStocksPageByCondition(condition, pageable);
        return PageResponse.from(cars.map(TestCarResponse::from));
    }

    @GetMapping("/{carId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험차량 관리] 시험차량 상세 정보", description = "시험차량 상세 정보를 가져옵니다.")
    public TestCarResponse getCarById(@PathVariable Long carId) {
        final Car car = testCarService.findById(carId);
        return TestCarResponse.from(car);
    }
}
