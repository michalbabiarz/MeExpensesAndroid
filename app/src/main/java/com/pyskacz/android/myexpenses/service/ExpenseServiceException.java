package com.pyskacz.android.myexpenses.service;

public class ExpenseServiceException extends Exception {

    public ExpenseServiceException(String message) {
        super(message);
    }

    public ExpenseServiceException(Throwable throwable) {
        super(throwable);
    }
}
