package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;
import com.mezyapps.bizprotect.view.activity.CustomerDetailsMyBlackListedActivity;

import java.util.ArrayList;

public class MyBlackListedCustomerListAdapter extends RecyclerView.Adapter<MyBlackListedCustomerListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private  ArrayList<MyBlackListedCustomerModel> myBlackListedCustomerModelArrayList;
    private  ArrayList<MyBlackListedCustomerModel> arrayListFiltered;

    public MyBlackListedCustomerListAdapter(Context mContext, ArrayList<MyBlackListedCustomerModel> myBlackListedCustomerModelArrayList) {
        this.mContext=mContext;
        this.myBlackListedCustomerModelArrayList = myBlackListedCustomerModelArrayList;
        this.arrayListFiltered= myBlackListedCustomerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_blacklisted_customer_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final MyBlackListedCustomerModel myBlackListedCustomerModel = myBlackListedCustomerModelArrayList.get(position);
        String status=myBlackListedCustomerModel.getStatus();
        holder.textName.setText(myBlackListedCustomerModel.getCustomer_name());
        holder.textGstNumber.setText(myBlackListedCustomerModel.getGst_no());
        holder.textAadharNumber.setText(myBlackListedCustomerModel.getAadhar_no());
        holder.textPanNumber.setText(myBlackListedCustomerModel.getPan_no());


        holder.linearlayout_my_blacklisted_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CustomerDetailsMyBlackListedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("MYCUSTOMER", (Parcelable) myBlackListedCustomerModelArrayList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBlackListedCustomerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textGstNumber,textAadharNumber,textPanNumber;
        LinearLayout linearlayout_my_blacklisted_customer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName=itemView.findViewById(R.id.textName);
            textGstNumber=itemView.findViewById(R.id.textGstNumber);
            textAadharNumber=itemView.findViewById(R.id.textAadharNumber);
            linearlayout_my_blacklisted_customer=itemView.findViewById(R.id.linearlayout_my_blacklisted_customer);
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
                    myBlackListedCustomerModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<MyBlackListedCustomerModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < myBlackListedCustomerModelArrayList.size(); i++) {
                        String gst_no=myBlackListedCustomerModelArrayList.get(i).getGst_no().replaceAll("\\s","").toLowerCase().trim();
                        String  aadhar_no=myBlackListedCustomerModelArrayList.get(i).getAadhar_no().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        String  pan_no=myBlackListedCustomerModelArrayList.get(i).getPan_no().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        if ((gst_no.contains(charString))||(aadhar_no.contains(charString))||(pan_no.contains(charString))) {
                            filteredList.add(myBlackListedCustomerModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        myBlackListedCustomerModelArrayList = filteredList;
                    } else {
                        myBlackListedCustomerModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myBlackListedCustomerModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                myBlackListedCustomerModelArrayList = (ArrayList<MyBlackListedCustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
