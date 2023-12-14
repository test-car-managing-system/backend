package com.testcar.car.common;

import static com.testcar.car.common.Constant.EXPENSE_AMOUNT;
import static com.testcar.car.common.Constant.EXPENSE_DATE;
import static com.testcar.car.common.Constant.EXPENSE_DESCRIPTION;

import com.testcar.car.domains.expense.entity.Expense;

public class ExpenseEntityFactory {
    private ExpenseEntityFactory() {}

    public static Expense createExpense() {
        return Expense.builder()
                .member(MemberEntityFactory.createMember())
                .carStock(CarEntityFactory.createCarStock())
                .description(EXPENSE_DESCRIPTION)
                .amount(EXPENSE_AMOUNT)
                .usedAt(EXPENSE_DATE)
                .build();
    }
}
