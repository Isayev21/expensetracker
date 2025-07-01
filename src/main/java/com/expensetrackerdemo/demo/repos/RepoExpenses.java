package com.expensetrackerdemo.demo.repos;

import com.expensetrackerdemo.demo.entity.Expense;
import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.enums.ExpenseCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RepoExpenses extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Page<Expense> findByUser(User user, Pageable pageable);
    Page<Expense> findByUserAndExpenseCategory(User user, ExpenseCategoryEnum expenseCategory, Pageable pageable);
    Page<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
