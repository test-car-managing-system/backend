package com.testcar.car.domains.expense.entity;

import static com.testcar.car.common.Constant.EXPENSE_AMOUNT;
import static com.testcar.car.common.Constant.EXPENSE_DATE;
import static com.testcar.car.common.Constant.EXPENSE_DESCRIPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.ExpenseEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.member.entity.Member;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ExpenseTest {
    @Test
    public void 새로운_지출이력을_생성한다() {
        // given
        final Member member = MemberEntityFactory.createMember();
        final CarStock carStock = CarStock.builder().build();
        final String description = EXPENSE_DESCRIPTION;
        final long amount = EXPENSE_AMOUNT;
        final LocalDate usedAt = EXPENSE_DATE;

        // when
        final Expense expense =
                Expense.builder()
                        .member(member)
                        .carStock(carStock)
                        .description(description)
                        .amount(amount)
                        .usedAt(usedAt)
                        .build();

        // then
        assertNotNull(expense);
        assertEquals(expense.getMember(), member);
        assertEquals(expense.getCarStock(), carStock);
        assertEquals(expense.getDescription(), description);
        assertEquals(expense.getAmount(), amount);
        assertEquals(expense.getUsedAt(), usedAt);
    }

    @Test
    void 지출이력의_차량재고_정보를_수정한다() {
        // given
        final Expense expense = ExpenseEntityFactory.createExpense();
        final CarStock updateCarStock = CarEntityFactory.createCarStock();

        // when
        expense.updateCarStock(updateCarStock);

        // then
        assertEquals(expense.getCarStock(), updateCarStock);
    }

    @Test
    void 지출이력의_수정인_정보를_수정한다() {
        // given
        final Expense expense = ExpenseEntityFactory.createExpense();
        final Member updateMember = MemberEntityFactory.createMember();

        // when
        expense.updateMemberBy(updateMember);

        // then
        assertEquals(expense.getUpdateMember(), updateMember);
    }

    @Test
    void 지출이력의_상세정보를_수정한다() {
        // given
        final Expense expense = ExpenseEntityFactory.createExpense();
        final String updateDescription = EXPENSE_DESCRIPTION + "update";
        final long updateAmount = EXPENSE_AMOUNT + 1000;
        final LocalDate updateUsedAt = EXPENSE_DATE.plusDays(1);

        // when
        expense.update(updateDescription, updateAmount, updateUsedAt);

        // then
        assertEquals(expense.getDescription(), updateDescription);
        assertEquals(expense.getAmount(), updateAmount);
        assertEquals(expense.getUsedAt(), updateUsedAt);
    }
}
