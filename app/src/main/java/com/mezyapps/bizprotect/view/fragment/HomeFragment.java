package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.mezyapps.bizprotect.model.BlackListCustomerModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.view.adpater.BlackListedCustomerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private Context mContext;
    private EditText edt_search;
    private RecyclerView recyclerView_blackList;
    private BlackListedCustomerAdapter blackListedCustomerAdapter;
    private ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh_blacklist_customer;
    public static ApiInterface apiInterface;
    private ShowProgressDialog showProgressDialog;
    private ImageView iv_no_record_found;

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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        edt_search = view.findViewById(R.id.edt_search);
        recyclerView_blackList = view.findViewById(R.id.recyclerView_blackList);
        swipeRefresh_blacklist_customer = view.findViewById(R.id.swipeRefresh_blacklist_customer);
        iv_no_record_found = view.findViewById(R.id.iv_no_record_found);

        //RecyclerView Assign linearLayout Manager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView_blackList.setLayoutManager(linearLayoutManager);
        recyclerView_blackList.hasFixedSize();
        showProgressDialog=new ShowProgressDialog(mContext);

        if (NetworkUtils.isNetworkAvailable(mContext)) {
            listBackListedCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(mContext);
        }
    }

    private void events() {
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setFocusableInTouchMode(true);
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
                    blackListedCustomerAdapter.getFilter().filter(edt_search.getText().toString());
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


       swipeRefresh_blacklist_customer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {

               if (NetworkUtils.isNetworkAvailable(mContext)) {
                   listBackListedCustomer();
               }
               else {
                   NetworkUtils.isNetworkNotAvailable(mContext);
               }
               swipeRefresh_blacklist_customer.setRefreshing(false);
           }
       });

    }
    private void listBackListedCustomer() {
        showProgressDialog.showDialog();

        Call<SuccessModel> call = apiInterface.allBlackListed();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModule = response.body();

                        String message = null, code = null;
                        if (successModule != null) {
                           message = successModule.getMessage();
                           code = successModule.getCode();
                            if (code.equalsIgnoreCase("1")) {
                                blackListCustomerModelArrayList=successModule.getBlackListCustomerModelArrayList();
                                Collections.reverse(blackListCustomerModelArrayList);
                                if(blackListCustomerModelArrayList.size()!=0) {
                                    blackListedCustomerAdapter = new BlackListedCustomerAdapter(mContext, blackListCustomerModelArrayList);
                                    recyclerView_blackList.setAdapter(blackListedCustomerAdapter);
                                    iv_no_record_found.setVisibility(View.GONE);
                                    blackListedCustomerAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    iv_no_record_found.setVisibility(View.VISIBLE);
                                    blackListedCustomerAdapter.notifyDataSetChanged();
                                }
                            } else {
                                iv_no_record_found.setVisibility(View.VISIBLE);
                                blackListedCustomerAdapter.notifyDataSetChanged();
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
