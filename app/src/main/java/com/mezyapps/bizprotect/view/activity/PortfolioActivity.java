package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.utils.SuccessDialog;

import java.net.ProtocolFamily;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioActivity extends AppCompatActivity {

    private EditText edt_company_name, edt_person_name, edt_address, edt_email,
            edt_mobile_number, edt_aadhar_number, edt_pan_number, edt_password, edt_gst_no;
    private Button btn_create_portfolio;
    private String company_name, person_name, address, email, mobile, pan_number, password, aadhar_number, gst_number;
    private TextInputLayout textPassword, textPanNumber, textAadharNumber, textMobileNumber, textEmail,
            textAddress, textGstNumber, textPersonName, textCompanyName;
    public static ApiInterface apiInterface;
    private ImageView ic_back;
    private SuccessDialog successDialog;
    private ShowProgressDialog showProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_person_name = findViewById(R.id.edt_person_name);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_number = findViewById(R.id.edt_mobile_number);
        edt_pan_number = findViewById(R.id.edt_pan_number);
        edt_password = findViewById(R.id.edt_password);
        edt_aadhar_number = findViewById(R.id.edt_aadhar_number);
        edt_gst_no = findViewById(R.id.edt_gst_no);
        btn_create_portfolio = findViewById(R.id.btn_create_portfolio);
        ic_back = findViewById(R.id.ic_back);


        textCompanyName = findViewById(R.id.textCompanyName);
        textPersonName = findViewById(R.id.textPersonName);
        textGstNumber = findViewById(R.id.textGstNumber);
        textAddress = findViewById(R.id.textAddress);
        textEmail = findViewById(R.id.textEmail);
        textMobileNumber = findViewById(R.id.textMobileNumber);
        textAadharNumber = findViewById(R.id.textAadharNumber);
        textPanNumber = findViewById(R.id.textPanNumber);
        textPassword = findViewById(R.id.textPassword);
        successDialog=new SuccessDialog(PortfolioActivity.this);
        showProgressDialog=new ShowProgressDialog(PortfolioActivity.this);
    }

    private void events() {


        btn_create_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {
                    if (NetworkUtils.isNetworkAvailable(PortfolioActivity.this)) {
                        callCreatePortfolio();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(PortfolioActivity.this);
                    }


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

    private void callCreatePortfolio() {
        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.registrationClient(person_name,company_name,address,gst_number,email,mobile,aadhar_number,pan_number,password);
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
                                Toast.makeText(PortfolioActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if(code.equalsIgnoreCase("3")) {
                                Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgressDialog.dismissDialog();
            }
        });

    }

    private boolean validation() {
        company_name = edt_company_name.getText().toString().trim();
        person_name = edt_person_name.getText().toString().trim();
        address = edt_address.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        mobile = edt_mobile_number.getText().toString().trim();
        pan_number = edt_pan_number.getText().toString();
        aadhar_number = edt_aadhar_number.getText().toString().trim();
        password = edt_password.getText().toString().trim();
        gst_number = edt_gst_no.getText().toString().trim();

        if (company_name.equalsIgnoreCase("")) {
            textCompanyName.setError("Enter Company Name");
            edt_company_name.requestFocus();
            return false;
        } else {
            textCompanyName.setError(null);
            textCompanyName.setErrorEnabled(false);
        }
    /*    if (person_name.equalsIgnoreCase("")) {
            textPersonName.setError("Enter Person Name");
            edt_person_name.requestFocus();
            return false;
        } else {
            textPersonName.setError(null);
            textPersonName.setErrorEnabled(false);
        }*/
     /*   if (gst_number.equalsIgnoreCase("") && aadhar_number.equalsIgnoreCase("")) {
            textGstNumber.setError("Enter GST Number or  Aadhar number ");
            textAadharNumber.setError("Enter GST Number or  Aadhar number ");
            edt_gst_no.requestFocus();
            return false;
        } else {
            textGstNumber.setError(null);
            textGstNumber.setErrorEnabled(false);
            textAadharNumber.setError(null);
            textAadharNumber.setErrorEnabled(false);

            if (aadhar_number.length()<12) {
                textAadharNumber.setError("Invalid Aadhar Number");
                edt_aadhar_number.requestFocus();
                return false;
            } else {
                textAadharNumber.setError(null);
                textAadharNumber.setErrorEnabled(false);
            }
        }*/
       /* if (address.equalsIgnoreCase("")) {
            textAddress.setError("Enter Address");
            edt_address.requestFocus();
            return false;
        } else {
            textAddress.setError(null);
            textAddress.setErrorEnabled(false);
        }*/
       /* if (email.equalsIgnoreCase("")) {
            textEmail.setError("Enter Email");
            edt_email.requestFocus();
            return false;
        } else {
            textEmail.setError(null);
            textEmail.setErrorEnabled(false);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Invalid  Email");
            edt_email.requestFocus();
            return false;
        } else {
            textEmail.setError(null);
            textEmail.setErrorEnabled(false);
        }*/
        if (mobile.equalsIgnoreCase("")) {
            textMobileNumber.setError("Enter Mobile Number");
            edt_mobile_number.requestFocus();
            return false;
        } else {
            textMobileNumber.setError(null);
            textMobileNumber.setErrorEnabled(false);
        }
        if (mobile.length() != 10) {
            textMobileNumber.setError("Invalid Mobile Number");
            edt_mobile_number.requestFocus();
            return false;
        } else {
            textMobileNumber.setError(null);
            textMobileNumber.setErrorEnabled(false);
        }
       /* if (aadhar_number.equalsIgnoreCase("")) {
            textAadharNumber.setError("Enter Aadhar Number");
            edt_aadhar_number.requestFocus();
            return false;
        } else {
            textAadharNumber.setError(null);
            textAadharNumber.setErrorEnabled(false);
        }*/

        if (password.equalsIgnoreCase("")) {
            textPassword.setError("Enter password");
            edt_password.requestFocus();
            return false;
        } else {
            textPassword.setError(null);
            textPassword.setErrorEnabled(false);
        }
        if (password.length()<6) {
            textPassword.setError("Password Must Contain At Least 6 Characters");
            edt_password.requestFocus();
            return false;
        } else {
            textPassword.setError(null);
            textPassword.setErrorEnabled(false);
        }

        return true;
    }

}
