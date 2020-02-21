package com.pyskacz.android.myexpenses;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.pyskacz.android.myexpenses.ui.main.ExpensesFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

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
        fab.setOnClickListener(view -> {
            switch (tabs.getSelectedTabPosition()) {
                case 0:
                    Fragment addExpenseFragment = expensesFragmentPagerPagerAdapter.getItem(0);
                case 1:
                    Fragment expenseLitFragment = expensesFragmentPagerPagerAdapter.getItem(1);
                    getSupportFragmentManager().beginTransaction().detach(expenseLitFragment).attach(expenseLitFragment).commit();
                    break;
            }

            Snackbar.make(view, "Complete button handling action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}