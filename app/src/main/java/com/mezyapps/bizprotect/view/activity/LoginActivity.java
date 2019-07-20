package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textPhone_Number,textPassword;
    private EditText edt_phone_number,edt_password;
    private Button btn_login;
    private String phone_number,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        find_View_Ids();
        events();

    }

    private void find_View_Ids() {
        textPhone_Number=findViewById(R.id.textPhone_Number);
        textPassword=findViewById(R.id.textPassword);
        edt_phone_number=findViewById(R.id.edt_phone_number);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);

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

    }

    private void callLogin() {
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
        SharedLoginUtils.putLoginSharedUtils(LoginActivity.this);
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
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
