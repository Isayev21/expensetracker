package com.expensetrackerdemo.demo.apis;

import com.expensetrackerdemo.demo.daos.DaoExpenses;
import com.expensetrackerdemo.demo.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenseTracker/expense")
public class ApiExpense {
    @Autowired
    DaoExpenses daoExpenses;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody Expense expense){
        return daoExpenses.insert(expense);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        return daoExpenses.delete(id);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Expense expense){
        return daoExpenses.update(expense);
    }

    @GetMapping("/select")
    public ResponseEntity<List<Expense>> select(){
        return daoExpenses.select();
    }

    @GetMapping("/selectById")
    public ResponseEntity<?> selectById(@RequestParam Long id){
        return daoExpenses.selectById(id);
    }
}
