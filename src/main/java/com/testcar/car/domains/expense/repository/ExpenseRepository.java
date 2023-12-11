package com.testcar.car.domains.expense.repository;


import com.testcar.car.domains.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseCustomRepository {}
