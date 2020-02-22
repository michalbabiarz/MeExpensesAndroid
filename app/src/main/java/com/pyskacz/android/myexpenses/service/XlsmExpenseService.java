package com.pyskacz.android.myexpenses.service;

import com.pyskacz.android.myexpenses.XlsmHandler;
import com.pyskacz.android.myexpenses.model.Expense;
import com.pyskacz.android.myexpenses.utils.NumberUtils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class XlsmExpenseService implements IExpenseService{
    private static final int FIRST_EXPENSE_ROW = 7;
    private static final int FIRST_DATA_COLUMN = 1;
    private static final int NUMBER_OF_SIGNIFICANT_COLUMNS = 6;

    private static final boolean WRITE_FILE = true;
    private static final boolean NO_WRITE_FILE = false;

    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat(".00");

    @Override
    public List<Expense> findAllExpenses() throws ExpenseServiceException {
        Function<Sheet, List<Expense>> findAllExpensesFunction = (sheet -> {
            List<Expense> expenses = new ArrayList<>();

            for (int i = FIRST_EXPENSE_ROW; !sheet.getRow(i).getCell(FIRST_DATA_COLUMN).toString().isEmpty(); i++) {
                Row row = sheet.getRow(i);
                String[] expenseParams = new String[NUMBER_OF_SIGNIFICANT_COLUMNS];
                for (int j = FIRST_DATA_COLUMN, k = 0; j < FIRST_DATA_COLUMN + NUMBER_OF_SIGNIFICANT_COLUMNS; j++, k++) {
                    String data = row.getCell(j).toString();
                    if (j == (FIRST_DATA_COLUMN + 1)) {
                       data = NumberUtils.sumUpStringDoubleValuesAndFormat(data, AMOUNT_FORMAT);
                    }
                    expenseParams[k] = data;
                }

                expenses.add(Expense.fromArray(expenseParams));
            }

            return expenses;
        });
        return performSheetOperation(findAllExpensesFunction, NO_WRITE_FILE);
    }

    @Override
    public Expense findLastExpense() throws ExpenseServiceException {
        List<Expense> expenses = findAllExpenses();
        return expenses.get(expenses.size() - 1);
    }

    @Override
    public void saveExpense(Expense expense) throws ExpenseServiceException {
        if (null == expense) throw new IllegalArgumentException("Expense was null");
        Function<Sheet, List<Expense>> saveExpensesFunction = (sheet -> {
            int i;
            for (i = FIRST_EXPENSE_ROW; !sheet.getRow(i).getCell(FIRST_DATA_COLUMN).toString().isEmpty(); i++)
                ;
            sheet.getRow(i).getCell(1).setCellValue(expense.getDate());
            sheet.getRow(i).getCell(2).setCellValue(expense.getAmount());
            sheet.getRow(i).getCell(3).setCellValue(expense.getCategory());
            sheet.getRow(i).getCell(4).setCellValue(expense.getSubcategory());
            sheet.getRow(i).getCell(5).setCellValue(expense.getType());
            sheet.getRow(i).getCell(6).setCellValue(expense.getComment());

            return null;
        });

        performSheetOperation(saveExpensesFunction, WRITE_FILE);
    }

    private List<Expense> performSheetOperation(Function<Sheet, List<Expense>> function, boolean writeFile) throws ExpenseServiceException {

        try {

            XlsmHandler xlsmHandler = XlsmHandler.getInstance();
            String currentMonthSheetName = com.pyskacz.android.myexpenses.enums.Sheet.CURRENT_MONTH.getName();
            Sheet sheet = xlsmHandler.getSheet(currentMonthSheetName);
            if (null == sheet.getSheetName()) {
                throw new ExpenseServiceException("\"" + currentMonthSheetName + "\" sheet NOT found. Please add it in the desktop app");
            }

            List<Expense> expenses = function.apply(sheet);

            if (writeFile) xlsmHandler.write();

            return expenses;

        } catch (IOException e) {
            throw new ExpenseServiceException(e);
        }
    }
}
