package com.pyskacz.android.myexpenses.service;

import com.google.inject.Inject;
import com.pyskacz.android.myexpenses.model.Configuration;
import com.pyskacz.android.myexpenses.model.Expense;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class XlsmExpenseService implements IExpenseService {
    private static final int FIRST_EXPENSE_ROW = 7;
    private static final int FIRST_DATA_COLUMN = 1;

    private static final boolean WRITE_FILE = true;
    private static final boolean NO_WRITE_FILE = false;

    @Inject
    private Configuration configuration = new Configuration();

    @Override
    public List<Expense> findAllExpenses() throws ExpenseServiceException {
        Function<Sheet, List<Expense>> findAllExpensesFunction = (sheet -> {
            List<Expense> expenses = new ArrayList<>();

            StringBuilder sb = new StringBuilder();
            for(int i = FIRST_EXPENSE_ROW; !sheet.getRow(i).getCell(FIRST_DATA_COLUMN).toString().isEmpty(); i++) {
                //TODO: fix builder implementaiom
                sb.setLength(0);
                Row row = sheet.getRow(i);
                row.cellIterator().forEachRemaining(cell -> sb.append(cell.toString()).append(","));
                expenses.add(Expense.fromString(sb.toString()));
            }

            return expenses;
        });
        return performSheetOperation(findAllExpensesFunction, NO_WRITE_FILE);
    }

    @Override
    public Expense findLastExpense() throws ExpenseServiceException {
        List<Expense> expenses = findAllExpenses();
        return expenses.get(expenses.size()-1);
    }

    @Override
    public void saveExpense(Expense expense) throws ExpenseServiceException {
        if(null == expense) throw new IllegalArgumentException("Expense was null");
        Function<Sheet, List<Expense>> saveExpensesFunction = (sheet -> {
            int i = 0;
            for(i = FIRST_EXPENSE_ROW; !sheet.getRow(i).getCell(FIRST_DATA_COLUMN).toString().isEmpty(); i++);
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
            InputStream in = new BufferedInputStream(new FileInputStream(configuration.workbookFileLocation));
            Workbook wb = new XSSFWorkbook(in);
            String currentMonthSheetName = com.pyskacz.android.myexpenses.enums.Sheet.CURRENT_MONTH.getName();
            Sheet sheet = wb.getSheet(currentMonthSheetName);
            if(null == sheet.getSheetName()) {
                throw new ExpenseServiceException("\"" + currentMonthSheetName+"\" sheet NOT found. Please add it in the desktop app");
            }

            List<Expense> expenses = function.apply(sheet);
            in.close();

            if(writeFile) {
                try (FileOutputStream out = new FileOutputStream(configuration.workbookFileLocation)) {
                    wb.write(out);
                }
            }

            return expenses;

        } catch (IOException e) {
            throw new ExpenseServiceException(e);
        }
    }
}
