package com.pyskacz.android.myexpenses;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.pyskacz.android.myexpenses.ui.main.ExpensesFragmentPagerAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ExpensesFragmentPagerAdapter expensesFragmentPagerPagerAdapter = new ExpensesFragmentPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(expensesFragmentPagerPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            String s = "";

            @Override
            public void onClick(View view) {
                int selectedTab = tabs.getSelectedTabPosition();
                Fragment activeFragment = expensesFragmentPagerPagerAdapter.getItem(selectedTab);
                String s = activeFragment.getClass().getName();


                switch(selectedTab) {
                    case 0:
                        Fragment addExpenseFragment = expensesFragmentPagerPagerAdapter.getItem(0);

                    case 1:
                        Fragment expenseLitFragment = expensesFragmentPagerPagerAdapter.getItem(1);
                        getSupportFragmentManager().beginTransaction().detach(expenseLitFragment).attach(expenseLitFragment).commit();
                        break;
                }

               // File file = new File("/mnt/sdcard/Download/Budget.xlsm");
                Snackbar.make(view, "Replace with your own action " + tabs.getSelectedTabPosition() + "" + s, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}