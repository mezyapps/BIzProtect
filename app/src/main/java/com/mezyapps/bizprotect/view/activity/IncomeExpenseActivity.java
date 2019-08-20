package com.mezyapps.bizprotect.view.activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.ConstantFields;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.view.adpater.TabViewBillReportAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeExpenseActivity extends AppCompatActivity {
    private ImageView ic_back,iv_dataBackUp;
    private TabLayout tabLayout_bill_report;
    private ViewPager viewPager_bill_report;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
    private String client_id;

    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tabLayout_bill_report=findViewById(R.id.tabLayout_bill_report);
        viewPager_bill_report=findViewById(R.id.viewPager_bill_report);
        iv_dataBackUp=findViewById(R.id.iv_dataBackUp);
        ic_back=findViewById(R.id.ic_back);

        tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Add Income/Expense"));
      //  tabLayout_bill_report.addTab(tabLayout_bill_report.newTab().setText("Report"));
        tabLayout_bill_report.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewBillReportAdapter tabViewBillReportAdapter=new TabViewBillReportAdapter(IncomeExpenseActivity.this,getSupportFragmentManager(),tabLayout_bill_report.getTabCount());
        viewPager_bill_report.setAdapter(tabViewBillReportAdapter);
        viewPager_bill_report.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_bill_report));

        clientProfileModelArrayList = SharedLoginUtils.getUserDetails(IncomeExpenseActivity.this);
        client_id = clientProfileModelArrayList.get(0).getClient_id();

    }

    private void events() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout_bill_report.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_bill_report.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        iv_dataBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(IncomeExpenseActivity.this)) {
                    callBackUpData();
                } else {
                   NetworkUtils.isNetworkNotAvailable(IncomeExpenseActivity.this);
                }
            }
        });

    }

    private void callBackUpData() {

        final DatabaseHandler databaseHandler=new DatabaseHandler(IncomeExpenseActivity.this);
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = databaseHandler.readLocalDatabase();

        while (cursor.moveToNext()) {

            String sync_status = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.SYNC_STATUS));
            if (sync_status.equalsIgnoreCase(ConstantFields.CommonConstant.sync_failed)) {
                final String localDbId=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.ID));
                String localDate = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.DATE));
                String status = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.STATUS));
                String income_amount = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.INCOME_AMOUNT));
                String expense_amount = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.EXPENSE_AMOUNT));
                String description = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.DESCRIPTION));
                String amount = "0";
                if (income_amount.equalsIgnoreCase("0")) {
                    amount = expense_amount;
                }
                if (expense_amount.equalsIgnoreCase("0")) {
                    amount = income_amount;
                }

                Call<SuccessModel> call = apiInterface.addIncomeExpense(client_id, localDate, amount, description, status);

                call.enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        String str_response = new Gson().toJson(response.body());
                        Log.d("Response >>", str_response);

                        try {
                            if (response.isSuccessful()) {
                                SuccessModel successModule = response.body();

                                String message = null, code = null;
                                if (successModule != null) {
                                    message = successModule.getMessage();
                                    code = successModule.getCode();
                                    if (code.equalsIgnoreCase("1")) {
                                        Toast.makeText(IncomeExpenseActivity.this, "BackUp Successfully", Toast.LENGTH_SHORT).show();
                                        String getCurrentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                                        databaseHandler.updateDatabaseStatus(localDbId,ConstantFields.CommonConstant.sync_ok);
                                        SharedLoginUtils.putBackUpDateTime(IncomeExpenseActivity.this, getCurrentDateTime);
                                    } else {
                                        Toast.makeText(IncomeExpenseActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(IncomeExpenseActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
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
    }


}
