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
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;
import com.mezyapps.bizprotect.model.MyCustomerModel;
import com.mezyapps.bizprotect.view.activity.CustomerDetailsActivity;
import com.mezyapps.bizprotect.view.activity.MyCustomerListActivity;

import java.util.ArrayList;

public class MyCustomerListAdapter extends RecyclerView.Adapter<MyCustomerListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<MyCustomerModel> myCustomerModelArrayList;
    private  ArrayList<MyCustomerModel> arrayListFiltered;

    public MyCustomerListAdapter(Context mContext, ArrayList<MyCustomerModel> myCustomerModelArrayList) {
        this.mContext=mContext;
        this.myCustomerModelArrayList=myCustomerModelArrayList;
        this.arrayListFiltered=myCustomerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_customer_adapter,parent,false);
        return new MyCustomerListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final MyCustomerModel myCustomerModel = myCustomerModelArrayList.get(position);
        String status=myCustomerModel.getStatus();
        holder.textName.setText(myCustomerModel.getCustomer_name());
        holder.textCustomerFirstName.setText(myCustomerModel.getCustomer_name());
        if(status.equalsIgnoreCase("4")) {
            holder.textBlackList.setText("My BlackListed");
        }
        else
        {
            holder.textBlackList.setText("Good Customer");
            holder.textBlackList.setTextColor(mContext.getResources().getColor(R.color.gray));
        }
        holder.textGstNumber.setText(myCustomerModel.getGst_no());
        holder.textAadharNumber.setText(myCustomerModel.getAadhar_no());


        holder.linearlayout_customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CustomerDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("MYCUSTOMER", (Parcelable) myCustomerModelArrayList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCustomerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textGstNumber,textBlackList,textCustomerFirstName,textAadharNumber;
        LinearLayout linearlayout_customer_details;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName=itemView.findViewById(R.id.textName);
            textGstNumber=itemView.findViewById(R.id.textGstNumber);
            textBlackList=itemView.findViewById(R.id.textBlackList);
            textCustomerFirstName=itemView.findViewById(R.id.textCustomerFirstName);
            textAadharNumber=itemView.findViewById(R.id.textAadharNumber);
            linearlayout_customer_details=itemView.findViewById(R.id.linearlayout_customer_details);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    myCustomerModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<MyCustomerModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < myCustomerModelArrayList.size(); i++) {
                        String gst_no=myCustomerModelArrayList.get(i).getGst_no().replaceAll("\\s","").toLowerCase().trim();
                        String  aadhar_no=myCustomerModelArrayList.get(i).getAadhar_no().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        if ((gst_no.contains(charString))||(aadhar_no.contains(charString))) {
                            filteredList.add(myCustomerModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        myCustomerModelArrayList = filteredList;
                    } else {
                        myCustomerModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myCustomerModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                myCustomerModelArrayList = (ArrayList<MyCustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
