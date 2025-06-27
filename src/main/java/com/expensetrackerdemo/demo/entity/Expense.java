package com.expensetrackerdemo.demo.entity;

import com.expensetrackerdemo.demo.enums.ExpenseCategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Expense {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Double amount;

    private ExpenseCategoryEnum expenseCategory;

    private LocalDate date;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean deleted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense() {
    }

    public Expense(Long id, String title, Double amount, ExpenseCategoryEnum expenseCategory, LocalDate date, boolean deleted) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.date = date;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseCategoryEnum getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategoryEnum expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", expenseCategory=" + expenseCategory +
                ", date=" + date +
                ", deleted=" + deleted +
                ", user=" + user +
                '}';
    }
}
