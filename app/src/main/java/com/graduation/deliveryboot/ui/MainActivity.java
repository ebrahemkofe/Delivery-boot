package com.graduation.deliveryboot.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.graduation.deliveryboot.Fragment.ControlFragment;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.Fragment.MapsFragment;
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
    Button login;
    CircleImageView profile;
    TextView ScreenName;

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


    @SuppressLint({"NonConstantResourceId", "WrongConstant"})
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
            case R.id.maps:
                fragment = new MapsFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "MapsFragment");
                transaction.commitNow();
                ScreenName.setText("Maps");
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.last_orders:
                Intent i = new Intent(MainActivity.this, FullOrders.class);
                startActivity(i);
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
            case R.id.search:
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(Gravity.START);

                break;
            case R.id.favorite:
                Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).show();
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
        login = navigationView.getHeaderView(0).findViewById(R.id.login);

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

        login.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            drawer.closeDrawer(Gravity.START);
        });

        profile.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, EditProfileFragment.class);
            startActivity(i);
            drawer.closeDrawer(Gravity.START);
        });
    }

}