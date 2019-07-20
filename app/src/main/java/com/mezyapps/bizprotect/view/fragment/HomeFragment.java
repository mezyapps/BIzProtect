package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.mezyapps.bizprotect.model.BlackListCustomerModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.view.adpater.BlackListedCustomerAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private Context mContext;
    private EditText edt_search;
    private RecyclerView recyclerView_blackList;
    private BlackListedCustomerAdapter blackListedCustomerAdapter;
    private ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh_blacklist_customer;
    public static ApiInterface apiInterface;

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

        //RecyclerView Assign linearLayout Manager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView_blackList.setLayoutManager(linearLayoutManager);
        recyclerView_blackList.hasFixedSize();



        if (NetworkUtils.isNetworkAvailable(mContext)) {
            listBackListedCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(mContext);
        }



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

       swipeRefresh_blacklist_customer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               swipeRefresh_blacklist_customer.setRefreshing(false);
           }
       });

    }
    private void listBackListedCustomer() {
         /*Call<SuccessModule> call = apiInterface.login(mobile_number,password);

        call.enqueue(new Callback<SuccessModule>() {
            @Override
            public void onResponse(Call<SuccessModule> call, Response<SuccessModule> response) {

                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModule successModule = response.body();

                        String message = null, code = null;
                        if (successModule != null) {
                          //  message = successModule.getMesaage();
                            //code = successModule.getCode();
                            if (code.equalsIgnoreCase("1")) {
                              //  userDetailsModuleArrayList=successModule.getUserDetailsModuleArrayList();
                                //successDialog.showDialog("Login Successfully");

                                //SharedLoginUtils.putSharedUtils(LoginActivity.this);
                               // SharedLoginUtils.addUserUtils(LoginActivity.this,userDetailsModuleArrayList);
                                //
                            } else {
                                //errorDialog.showDialog("User Not Registered");
                            }


                        } else {
                            Toast.makeText(PortfolioActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<SuccessModule> call, Throwable t) {

            }
        });*/

    }

}
