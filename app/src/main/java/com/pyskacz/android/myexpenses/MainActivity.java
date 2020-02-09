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

import android.view.View;
import android.widget.Toast;

import com.pyskacz.android.myexpenses.model.Expense;
import com.pyskacz.android.myexpenses.service.ExpenseServiceException;
import com.pyskacz.android.myexpenses.service.IExpenseService;
import com.pyskacz.android.myexpenses.service.XlsmExpenseService;
import com.pyskacz.android.myexpenses.ui.main.ExpensesFragmentPagerAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    private IExpenseService expenseService = new XlsmExpenseService();

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
                switch(tabs.getSelectedTabPosition()) {
                    case 0:
                        Fragment addExpenseFragment = expensesFragmentPagerPagerAdapter.getItem(0);
                        try {

//                            Collection<Expense> exspenses = expenseService.findAllExpenses();

                          expenseService.findAllExpenses().forEach(e -> Toast.makeText(getApplication(), e.getDate() + " " + e.getAmount() +" "+ e.getCategory()+" "+ e.getSubcategory(), Toast.LENGTH_SHORT).show());
                            Thread.sleep(500);
                        } catch (ExpenseServiceException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    case 1:
                        Fragment expenseLitFragment = expensesFragmentPagerPagerAdapter.getItem(1);
                        getSupportFragmentManager().beginTransaction().detach(expenseLitFragment).attach(expenseLitFragment).commit();
                        break;
                }

               // File file = new File("/mnt/sdcard/Download/Budget.xlsm");
                Snackbar.make(view, "Complete button handling action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}