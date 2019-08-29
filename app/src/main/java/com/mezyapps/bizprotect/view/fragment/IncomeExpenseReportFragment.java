package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.model.MonthlyReportModel;

import java.util.ArrayList;

public class IncomeExpenseReportFragment extends Fragment {
    private Context mContext;
    private BottomNavigationView bottom_navigation_report;
    private DatabaseHandler databaseHandler;
    private ArrayList<MonthlyReportModel> monthlyReportModelArrayList = new ArrayList<>();
    private RecyclerView recyclerView_report;
    private TextView textTotalIncome, textTotalExpense, textTotalSaving;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_income_expense_report, container, false);
        mContext = getActivity();
        find_View_IDs(view);
        events();
        return view;
    }

    private void find_View_IDs(View view) {
        databaseHandler = new DatabaseHandler(mContext);
        bottom_navigation_report = view.findViewById(R.id.bottom_navigation_report);
        recyclerView_report = view.findViewById(R.id.recyclerView_report);

        textTotalIncome = view.findViewById(R.id.textTotalIncome);
        textTotalExpense = view.findViewById(R.id.textTotalExpense);
        textTotalSaving = view.findViewById(R.id.textTotalSaving);
    }

    private void events() {

        bottom_navigation_report.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.nav_my_list_customer:
                        Toast.makeText(mContext, "Monthly Report", Toast.LENGTH_SHORT).show();
                        callMonthlyIncomeExpense();
                        break;

                    case R.id.nav_my_customer:
                        Toast.makeText(mContext, "Yearly Report", Toast.LENGTH_SHORT).show();
                        break;

                }

                return true;
            }
        });


    }

    private void callMonthlyIncomeExpense() {

        int totalIncome=0,totalExpense=0,saving=0;
        try {
            monthlyReportModelArrayList = databaseHandler.getMonthlyReport();
            for (int i = 0; i <monthlyReportModelArrayList.size();i++) {
                totalIncome= totalIncome+Integer.parseInt(monthlyReportModelArrayList.get(i).getIncome_amount());
                totalExpense=totalExpense+Integer.parseInt(monthlyReportModelArrayList.get(i).getExpense_amount());
            }
            textTotalIncome.setText("Income : "+totalIncome);
            textTotalExpense.setText("Expenses : "+totalExpense);
            saving=totalIncome-totalExpense;
            textTotalSaving.setText("Saving : "+saving);

        }
        catch (Exception e)
        {
         e.printStackTrace();
        }
    }

}
