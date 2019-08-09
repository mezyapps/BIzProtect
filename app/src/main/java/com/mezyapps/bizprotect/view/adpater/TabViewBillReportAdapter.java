package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mezyapps.bizprotect.view.fragment.AddBillFragment;
import com.mezyapps.bizprotect.view.fragment.ListBillFragment;

public class TabViewBillReportAdapter extends FragmentPagerAdapter {

    private Context mContext;
    int totalTabs;
    public TabViewBillReportAdapter(Context mContext,FragmentManager fragmentManager,int totalTabs) {
        super(fragmentManager);
        this.mContext=mContext;
        this.totalTabs=totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddBillFragment addBillFragment = new AddBillFragment();
                return addBillFragment;
            case 1:
                ListBillFragment listBillFragment = new ListBillFragment();
                return listBillFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
