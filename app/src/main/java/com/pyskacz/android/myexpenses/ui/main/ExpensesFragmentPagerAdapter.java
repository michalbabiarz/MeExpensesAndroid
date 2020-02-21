package com.pyskacz.android.myexpenses.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.pyskacz.android.myexpenses.R;


public class ExpensesFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] fragments = {new AddExpenseFragment(), new ExpenseListFragment()};

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1, R.string.tab_text_2};
    private final Context context;

    public ExpensesFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}