package com.graduation.deliveryboot.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.Fragment.MapsFragment;
import com.graduation.deliveryboot.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    FragmentTransaction transaction;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawer;
    ImageView NavOpen;
    Button login;

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
        switch(item.getItemId()){
            case R.id.home:
                fragment = new HomeFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "HomeFragment");
                transaction.commitNow();
                drawer.closeDrawer(Gravity.START);
              break ;
            case R.id.maps:
                fragment = new MapsFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "MapsFragment");
                transaction.commitNow();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.last_orders:
                Toast.makeText(MainActivity.this, "last_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.boot_info:
                Toast.makeText(MainActivity.this, "boot_info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorite:
                Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).show();
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

    public void FindViewByIds(){
        drawer =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        NavOpen=findViewById(R.id.NavBarButton);
        login = findViewById(R.id.login);
    }

    public void StartFragment(){
        fragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "HomeFragment");
        transaction.commitNow();

    }

    public void OnClicks(){
        NavOpen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.START);
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this , LoginActivity.class );
//                startActivity(intent);
//            }
//        });
    }

}