package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mezyapps.bizprotect.R;

public class IncomeExpenseReportFragment extends Fragment {
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_income_expense_report, container, false);
        mContext=getActivity();
        find_View_IDs(view);
        events();
        return  view;
    }

    private void find_View_IDs(View view) {

    }
    private void events() {
    }

}
