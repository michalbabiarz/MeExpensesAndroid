package com.pyskacz.android.myexpenses.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pyskacz.android.myexpenses.R;

public class AddExpenseFragment extends Fragment {

//    private PageViewModel pageViewModel;
//
////    public static AddExpenseFragment newInstance(int index) {
//        AddExpenseFragment fragment = new AddExpenseFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_expense, container, false);

//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
