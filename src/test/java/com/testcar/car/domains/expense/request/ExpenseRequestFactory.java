package com.testcar.car.domains.expense.request;

import static com.testcar.car.common.Constant.ANOTHER_CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.EXPENSE_AMOUNT;
import static com.testcar.car.common.Constant.EXPENSE_DATE;
import static com.testcar.car.common.Constant.EXPENSE_DESCRIPTION;

import com.testcar.car.domains.expense.model.RegisterExpenseRequest;

public class ExpenseRequestFactory {
    private ExpenseRequestFactory() {}

    public static RegisterExpenseRequest createRegisterExpenseRequestWithStock() {
        return RegisterExpenseRequest.builder()
                .description(EXPENSE_DESCRIPTION)
                .stockNumber(CAR_STOCK_NUMBER)
                .usedAt(EXPENSE_DATE)
                .amount(EXPENSE_AMOUNT)
                .build();
    }

    public static RegisterExpenseRequest createRegisterExpenseRequestWithAnotherStock() {
        return RegisterExpenseRequest.builder()
                .description(EXPENSE_DESCRIPTION)
                .stockNumber(ANOTHER_CAR_STOCK_NUMBER)
                .usedAt(EXPENSE_DATE)
                .amount(EXPENSE_AMOUNT)
                .build();
    }

    public static RegisterExpenseRequest createRegisterExpenseRequestWithoutStock() {
        return RegisterExpenseRequest.builder()
                .description(EXPENSE_DESCRIPTION)
                .usedAt(EXPENSE_DATE)
                .amount(EXPENSE_AMOUNT)
                .build();
    }
}
