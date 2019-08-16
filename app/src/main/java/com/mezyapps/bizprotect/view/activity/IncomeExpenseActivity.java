package com.mezyapps.bizprotect.view.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.database.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IncomeExpenseActivity extends AppCompatActivity {
    private ImageView ic_back;
    private EditText edit_date, edit_amount, edit_description;
    private String date, status = "1", amount, description;
    private RadioGroup radioGroupIncomeExpenses;
    private RadioButton radioStatusButton;
    private Button btnSave;
    private DatabaseHandler databaseHandler;
    String income, expense;
    private TextView textIncome, textExpense, textBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        databaseHandler = new DatabaseHandler(this);
        ic_back = findViewById(R.id.ic_back);
        edit_date = findViewById(R.id.edit_date);
        edit_amount = findViewById(R.id.edit_amount);
        edit_description = findViewById(R.id.edit_description);
        btnSave = findViewById(R.id.btnSave);

        textIncome = findViewById(R.id.textIncome);
        textExpense = findViewById(R.id.textExpense);
        textBalance = findViewById(R.id.textBalance);

        radioGroupIncomeExpenses = findViewById(R.id.radioGroupIncomeExpenses);
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        edit_date.setText(date);
        edit_date.setFocusable(false);

        income = databaseHandler.getAllIncome(date);
        expense = databaseHandler.getAllExpense(date);
        callCalculation(income, expense);

    }

    private void events() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCalendarPicker();
            }
        });

        radioGroupIncomeExpenses.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioStatusButton = findViewById(checkedId);
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
            Toast.makeText(this, "Enter Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (description.equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void callCalendarPicker() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(IncomeExpenseActivity.this,
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

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
