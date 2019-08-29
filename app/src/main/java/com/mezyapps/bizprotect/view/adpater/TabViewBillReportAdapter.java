package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mezyapps.bizprotect.view.fragment.IncomeExpenseFragment;

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
                IncomeExpenseFragment incomeExpenseFragment = new IncomeExpenseFragment();
                return incomeExpenseFragment;
           /* case 1:
                IncomeExpenseReportFragment incomeExpenseReportFragment = new IncomeExpenseReportFragment();
                return incomeExpenseReportFragment;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
