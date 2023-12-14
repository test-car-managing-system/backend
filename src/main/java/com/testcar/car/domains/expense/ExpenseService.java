package com.testcar.car.domains.expense;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.expense.entity.Expense;
import com.testcar.car.domains.expense.exception.ErrorCode;
import com.testcar.car.domains.expense.model.RegisterExpenseRequest;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.expense.model.vo.ExpenseFilterCondition;
import com.testcar.car.domains.expense.repository.ExpenseRepository;
import com.testcar.car.domains.member.entity.Member;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final CarStockService carStockService;
    private final ExpenseRepository expenseRepository;

    /** 조건에 맞는 지출 이력을 모두 조회합니다. */
    public Page<ExpenseDto> findAllByCondition(
            ExpenseFilterCondition condition, Pageable pageable) {
        return expenseRepository.findAllPageByCondition(condition, pageable);
    }

    /** 지출 이력을 id로 조회합니다. */
    public ExpenseDto findById(Long expenseId) {
        return expenseRepository
                .findDetailById(expenseId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND));
    }

    /** 지출 이력을 등록합니다. */
    public Expense register(Member member, RegisterExpenseRequest request) {
        final Expense expense =
                Expense.builder()
                        .member(member)
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .usedAt(request.getUsedAt())
                        .build();
        if (request.getStockNumber() != null && !request.getStockNumber().isBlank()) {
            final CarStock carStock = carStockService.findByStockNumber(request.getStockNumber());
            expense.updateCarStock(carStock);
        }
        return expenseRepository.save(expense);
    }

    /** 지출 이력을 수정합니다. */
    public Expense update(Member member, Long expenseId, RegisterExpenseRequest request) {
        final ExpenseDto expenseDto = this.findById(expenseId);
        final Expense expense = expenseDto.getExpense();
        final String updateStockNumber = request.getStockNumber();

        if (updateStockNumber == null) {
            expense.updateCarStock(null);
        } else if (!Objects.equals(expenseDto.getStockNumber(), request.getStockNumber())) {
            final CarStock carStock = carStockService.findByStockNumber(request.getStockNumber());
            expense.updateCarStock(carStock);
        }
        expense.updateMemberBy(member);
        expense.update(request.getDescription(), request.getAmount(), request.getUsedAt());
        return expenseRepository.save(expense);
    }

    /** 지출 이력을 삭제합니다. */
    public Expense delete(Member member, Long expenseId) {
        final ExpenseDto expenseDto = this.findById(expenseId);
        final Expense expense = expenseDto.getExpense();
        expense.updateMemberBy(member);
        expense.delete();
        return expenseRepository.save(expense);
    }
}
