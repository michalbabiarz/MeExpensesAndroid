package com.pyskacz.android.myexpenses.ui.main;

import android.os.Bundle;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.pyskacz.android.myexpenses.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseListFragment extends ListFragment {

    String[] text1 = {"left_1", "left_2", "left_3", "left_4", "left_5", "left_6"};
    String[] text2 = {"right_1", "right_2", "right_3", "right_4", "right_5", "right_6"};

    private static int counter = 0;

    @Override
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        counter++;
        ArrayList<HashMap<String, String>> expensesList = new ArrayList<>();
        for (int i = 0; i < text1.length; i++) {
            HashMap<String, String> keyToExpenseDetailsMapping = new HashMap<>();
            keyToExpenseDetailsMapping.put("text1", text1[i] + "_" + counter);
            keyToExpenseDetailsMapping.put("text2", text2[i] + "_" + counter);
            expensesList.add(keyToExpenseDetailsMapping);
        }

        String[] keys = {"text1", "text2"};
        int[] values = {R.id.listItemDate, R.id.listItemAmount};

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), expensesList, R.layout.widget_expense_list_item, keys, values);
        setListAdapter(adapter);
    }
}
