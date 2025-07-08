package com.expensetrackerdemo.demo.apis;

import com.expensetrackerdemo.demo.daos.DaoExpenses;
import com.expensetrackerdemo.demo.entity.Expense;
import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoExpenses;
import com.expensetrackerdemo.demo.repos.RepoUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/expenseTracker/expense")
public class ApiExpense {
    @Autowired
    DaoExpenses daoExpenses;
    @Autowired
    RepoUser repoUser;
    @Autowired
    RepoExpenses repoExpenses;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody Expense expense) {
        return daoExpenses.insert(expense);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return daoExpenses.delete(id);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Expense expense) {
        return daoExpenses.update(expense);
    }

    @GetMapping("/select")
    public ResponseEntity<List<Expense>> select() {
        return daoExpenses.select();
    }

    @GetMapping("/selectById")
    public ResponseEntity<?> selectById(@RequestParam Long id) {
        return daoExpenses.selectById(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedExpenses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return daoExpenses.selectPaginated(page, size);
    }

    @GetMapping("/filterExpenses")
    public ResponseEntity<?> getFilteredExpenses(@RequestParam(required = false) String category,
                                                 @RequestParam(defaultValue = "date") String sortBy,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return daoExpenses.getExpensesWithFilter(category, sortBy, page, size);
    }

    @GetMapping("/getMonthlyTotalExpense")
    public ResponseEntity<?> getMonthlyTotalExpense(@RequestParam int month,
                                                    @RequestParam int year,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        return daoExpenses.getMonthlyTotalExpense(month, year, page,size);
    }

    @GetMapping("/export-csv")
    public void exportExpensesToCsv(HttpServletResponse response) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repoUser.findByUserName(username).orElseThrow();

        List<Expense> expenses = repoExpenses.findByUser(user);

        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses.csv";
        response.setHeader(headerKey, headerValue);

        daoExpenses.exportExpenseCSV(response.getWriter(), expenses);
    }

}
