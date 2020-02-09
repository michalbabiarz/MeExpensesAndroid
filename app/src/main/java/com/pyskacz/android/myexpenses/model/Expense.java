package com.pyskacz.android.myexpenses.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Expense {
    //TODO: fix that
//    private final Date date;
//    private final float amount;

    private final String date;
    private final String amount;
    private final String category;
    private final String subcategory;
    private final String type;
    private final String comment;

    public static Expense fromString(String expenseSetring) {
        String[] tokens = expenseSetring.split(",");
        return new Expense(tokens[0],tokens[1], tokens[2],tokens[3],"","");
    }
}
