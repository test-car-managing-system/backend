package com.testcar.car.domains.carStock;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.model.CarStockResponse;
import com.testcar.car.domains.carStock.model.RegisterCarStockRequest;
import com.testcar.car.domains.carStock.model.UpdateCarStockRequest;
import com.testcar.car.domains.carStock.model.vo.CarStockFilterCondition;
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
@RequestMapping("/cars/stocks")
@RequiredArgsConstructor
public class CarStockController {
    private final CarStockService carStockService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[재고 관리] 차량 재고 조회 필터", description = "조건에 맞는 모든 차량을 페이지네이션으로 조회합니다.")
    public PageResponse<CarStockResponse> getCarStocksByCondition(
            @ParameterObject @ModelAttribute CarStockFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<CarStock> carStocks =
                carStockService.findAllPageByCondition(condition, pageable);
        return PageResponse.from(carStocks.map(CarStockResponse::from));
    }

    @GetMapping("/{carStockId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[재고 관리] 재고 상세 정보", description = "차량 재고 상세 정보를 가져옵니다.")
    public CarStockResponse getStockById(@PathVariable Long carStockId) {
        final CarStock carStock = carStockService.findById(carStockId);
        return CarStockResponse.from(carStock);
    }

    @PostMapping("/register")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[재고 관리] 차량 재고 등록", description = "(관리자) 새로운 차량 재고를 등록합니다.")
    public CarStockResponse register(@Valid @RequestBody RegisterCarStockRequest request) {
        final CarStock carStock = carStockService.register(request);
        return CarStockResponse.from(carStock);
    }

    @PatchMapping("/{carStockId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[재고 관리] 차량 재고 정보 수정", description = "(관리자) 차량 재고 정보를 수정합니다.")
    public CarStockResponse update(
            @PathVariable Long carStockId, @Valid @RequestBody UpdateCarStockRequest request) {
        final CarStock carStock = carStockService.updateById(carStockId, request);
        return CarStockResponse.from(carStock);
    }

    @DeleteMapping("/{carStockId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[재고 관리] 재고 삭제", description = "(관리자) 차량 재고를 삭제합니다.")
    public CarStockResponse delete(@PathVariable Long carStockId) {
        final CarStock carStock = carStockService.deleteById(carStockId);
        return CarStockResponse.from(carStock);
    }
}
