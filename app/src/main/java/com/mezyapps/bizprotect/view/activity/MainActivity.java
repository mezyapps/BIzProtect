package com.mezyapps.bizprotect.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.view.fragment.AllBlackListedFragment;
import com.mezyapps.bizprotect.view.fragment.HomeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_drawer;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_main;
    private String fragmentName = null;
    Fragment fragmentInstance;
    FragmentManager fragmentManager;
    private boolean doubleBackToExitPressedOnce = false;
    private RelativeLayout relativeLayout_Dashboard, relativeLayout_ourCustomer,relativeLayout_blocked_customer,
            relativeLayout_logout,relativeLayout_share,relativeLayout_bill_report;
    private Dialog dialog_logout;
    private TextView text_app_name;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
    private ViewFlipper viewFlipper_banner_add;
    private int[] images={R.drawable.image_silder1,R.drawable.image_silder2,R.drawable.image_silder3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        find_View_Ids();
        loadFragment(new HomeFragment());
        events();
    }

    private void find_View_Ids() {
        iv_drawer = findViewById(R.id.iv_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        relativeLayout_Dashboard = findViewById(R.id.relativeLayout_Dashboard);
        relativeLayout_ourCustomer = findViewById(R.id.relativeLayout_ourCustomer);
        relativeLayout_blocked_customer = findViewById(R.id.relativeLayout_blocked_customer);
        relativeLayout_logout = findViewById(R.id.relativeLayout_logout);
        text_app_name = findViewById(R.id.text_app_name);
        viewFlipper_banner_add = findViewById(R.id.viewFlipper_banner_add);
        relativeLayout_share = findViewById(R.id.relativeLayout_share);
        relativeLayout_bill_report = findViewById(R.id.relativeLayout_bill_report);

        clientProfileModelArrayList=SharedLoginUtils.getUserDetails(MainActivity.this);
        text_app_name.setText("Welcome "+clientProfileModelArrayList.get(0).getCompany_name());


        for(int i=0;i<images.length;i++)
        {
            flipImage(images[i]);
        }
    }

    private void flipImage(int image) {
         ImageView imageView=new ImageView(this);
         imageView.setBackgroundResource(image);
         viewFlipper_banner_add.addView(imageView);
         viewFlipper_banner_add.setFlipInterval(5000);
         viewFlipper_banner_add.setAutoStart(true);

         viewFlipper_banner_add.setInAnimation(this,android.R.anim.slide_in_left);
    }

    private void events() {
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        relativeLayout_Dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment(new HomeFragment());
                }

            }
        });
        relativeLayout_ourCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                   // loadFragment(new MyBlackListedCustomerFragment());
                    Intent intent=new Intent(MainActivity.this,MyCustomerListActivity.class);
                    startActivity(intent);
                }

            }
        });
        relativeLayout_blocked_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment(new AllBlackListedFragment());
                }

            }
        });
        relativeLayout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    logoutApplication();
                }
            }
        });

        relativeLayout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Biz Protect Application");
                    String app_url = "https://play.google.com/store/apps/details?id=com.mezyapps.bizprotect";
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
            }
        });
        relativeLayout_bill_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, IncomeExpenseActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void logoutApplication() {
        dialog_logout = new Dialog(MainActivity.this);
        dialog_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_logout.setContentView(R.layout.dialog_logout);
        TextView txt_cancel = dialog_logout.findViewById(R.id.txt_cancel);
        TextView txt_logout = dialog_logout.findViewById(R.id.txt_logout);
        dialog_logout.setCancelable(false);
        dialog_logout.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_logout.show();

        Window window = dialog_logout.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_logout.dismiss();
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedLoginUtils.removeLoginSharedUtils(MainActivity.this);
                DatabaseHandler databaseHandler=new DatabaseHandler(MainActivity.this);
                databaseHandler.deleteTable();
                SharedLoginUtils.removeBackUpStatus(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void loadFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();
        if(fragmentName.equalsIgnoreCase("HomeFragment")) {
            text_app_name.setText("Welcome "+clientProfileModelArrayList.get(0).getCompany_name());
        }else if(fragmentName.equalsIgnoreCase("AllBlackListedFragment")) {
            text_app_name.setText("All BlackListed");
        }else if(fragmentName.equalsIgnoreCase("MyBlackListedCustomerFragment")) {
            text_app_name.setText("My BlackListed");
        }else if(fragmentName.equalsIgnoreCase("MyCustomerFragment")){
            text_app_name.setText("My Customer List");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }




    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentName.equals(fragmentInstance.getClass().getSimpleName())) {
                if (fragmentName.equals("HomeFragment")) {
                    doubleBackPressLogic();
                } else
                    loadFragment(new HomeFragment());
            }
        }
    }

    // ============ End Double tab back press logic =================
    private void doubleBackPressLogic() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
