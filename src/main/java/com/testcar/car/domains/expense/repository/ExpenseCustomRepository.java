package com.testcar.car.domains.expense.repository;


import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.expense.model.vo.ExpenseFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseCustomRepository {
    Optional<ExpenseDto> findDetailById(Long id);

    Page<ExpenseDto> findAllPageByCondition(ExpenseFilterCondition condition, Pageable pageable);
}
