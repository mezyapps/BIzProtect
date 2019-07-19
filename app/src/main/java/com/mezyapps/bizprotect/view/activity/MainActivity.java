package com.mezyapps.bizprotect.view.activity;

import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mezyapps.bizprotect.R;
import com.mezyapps.bizprotect.view.fragment.HomeFragment;
import com.mezyapps.bizprotect.view.fragment.OurCustomerFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_drawer;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_main;
    private String fragmentName=null;
    Fragment fragmentInstance;
    FragmentManager fragmentManager;
    private boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottom_navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find_View_Ids();
        events();
    }

    private void find_View_Ids() {
        loadFragment(new HomeFragment());
        iv_drawer=findViewById(R.id.iv_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);
        bottom_navigation=findViewById(R.id.bottom_navigation);
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

                switch (menuItem.getItemId())
                {

                    case R.id.nav_home:
                        loadFragment(new HomeFragment());
                        break;

                    case R.id.nav_our_customer:
                        loadFragment(new OurCustomerFragment());
                        break;

                    case R.id.nav_logout:
                        
                        break;


                }

                return true;
            }
        });

    }
    public void loadFragment(Fragment fragment) {
        fragmentInstance = fragment;
        fragmentName=fragment.getClass().getSimpleName();
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
