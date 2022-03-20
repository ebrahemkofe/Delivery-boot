package com.graduation.deliveryboot.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.graduation.deliveryboot.Fragment.ControlFragment;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    FragmentTransaction transaction;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawer;
    ImageView NavOpen;
    CircleImageView profile;
    TextView ScreenName;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        FindViewByIds();
        StartFragment();
        OnClicks();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @SuppressLint({"NonConstantResourceId", "WrongConstant", "SetTextI18n"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.home:
                fragment = new HomeFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "HomeFragment");
                transaction.commitNow();
                ScreenName.setText("Delivery boot");
                drawer.closeDrawer(Gravity.START);
                break;

            case R.id.boot_control:
                fragment = new ControlFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "ControlFragment");
                transaction.commitNow();
                ScreenName.setText("Boot Control");
                drawer.closeDrawer(Gravity.START);
                break;

            case R.id.About_us:
                Toast.makeText(MainActivity.this, "About us", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(Gravity.START);
                break;

            case R.id.exit:
                finish();
                System.exit(0);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void FindViewByIds() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        NavOpen = findViewById(R.id.NavBarButton);
        ScreenName = findViewById(R.id.ScreenName);
        profile = navigationView.getHeaderView(0).findViewById(R.id.account_image);
    }

    public void StartFragment() {
        fragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "HomeFragment");
        transaction.commitNow();

    }

    @SuppressLint("WrongConstant")
    public void OnClicks() {
        NavOpen.setOnClickListener(view -> drawer.openDrawer(Gravity.START));

        profile.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, EditProfileFragment.class);
            startActivity(i);
            drawer.closeDrawer(Gravity.START);
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}