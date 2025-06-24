package com.expensetrackerdemo.demo.enums;

public enum ExpenseCategoryEnum {
    FOOD(1),
    TRAVEL(2),
    WORK(3),
    SHOPPING(4),
    GROCERIES(5);

    public final int status;

    ExpenseCategoryEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
