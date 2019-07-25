package com.mezyapps.bizprotect.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;
import com.mezyapps.bizprotect.model.MyCustomerModel;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;

import java.util.ArrayList;

public class CustomerDetailsMyBlackListedActivity extends AppCompatActivity {

    private MyBlackListedCustomerModel myBlackListedCustomerModel;
    private TextView textName, textContact_Person, textAddress, textgst_no, textEmail, textMobile_No, textAadhar_no, textPan_no, text_unblacklisted;
    private LinearLayout linearlayout_status_unblacklist;
    private String customer_id, client_id, status;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
    private ImageView ic_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_my_black_listed);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
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
        text_unblacklisted = findViewById(R.id.text_unblacklisted);


        clientProfileModelArrayList = SharedLoginUtils.getUserDetails(CustomerDetailsMyBlackListedActivity.this);
        client_id = clientProfileModelArrayList.get(0).getClient_id();


        Bundle bundle = getIntent().getExtras();
        myBlackListedCustomerModel = bundle.getParcelable("MYCUSTOMER");
        textName.setText(myBlackListedCustomerModel.getCustomer_name());
        textContact_Person.setText(myBlackListedCustomerModel.getContact_person());
        textAddress.setText(myBlackListedCustomerModel.getAddress());
        textgst_no.setText(myBlackListedCustomerModel.getGst_no());
        textEmail.setText(myBlackListedCustomerModel.getEmail());
        textMobile_No.setText(myBlackListedCustomerModel.getMobile_no());
        textAadhar_no.setText(myBlackListedCustomerModel.getAadhar_no());
        textPan_no.setText(myBlackListedCustomerModel.getPan_no());
        customer_id = myBlackListedCustomerModel.getCustomer_name();
        status = myBlackListedCustomerModel.getStatus();

    }

    private void events() {

        text_unblacklisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "1";
            }
        });


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
