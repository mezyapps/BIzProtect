package com.mezyapps.bizprotect.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLicenseUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LicenseKeyActivity extends AppCompatActivity {

    private EditText edit_license_number;
    private Button btn_license;
    private TextView textTechnicalSupport,textMacAddress;
    private String strLicenseKey, strMakeCall, macAddress;
    public static ApiInterface apiInterface;
    private String TAG = this.getClass().getSimpleName();
    private TelephonyManager telephonyManager;
    private ShowProgressDialog showProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_key);

        macAddress=SharedLicenseUtils.getDeviceId(LicenseKeyActivity.this);
        if(macAddress.equalsIgnoreCase("")) {
            getDeviceId();
        }
        find_View_Ids();
        events();
    }

    private void getDeviceId() {
        //Take Mac Address
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        macAddress = telephonyManager.getDeviceId();
        SharedLicenseUtils.putDeviceId(LicenseKeyActivity.this,macAddress);
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        edit_license_number = findViewById(R.id.edit_license_number);
        btn_license = findViewById(R.id.btn_license);
        textMacAddress = findViewById(R.id.textMacAddress);
        textTechnicalSupport = findViewById(R.id.textTechnicalSupport);
        showProgressDialog=new ShowProgressDialog(LicenseKeyActivity.this);

        textMacAddress.setText("ID:-"+macAddress);

    }

    private void events() {

        btn_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    if (!(macAddress.equalsIgnoreCase(""))){

                        if (NetworkUtils.isNetworkAvailable(LicenseKeyActivity.this)) {
                            callSendLicenseKey();
                        } else {
                            NetworkUtils.isNetworkNotAvailable(LicenseKeyActivity.this);
                        }
                    }
                    else
                    {
                        getDeviceId();
                    }

                }
            }
        });
        textTechnicalSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMakeCall = textTechnicalSupport.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + strMakeCall));
                startActivity(callIntent);
            }
        });

    }

    private void callSendLicenseKey() {
        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.licenseKeySend(strLicenseKey, macAddress);
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
                                Toast.makeText(LicenseKeyActivity.this, "License Key Send For Approval", Toast.LENGTH_LONG).show();

                            } else if (code.equalsIgnoreCase("3")) {
                            } else {
                                Toast.makeText(LicenseKeyActivity.this, message, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(LicenseKeyActivity.this, "Response Null", Toast.LENGTH_LONG).show();
                        }
                        edit_license_number.setText("");
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
        strLicenseKey = edit_license_number.getText().toString().trim();
        if (strLicenseKey.equalsIgnoreCase("")) {
            edit_license_number.setError("Enter License Key");
            edit_license_number.requestFocus();
            return false;
        }
        return true;
    }

}
