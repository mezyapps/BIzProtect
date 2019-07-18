package com.mezyapps.bizprotect.view.activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mezyapps.bizprotect.R;

public class PortfolioActivity extends AppCompatActivity {

    private TextInputEditText edt_company_name,edt_person_name,edt_address,edt_email,
            edt_mobile_number,edt_aadhar_number,edt_pan_number,edt_password,edt_gst_no;
    private Button btn_create_portfolio;
    private String company_name,person_name,address,email,mobile,pan_number,password,aadhar_number,gst_number;
    private TextInputLayout textPassword,textPanNumber,textAadharNumber,textMobileNumber,textEmail,
                            textAddress,textGstNumber,textPersonName,textCompanyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        edt_company_name=findViewById(R.id.edt_company_name);
        edt_person_name=findViewById(R.id.edt_person_name);
        edt_address=findViewById(R.id.edt_address);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile_number=findViewById(R.id.edt_mobile_number);
        edt_pan_number=findViewById(R.id.edt_pan_number);
        edt_password=findViewById(R.id.edt_password);
        edt_aadhar_number=findViewById(R.id.edt_aadhar_number);
        edt_gst_no=findViewById(R.id.edt_gst_no);
        btn_create_portfolio=findViewById(R.id.btn_create_portfolio);


        textCompanyName=findViewById(R.id.textCompanyName);
        textPersonName=findViewById(R.id.textPersonName);
        textGstNumber=findViewById(R.id.textGstNumber);
        textAddress=findViewById(R.id.textAddress);
        textEmail=findViewById(R.id.textEmail);
        textMobileNumber=findViewById(R.id.textMobileNumber);
        textAadharNumber=findViewById(R.id.textAadharNumber);
        textPanNumber=findViewById(R.id.textPanNumber);
        textPassword=findViewById(R.id.textPassword);
    }

    private void events() {


        btn_create_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validation())
                {

                }

            }
        });


    }

    private boolean validation() {
        company_name=edt_company_name.getText().toString().trim();
        person_name=edt_person_name.getText().toString().trim();
        address=edt_address.getText().toString().trim();
        email=edt_email.getText().toString().trim();
        mobile=edt_mobile_number.getText().toString().trim();
        pan_number=edt_pan_number.getText().toString();
        aadhar_number=edt_aadhar_number.getText().toString().trim();
        password=edt_password.getText().toString().trim();
        gst_number=edt_gst_no.getText().toString().trim();

        if(company_name.equalsIgnoreCase(""))
        {
            textCompanyName.setError("Enter Company Name");
            textCompanyName.requestFocus();
            return false;
        }
        else
        {
            textCompanyName.setError(null);
        }
       if(person_name.equalsIgnoreCase(""))
        {
            textPersonName.setError("Enter Person Name");
            textPersonName.requestFocus();
            return false;
        }
        else if(address.equalsIgnoreCase(""))
        {
            textAddress.setError("Enter Address");
            textAddress.requestFocus();
            return false;
        }
        else if(email.equalsIgnoreCase(""))
        {
            textEmail.setError("Enter Email");
            textEmail.requestFocus();
            return false;
        }
        else if(mobile.equalsIgnoreCase(""))
        {
            textMobileNumber.setError("Enter Mobile Number");
            textMobileNumber.requestFocus();
            return false;
        }
        else if(mobile.length()!=10)
        {
            textMobileNumber.setError("Invalid Mobile Number");
            textMobileNumber.requestFocus();
            return false;
        }
        else if(aadhar_number.length()!=10)
        {
            textAadharNumber.setError("Enter Aadhar Number");
            textAadharNumber.requestFocus();
            return false;
        }
        else if(gst_number.equalsIgnoreCase("")&&pan_number.equalsIgnoreCase("")) {
            textGstNumber.setError("Enter GST Number or Pan Number ");
            textGstNumber.requestFocus();
            return false;
        }

        return true;
    }
}
