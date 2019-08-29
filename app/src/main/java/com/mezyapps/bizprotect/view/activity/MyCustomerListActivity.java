package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.MyCustomerModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.view.adpater.MyCustomerListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCustomerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView_myCustomer;
    private FloatingActionButton fab_add_customer;
    private EditText edt_search;
    private SwipeRefreshLayout swipeRefresh_our_customer;
    public static ApiInterface apiInterface;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private String client_id;
    private ShowProgressDialog showProgressDialog;
    private ImageView iv_no_record_found;
    private ArrayList<MyCustomerModel> myCustomerModelArrayList=new ArrayList<>();
    private MyCustomerListAdapter myCustomerListAdapter;
    private ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customer_list);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerView_myCustomer =findViewById(R.id.recyclerView_myCustomer);
        fab_add_customer = findViewById(R.id.fab_add_customer);
        edt_search = findViewById(R.id.edt_search);
        swipeRefresh_our_customer = findViewById(R.id.swipeRefresh_our_customer);
        iv_no_record_found = findViewById(R.id.iv_no_record_found);
        ic_back = findViewById(R.id.ic_back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyCustomerListActivity.this);
        recyclerView_myCustomer.setLayoutManager(linearLayoutManager);
        recyclerView_myCustomer.hasFixedSize();

        clientProfileModelArrayList= SharedLoginUtils.getUserDetails(MyCustomerListActivity.this);
        client_id=clientProfileModelArrayList.get(0).getClient_id();

        showProgressDialog=new ShowProgressDialog(MyCustomerListActivity.this);

        if (NetworkUtils.isNetworkAvailable(MyCustomerListActivity.this)) {
            listMyCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(MyCustomerListActivity.this);
        }

    }

    private void events() {

        swipeRefresh_our_customer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (NetworkUtils.isNetworkAvailable(MyCustomerListActivity.this)) {
                    listMyCustomer();
                }
                else {
                    NetworkUtils.isNetworkNotAvailable(MyCustomerListActivity.this);
                }
                swipeRefresh_our_customer.setRefreshing(false);
            }
        });

        fab_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyCustomerListActivity.this, AddCustomerActivity.class);
                startActivity(intent);
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
                    myCustomerListAdapter.getFilter().filter(edt_search.getText().toString());
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
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void listMyCustomer() {
        showProgressDialog.showDialog();

        Call<SuccessModel> call = apiInterface.myCustomerList(client_id);
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
                                myCustomerModelArrayList=successModule.getMyCustomerModelArrayList();
                                Collections.reverse(myCustomerModelArrayList);
                                if(myCustomerModelArrayList.size()!=0) {
                                    myCustomerListAdapter = new MyCustomerListAdapter(MyCustomerListActivity.this, myCustomerModelArrayList);
                                    recyclerView_myCustomer.setAdapter(myCustomerListAdapter);
                                    iv_no_record_found.setVisibility(View.GONE);
                                    myCustomerListAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    iv_no_record_found.setVisibility(View.VISIBLE);
                                    myCustomerListAdapter.notifyDataSetChanged();
                                }
                            } else {
                                iv_no_record_found.setVisibility(View.VISIBLE);
                                myCustomerListAdapter.notifyDataSetChanged();
                            }


                        } else {
                            Toast.makeText(MyCustomerListActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        if (NetworkUtils.isNetworkAvailable(MyCustomerListActivity.this)) {
            listMyCustomer();
        }
        else {
            NetworkUtils.isNetworkNotAvailable(MyCustomerListActivity.this);
        }

    }
}
