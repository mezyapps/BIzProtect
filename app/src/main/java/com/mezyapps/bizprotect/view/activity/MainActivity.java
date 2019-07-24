package com.mezyapps.bizprotect.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;
import com.mezyapps.bizprotect.view.fragment.HomeFragment;
import com.mezyapps.bizprotect.view.fragment.MyBlackListedCustomerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_drawer;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_main;
    private String fragmentName = null;
    Fragment fragmentInstance;
    FragmentManager fragmentManager;
    private boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottom_navigation;
    private RelativeLayout relativeLayout_Dashboard, relativeLayout_ourCustomer, relativeLayout_blocked_customer, relativeLayout_logout;
    private Dialog dialog_logout;
    private TextView text_app_name;
    private ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        loadFragment(new HomeFragment());
        iv_drawer = findViewById(R.id.iv_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        relativeLayout_Dashboard = findViewById(R.id.relativeLayout_Dashboard);
        relativeLayout_ourCustomer = findViewById(R.id.relativeLayout_ourCustomer);
        relativeLayout_ourCustomer = findViewById(R.id.relativeLayout_ourCustomer);
        relativeLayout_blocked_customer = findViewById(R.id.relativeLayout_blocked_customer);
        relativeLayout_logout = findViewById(R.id.relativeLayout_logout);
        text_app_name = findViewById(R.id.text_app_name);

        clientProfileModelArrayList=SharedLoginUtils.getUserDetails(MainActivity.this);
        text_app_name.setText("Welcome "+clientProfileModelArrayList.get(0).getCompany_name());
    }

    private void events() {
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        loadFragment(new HomeFragment());
                        break;

                    case R.id.nav_our_customer:
                        loadFragment(new MyBlackListedCustomerFragment());
                        break;

                    case R.id.nav_logout:
                        logoutApplication();
                        break;


                }

                return true;
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
                    loadFragment(new MyBlackListedCustomerFragment());
                }

            }
        });
        relativeLayout_blocked_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment(new HomeFragment());
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
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();
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
