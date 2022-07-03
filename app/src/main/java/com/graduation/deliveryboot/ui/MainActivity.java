package com.graduation.deliveryboot.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.graduation.deliveryboot.Fragment.ControlFragment;
import com.graduation.deliveryboot.Fragment.Help_fragment;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.Fragment.WalletFragment;
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
    @SuppressLint("StaticFieldLeak")
    public static TextView WalletValue;
    boolean doubleBackToExitPressedOnce = false;
    public static boolean admin = false;
    MenuItem menuItem;
    Menu menu;
    float wallet;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        FindViewByIds();
        StartFragment();
        OnClicks();
        menu = navigationView.getMenu();
        menuItem = menu.findItem(R.id.boot_control);

        if (admin)
            menuItem.setVisible(true);

        SharedPreferences myPref = getSharedPreferences("wallet", 0);
        wallet = myPref.getFloat("Amount", 0.0f);

        WalletValue.setText(wallet + " EGP");

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

            case R.id.ReceiveAnOrder:
                Intent intent = new Intent(MainActivity.this, ReceiveOrder.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.wallet:
                fragment = new WalletFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "Wallet_Fragment");
                transaction.commitNow();
                ScreenName.setText("Wallet");
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.Help:
                fragment = new Help_fragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "Help_Fragment");
                transaction.commitNow();
                ScreenName.setText("Help");
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
        profile = navigationView.getHeaderView(0).findViewById(R.id.accountImage);
        WalletValue = navigationView.getHeaderView(0).findViewById(R.id.walletValue);
    }

    @SuppressLint("SetTextI18n")
    public void StartFragment() {
        fragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "HomeFragment");
        transaction.commitNow();
        ScreenName.setText("Delivery boot");

    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    public void OnClicks() {
        NavOpen.setOnClickListener(view -> drawer.openDrawer(Gravity.START));

        profile.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(i);
            drawer.closeDrawer(Gravity.START);
        });

        WalletValue.setOnClickListener(v ->
        {
            fragment = new WalletFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.FragmentLayout, fragment, "Wallet_Fragment");
            transaction.commitNow();
            ScreenName.setText("Wallet");
            drawer.closeDrawer(Gravity.START);
        });

    }

    @Override
    public void onBackPressed() {
        if (!ScreenName.getText().equals("Delivery boot"))
            StartFragment();
        else {
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
}