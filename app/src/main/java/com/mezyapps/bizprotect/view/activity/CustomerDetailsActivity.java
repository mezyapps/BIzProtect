package com.mezyapps.bizprotect.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.mezyapps.bizprotect.view.fragment.MyCustomerFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {

    private MyCustomerModel myCustomerModel;
    private TextView textName, textContact_Person, textAddress, textgst_no, textEmail, textMobile_No, textAadhar_no, textPan_no, text_blacklisted, text_unblacklisted;
    private LinearLayout linearlayout_status_blacklist, linearlayout_status_unblacklist;
    private String customer_id,client_id,status;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private ImageView ic_back;
    public static ApiInterface apiInterface;
    private ShowProgressDialog showProgressDialog;
   // private LinearLayout linearlayout_edit_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearlayout_status_blacklist = findViewById(R.id.linearlayout_status_blacklist);
        linearlayout_status_unblacklist = findViewById(R.id.linearlayout_status_unblacklist);
        ic_back = findViewById(R.id.ic_back);
        textName = findViewById(R.id.textName);
        textContact_Person = findViewById(R.id.textContact_Person);
        textAddress = findViewById(R.id.textAddress);
        textgst_no = findViewById(R.id.textgst_no);
        textEmail = findViewById(R.id.textEmail);
        textMobile_No = findViewById(R.id.textMobile_No);
        textAadhar_no = findViewById(R.id.textAadhar_no);
        textPan_no = findViewById(R.id.textPan_no);
        text_blacklisted = findViewById(R.id.text_blacklisted);
        text_unblacklisted = findViewById(R.id.text_unblacklisted);
       // linearlayout_edit_customer = findViewById(R.id.linearlayout_edit_customer);

        showProgressDialog=new ShowProgressDialog(CustomerDetailsActivity.this);

        clientProfileModelArrayList= SharedLoginUtils.getUserDetails(CustomerDetailsActivity.this);
        client_id=clientProfileModelArrayList.get(0).getClient_id();


        Bundle bundle = getIntent().getExtras();
        myCustomerModel = bundle.getParcelable("MYCUSTOMER");
        textName.setText(myCustomerModel.getCustomer_name());
        textContact_Person.setText(myCustomerModel.getContact_person());
        textAddress.setText(myCustomerModel.getAddress());
        textgst_no.setText(myCustomerModel.getGst_no());
        textEmail.setText(myCustomerModel.getEmail());
        textMobile_No.setText(myCustomerModel.getMobile_no());
        textAadhar_no.setText(myCustomerModel.getAadhar_no());
        textPan_no.setText(myCustomerModel.getPan_no());
        customer_id=myCustomerModel.getCustomer_id();
        status=myCustomerModel.getStatus();

    }

    private void events() {
        if(status.equalsIgnoreCase("4"))
        {
            linearlayout_status_unblacklist.setVisibility(View.VISIBLE);
            linearlayout_status_blacklist.setVisibility(View.GONE);
            text_unblacklisted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status="1";

                    if (NetworkUtils.isNetworkAvailable(CustomerDetailsActivity.this)) {
                        callUpdateCustomerStatus();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(CustomerDetailsActivity.this);
                    }
                }
            });



        }
        else {
            linearlayout_status_unblacklist.setVisibility(View.GONE);
            linearlayout_status_blacklist.setVisibility(View.VISIBLE);
            text_blacklisted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status="4";

                    if (NetworkUtils.isNetworkAvailable(CustomerDetailsActivity.this)) {
                        callUpdateCustomerStatus();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(CustomerDetailsActivity.this);
                    }

                }
            });
        }

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callUpdateCustomerStatus() {
        showProgressDialog.showDialog();
        Call<SuccessModel> call=apiInterface.callUpdateCustomerStatus(client_id,customer_id,status);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMessage();
                            code = successModel.getCode();
                            if (code.equalsIgnoreCase("1")) {
                                Toast.makeText(CustomerDetailsActivity.this, "Update Customer Status Successfully", Toast.LENGTH_SHORT).show();
                                //successDialog.showDialog("Registration Successfully");
                                    MyCustomerFragment.isToRefresh = true;
                                finish();
                            } else {
                                Toast.makeText(CustomerDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CustomerDetailsActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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
