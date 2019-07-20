package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.OurCustomerModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.view.activity.AddCustomerActivity;
import com.mezyapps.bizprotect.view.activity.PortfolioActivity;
import com.mezyapps.bizprotect.view.adpater.OurCustomerListAdapter;

import java.util.ArrayList;


public class OurCustomerFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView_ourCustomer;
    private FloatingActionButton fab_add_customer;
    private EditText edt_search;
    private OurCustomerListAdapter ourCustomerListAdapter;
    private ArrayList<OurCustomerModel> ourCustomerModelArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh_our_customer;
    public static ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_our_customer, container, false);
        mContext = getContext();

        find_View_IdS(view);
        events();

        return view;
    }

    private void find_View_IdS(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerView_ourCustomer = view.findViewById(R.id.recyclerView_ourCustomer);
        fab_add_customer = view.findViewById(R.id.fab_add_customer);
        edt_search = view.findViewById(R.id.edt_search);
        swipeRefresh_our_customer = view.findViewById(R.id.swipeRefresh_our_customer);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView_ourCustomer.setLayoutManager(linearLayoutManager);
        recyclerView_ourCustomer.hasFixedSize();



        if (NetworkUtils.isNetworkAvailable(mContext)) {
            listOurCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(mContext);
        }


        for (int i = 0; i <= 10; i++) {
            ourCustomerModelArrayList.add(new OurCustomerModel("Akshay Jathar", "6567677", "Good"));
        }

        ourCustomerListAdapter = new OurCustomerListAdapter(mContext, ourCustomerModelArrayList);
        recyclerView_ourCustomer.setAdapter(ourCustomerListAdapter);
        ourCustomerListAdapter.notifyDataSetChanged();

    }

    private void events() {


        swipeRefresh_our_customer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefresh_our_customer.setRefreshing(false);
            }
        });

        fab_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listOurCustomer() {
    }


}
