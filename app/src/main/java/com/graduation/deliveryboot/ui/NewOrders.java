package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.Random;

public class NewOrders extends AppCompatActivity {
    Spinner firstspinner, secondspinner;
    Button done;
    ArrayList<String> track_from = new ArrayList<>();
    ArrayList<String> track_to = new ArrayList<>();
    String RandomCode;
    boolean time = true;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

        firstspinner = findViewById(R.id.firstspinner);
        secondspinner = findViewById(R.id.secondspinner);
        done = findViewById(R.id.done);
        track_from.add("Room 101");
        track_from.add("Room 102");
        track_from.add("Room 103");
        track_from.add("Room 104");
        track_from.add("Room 105");

        track_to.addAll(track_from);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, track_from);
        firstspinner.setAdapter(adapter);

        firstspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (time) {
                    track_to.remove(i);
                    time = false;
                } else {
                    track_to.clear();
                    track_to.addAll(track_from);
                    track_to.remove(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, track_to);
        secondspinner.setAdapter(adapter2);

        secondspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        done.setOnClickListener(view -> {
            SharedPreferences myPref1 = getSharedPreferences("wallet", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor e = myPref1.edit();
            float m = myPref1.getFloat("Amount", 0.0f);
            if (m >= 15) {
                e.putFloat("Amount", m - 15);
                e.apply();
                Random random = new Random();
                RandomCode = String.format("%04d", random.nextInt(10000));
                CustomDialog customDialog = new CustomDialog(NewOrders.this, RandomCode, 2);
                customDialog.show();
                customDialog.setOnDismissListener(dialogInterface -> finish());
            } else
                Toast.makeText(this, "Sorry you don't have enough credit.", Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void cancel_image(View view) {
        this.finish();
    }
}