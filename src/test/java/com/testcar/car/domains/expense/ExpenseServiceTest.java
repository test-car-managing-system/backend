package com.testcar.car.domains.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.DtoFactory;
import com.testcar.car.common.ExpenseEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.carStock.CarStockService;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.expense.entity.Expense;
import com.testcar.car.domains.expense.model.RegisterExpenseRequest;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.expense.model.vo.ExpenseFilterCondition;
import com.testcar.car.domains.expense.repository.ExpenseRepository;
import com.testcar.car.domains.expense.request.ExpenseRequestFactory;
import com.testcar.car.domains.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock private ExpenseRepository expenseRepository;
    @Mock private CarStockService carStockService;
    @InjectMocks private ExpenseService expenseService;

    private static Member member;
    private static CarStock carStock;
    private static Expense expense;
    private static ExpenseDto expenseDto;
    private static final Long expenseId = 1L;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        carStock = CarEntityFactory.createCarStock();
        expense = ExpenseEntityFactory.createExpense();
        expenseDto = DtoFactory.createExpenseDto(expense);
    }

    @Test
    void 조건에_맞는_모든_지출내역을_DB에서_조회한다() {
        // given
        final ExpenseFilterCondition condition = new ExpenseFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<ExpenseDto> mockPage = mock(Page.class);
        when(expenseRepository.findAllPageByCondition(condition, pageable)).thenReturn(mockPage);

        // when
        final Page<ExpenseDto> result = expenseService.findAllByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(expenseRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 지출이력을_ID로_조회한다() {
        // given
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.of(expenseDto));
        // when
        final ExpenseDto result = expenseService.findById(expenseId);

        // then
        assertEquals(expenseDto, result);
        verify(expenseRepository).findDetailById(expenseId);
    }

    @Test
    void DB에_해당_ID의_지출이력이_없다면_오류가_발생한다() {
        // given
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    expenseService.findById(expenseId);
                });
    }

    @Test
    void 재고번호_없이_지출이력을_등록한다() {
        // given
        final RegisterExpenseRequest request =
                ExpenseRequestFactory.createRegisterExpenseRequestWithoutStock();
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // when
        final Expense result = expenseService.register(member, request);

        // then
        assertEquals(expense, result);
        verify(expenseRepository).save(any(Expense.class));
        then(carStockService).shouldHaveNoInteractions();
    }

    @Test
    void 재고번호를_포함하여_지출이력을_등록한다() {
        // given
        final RegisterExpenseRequest request =
                ExpenseRequestFactory.createRegisterExpenseRequestWithStock();
        when(carStockService.findByStockNumber(request.getStockNumber())).thenReturn(carStock);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // when
        final Expense result = expenseService.register(member, request);

        // then
        assertEquals(expense, result);
        verify(expenseRepository).save(any(Expense.class));
        verify(carStockService).findByStockNumber(request.getStockNumber());
    }

    @Test
    void 재고번호가_없어지면_지출이력의_차량재고_정보를_제거한다() {
        // given
        final RegisterExpenseRequest request =
                ExpenseRequestFactory.createRegisterExpenseRequestWithoutStock();
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.of(expenseDto));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // when
        final Expense result = expenseService.update(member, expenseId, request);

        // then
        assertEquals(expense, result);
        verify(expenseRepository).findDetailById(expenseId);
        verify(expenseRepository).save(any(Expense.class));
        then(carStockService).shouldHaveNoInteractions();
    }

    @Test
    void 재고번호가_변경되면_지출이력의_차량재고_정보를_수정한다() {
        // given
        final RegisterExpenseRequest request =
                ExpenseRequestFactory.createRegisterExpenseRequestWithAnotherStock();
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.of(expenseDto));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(carStockService.findByStockNumber(request.getStockNumber())).thenReturn(carStock);

        // when
        final Expense result = expenseService.update(member, expenseId, request);

        // then
        assertEquals(expense, result);
        verify(expenseRepository).findDetailById(expenseId);
        verify(expenseRepository).save(any(Expense.class));
        verify(carStockService).findByStockNumber(request.getStockNumber());
    }

    @Test
    void 재고번호가_동일하면_지출이력_나머지_정보만_수정한다() {
        // given
        final RegisterExpenseRequest request =
                ExpenseRequestFactory.createRegisterExpenseRequestWithStock();
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.of(expenseDto));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // when
        final Expense result = expenseService.update(member, expenseId, request);

        // then
        assertEquals(expense, result);
        verify(expenseRepository).save(any(Expense.class));
        verify(expenseRepository).findDetailById(expenseId);
        then(carStockService).shouldHaveNoInteractions();
    }

    @Test
    void 지출이력을_삭제한다() {
        // given
        when(expenseRepository.findDetailById(expenseId)).thenReturn(Optional.of(expenseDto));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // when
        final Expense result = expenseService.delete(member, expenseId);

        // then
        assertEquals(expense, result);
        assertTrue(result.getDeleted());
        verify(expenseRepository).findDetailById(expenseId);
        verify(expenseRepository).save(any(Expense.class));
    }
}
