package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.view.activity.AddCustomerActivity;
import com.mezyapps.bizprotect.view.activity.MainActivity;


public class HomeFragment extends Fragment {

    private Context mContext;
    private CardView cardViewAllBlackList, cardViewsMyBlackList, cardViewMyCustomer, cardView_add_customer;
    private ImageView iv_main_advertisement, iv_advertisement_one, iv_advertisement_two, iv_advertisement_three, iv_advertisement_four;
    private ViewFlipper viewFlipper_main_add;
    private int[] images={R.drawable.advertisement1,R.drawable.advertisement2,R.drawable.advertisement3};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getActivity();

        find_View_IdS(view);
        events();

        return view;
    }

    private void find_View_IdS(View view) {
        cardViewAllBlackList = view.findViewById(R.id.cardViewAllBlackList);
        cardViewsMyBlackList = view.findViewById(R.id.cardViewsMyBlackList);
        cardViewMyCustomer = view.findViewById(R.id.cardViewMyCustomer);
        cardView_add_customer = view.findViewById(R.id.cardView_add_customer);
        viewFlipper_main_add = view.findViewById(R.id.viewFlipper_main_add);

        //iv_main_advertisement = view.findViewById(R.id.iv_main_advertisement);
        iv_advertisement_one = view.findViewById(R.id.iv_advertisement_one);
        iv_advertisement_two = view.findViewById(R.id.iv_advertisement_two);
        iv_advertisement_three = view.findViewById(R.id.iv_advertisement_three);
        iv_advertisement_four = view.findViewById(R.id.iv_advertisement_four);

        for(int i=0;i<images.length;i++)
        {
            flipImage(images[i]);
        }
    }

    private void flipImage(int image) {
        ImageView imageView=new ImageView(mContext);
        imageView.setBackgroundResource(image);
        viewFlipper_main_add.addView(imageView);
        viewFlipper_main_add.setFlipInterval(7000);
        viewFlipper_main_add.setAutoStart(true);

        viewFlipper_main_add.setInAnimation(mContext,android.R.anim.slide_in_left

        );
        //viewFlipper_main_add.setOutAnimation(mContext,android.R.anim.slide_in_left);
    }

    private void events() {
        cardViewAllBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new AllBlackListedFragment());
            }
        });
        cardViewsMyBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new MyBlackListedCustomerFragment());
            }
        });
        cardViewMyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new MyCustomerFragment());
            }
        });
        cardView_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
    }

}
