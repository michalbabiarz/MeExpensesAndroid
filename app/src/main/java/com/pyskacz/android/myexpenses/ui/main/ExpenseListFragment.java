package com.pyskacz.android.myexpenses.ui.main;

import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.pyskacz.android.myexpenses.R;
import com.pyskacz.android.myexpenses.model.Expense;
import com.pyskacz.android.myexpenses.service.ExpenseServiceException;
import com.pyskacz.android.myexpenses.service.IExpenseService;
import com.pyskacz.android.myexpenses.service.XlsmExpenseService;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ExpenseListFragment extends ListFragment {

    private IExpenseService expenseService = new XlsmExpenseService();

    @Override
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            ArrayList<HashMap<String, String>> expensesList = new ArrayList<>();
            Collection<Expense> expenses = expenseService.findAllExpenses();
            StringBuilder sb = new StringBuilder();
            for (Expense e : expenses) {
                HashMap<String, String> keyToExpenseDetailsMapping = new HashMap<>();
                keyToExpenseDetailsMapping.put("date", e.getDate());
                keyToExpenseDetailsMapping.put("amount", e.getAmount());

                sb.setLength(0);
                sb.append(e.getCategory()).append(" > ").append(e.getSubcategory());
                String type = e.getType();
                if (!StringUtils.isEmpty(type)) sb.append(" > ").append(type);
                keyToExpenseDetailsMapping.put("description", sb.toString());
                keyToExpenseDetailsMapping.put("comment", e.getComment());
                expensesList.add(keyToExpenseDetailsMapping);
            }

            String[] keys = {"date", "amount", "description", "comment"};
            int[] values = {R.id.listItemDate, R.id.listItemAmount, R.id.listItemDescription, R.id.listItemComment};

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), expensesList, R.layout.widget_expense_list_item, keys, values);
            setListAdapter(adapter);
        } catch (ExpenseServiceException e) {
            Toast.makeText(getActivity(), "Ups! Jakiś błąd w pliku z danymi", Toast.LENGTH_SHORT).show();
        }
    }
}
