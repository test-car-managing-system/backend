package com.testcar.car.domains.gasStationHistory;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.gasStationHistory.entity.GasStationHistory;
import com.testcar.car.domains.gasStationHistory.model.GasStationHistoryResponse;
import com.testcar.car.domains.gasStationHistory.model.RegisterGasStationHistoryRequest;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.member.entity.Role;
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

@Tag(name = "[주유소 관리]", description = "주유 이력 관련 API")
@RestController
@RequestMapping("/gas-stations/history")
@RequiredArgsConstructor
public class GasStationHistoryController {
    private final GasStationHistoryService gasStationHistoryService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유 이력 관리] 주유 이력 조회", description = "주유 이력을 조건에 맞게 조회합니다.")
    public PageResponse<GasStationHistoryResponse> getGasStationHistoriesByCondition(
            @ParameterObject @ModelAttribute GasStationHistoryFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<GasStationHistoryDto> page =
                gasStationHistoryService.findAllByCondition(condition, pageable);
        return PageResponse.from(page.map(GasStationHistoryResponse::from));
    }

    @GetMapping("/{historyId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유 이력 관리] 주유 이력 상세 조회", description = "주유 이력 상세 정보를 id로 조회합니다.")
    public GasStationHistoryResponse getGasStationHistoryById(@PathVariable Long historyId) {
        final GasStationHistoryDto dto = gasStationHistoryService.findById(historyId);
        return GasStationHistoryResponse.from(dto);
    }

    @PostMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유 이력 관리] 주유 이력 등록", description = "주유 이력을 등록합니다.")
    public GasStationHistoryResponse register(
            @AuthMember Member member,
            @Valid @RequestBody RegisterGasStationHistoryRequest request) {
        final GasStationHistory gasStationHistory =
                gasStationHistoryService.register(member, request);
        return GasStationHistoryResponse.from(gasStationHistory);
    }

    @PatchMapping("/{gasStationHistoryId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유 이력 관리] 주유 이력 수정", description = "주유 이력을 수정합니다.")
    public GasStationHistoryResponse update(
            @AuthMember Member member,
            @PathVariable Long gasStationHistoryId,
            @Valid @RequestBody RegisterGasStationHistoryRequest request) {
        final GasStationHistory gasStationHistory =
                gasStationHistoryService.update(member, gasStationHistoryId, request);
        return GasStationHistoryResponse.from(gasStationHistory);
    }

    @DeleteMapping("/{gasStationHistoryId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[주유 이력 관리] 주유 이력 삭제", description = "주유 이력을 삭제합니다.")
    public GasStationHistoryResponse delete(
            @AuthMember Member member, @PathVariable Long gasStationHistoryId) {
        final GasStationHistory gasStationHistory =
                gasStationHistoryService.delete(member, gasStationHistoryId);
        return GasStationHistoryResponse.from(gasStationHistory);
    }
}
