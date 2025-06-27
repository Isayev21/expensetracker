package com.expensetrackerdemo.demo.repos;

import com.expensetrackerdemo.demo.entity.Expense;
import com.expensetrackerdemo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoExpenses extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
