package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mezyapps.bizprotect.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddBillFragment extends Fragment {
    private Context mContext;
    private EditText edit_date;
    private String date;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_bill, container, false);

        find_View_IDs(view);
        events();

        return view;
    }

    private void find_View_IDs(View view) {
        edit_date=view.findViewById(R.id.edit_date);
        date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        edit_date.setText(date);
    }

    private void events() {

    }

}
