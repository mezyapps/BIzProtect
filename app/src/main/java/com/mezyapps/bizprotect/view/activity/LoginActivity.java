package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textPhone_Number,textPassword;
    private EditText edt_phone_number,edt_password;
    private Button btn_login,btn_signup;
    private String phone_number,password;
    public static ApiInterface apiInterface;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private ShowProgressDialog showProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        find_View_Ids();
        events();

    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        textPhone_Number=findViewById(R.id.textPhone_Number);
        textPassword=findViewById(R.id.textPassword);
        edt_phone_number=findViewById(R.id.edt_phone_number);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);
        showProgressDialog=new ShowProgressDialog(LoginActivity.this);

    }

    private void events() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation())
                {
                    if (NetworkUtils.isNetworkAvailable(LoginActivity.this)) {
                        callLogin();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(LoginActivity.this);
                    }


                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,PortfolioActivity.class);
                startActivity(intent);
            }
        });

    }

    private void callLogin() {
        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.login(phone_number,password);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModule = response.body();
                        clientProfileModelArrayList.clear();
                        String message = null, code = null;
                        if (successModule != null) {
                             message = successModule.getMessage();
                             code = successModule.getCode();
                            if (code.equalsIgnoreCase("1")) {

                                clientProfileModelArrayList=successModule.getClientProfileModelArrayList();

                                if(clientProfileModelArrayList.size()!=0) {
                                    SharedLoginUtils.putLoginSharedUtils(LoginActivity.this);
                                    SharedLoginUtils.addUserUtils(LoginActivity.this, clientProfileModelArrayList);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(LoginActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
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
        phone_number=edt_phone_number.getText().toString().trim();
        password=edt_password.getText().toString().trim();


        if (phone_number.equalsIgnoreCase("")) {
            textPhone_Number.setError("Enter Mobile Number");
            edt_phone_number.requestFocus();
            return false;
        } else {
            textPhone_Number.setError(null);
            textPhone_Number.setErrorEnabled(false);
        }
        if (phone_number.length() != 10) {
            textPhone_Number.setError("Invalid Mobile Number");
            edt_phone_number.requestFocus();
            return false;
        } else {
            textPhone_Number.setError(null);
            textPhone_Number.setErrorEnabled(false);
        }
        if (password.equalsIgnoreCase("")) {
            textPassword.setError("Enter Password");
            edt_password.requestFocus();
            return false;
        } else {
            textPassword.setErrorEnabled(false);
            textPassword.setError(null);
        }
        if (password.length() < 6) {
            textPassword.setError("Invalid Mobile Number");
            edt_password.requestFocus();
            return false;
        } else {
            textPassword.setErrorEnabled(false);
            textPassword.setError(null);
        }
        return true;
    }
}
