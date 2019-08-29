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
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.view.fragment.MyBlackListedCustomerFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsMyBlackListedActivity extends AppCompatActivity {

    private MyBlackListedCustomerModel myBlackListedCustomerModel;
    private TextView textName, textContact_Person, textAddress, textgst_no, textEmail, textMobile_No, textAadhar_no, textPan_no, text_unblacklisted;
    private LinearLayout linearlayout_status_unblacklist;
    private String customer_id, client_id, status;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
    private ImageView ic_back;
    public static ApiInterface apiInterface;
    private ShowProgressDialog showProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_my_black_listed);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
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
        text_unblacklisted = findViewById(R.id.text_unblacklisted);

        showProgressDialog=new ShowProgressDialog(CustomerDetailsMyBlackListedActivity.this);

        clientProfileModelArrayList = SharedLoginUtils.getUserDetails(CustomerDetailsMyBlackListedActivity.this);
        client_id = clientProfileModelArrayList.get(0).getClient_id();


        Bundle bundle = getIntent().getExtras();
        myBlackListedCustomerModel = bundle.getParcelable("MYCUSTOMER");
        textName.setText(myBlackListedCustomerModel.getCustomer_name());
        textContact_Person.setText(myBlackListedCustomerModel.getContact_person());
        textAddress.setText(myBlackListedCustomerModel.getAddress());
        textgst_no.setText(myBlackListedCustomerModel.getGst_no());
        textEmail.setText(myBlackListedCustomerModel.getEmail());
        textMobile_No.setText(myBlackListedCustomerModel.getMobile_no());
        textAadhar_no.setText(myBlackListedCustomerModel.getAadhar_no());
        textPan_no.setText(myBlackListedCustomerModel.getPan_no());
        customer_id = myBlackListedCustomerModel.getCustomer_id();
        status = myBlackListedCustomerModel.getStatus();

    }

    private void events() {

        text_unblacklisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "1";

                if (NetworkUtils.isNetworkAvailable(CustomerDetailsMyBlackListedActivity.this)) {
                    callUpdateCustomerStatus();
                }
                else {
                    NetworkUtils.isNetworkNotAvailable(CustomerDetailsMyBlackListedActivity.this);
                }
            }
        });


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
                                Toast.makeText(CustomerDetailsMyBlackListedActivity.this, "Update Customer Status Successfully", Toast.LENGTH_SHORT).show();
                                MyBlackListedCustomerFragment.isToRefresh = true;
                                finish();
                            } else {
                                Toast.makeText(CustomerDetailsMyBlackListedActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CustomerDetailsMyBlackListedActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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
