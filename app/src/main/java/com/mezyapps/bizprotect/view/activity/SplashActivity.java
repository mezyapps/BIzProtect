package com.mezyapps.bizprotect.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.SharedLicenseUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    private String is_login="",macAddress,str_order_date,date;
    //private String is_key_approve="";
    private Handler handler;
    private TelephonyManager telephonyManager;
    public static ApiInterface apiInterface;
    private String TAG=this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        find_View_IdS();
        events();
    }

    private void find_View_IdS() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        is_login = SharedLoginUtils.getLoginSharedUtils(getApplicationContext());
        //is_key_approve= SharedLicenseUtils.getLicenseSharedUtils(getApplicationContext());
         macAddress=SharedLicenseUtils.getDeviceId(SplashActivity.this);
    }
    private void events() {
       /* if(is_key_approve.equalsIgnoreCase("")) {

            if (NetworkUtils.isNetworkAvailable(SplashActivity.this)) {
                callLicenseApprove();
            } else {
                NetworkUtils.isNetworkNotAvailable(SplashActivity.this);
            }

        }*/

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        } else {
            handlerCall();
        }
    }
    private void checkPermissions() {
        RxPermissions.getInstance(SplashActivity.this)
                .request(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        initialize(aBoolean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    private void initialize(Boolean isAppInitialized) {
        if (isAppInitialized) {
            handlerCall();

        } else {
            /* If one Of above permission not grant show alert (force to grant permission)*/
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("Alert");
            builder.setMessage("All permissions necessary");

            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkPermissions();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }

    private void handlerCall() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                        Intent login_intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(login_intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    finish();
                }
        }, 3000);
    }
    private void callLicenseApprove() {
        Call<SuccessModel> call = apiInterface.checkLicenseKey(macAddress);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

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
                                //Toast.makeText(SplashActivity.this, "License Key Send For Approval", Toast.LENGTH_LONG).show();
                                SharedLicenseUtils.putLicenseSharedUtils(SplashActivity.this);

                            } else  if(code.equalsIgnoreCase("3")){
                               // Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(SplashActivity.this, message, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(SplashActivity.this, "Response Null", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

}
