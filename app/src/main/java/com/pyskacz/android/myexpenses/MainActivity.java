package com.pyskacz.android.myexpenses;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
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

            @Override
            public void onClick(View view) {
//                expensesFragmentPagerPagerAdapter.getItem(tabs.getSelectedTabPosition()).
               // File file = new File("/mnt/sdcard/Download/Budget.xlsm");
                Snackbar.make(view, "Replace with your own action " + tabs.getSelectedTabPosition(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}