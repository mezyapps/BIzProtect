package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.BalanceAmountListModel;

import java.util.ArrayList;

public class BalanceAmountAdapter extends RecyclerView.Adapter<BalanceAmountAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BalanceAmountListModel> balanceAmountListModelArrayList;
    public BalanceAmountAdapter(Context mContext, ArrayList<BalanceAmountListModel> balanceAmountListModelArrayList) {
        this.mContext = mContext;
        this.balanceAmountListModelArrayList=balanceAmountListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_balance_list_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BalanceAmountListModel balanceAmountListModel=balanceAmountListModelArrayList.get(position);

        holder.textName.setText(balanceAmountListModel.getName());
        holder.textAddress.setText(balanceAmountListModel.getAddress());
        holder.textMobileNumber.setText(balanceAmountListModel.getMobile_no());
        holder.textBalance.setText(balanceAmountListModel.getBalance_amt());
    }

    @Override
    public int getItemCount() {
        return balanceAmountListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textAddress, textMobileNumber, textBalance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            textMobileNumber = itemView.findViewById(R.id.textMobileNumber);
            textBalance = itemView.findViewById(R.id.textBalance);
        }
    }
}
