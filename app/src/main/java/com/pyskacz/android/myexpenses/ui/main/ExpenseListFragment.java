package com.pyskacz.android.myexpenses.ui.main;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.ListFragment;

import com.pyskacz.android.myexpenses.R;
import com.pyskacz.android.myexpenses.XlsmHandler;
import com.pyskacz.android.myexpenses.model.Expense;
import com.pyskacz.android.myexpenses.service.ExpenseServiceException;
import com.pyskacz.android.myexpenses.service.IExpenseService;
import com.pyskacz.android.myexpenses.service.XlsmExpenseService;
import com.pyskacz.android.myexpenses.ui.utils.TextViewUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ExpenseListFragment extends ListFragment implements XlsmHandler.OnWorkbookStateChangeListener {

    private static final String ALREADY_CREATED = "already_created";

    //TODO: refactor this
    private IExpenseService expenseService = new XlsmExpenseService();
    private DialogFragment dialogFragment = new DataLoadingDialogFragment();
    private boolean needsReloading = true;


    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
        if(savedInstanceBundle != null && savedInstanceBundle.containsKey(ALREADY_CREATED)){
            savedInstanceBundle.remove(ALREADY_CREATED);
            needsReloading = false;
        } else {
            try {
                XlsmHandler.getInstance().addOnWorkbookStateChangeListener(this);
            } catch (IOException e) {
                // TODO: fix
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(needsReloading) {
            try {
                XlsmHandler.getInstance().reload();
            } catch (IOException e) {
                // TODO: fix
                e.printStackTrace();
            }
            needsReloading = false;
    }}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceBundls) {
        super.onSaveInstanceState(savedInstanceBundls);
        savedInstanceBundls.putCharSequence(ALREADY_CREATED,ALREADY_CREATED);

    }


    @Override
    public void onWorkbookReloading() {
        getActivity().runOnUiThread(() -> {
            dialogFragment.show(getFragmentManager(), "fragment_edit_name");
        });
    }

    @Override
    public void onWorkbookReloaded() {
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

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), expensesList, R.layout.widget_expense_list_item, keys, values) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView listItemAmountTextView = (TextView) ((RelativeLayout) ((LinearLayout) view).getChildAt(0)).getChildAt(1);
                    TextViewUtils.setAmountColor(listItemAmountTextView);

                    return view;
                }
            };

            getActivity().runOnUiThread(() -> {
                setListAdapter(adapter);
                if (dialogFragment.isAdded()) {
                    dialogFragment.dismiss();
                }
            });
        } catch (ExpenseServiceException e) {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Ups! Jakiś błąd w pliku z danymi", Toast.LENGTH_SHORT).show();
            });

        }
    }
}
