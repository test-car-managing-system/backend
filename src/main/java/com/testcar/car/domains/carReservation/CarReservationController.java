package com.testcar.car.domains.carReservation;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.model.CarReservationResponse;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import com.testcar.car.domains.member.Member;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[시험차량 관리] ", description = "시험차량 관리 API")
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarReservationController {
    private final CarReservationService carReservationService;

    @GetMapping("/reservations")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험차량 관리] 시험차량 대여 이력", description = "조건에 맞는 시험차량 대여 이력을 모두 조회합니다.")
    public PageResponse<CarReservationResponse> getCarReservationsByCondition(
            @ParameterObject @ModelAttribute CarReservationFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<CarReservationDto> carReservations =
                carReservationService.findAllPageByCondition(condition, pageable);
        return PageResponse.from(carReservations.map(CarReservationResponse::from));
    }

    @PostMapping("/{carStockId}/reserve")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[시험차량 관리] 시험차량 대여", description = "시험 차량을 예약합니다.")
    public CarReservationResponse postCarReservation(
            @AuthMember Member member, @PathVariable Long carStockId) {
        final CarReservation carReservation = carReservationService.reserve(member, carStockId);
        return CarReservationResponse.from(carReservation);
    }
}
