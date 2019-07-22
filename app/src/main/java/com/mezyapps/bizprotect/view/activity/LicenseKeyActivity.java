package com.mezyapps.bizprotect.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
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
import com.mezyapps.bizprotect.model.SuccessModule;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLicenseUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LicenseKeyActivity extends AppCompatActivity {

    private EditText edit_license_number;
    private Button btn_license;
    private TextView textTechnicalSupport;
    private String strLicenseKey,strMakeCall,macAddress;
    public static ApiInterface apiInterface;
    private String TAG=this.getClass().getSimpleName();
    private TelephonyManager telephonyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_key);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        edit_license_number=findViewById(R.id.edit_license_number);
        btn_license=findViewById(R.id.btn_license);
        textTechnicalSupport=findViewById(R.id.textTechnicalSupport);

        //Take Mac Address
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        macAddress = telephonyManager.getDeviceId();
    }

    private void events() {

        btn_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation())
                {

                    if (NetworkUtils.isNetworkAvailable(LicenseKeyActivity.this)) {
                        callSendLicenseKey();
                    }
                    else {
                        NetworkUtils.isNetworkNotAvailable(LicenseKeyActivity.this);
                    }

                }
            }
        });
        textTechnicalSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMakeCall=textTechnicalSupport.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+strMakeCall));
                startActivity(callIntent);
            }
        });

    }

    private void callSendLicenseKey() {

        Call<SuccessModule> call = apiInterface.licenseKeySend(strLicenseKey,macAddress);
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
                            message = successModule.getMessage();
                            code = successModule.getCode();
                            if (code.equalsIgnoreCase("1")) {
                                Toast.makeText(LicenseKeyActivity.this, "License Key Send For Approval", Toast.LENGTH_LONG).show();

                            } else  if(code.equalsIgnoreCase("3")){
                            }else
                            {
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
            public void onFailure(Call<SuccessModule> call, Throwable t) {

            }
        });
       /* SharedLicenseUtils.putLicenseSharedUtils(LicenseKeyActivity.this);
        Intent intent=new Intent(LicenseKeyActivity.this,PortfolioActivity.class);
        startActivity(intent);
        finish();*/


    }

    private boolean validation() {
        strLicenseKey=edit_license_number.getText().toString().trim();
        if(strLicenseKey.equalsIgnoreCase(""))
        {
            edit_license_number.setError("Enter License Key");
            edit_license_number.requestFocus();
            return false;
        }
       return  true;
    }

}
