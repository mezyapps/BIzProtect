package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mezyapps.bizprotect.R;

public class BlackListedCustomerFragment extends Fragment {

    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_black_listed_customer, container, false);
        mContext=getActivity();
        find_View_IdS();
        events();
        return view;
    }
    private void find_View_IdS() {

    }

    private void events() {

    }


}
