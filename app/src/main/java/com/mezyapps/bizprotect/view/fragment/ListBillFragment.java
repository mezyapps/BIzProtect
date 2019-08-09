package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.BalanceAmountListModel;
import com.mezyapps.bizprotect.view.adpater.BalanceAmountAdapter;

import java.util.ArrayList;

public class ListBillFragment extends Fragment {

    private  Context mContext;
    private RecyclerView recyclerView_balance_list;
    private ArrayList<BalanceAmountListModel> balanceAmountListModelArrayList=new ArrayList<>();
    private BalanceAmountAdapter balanceAmountAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_list_bill, container, false);

        find_View_IDs(view);
        events();

        return  view;
    }

    private void find_View_IDs(View view) {
        recyclerView_balance_list=view.findViewById(R.id.recyclerView_balance_list);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView_balance_list.setLayoutManager(linearLayoutManager);

        for(int i=0;i<=5;i++)
        {
            balanceAmountListModelArrayList.add(new BalanceAmountListModel("Nehal","Natepute","9503873045","2000"));
        }

        balanceAmountAdapter=new BalanceAmountAdapter(mContext,balanceAmountListModelArrayList);
        recyclerView_balance_list.setAdapter(balanceAmountAdapter);
        balanceAmountAdapter.notifyDataSetChanged();



    }

    private void events() {

    }

}
