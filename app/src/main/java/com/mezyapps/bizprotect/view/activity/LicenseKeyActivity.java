package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.utils.NetworkUtils;

public class LicenseKeyActivity extends AppCompatActivity {

    private EditText edit_license_number;
    private Button btn_license;
    private TextView textTechnicalSupport;
    private String strLicenseKey,strMakeCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_key);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        edit_license_number=findViewById(R.id.edit_license_number);
        btn_license=findViewById(R.id.btn_license);
        textTechnicalSupport=findViewById(R.id.textTechnicalSupport);
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

        Intent intent=new Intent(LicenseKeyActivity.this,PortfolioActivity.class);
        startActivity(intent);


    }

    private boolean validation() {
        strLicenseKey=edit_license_number.getText().toString().trim();
        if(strLicenseKey.equalsIgnoreCase(""))
        {
            edit_license_number.setError("Enter License Number");
            edit_license_number.requestFocus();
            return false;
        }
       return  true;
    }

}
