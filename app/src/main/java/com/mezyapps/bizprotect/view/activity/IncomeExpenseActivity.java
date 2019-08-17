package com.mezyapps.bizprotect.view.activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.view.adpater.TabViewBillReportAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IncomeExpenseActivity extends AppCompatActivity {
    private ImageView ic_back;
    private TabLayout tabLayout_bill_report;
    private ViewPager viewPager_bill_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        tabLayout_bill_report=findViewById(R.id.tabLayout_bill_report);
        viewPager_bill_report=findViewById(R.id.viewPager_bill_report);
        ic_back=findViewById(R.id.ic_back);

        tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Add Income/Expense"));
        tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Report"));
        tabLayout_bill_report.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewBillReportAdapter tabViewBillReportAdapter=new TabViewBillReportAdapter(IncomeExpenseActivity.this,getSupportFragmentManager(),tabLayout_bill_report.getTabCount());
        viewPager_bill_report.setAdapter(tabViewBillReportAdapter);
        viewPager_bill_report.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_bill_report));

    }

    private void events() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout_bill_report.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_bill_report.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }




}
