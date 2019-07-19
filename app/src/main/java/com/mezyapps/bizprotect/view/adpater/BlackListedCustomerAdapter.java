package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.BlackListCustomerModel;

import java.util.ArrayList;

public class BlackListedCustomerAdapter extends RecyclerView.Adapter<BlackListedCustomerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList;

    public BlackListedCustomerAdapter(Context mContext, ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList)
    {
        this.mContext=mContext;
        this.blackListCustomerModelArrayList=blackListCustomerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_black_list_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BlackListCustomerModel blackListCustomerModel=blackListCustomerModelArrayList.get(position);

        holder.textName.setText(blackListCustomerModel.getCustomer_name());
        holder.textCustomerFirstName.setText(blackListCustomerModel.getCustomer_name());
        holder.textBlackList.setText(blackListCustomerModel.getStatus());
        holder.textGstNumber.setText(blackListCustomerModel.getGst_no());

    }

    @Override
    public int getItemCount() {
        return blackListCustomerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textGstNumber,textBlackList,textCustomerFirstName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName=itemView.findViewById(R.id.textName);
            textGstNumber=itemView.findViewById(R.id.textGstNumber);
            textBlackList=itemView.findViewById(R.id.textBlackList);
            textCustomerFirstName=itemView.findViewById(R.id.textCustomerFirstName);
        }
    }
}
