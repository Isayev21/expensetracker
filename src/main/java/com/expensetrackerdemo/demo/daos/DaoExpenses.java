package com.expensetrackerdemo.demo.daos;

import com.expensetrackerdemo.demo.entity.Expense;
import com.expensetrackerdemo.demo.repos.RepoExpenses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DaoExpenses {
    @Autowired
    RepoExpenses repoExpenses;

    public ResponseEntity<String> insert(Expense expense){
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

        repoExpenses.save(expense);
        return ResponseEntity.ok("Data inserted successfully");
    }

    public ResponseEntity<String> update(Expense expense){
        Optional<Expense> optionalExpense = repoExpenses.findById(expense.getId());
        ResponseEntity responseEntity = null;

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            existingExpense.setTitle(expense.getTitle());
            existingExpense.setAmount(expense.getAmount());
            existingExpense.setExpenseCategory(expense.getExpenseCategory());
            existingExpense.setDate(expense.getDate());

            repoExpenses.save(existingExpense);
           responseEntity = ResponseEntity.ok("Expense updated successfully");
            //return ResponseEntity.ok("Expense updated successfully");
        } else {
            responseEntity = (ResponseEntity) ResponseEntity.notFound();
        }

        return responseEntity;
    }

    public ResponseEntity<String> delete(Long id){
        Optional<Expense> optionalExpense = repoExpenses.findById(id);
        if(optionalExpense.isPresent()){
            Expense existingExpense = optionalExpense.get();
            existingExpense.setDeleted(true);
            repoExpenses.save(existingExpense);
            return ResponseEntity.ok("Expense deleted successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }
    }

    public ResponseEntity<List<Expense>> select(){
        List<Expense> expenses = repoExpenses.findAll();
        if(!expenses.isEmpty()){
            return ResponseEntity.ok(expenses);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> selectById(Long id){
        Optional<Expense> optionalExpense = repoExpenses.findById(id);
        if(optionalExpense.isPresent()){
            return ResponseEntity.ok(optionalExpense.get());
        }else {
            return ResponseEntity.noContent().build();
        }

    }

}
