package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.view.activity.AddCustomerActivity;
import com.mezyapps.bizprotect.view.adpater.MyBlackListedCustomerListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBlackListedCustomerFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView_ourCustomer;
    private EditText edt_search;
    private MyBlackListedCustomerListAdapter myBlackListedCustomerListAdapter;
    private ArrayList<MyBlackListedCustomerModel> myBlackListedCustomerModelArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh_our_customer;
    public static ApiInterface apiInterface;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private String client_id;
    private ShowProgressDialog showProgressDialog;
    private ImageView iv_no_record_found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_blacklisted_customer, container, false);
        mContext = getContext();

        find_View_IdS(view);
        events();

        return view;
    }

    private void find_View_IdS(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerView_ourCustomer = view.findViewById(R.id.recyclerView_ourCustomer);
        edt_search = view.findViewById(R.id.edt_search);
        swipeRefresh_our_customer = view.findViewById(R.id.swipeRefresh_our_customer);
        iv_no_record_found = view.findViewById(R.id.iv_no_record_found);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView_ourCustomer.setLayoutManager(linearLayoutManager);
        recyclerView_ourCustomer.hasFixedSize();

        clientProfileModelArrayList= SharedLoginUtils.getUserDetails(mContext);
        client_id=clientProfileModelArrayList.get(0).getClient_id();

        showProgressDialog=new ShowProgressDialog(mContext);


        if (NetworkUtils.isNetworkAvailable(mContext)) {
            listOurCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(mContext);
        }

    }

    private void events() {


        swipeRefresh_our_customer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (NetworkUtils.isNetworkAvailable(mContext)) {
                    listOurCustomer();
                }
                else {
                    NetworkUtils.isNetworkNotAvailable(mContext);
                }
                swipeRefresh_our_customer.setRefreshing(false);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    edt_search.setFocusableInTouchMode(true);
                    myBlackListedCustomerListAdapter.getFilter().filter(edt_search.getText().toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void listOurCustomer() {
        showProgressDialog.showDialog();

        Call<SuccessModel> call = apiInterface.myBlackListed(client_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModule = response.body();
                        myBlackListedCustomerModelArrayList.clear();
                        String message = null, code = null;
                        if (successModule != null) {
                            message = successModule.getMessage();
                            code = successModule.getCode();
                            if (code.equalsIgnoreCase("1")) {
                                myBlackListedCustomerModelArrayList=successModule.getMyBlackListedCustomerModelArrayList();
                                Collections.reverse(myBlackListedCustomerModelArrayList);
                                if(myBlackListedCustomerModelArrayList.size()!=0) {
                                    myBlackListedCustomerListAdapter = new MyBlackListedCustomerListAdapter(mContext, myBlackListedCustomerModelArrayList);
                                    recyclerView_ourCustomer.setAdapter(myBlackListedCustomerListAdapter);
                                    iv_no_record_found.setVisibility(View.GONE);
                                    myBlackListedCustomerListAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    iv_no_record_found.setVisibility(View.VISIBLE);
                                    myBlackListedCustomerListAdapter.notifyDataSetChanged();
                                }
                            } else {
                                iv_no_record_found.setVisibility(View.VISIBLE);
                                myBlackListedCustomerListAdapter.notifyDataSetChanged();
                            }


                        } else {
                            Toast.makeText(mContext, "Response Null", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgressDialog.dismissDialog();
            }
        });
    }
    
}
