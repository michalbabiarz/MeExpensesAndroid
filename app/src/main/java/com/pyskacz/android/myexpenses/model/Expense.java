package com.pyskacz.android.myexpenses.model;

import java.util.Date;
import java.util.StringTokenizer;

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

    public static Expense fromArray(String[] params) {
        return new Expense(params[0],params[1], params[2],params[3],params[4],params[5]);
    }
}
