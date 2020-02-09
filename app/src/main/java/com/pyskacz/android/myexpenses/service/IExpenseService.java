package com.pyskacz.android.myexpenses.service;

import com.pyskacz.android.myexpenses.model.Expense;

import java.util.Collection;

public interface IExpenseService {
    Collection<Expense> findAllExpenses() throws ExpenseServiceException;
    Expense findLastExpense() throws ExpenseServiceException;
    void saveExpense(Expense expense) throws ExpenseServiceException;
}
