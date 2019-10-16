package com.mezyapps.bizprotect.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.view.activity.AddCustomerActivity;
import com.mezyapps.bizprotect.view.activity.BlackListActivity;
import com.mezyapps.bizprotect.view.activity.IncomeExpenseActivity;
import com.mezyapps.bizprotect.view.activity.MainActivity;


public class HomeFragment extends Fragment {

    private Context mContext;
    private CardView cardViewAllBlackList, cardViewMyCustomer, cardView_add_customer,cardViewIncomeExpense;
    private ImageView iv_main_advertisement, iv_advertisement_one, iv_advertisement_two, iv_advertisement_three, iv_advertisement_four;
   // private ViewFlipper viewFlipper_main_add;
    //private int[] images={R.drawable.advertisement1,R.drawable.advertisement2,R.drawable.advertisement3};

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
        cardViewMyCustomer = view.findViewById(R.id.cardViewMyCustomer);
        cardView_add_customer = view.findViewById(R.id.cardView_add_customer);
      //  viewFlipper_main_add = view.findViewById(R.id.viewFlipper_main_add);
        cardViewIncomeExpense = view.findViewById(R.id.cardViewIncomeExpense);

        iv_main_advertisement = view.findViewById(R.id.iv_main_advertisement);
        iv_advertisement_one = view.findViewById(R.id.iv_advertisement_one);
        iv_advertisement_two = view.findViewById(R.id.iv_advertisement_two);
        iv_advertisement_three = view.findViewById(R.id.iv_advertisement_three);
        iv_advertisement_four = view.findViewById(R.id.iv_advertisement_four);

       /* for(int i=0;i<images.length;i++)
        {
            flipImage(images[i]);
        }*/
    }

    private void flipImage(int image) {
        ImageView imageView=new ImageView(mContext);
        imageView.setBackgroundResource(image);
      /*  viewFlipper_main_add.addView(imageView);
        viewFlipper_main_add.setFlipInterval(7000);
        viewFlipper_main_add.setAutoStart(true);

        viewFlipper_main_add.setInAnimation(mContext,android.R.anim.slide_in_left
    );*/

        //viewFlipper_main_add.setOutAnimation(mContext,android.R.anim.slide_in_left);
    }

    private void events() {
        cardViewAllBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final InterstitialAd interstitialAd;
                interstitialAd = new InterstitialAd(mContext);
                //interstitialAd.setAdUnitId("ca-app-pub-3637958081667905/7508071732");
                interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                AdRequest adRequest=new AdRequest.Builder().addTestDevice("EA935C5980439BBAE926C776B1C83FAB").build();
                //AdRequest adRequest=new AdRequest.Builder().build();
                interstitialAd.loadAd(adRequest);

                interstitialAd.setAdListener(new AdListener(){

                    @Override
                    public void onAdLoaded() {
                        if(interstitialAd.isLoaded())
                        {
                            interstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        Intent intent=new Intent(mContext, BlackListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdClosed() {
                        Intent intent=new Intent(mContext, BlackListActivity.class);
                        startActivity(intent);
                    }
                });*/

                Intent intent=new Intent(mContext, BlackListActivity.class);
                startActivity(intent);


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
        cardViewIncomeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final InterstitialAd interstitialAd;
                interstitialAd = new InterstitialAd(mContext);
                interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                //interstitialAd.setAdUnitId("ca-app-pub-3637958081667905/1601138935");
                AdRequest adRequest=new AdRequest.Builder().addTestDevice("EA935C5980439BBAE926C776B1C83FAB").build();
               // AdRequest adRequest=new AdRequest.Builder().build();
                interstitialAd.loadAd(adRequest);

                interstitialAd.setAdListener(new AdListener(){

                    @Override
                    public void onAdLoaded() {
                        if(interstitialAd.isLoaded())
                        {
                            interstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        Intent intent = new Intent(mContext, IncomeExpenseActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(mContext, IncomeExpenseActivity.class);
                        startActivity(intent);
                    }
                });*/
                Intent intent = new Intent(mContext, IncomeExpenseActivity.class);
                startActivity(intent);

            }
        });
        iv_main_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileno="9373343000";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mobileno));
                startActivity(intent);
            }
        });
    }

}
