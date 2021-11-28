package com.graduation.deliveryboot.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.databinding.ActivitySideMenuBinding;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



public class side_menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivitySideMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySideMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarSideMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_side_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_side_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void btn_register(View view){
        Toast.makeText(side_menu.this, "done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Toast.makeText(side_menu.this, "home", Toast.LENGTH_SHORT).show();
              break ;
            case R.id.maps:
                Toast.makeText(side_menu.this, "maps", Toast.LENGTH_SHORT).show();
                break;
            case R.id.last_orders:
                Toast.makeText(side_menu.this, "last_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.boot_info:
                Toast.makeText(side_menu.this, "boot_info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(side_menu.this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorite:
                Toast.makeText(side_menu.this, "favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                Toast.makeText(side_menu.this, "exit", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}