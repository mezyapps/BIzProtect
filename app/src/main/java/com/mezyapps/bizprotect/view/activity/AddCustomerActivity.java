package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddCustomerActivity extends AppCompatActivity {

    private EditText edt_customer_name, edt_contact_person, edt_customer_gst_no, edt_customer_address, edt_customer_email,
            edt_customer_mobile_number, edt_customer_aadhar_number, edt_Customer_pan_number;
    private Button btn_add_new_customer;

    private String customer_name, contact_person_name, address, email, mobile, pan_number, aadhar_number, gst_number, client_id, customer_id, btn_name;

    private TextInputLayout textCustomerName, textContactPerson, textCustomerGstNumber, textCustomerAddress, textCustomerEmail,
            textCustomerMobileNumber, textCustomerAadharNumber, textCustomerPanNumber;
    public static ApiInterface apiInterface;
    private ImageView ic_back;
    private ShowProgressDialog showProgressDialog;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
    private MyCustomerModel myCustomerModel;
    private TextView textAddTitle,textAadharNumberMsg,textPanNumberMsg,textGstNumberMsg;
    private RadioButton radioStatusButton,rbGoodCustomer,rbBlackList;
    private RadioGroup radioGroupStatus;
    private String status="1"; //Good Customer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        find_View_IdS();
        events();
    }

    private void find_View_IdS() {
        ic_back = findViewById(R.id.ic_back);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        textCustomerName = findViewById(R.id.textCustomerName);
        textContactPerson = findViewById(R.id.textContactPerson);
        textCustomerGstNumber = findViewById(R.id.textCustomerGstNumber);
        textCustomerAddress = findViewById(R.id.textCustomerAddress);
        textCustomerEmail = findViewById(R.id.textCustomerEmail);
        textCustomerMobileNumber = findViewById(R.id.textCustomerMobileNumber);
        textCustomerAadharNumber = findViewById(R.id.textCustomerAadharNumber);
        textCustomerPanNumber = findViewById(R.id.textCustomerPanNumber);
        radioGroupStatus=findViewById(R.id.radioGroupStatus);

        edt_customer_name = findViewById(R.id.edt_customer_name);
        edt_contact_person = findViewById(R.id.edt_contact_person);
        edt_customer_gst_no = findViewById(R.id.edt_customer_gst_no);
        edt_customer_address = findViewById(R.id.edt_customer_address);
        edt_customer_email = findViewById(R.id.edt_customer_email);
        edt_customer_mobile_number = findViewById(R.id.edt_customer_mobile_number);
        edt_customer_aadhar_number = findViewById(R.id.edt_customer_aadhar_number);
        edt_Customer_pan_number = findViewById(R.id.edt_Customer_pan_number);
        textAddTitle = findViewById(R.id.textAddTitle);
        rbGoodCustomer = findViewById(R.id.rbGoodCustomer);
        rbBlackList = findViewById(R.id.rbBlackList);

        textAadharNumberMsg = findViewById(R.id.textAadharNumberMsg);
        textPanNumberMsg = findViewById(R.id.textPanNumberMsg);
        textGstNumberMsg = findViewById(R.id.textGstNumberMsg);


        btn_add_new_customer = findViewById(R.id.btn_add_new_customer);
        showProgressDialog = new ShowProgressDialog(AddCustomerActivity.this);


        clientProfileModelArrayList = SharedLoginUtils.getUserDetails(AddCustomerActivity.this);
        client_id = clientProfileModelArrayList.get(0).getClient_id();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myCustomerModel = bundle.getParcelable("MYCUSTOMER");
            edt_customer_name.setText(myCustomerModel.getCustomer_name());
            edt_contact_person.setText(myCustomerModel.getContact_person());
            edt_customer_gst_no.setText(myCustomerModel.getGst_no());
            edt_customer_address.setText(myCustomerModel.getAddress());
            edt_customer_email.setText(myCustomerModel.getEmail());
            edt_customer_mobile_number.setText(myCustomerModel.getMobile_no());
            edt_customer_aadhar_number.setText(myCustomerModel.getAadhar_no());
            edt_Customer_pan_number.setText(myCustomerModel.getPan_no());
            status=myCustomerModel.getStatus();
            btn_add_new_customer.setText("Update Customer");
            customer_id = myCustomerModel.getCustomer_id();
            textAddTitle.setText("Update Customer");

            if(status.equalsIgnoreCase("1"))
            {
                rbGoodCustomer.setChecked(true);
            }
            else
            {
                rbBlackList.setChecked(true);
            }

        }

        btn_name = btn_add_new_customer.getText().toString().trim();
    }

    private void events() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_add_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_name.equalsIgnoreCase("Update Customer")) {
                    if (validation()) {
                        if (NetworkUtils.isNetworkAvailable(AddCustomerActivity.this)) {
                            updateCustomer();
                        } else {
                            NetworkUtils.isNetworkNotAvailable(AddCustomerActivity.this);
                        }

                    }
                } else {
                    if (validation()) {
                        if (NetworkUtils.isNetworkAvailable(AddCustomerActivity.this)) {
                            addCustomer();
                        } else {
                            NetworkUtils.isNetworkNotAvailable(AddCustomerActivity.this);
                        }

                    }
                }

            }
        });
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioStatusButton=findViewById(checkedId);
                String statusName=radioStatusButton.getText().toString().trim();
                if(statusName.equalsIgnoreCase("Good Customer"))
                {
                    status="1";
                }
                else
                {
                    status="4";
                }
            }
        });

        edt_customer_gst_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textGstNumberMsg.setVisibility(View.VISIBLE);
                textPanNumberMsg.setVisibility(View.GONE);
                textAadharNumberMsg.setVisibility(View.GONE);
            }
        });

        edt_customer_aadhar_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textPanNumberMsg.setVisibility(View.GONE);
                textGstNumberMsg.setVisibility(View.GONE);
                textAadharNumberMsg.setVisibility(View.VISIBLE);
            }
        });

        edt_Customer_pan_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textGstNumberMsg.setVisibility(View.GONE);
                textAadharNumberMsg.setVisibility(View.GONE);
                textPanNumberMsg.setVisibility(View.VISIBLE);
            }
        });
    }
    private boolean validation() {
        customer_name = edt_customer_name.getText().toString().trim();
        contact_person_name = edt_contact_person.getText().toString().trim();
        address = edt_customer_address.getText().toString().trim();
        email = edt_customer_email.getText().toString().trim();
        mobile = edt_customer_mobile_number.getText().toString().trim();
        pan_number = edt_Customer_pan_number.getText().toString();
        aadhar_number = edt_customer_aadhar_number.getText().toString().trim();
        gst_number = edt_customer_gst_no.getText().toString().trim();
        int flag = 0;


        if (customer_name.equalsIgnoreCase("")) {
            textCustomerName.setError("Enter Business Name");
            edt_customer_name.requestFocus();
            return false;
        } else {
            textCustomerName.setError(null);
            textCustomerName.setErrorEnabled(false);
        }

        if (mobile.equalsIgnoreCase("")) {
            textCustomerMobileNumber.setError("Enter Mobile Number");
            edt_customer_mobile_number.requestFocus();
            return false;
        } else if(mobile.length()!=10) {
            textCustomerMobileNumber.setError("Invalid Mobile Number");
            edt_customer_mobile_number.requestFocus();
            return false;
        } else {
            textCustomerMobileNumber.setError(null);
            textCustomerMobileNumber.setErrorEnabled(false);
        }
        if (gst_number.equalsIgnoreCase("")) {
            flag++;
        } else if (gst_number.length() < 15) {
            Toast.makeText(this, "Invalid GST Number", Toast.LENGTH_SHORT).show();
            flag++;
            return false;
        }
        if (pan_number.equalsIgnoreCase("")) {
            flag++;
        } else if (pan_number.length() < 10) {
            Toast.makeText(this, "Invalid Pan Number", Toast.LENGTH_SHORT).show();
            flag++;
            return false;
        }
        if (aadhar_number.equalsIgnoreCase("")) {
            flag++;
        } else if (aadhar_number.length() < 12) {
            Toast.makeText(this, "Invalid Aadhar Number", Toast.LENGTH_SHORT).show();
            flag++;
            return false;
        }


        if(flag>=3)
        {
            //Toast.makeText(this, "Please Enter Aadhar Or Pan Or Gst Number", Toast.LENGTH_SHORT).show();
            textGstNumberMsg.setVisibility(View.VISIBLE);
            textAadharNumberMsg.setVisibility(View.GONE);
            textAadharNumberMsg.setVisibility(View.GONE);
            flag=0;
            return false;
        }


   /* if (contact_person_name.equalsIgnoreCase("")) {
            textContactPerson.setError("Enter Customer Name");
            edt_contact_person.requestFocus();
            return false;
        } else {
            textContactPerson.setError(null);
            textContactPerson.setErrorEnabled(false);
        }*/

       /* if (address.equalsIgnoreCase("")) {
            textCustomerAddress.setError("Enter Address");
            edt_customer_address.requestFocus();
            return false;
        } else {
            textCustomerAddress.setError(null);
            textCustomerAddress.setErrorEnabled(false);
        }*/
        /*if (email.equalsIgnoreCase("")) {
            textCustomerEmail.setError("Enter Email");
            edt_customer_email.requestFocus();
            return false;
        } else {
            textCustomerEmail.setError(null);
            textCustomerEmail.setErrorEnabled(false);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textCustomerEmail.setError("Invalid  Email");
            edt_customer_email.requestFocus();
            return false;
        } else {
            textCustomerEmail.setError(null);
            textCustomerEmail.setErrorEnabled(false);
        }*/

       /* if (aadhar_number.equalsIgnoreCase("")) {
            textCustomerAadharNumber.setError("Enter Aadhar Number");
            edt_customer_aadhar_number.requestFocus();
            return false;
        } else {
             textCustomerAadharNumber.setError(null);
            textCustomerAadharNumber.setErrorEnabled(false);
        }
        if (aadhar_number.length() < 12) {
            textCustomerAadharNumber.setError("Invalid Aadhar Number");
            edt_customer_aadhar_number.requestFocus();
            return false;
        } else {
            textCustomerAadharNumber.setError(null);
            textCustomerAadharNumber.setErrorEnabled(false);
        }*/
        return true;
    }
    private void addCustomer() {

        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.registrationCustomer(customer_name, contact_person_name, gst_number, address, email, aadhar_number, pan_number, mobile, client_id,status);

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
                                Toast.makeText(AddCustomerActivity.this, "Customer Registration Successfully", Toast.LENGTH_SHORT).show();
                                MyCustomerFragment.isToRefresh = true;
                                finish();

                            } else if(code.equalsIgnoreCase("2")){
                                Toast.makeText(AddCustomerActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(AddCustomerActivity.this, "Customer Registration Unsuccessfully ", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AddCustomerActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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

    private void updateCustomer() {

        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.updateCustomer(customer_name, contact_person_name, gst_number, address, email, aadhar_number, pan_number, mobile, client_id, customer_id,status);

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
                                Toast.makeText(AddCustomerActivity.this, "Customer Update Successfully", Toast.LENGTH_SHORT).show();
                                 MyCustomerFragment.isToRefresh = true;
                                finish();
                            } else {
                                Toast.makeText(AddCustomerActivity.this, "Customer Update Unsuccessfully ", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AddCustomerActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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
