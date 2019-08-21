package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mezyapps.bizprotect.view.fragment.AllBlackListedFragment;
import com.mezyapps.bizprotect.view.fragment.IncomeExpenseFragment;
import com.mezyapps.bizprotect.view.fragment.MyBlackListedCustomerFragment;

public class TabViewBlackListAdapter extends FragmentPagerAdapter {

    private Context mContext;
    int totalTabs;

    public TabViewBlackListAdapter(Context mContext, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);
        this.mContext=mContext;
        this.totalTabs=totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllBlackListedFragment allBlackListedFragment = new AllBlackListedFragment();
                return allBlackListedFragment;
            case 1:
                MyBlackListedCustomerFragment myBlackListedCustomerFragment = new MyBlackListedCustomerFragment();
                return myBlackListedCustomerFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
