package com.mezyapps.bizprotect.view.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.utils.NetworkUtils;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText edt_customer_name,edt_contact_person,edt_customer_gst_no,edt_customer_address,edt_customer_email,
            edt_customer_mobile_number,edt_customer_aadhar_number,edt_Customer_pan_number;
    private Button btn_add_new_customer;

    private String customer_name, contact_person_name, address, email, mobile, pan_number, aadhar_number, gst_number;

    private TextInputLayout textCustomerName,textContactPerson,textCustomerGstNumber,textCustomerAddress,textCustomerEmail,
            textCustomerMobileNumber,textCustomerAadharNumber,textCustomerPanNumber;
    public static ApiInterface apiInterface;
    private ImageView ic_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        find_View_IdS();
        events();
    }
    private void find_View_IdS() {
        ic_back=findViewById(R.id.ic_back);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        textCustomerName=findViewById(R.id.textCustomerName);
        textContactPerson=findViewById(R.id.textContactPerson);
        textCustomerGstNumber=findViewById(R.id.textCustomerGstNumber);
        textCustomerAddress=findViewById(R.id.textCustomerAddress);
        textCustomerEmail=findViewById(R.id.textCustomerEmail);
        textCustomerMobileNumber=findViewById(R.id.textCustomerMobileNumber);
        textCustomerAadharNumber=findViewById(R.id.textCustomerAadharNumber);
        textCustomerPanNumber=findViewById(R.id.textCustomerPanNumber);


        edt_customer_name=findViewById(R.id.edt_customer_name);
        edt_contact_person=findViewById(R.id.edt_contact_person);
        edt_customer_gst_no=findViewById(R.id.edt_customer_gst_no);
        edt_customer_address=findViewById(R.id.edt_customer_address);
        edt_customer_email=findViewById(R.id.edt_customer_email);
        edt_customer_mobile_number=findViewById(R.id.edt_customer_mobile_number);
        edt_customer_aadhar_number=findViewById(R.id.edt_customer_aadhar_number);
        edt_Customer_pan_number=findViewById(R.id.edt_Customer_pan_number);

        btn_add_new_customer=findViewById(R.id.btn_add_new_customer);



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

                if(validation())
                {
                    if (NetworkUtils.isNetworkAvailable(AddCustomerActivity.this)) {
                        addCustomer();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(AddCustomerActivity.this);
                    }

                }

            }
        });
    }

    private boolean validation() {
        customer_name = edt_customer_name.getText().toString().trim();
        contact_person_name = edt_contact_person.getText().toString().trim();
        address = edt_customer_address.getText().toString().trim();
        email =edt_customer_email.getText().toString().trim();
        mobile = edt_customer_mobile_number.getText().toString().trim();
        pan_number = edt_Customer_pan_number.getText().toString();
        aadhar_number = edt_customer_aadhar_number.getText().toString().trim();
        gst_number = edt_customer_gst_no.getText().toString().trim();


        if (customer_name.equalsIgnoreCase("")) {
            textCustomerName.setError("Enter Customer Name");
            edt_customer_name.requestFocus();
            return false;
        } else {
            textCustomerName.setError(null);
            textCustomerName.setErrorEnabled(false);
        }
        if (contact_person_name.equalsIgnoreCase("")) {
            textContactPerson.setError("Enter Contact Person Name");
            edt_contact_person.requestFocus();
            return false;
        } else {
            textContactPerson.setError(null);
            textContactPerson.setErrorEnabled(false);
        }
        if (gst_number.equalsIgnoreCase("") && pan_number.equalsIgnoreCase("")) {
            textCustomerGstNumber.setError("Enter GST Number or Pan Number ");
            edt_customer_gst_no.requestFocus();
            return false;
        } else {
            textCustomerGstNumber.setError(null);
            textCustomerGstNumber.setErrorEnabled(false);
        }
        if (address.equalsIgnoreCase("")) {
            textCustomerAddress.setError("Enter Address");
            edt_customer_address.requestFocus();
            return false;
        } else {
            textCustomerAddress.setError(null);
            textCustomerAddress.setErrorEnabled(false);
        }
        if (email.equalsIgnoreCase("")) {
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
        }
        if (mobile.equalsIgnoreCase("")) {
            textCustomerMobileNumber.setError("Enter Mobile Number");
            edt_customer_mobile_number.requestFocus();
            return false;
        } else {
            textCustomerMobileNumber.setError(null);
            textCustomerMobileNumber.setErrorEnabled(false);
        }
        if (mobile.length() != 10) {
            textCustomerMobileNumber.setError("Invalid Mobile Number");
            edt_customer_mobile_number.requestFocus();
            return false;
        } else {
            textCustomerMobileNumber.setError(null);
            textCustomerMobileNumber.setErrorEnabled(false);
        }
        if (aadhar_number.equalsIgnoreCase("")) {
            textCustomerAadharNumber.setError("Enter Aadhar Number");
            edt_customer_aadhar_number.requestFocus();
            return false;
        } else {
            textCustomerAadharNumber.setError(null);
            textCustomerAadharNumber.setErrorEnabled(false);
        }
        if (aadhar_number.length()<12) {
            textCustomerAadharNumber.setError("Invalid Aadhar Number");
            edt_customer_aadhar_number.requestFocus();
            return false;
        } else {
            textCustomerAadharNumber.setError(null);
            textCustomerAadharNumber.setErrorEnabled(false);
        }

        return true;
    }


    private void addCustomer() {

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
        Toast.makeText(this, "Customer Add Successfully", Toast.LENGTH_SHORT).show();
    }
}
