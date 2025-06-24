package com.expensetrackerdemo.demo.repos;

import com.expensetrackerdemo.demo.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RepoExpenses extends JpaRepository<Expense, Long> {

}
