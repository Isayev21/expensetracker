package com.expensetrackerdemo.demo.daos;

import com.expensetrackerdemo.demo.entity.Expense;
import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoExpenses;
import com.expensetrackerdemo.demo.repos.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DaoExpenses {
    @Autowired
    RepoExpenses repoExpenses;
    @Autowired
    RepoUser repoUser;

    public ResponseEntity<String> insert(Expense expense){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = repoUser.findByUserName(userName).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if(expense.getTitle() == null || expense.getTitle().isBlank()){
            return ResponseEntity.badRequest().body("Title cannot be empty");
        }
        if(expense.getAmount() == null || expense.getAmount() <= 0){
            return ResponseEntity.badRequest().body("Amount cannot be less than zero");
        }
        if(expense.getDate() == null){
            expense.setDate(LocalDate.now());
        }
        if(expense.getExpenseCategory() == null){
            return ResponseEntity.badRequest().body("Expense category is required");
        }

        expense.setUser(user);

        repoExpenses.save(expense);
        return ResponseEntity.ok("Data inserted successfully");
    }

    public ResponseEntity<String> update(Expense expense){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repoUser.findByUserName(username).orElseThrow();

        Optional<Expense> optionalExpense = repoExpenses.findById(expense.getId());

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            if(!existingExpense.getUser().getId().equals(user.getId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this expense");
            }
            existingExpense.setTitle(expense.getTitle());
            existingExpense.setAmount(expense.getAmount());
            existingExpense.setExpenseCategory(expense.getExpenseCategory());
            existingExpense.setDate(expense.getDate());

            repoExpenses.save(existingExpense);
           return ResponseEntity.ok("Expense updated successfully");
        } else {
            return (ResponseEntity<String>) ResponseEntity.notFound();
        }
    }

    public ResponseEntity<String> delete(Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repoUser.findByUserName(username).orElseThrow();
        Optional<Expense> optionalExpense = repoExpenses.findById(id);
        if(optionalExpense.isPresent()){
            Expense existingExpense = optionalExpense.get();
            if(!existingExpense.getUser().getId().equals(user.getId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized perform this operation");
            }
            existingExpense.setDeleted(true);
            repoExpenses.save(existingExpense);
            return ResponseEntity.ok("Expense deleted successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }
    }

    public ResponseEntity<List<Expense>> select(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repoUser.findByUserName(username).orElseThrow();
        List<Expense> userExpenses = repoExpenses.findByUser(user);
        if(!userExpenses.isEmpty()){
            return ResponseEntity.ok(userExpenses);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> selectById(Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repoUser.findByUserName(username).orElseThrow();

        Optional<Expense> optionalExpense = repoExpenses.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            if (!expense.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
            return ResponseEntity.ok(expense);
        }else {
            return ResponseEntity.noContent().build();
        }

    }
}
