package com.testcar.car.domains.gasStation;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStation.model.DeleteGasStationRequest;
import com.testcar.car.domains.gasStation.model.GasStationResponse;
import com.testcar.car.domains.gasStation.model.RegisterGasStationRequest;
import com.testcar.car.domains.gasStation.model.UpdateGasStationRequest;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[주유소 관리]", description = "주유소 관리 API")
@RestController
@RequestMapping("/gas-stations")
@RequiredArgsConstructor
public class GasStationController {
    private final GasStationService gasStationService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유소 관리] 주유소 조회", description = "주유소를 모두 조회합니다.")
    public List<GasStationResponse> getGasStationHistories() {
        final List<GasStation> gasStations = gasStationService.findAll();
        return gasStations.stream().map(GasStationResponse::from).toList();
    }

    @GetMapping("/{gasStationId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유소 관리] 주유소 상세 조회", description = "주유소 상세 정보를 id로 조회합니다.")
    public GasStationResponse getGasStationHistoryById(@PathVariable Long gasStationId) {
        final GasStation gasStation = gasStationService.findById(gasStationId);
        return GasStationResponse.from(gasStation);
    }

    @PostMapping
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[주유소 관리] 주유소 등록", description = "주유소를 등록합니다.")
    public GasStationResponse register(@Valid @RequestBody RegisterGasStationRequest request) {
        final GasStation gasStation = gasStationService.register(request);
        return GasStationResponse.from(gasStation);
    }

    @PatchMapping("/{gasStationId}")
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[주유소 관리] 주유소 수정", description = "주유소를 등록합니다.")
    public GasStationResponse update(
            @PathVariable Long gasStationId, @Valid @RequestBody UpdateGasStationRequest request) {
        final GasStation gasStation = gasStationService.update(gasStationId, request);
        return GasStationResponse.from(gasStation);
    }

    @DeleteMapping
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[주유소 관리] 주유소 삭제", description = "주유소를 삭제합니다.")
    public List<Long> deleteAll(@Valid @RequestBody DeleteGasStationRequest request) {
        gasStationService.deleteAll(request);
        return request.getIds();
    }
}
