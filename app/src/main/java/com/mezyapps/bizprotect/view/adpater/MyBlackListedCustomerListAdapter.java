package com.mezyapps.bizprotect.view.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.MyBlackListedCustomerModel;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyBlackListedCustomerModel myBlackListedCustomerModel = myBlackListedCustomerModelArrayList.get(position);
        String status=myBlackListedCustomerModel.getStatus();
        holder.textName.setText(myBlackListedCustomerModel.getCustomer_name());
        holder.textCustomerFirstName.setText(myBlackListedCustomerModel.getCustomer_name());
        if(status.equalsIgnoreCase("4")) {
            holder.textBlackList.setText("My BlackListed");
        }
        holder.textGstNumber.setText(myBlackListedCustomerModel.getGst_no());

    }

    @Override
    public int getItemCount() {
        return myBlackListedCustomerModelArrayList.size();
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
                        String customer_name= myBlackListedCustomerModelArrayList.get(i).getCustomer_name().replaceAll("\\s","").toLowerCase().trim();
                        //String  address=arrayList.get(i).getAddress().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        //String company_name=arrayList.get(i).getCompany_name().replaceAll("\\s","").toLowerCase().trim();
                        if (customer_name.contains(charString)) {
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
