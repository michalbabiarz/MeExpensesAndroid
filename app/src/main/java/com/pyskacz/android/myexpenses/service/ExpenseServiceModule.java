package com.pyskacz.android.myexpenses.service;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;


public class ExpenseServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IExpenseService.class).to(XlsmExpenseService.class).in(Scopes.SINGLETON);
    }
}
