package com.testcar.car.domains.expense;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.expense.entity.Expense;
import com.testcar.car.domains.expense.model.ExpenseResponse;
import com.testcar.car.domains.expense.model.RegisterExpenseRequest;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.expense.model.vo.ExpenseFilterCondition;
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

@Tag(name = "[지출 관리]", description = "지출 관련 API")
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[지출 내역 관리] 지출 내역 조회", description = "지출 내역을 조건에 맞게 조회합니다.")
    public PageResponse<ExpenseResponse> getExpensesByCondition(
            @ParameterObject @ModelAttribute ExpenseFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<ExpenseDto> page = expenseService.findAllByCondition(condition, pageable);
        return PageResponse.from(page.map(ExpenseResponse::from));
    }

    @GetMapping("/{expenseId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[지출 내역 관리] 지출 이력 조회", description = "지출 이력 상세 정보를 id로 조회합니다.")
    public ExpenseResponse getExpenseById(@PathVariable Long expenseId) {
        final ExpenseDto dto = expenseService.findById(expenseId);
        return ExpenseResponse.from(dto);
    }

    @PostMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[지출 내역 관리] 지출 이력 등록", description = "지출 이력을 등록합니다.")
    public ExpenseResponse register(
            @AuthMember Member member, @Valid @RequestBody RegisterExpenseRequest request) {
        final Expense expense = expenseService.register(member, request);
        return ExpenseResponse.from(expense);
    }

    @PatchMapping("/{expenseId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[지출 내역 관리] 지출 이력 수정", description = "지출 이력을 수정합니다.")
    public ExpenseResponse update(
            @AuthMember Member member,
            @PathVariable Long expenseId,
            @Valid @RequestBody RegisterExpenseRequest request) {
        final Expense expense = expenseService.update(member, expenseId, request);
        return ExpenseResponse.from(expense);
    }

    @DeleteMapping("/{expenseId}")
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[지출 내역 관리] 지출 이력 삭제", description = "지출 이력을 삭제합니다.")
    public Long delete(@AuthMember Member member, @PathVariable Long expenseId) {
        expenseService.delete(member, expenseId);
        return expenseId;
    }
}
