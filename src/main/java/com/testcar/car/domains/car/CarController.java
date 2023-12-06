package com.testcar.car.domains.car;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.model.CarResponse;
import com.testcar.car.domains.car.model.RegisterCarRequest;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[차량 관리] 차량 조회 필터", description = "조건에 맞는 모든 차량을 페이지네이션으로 조회합니다.")
    public PageResponse<CarResponse> getMembersByCondition(
            @ParameterObject @ModelAttribute CarFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<Car> cars = carService.findAllPageByCondition(condition, pageable);
        return PageResponse.from(cars.map(CarResponse::from));
    }

    @GetMapping("/{carId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[차량 관리] 사용자 상세 정보", description = "차량 상세 정보를 가져옵니다.")
    public CarResponse getMemberById(@PathVariable Long carId) {
        final Car car = carService.findById(carId);
        return CarResponse.from(car);
    }

    @PostMapping("/register")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[차량 관리] 차량 등록", description = "(관리자) 새로운 차량을 등록합니다.")
    public CarResponse register(@Valid @RequestBody RegisterCarRequest request) {
        final Car car = carService.register(request);
        return CarResponse.from(car);
    }

    @PatchMapping("/{carId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[차량 관리] 차량 정보 수정", description = "(관리자) 차량 정보를 수정합니다.")
    public CarResponse update(
            @PathVariable Long carId, @Valid @RequestBody RegisterCarRequest request) {
        final Car car = carService.updateById(carId, request);
        return CarResponse.from(car);
    }

    @DeleteMapping("/{carId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[차량 관리] 차량 삭제", description = "(관리자) 차량을 삭제합니다.")
    public CarResponse withdraw(@PathVariable Long carId) {
        final Car car = carService.deleteById(carId);
        return CarResponse.from(car);
    }
}
