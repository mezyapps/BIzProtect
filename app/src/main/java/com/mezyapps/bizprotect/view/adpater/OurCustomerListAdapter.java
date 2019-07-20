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
import com.mezyapps.bizprotect.model.BlackListCustomerModel;
import com.mezyapps.bizprotect.model.OurCustomerModel;

import java.util.ArrayList;

public class OurCustomerListAdapter extends RecyclerView.Adapter<OurCustomerListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private  ArrayList<OurCustomerModel> ourCustomerModelArrayList;
    private  ArrayList<OurCustomerModel> arrayListFiltered;

    public OurCustomerListAdapter(Context mContext, ArrayList<OurCustomerModel> ourCustomerModelArrayList) {
        this.mContext=mContext;
        this.ourCustomerModelArrayList=ourCustomerModelArrayList;
        this.arrayListFiltered=ourCustomerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_our_customer_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OurCustomerModel ourCustomerModel=ourCustomerModelArrayList.get(position);

        holder.textName.setText(ourCustomerModel.getCustomer_name());
        holder.textCustomerFirstName.setText(ourCustomerModel.getCustomer_name());
        holder.textBlackList.setText(ourCustomerModel.getStatus());
        holder.textGstNumber.setText(ourCustomerModel.getGst_no());

    }

    @Override
    public int getItemCount() {
        return ourCustomerModelArrayList.size();
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
                    ourCustomerModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<OurCustomerModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < ourCustomerModelArrayList.size(); i++) {
                        String customer_name=ourCustomerModelArrayList.get(i).getCustomer_name().replaceAll("\\s","").toLowerCase().trim();
                        //String  address=arrayList.get(i).getAddress().toLowerCase().replaceAll("\\s","").toLowerCase().trim();
                        //String company_name=arrayList.get(i).getCompany_name().replaceAll("\\s","").toLowerCase().trim();
                        if (customer_name.contains(charString)) {
                            filteredList.add(ourCustomerModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        ourCustomerModelArrayList = filteredList;
                    } else {
                        ourCustomerModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ourCustomerModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ourCustomerModelArrayList = (ArrayList<OurCustomerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
