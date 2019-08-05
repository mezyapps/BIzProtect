package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.BlackListCustomerModel;
import com.mezyapps.bizprotect.view.activity.CustomerDetailsActivity;

import java.util.ArrayList;

public class BlackListedCustomerAdapter extends RecyclerView.Adapter<BlackListedCustomerAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList;
    private ArrayList<BlackListCustomerModel> arrayListFiltered;

    public BlackListedCustomerAdapter(Context mContext, ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList)
    {
        this.mContext=mContext;
        this.blackListCustomerModelArrayList=blackListCustomerModelArrayList;
        this.arrayListFiltered=blackListCustomerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_black_list_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final BlackListCustomerModel blackListCustomerModel=blackListCustomerModelArrayList.get(position);

        String status=blackListCustomerModel.getStatus().trim();
        holder.textName.setText(blackListCustomerModel.getCustomer_name());
        holder.textCustomerFirstName.setText(blackListCustomerModel.getCustomer_name());
        if(status.equalsIgnoreCase("4")) {
            holder.textBlackList.setText("All BlackListed");
        }
        holder.textGstNumber.setText(blackListCustomerModel.getGst_no());
        holder.textAadharNumber.setText(blackListCustomerModel.getAadhar_no());
        holder.textPanNumber.setText(blackListCustomerModel.getPan_no());


    }

    @Override
    public int getItemCount() {
        return blackListCustomerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textGstNumber,textBlackList,textCustomerFirstName,textAadharNumber,textPanNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName=itemView.findViewById(R.id.textName);
            textGstNumber=itemView.findViewById(R.id.textGstNumber);
            textBlackList=itemView.findViewById(R.id.textBlackList);
            textCustomerFirstName=itemView.findViewById(R.id.textCustomerFirstName);
            textAadharNumber=itemView.findViewById(R.id.textAadharNumber);
            textPanNumber=itemView.findViewById(R.id.textPanNumber);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    blackListCustomerModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<BlackListCustomerModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < blackListCustomerModelArrayList.size(); i++) {
                        String gst_no=blackListCustomerModelArrayList.get(i).getGst_no().replaceAll("\\s","").toLowerCase().trim();
                        String  aadhar_no=blackListCustomerModelArrayList.get(i).getAadhar_no().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        String  pan_no=blackListCustomerModelArrayList.get(i).getPan_no().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        if ((gst_no.contains(charString))||(aadhar_no.contains(charString))||(pan_no.contains(charString))) {
                            filteredList.add(blackListCustomerModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        blackListCustomerModelArrayList = filteredList;
                    } else {
                        blackListCustomerModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = blackListCustomerModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                blackListCustomerModelArrayList = (ArrayList<BlackListCustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
