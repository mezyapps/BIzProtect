package com.mezyapps.bizprotect.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.model.DailyReportModel;
import com.mezyapps.bizprotect.view.activity.IncomeExpenseActivity;
import com.mezyapps.bizprotect.view.adpater.DailyReportAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IncomeExpenseFragment extends Fragment {

    private Context mContext;
    private EditText edit_date, edit_amount, edit_description;
    private String date, status = "1", amount, description;
    private RadioGroup radioGroupIncomeExpenses;
    private RadioButton radioStatusButton;
    private Button btnSave;
    private DatabaseHandler databaseHandler;
    String income, expense;
    private TextView textIncome, textExpense, textBalance;
    private View view;
    private RecyclerView recyclerView_daily;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_income_expense, container, false);
        mContext=getActivity();
        find_View_Ids(view);
        events();

        return view;
    }

    private void find_View_Ids(View view) {
        databaseHandler = new DatabaseHandler(mContext);
        edit_date =view.findViewById(R.id.edit_date);
        edit_amount = view.findViewById(R.id.edit_amount);
        edit_description =view.findViewById(R.id.edit_description);
        btnSave =view.findViewById(R.id.btnSave);

        textIncome =view.findViewById(R.id.textIncome);
        textExpense =view.findViewById(R.id.textExpense);
        textBalance =view.findViewById(R.id.textBalance);
        recyclerView_daily =view.findViewById(R.id.recyclerView_daily);

        radioGroupIncomeExpenses =view.findViewById(R.id.radioGroupIncomeExpenses);
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        edit_date.setText(date);
        edit_date.setFocusable(false);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView_daily.setLayoutManager(linearLayoutManager);

        income = databaseHandler.getAllIncome(date);
        expense = databaseHandler.getAllExpense(date);
        callCalculation(income, expense);
        callDailyReport();

    }
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
                radioStatusButton =view.findViewById(checkedId);
                String statusName = radioStatusButton.getText().toString().trim();
                if (statusName.equalsIgnoreCase("Income")) {
                    status = "0";
                } else {
                    status = "1";
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    boolean result = databaseHandler.addIncomeExpense(amount, date, status, description);
                    if (result = true) {
                        edit_amount.setText("");
                        edit_description.setText("");
                        edit_amount.requestFocus();
                        income = databaseHandler.getAllIncome(date);
                        expense = databaseHandler.getAllExpense(date);
                        callCalculation(income, expense);
                        callDailyReport();
                    }

                }

            }
        });
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
            int totalIncomeVal=Integer.parseInt(totalIncome);
            int totalExpenseVal=Integer.parseInt(totalExpense);
            int balance_amount =(totalIncomeVal-totalExpenseVal);
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
                        date = dateString;


                        edit_amount.setText("");
                        edit_description.setText("");
                        edit_amount.requestFocus();

                        edit_date.setText(dateString);
                        income = databaseHandler.getAllIncome(date);
                        expense = databaseHandler.getAllExpense(date);
                        callCalculation(income, expense);
                        callDailyReport();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void callDailyReport() {
        ArrayList<DailyReportModel> dailyReportModelArrayList=new ArrayList<>();
        dailyReportModelArrayList=databaseHandler.getDailyReport(date);
        DailyReportAdapter dailyReportAdapter=new DailyReportAdapter(mContext,dailyReportModelArrayList);
        recyclerView_daily.setAdapter(dailyReportAdapter);
        dailyReportAdapter.notifyDataSetChanged();
    }
}
