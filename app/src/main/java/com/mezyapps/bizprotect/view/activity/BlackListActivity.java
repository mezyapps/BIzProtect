package com.mezyapps.bizprotect.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.view.adpater.TabViewBillReportAdapter;
import com.mezyapps.bizprotect.view.adpater.TabViewBlackListAdapter;

public class BlackListActivity extends AppCompatActivity {

    private TabLayout tabLayout_blacklist;
    private ViewPager viewPager_blacklist;
    private ImageView ic_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);

        find_View_Id();
        events();
    }


    private void find_View_Id() {
        viewPager_blacklist=findViewById(R.id.viewPager_blacklist);
        tabLayout_blacklist=findViewById(R.id.tabLayout_blacklist);
        ic_back=findViewById(R.id.ic_back);

        tabLayout_blacklist.addTab(tabLayout_blacklist.newTab().setText(R.string.all_blacklisted));
        tabLayout_blacklist.addTab(tabLayout_blacklist.newTab().setText(R.string.my_blacklisted));
        tabLayout_blacklist.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewBlackListAdapter tabViewBlackListAdapter=new TabViewBlackListAdapter(BlackListActivity.this,getSupportFragmentManager(),tabLayout_blacklist.getTabCount());
        viewPager_blacklist.setAdapter(tabViewBlackListAdapter);
        viewPager_blacklist.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_blacklist));

    }

    private void events() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout_blacklist.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_blacklist.setCurrentItem(tab.getPosition());
                String blackList=tab.getText().toString();

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
