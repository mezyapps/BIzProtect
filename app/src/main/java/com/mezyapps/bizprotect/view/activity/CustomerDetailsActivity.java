package com.mezyapps.bizprotect.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.MyCustomerModel;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomerDetailsActivity extends AppCompatActivity {

    private MyCustomerModel myCustomerModel;
    private TextView textName, textContact_Person, textAddress, textgst_no, textEmail, textMobile_No, textAadhar_no, textPan_no, text_blacklisted, text_unblacklisted;
    private LinearLayout linearlayout_status_blacklist, linearlayout_status_unblacklist;
    private String customer_id,client_id,status;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        linearlayout_status_blacklist = findViewById(R.id.linearlayout_status_blacklist);
        linearlayout_status_unblacklist = findViewById(R.id.linearlayout_status_unblacklist);
        ic_back = findViewById(R.id.ic_back);
        textName = findViewById(R.id.textName);
        textContact_Person = findViewById(R.id.textContact_Person);
        textAddress = findViewById(R.id.textAddress);
        textgst_no = findViewById(R.id.textgst_no);
        textEmail = findViewById(R.id.textEmail);
        textMobile_No = findViewById(R.id.textMobile_No);
        textAadhar_no = findViewById(R.id.textAadhar_no);
        textPan_no = findViewById(R.id.textPan_no);
        text_blacklisted = findViewById(R.id.text_blacklisted);
        text_unblacklisted = findViewById(R.id.text_unblacklisted);


        clientProfileModelArrayList= SharedLoginUtils.getUserDetails(CustomerDetailsActivity.this);
        client_id=clientProfileModelArrayList.get(0).getClient_id();


        Bundle bundle = getIntent().getExtras();
        myCustomerModel = bundle.getParcelable("MYCUSTOMER");
        textName.setText(myCustomerModel.getCustomer_name());
        textContact_Person.setText(myCustomerModel.getContact_person());
        textAddress.setText(myCustomerModel.getAddress());
        textgst_no.setText(myCustomerModel.getGst_no());
        textEmail.setText(myCustomerModel.getEmail());
        textMobile_No.setText(myCustomerModel.getMobile_no());
        textAadhar_no.setText(myCustomerModel.getAadhar_no());
        textPan_no.setText(myCustomerModel.getPan_no());
        customer_id=myCustomerModel.getCustomer_name();
        status=myCustomerModel.getStatus();

    }

    private void events() {
        if(status.equalsIgnoreCase("4"))
        {
            linearlayout_status_unblacklist.setVisibility(View.VISIBLE);
            linearlayout_status_blacklist.setVisibility(View.GONE);
            text_unblacklisted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status="1";
                }
            });
        }
        else {
            linearlayout_status_unblacklist.setVisibility(View.GONE);
            linearlayout_status_blacklist.setVisibility(View.VISIBLE);
            text_blacklisted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status="4";
                }
            });
        }

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
