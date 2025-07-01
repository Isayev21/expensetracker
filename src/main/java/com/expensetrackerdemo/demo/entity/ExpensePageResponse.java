package com.expensetrackerdemo.demo.entity;

import java.util.List;

public class ExpensePageResponse {
    private List<Expense> expenses;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public ExpensePageResponse() {
    }

    public ExpensePageResponse(List<Expense> expenses, int currentPage, int totalPages, long totalItems) {
        this.expenses = expenses;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
