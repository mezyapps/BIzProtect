package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.DailyReportModel;

import java.util.ArrayList;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<DailyReportModel> dailyReportModelArrayList;

    public DailyReportAdapter(Context mContext, ArrayList<DailyReportModel> dailyReportModelArrayList) {
        this.mContext = mContext;
        this.dailyReportModelArrayList = dailyReportModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_daily_report, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DailyReportModel dailyReportModel = dailyReportModelArrayList.get(position);

        if (dailyReportModel.getIncome_amount().equalsIgnoreCase("0")) {
            holder.textIncome.setText(" ");
        } else {
            holder.textIncome.setText(dailyReportModel.getIncome_amount());
        }

        if (dailyReportModel.getExpense_amount().equalsIgnoreCase("0")) {
            holder.textExpense.setText(" ");
        } else {
            holder.textExpense.setText(dailyReportModel.getExpense_amount());
        }

        holder.textDescription.setText(dailyReportModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return dailyReportModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textIncome, textExpense, textDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textIncome = itemView.findViewById(R.id.textIncome);
            textExpense = itemView.findViewById(R.id.textExpense);
            textDescription = itemView.findViewById(R.id.textDescription);
        }
    }
}
