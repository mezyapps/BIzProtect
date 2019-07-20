package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.BlackListCustomerModel;
import com.mezyapps.bizprotect.view.adpater.BlackListedCustomerAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private Context mContext;
    private EditText edt_search;
    private RecyclerView recyclerView_blackList;
    private BlackListedCustomerAdapter blackListedCustomerAdapter;
    private ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();

        find_View_IdS(view);
        events();

        return view;
    }

    private void find_View_IdS(View view) {
        edt_search = view.findViewById(R.id.edt_search);
        recyclerView_blackList = view.findViewById(R.id.recyclerView_blackList);

        //RecyclerView Assign linearLayout Manager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView_blackList.setLayoutManager(linearLayoutManager);
        recyclerView_blackList.hasFixedSize();


        for(int i=0;i<=10;i++) {
            blackListCustomerModelArrayList.add(new BlackListCustomerModel("Nehal Jathar", "1234567890", "BlackListed"));
        }
        blackListedCustomerAdapter=new BlackListedCustomerAdapter(mContext,blackListCustomerModelArrayList);
        recyclerView_blackList.setAdapter(blackListedCustomerAdapter);
        blackListedCustomerAdapter.notifyDataSetChanged();
    }

    private void events() {

       edt_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               edt_search.setFocusableInTouchMode(true);
           }
       });

    }

}
