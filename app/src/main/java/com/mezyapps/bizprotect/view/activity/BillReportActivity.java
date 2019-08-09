package com.mezyapps.bizprotect.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.view.adpater.TabViewBillReportAdapter;

public class BillReportActivity extends AppCompatActivity {

    private TabLayout tabLayout_bill_report;
    private ViewPager viewPager_bill_report;
    private ImageView ic_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_report);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        tabLayout_bill_report=findViewById(R.id.tabLayout_bill_report);
        viewPager_bill_report=findViewById(R.id.viewPager_bill_report);
        ic_back=findViewById(R.id.ic_back);

        tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Add Bill"));
        tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Balance list"));
        tabLayout_bill_report.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewBillReportAdapter tabViewBillReportAdapter=new TabViewBillReportAdapter(BillReportActivity.this,getSupportFragmentManager(),tabLayout_bill_report.getTabCount());
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
