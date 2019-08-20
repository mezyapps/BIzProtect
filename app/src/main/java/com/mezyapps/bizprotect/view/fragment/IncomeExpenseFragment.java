package com.mezyapps.bizprotect.view.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.mezyapps.bizprotect.model.DailyReportModel;
import com.mezyapps.bizprotect.model.OnlineDataBackUpModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.ConstantFields;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.utils.ShowProgressDialog;
import com.mezyapps.bizprotect.view.activity.AddCustomerActivity;
import com.mezyapps.bizprotect.view.activity.IncomeExpenseActivity;
import com.mezyapps.bizprotect.view.adpater.DailyReportAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeExpenseFragment extends Fragment {

    private Context mContext;
    private EditText edit_date, edit_amount, edit_description;
    private String showDate,sendDate,status = "1", amount, description;
    private RadioGroup radioGroupIncomeExpenses;
    private RadioButton radioStatusButton;
    private FloatingActionButton fabSave;
    private DatabaseHandler databaseHandler;
    String income, expense, backupStatus;
    private TextView textIncome, textExpense, textBalance, textBackup;
    private View view;
    private RecyclerView recyclerView_daily;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList = new ArrayList<>();
    private String client_id, sharedPreferenceDate, getBackUpDateTime;
    public static ApiInterface apiInterface;
    private ShowProgressDialog showProgressDialog;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout linearlayout_backup;
    private ArrayList<OnlineDataBackUpModel> onlineDataBackUpModelArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_income_expense, container, false);
        mContext = getActivity();
        find_View_Ids(view);
        events();

        return view;
    }

    private void find_View_Ids(View view) {
        databaseHandler = new DatabaseHandler(mContext);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        showProgressDialog = new ShowProgressDialog(mContext);

        edit_date = view.findViewById(R.id.edit_date);
        edit_amount = view.findViewById(R.id.edit_amount);
        edit_description = view.findViewById(R.id.edit_description);
        fabSave = view.findViewById(R.id.fabSave);
        linearlayout_backup = view.findViewById(R.id.linearlayout_backup);
        textBackup = view.findViewById(R.id.textBackup);

        textIncome = view.findViewById(R.id.textIncome);
        textExpense = view.findViewById(R.id.textExpense);
        textBalance = view.findViewById(R.id.textBalance);
        recyclerView_daily = view.findViewById(R.id.recyclerView_daily);
        radioGroupIncomeExpenses = view.findViewById(R.id.radioGroupIncomeExpenses);

        clientProfileModelArrayList = SharedLoginUtils.getUserDetails(mContext);
        client_id = clientProfileModelArrayList.get(0).getClient_id();


        sharedPreferenceDate = SharedLoginUtils.getCurrentDate(mContext);
        showDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        sendDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        getBackUpDateTime = SharedLoginUtils.getBackupDateTime(mContext);

        //Backup Local DataBase
        backupStatus = SharedLoginUtils.getBackUpStatus(mContext);
        if (backupStatus == null || backupStatus.equalsIgnoreCase("") || backupStatus.equalsIgnoreCase("false")) {

            if (NetworkUtils.isNetworkAvailable(mContext)) {
                getAllBackUpInLocalDatabase();
            } else {
                NetworkUtils.isNetworkNotAvailable(mContext);
            }
        }

        if (sharedPreferenceDate != null) {
            if (!sharedPreferenceDate.equalsIgnoreCase(sendDate)) {

                if (NetworkUtils.isNetworkAvailable(mContext)) {
                    callBackUpData();
                } else {
                    //NetworkUtils.isNetworkNotAvailable(mContext);
                }
            }
        } else {
            if (NetworkUtils.isNetworkAvailable(mContext)) {
                callBackUpData();
            } else {
                // NetworkUtils.isNetworkNotAvailable(mContext);
            }
        }

        if (getBackUpDateTime == null || getBackUpDateTime.equalsIgnoreCase("")) {
            linearlayout_backup.setVisibility(View.GONE);
        } else {
            linearlayout_backup.setVisibility(View.VISIBLE);
            textBackup.setText("Last Backup At : " + getBackUpDateTime);
        }

        //End BackUp Local Database

        edit_date.setText(showDate);
        edit_date.setFocusable(false);
        edit_amount.setFocusable(false);
        edit_description.setFocusable(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView_daily.setLayoutManager(linearLayoutManager);


        income = databaseHandler.getAllIncome(sendDate);
        expense = databaseHandler.getAllExpense(sendDate);
        callCalculation(income, expense);
        callDailyReport();

    }


    @SuppressLint("ClickableViewAccessibility")
    private void events() {
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCalendarPicker();
            }
        });

        radioGroupIncomeExpenses.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioStatusButton = view.findViewById(checkedId);
                String statusName = radioStatusButton.getText().toString().trim();
                if (statusName.equalsIgnoreCase("Income")) {
                    status = "0";
                } else {
                    status = "1";
                }
            }
        });
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                    if (NetworkUtils.isNetworkAvailable(mContext)) {
                        callInsertIncomeExpense();
                    } else {
                        insertLocalDatabase(ConstantFields.CommonConstant.sync_failed);
                    }
                }
            }
        });

        edit_amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edit_amount.setFocusableInTouchMode(true);

                return false;

            }
        });

        edit_description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edit_description.setFocusableInTouchMode(true);

                return false;

            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
    }

    private void callCalculation(String income, String expense) {
        if (income == null) {
            income = "0";
        }
        if (expense == null) {
            expense = "0";
        }
        try {
            textIncome.setText(income);
            textExpense.setText(expense);
            String totalIncome = textIncome.getText().toString().trim();
            String totalExpense = textExpense.getText().toString().trim();
            int totalIncomeVal = Integer.parseInt(totalIncome);
            int totalExpenseVal = Integer.parseInt(totalExpense);
            int balance_amount = (totalIncomeVal - totalExpenseVal);
            textBalance.setText(Integer.toString(balance_amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validation() {
        amount = edit_amount.getText().toString().trim();
        description = edit_description.getText().toString().trim();
        if (amount.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Enter Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (description.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void callCalendarPicker() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        String dateString = format.format(calendar.getTime());

                        SimpleDateFormat formatSendDate = new SimpleDateFormat("yyyy-MM-dd");
                        String dateSendString = formatSendDate.format(calendar.getTime());
                        sendDate=dateSendString;
                        edit_amount.setText("");
                        edit_description.setText("");
                        edit_amount.requestFocus();
                        edit_date.setText(dateString);


                        income = databaseHandler.getAllIncome(sendDate);
                        expense = databaseHandler.getAllExpense(sendDate);
                        callCalculation(income, expense);
                        callDailyReport();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void callDailyReport() {
        ArrayList<DailyReportModel> dailyReportModelArrayList = new ArrayList<>();
        dailyReportModelArrayList = databaseHandler.getDailyReport(sendDate);
        DailyReportAdapter dailyReportAdapter = new DailyReportAdapter(mContext, dailyReportModelArrayList);
        recyclerView_daily.setAdapter(dailyReportAdapter);
        dailyReportAdapter.notifyDataSetChanged();
    }


    public void insertLocalDatabase(String syncStatus) {
        boolean result = databaseHandler.addIncomeExpense(amount, sendDate, status, description, syncStatus);
        if (result = true) {
            edit_amount.setText("");
            edit_description.setText("");
            edit_amount.requestFocus();
            income = databaseHandler.getAllIncome(sendDate);
            expense = databaseHandler.getAllExpense(sendDate);
            callCalculation(income, expense);
            callDailyReport();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.registerReceiver(broadcastReceiver, new IntentFilter(ConstantFields.DatabaseConstant.UI_UPDATE_BROADCAST));
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(broadcastReceiver);
    }

    private void callInsertIncomeExpense() {
        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.addIncomeExpense(client_id, sendDate, amount, description, status);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
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
                                Toast.makeText(mContext, "Save  Successfully", Toast.LENGTH_SHORT).show();
                                insertLocalDatabase(ConstantFields.CommonConstant.sync_ok);
                            } else {
                                Toast.makeText(mContext, "Save Unsuccessfully ", Toast.LENGTH_SHORT).show();
                                insertLocalDatabase(ConstantFields.CommonConstant.sync_failed);
                            }

                        } else {
                            Toast.makeText(mContext, "Response Null", Toast.LENGTH_SHORT).show();
                            insertLocalDatabase(ConstantFields.CommonConstant.sync_failed);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgressDialog.dismissDialog();
                insertLocalDatabase(ConstantFields.CommonConstant.sync_failed);
            }
        });
    }


    private void callBackUpData() {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = databaseHandler.readLocalDatabase();

        while (cursor.moveToNext()) {

            String sync_status = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.SYNC_STATUS));
            if (sync_status.equalsIgnoreCase(ConstantFields.CommonConstant.sync_failed)) {
                final String localDbId = cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.ID));
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
                                        Toast.makeText(mContext, "BackUp Successfully", Toast.LENGTH_SHORT).show();
                                        SharedLoginUtils.putCurrentDate(mContext, sendDate);
                                        String getCurrentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                                        databaseHandler.updateDatabaseStatus(localDbId, ConstantFields.CommonConstant.sync_ok);
                                        SharedLoginUtils.putBackUpDateTime(mContext, getCurrentDateTime);
                                        linearlayout_backup.setVisibility(View.VISIBLE);
                                        textBackup.setText("Last Backup At : " + getCurrentDateTime);
                                    } else {
                                        Toast.makeText(mContext, "Try Again", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(mContext, "Try Again", Toast.LENGTH_SHORT).show();
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

    private void getAllBackUpInLocalDatabase() {

        showProgressDialog.showDialog();
        Call<SuccessModel> call = apiInterface.getIncomeExpenseBackUp(client_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgressDialog.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModule = response.body();

                        String message = null, code = null;
                        boolean result = false;
                        if (successModule != null) {
                            message = successModule.getMessage();
                            code = successModule.getCode();
                            onlineDataBackUpModelArrayList.clear();
                            if (code.equalsIgnoreCase("1")) {
                                onlineDataBackUpModelArrayList = successModule.getOnlineDataBackUpModelArrayList();

                                for (int i = 0; i < onlineDataBackUpModelArrayList.size(); i++) {
                                    String date = onlineDataBackUpModelArrayList.get(i).getDate();
                                    String income_amt = onlineDataBackUpModelArrayList.get(i).getIncome_Amount();
                                    String expense_amount = onlineDataBackUpModelArrayList.get(i).getIncome_Amount();
                                    String description = onlineDataBackUpModelArrayList.get(i).getDescription();
                                    String status = onlineDataBackUpModelArrayList.get(i).getStatus();
                                    String amount="";
                                    if(status.equalsIgnoreCase("0"))
                                    {
                                        amount=income_amt;
                                    }else {
                                        amount=expense_amount;
                                    }

                                     result=databaseHandler.addIncomeExpense(amount,date,status,description,ConstantFields.CommonConstant.sync_ok);
                                }
                                if(result==true)
                                {
                                    Toast.makeText(mContext, "Backup Successfully Restore ", Toast.LENGTH_SHORT).show();
                                    SharedLoginUtils.putBackUpStatus(mContext);
                                }

                            } else {
                                Toast.makeText(mContext, "Save Unsuccessfully ", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(mContext, "Response Null", Toast.LENGTH_SHORT).show();
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
}
