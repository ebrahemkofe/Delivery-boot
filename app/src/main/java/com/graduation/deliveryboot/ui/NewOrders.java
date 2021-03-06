package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.Models.OrdersModel;
import com.graduation.deliveryboot.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NewOrders extends AppCompatActivity {
    Spinner FirstSpinner, SecondSpinner;
    Button done;
    EditText theOther;
    ArrayList<String> track_from = new ArrayList<>();
    ArrayList<String> track_to = new ArrayList<>();
    String RandomCode;
    boolean time = true;
    String currentDate ;
    String currentTime ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);
        currentDate = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date());
        FirstSpinner = findViewById(R.id.firstSpinner);
        SecondSpinner = findViewById(R.id.secondSpinner);
        done = findViewById(R.id.done);
        theOther = findViewById(R.id.edit_text);
        track_from.add("101");
        track_from.add("102");
        track_from.add("103");
        track_from.add("104");
        track_from.add("105");

        track_to.addAll(track_from);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, track_from);
        FirstSpinner.setAdapter(adapter);

        FirstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(ColorStateList.valueOf(R.color.blue));
                ((TextView) adapterView.getChildAt(0)).setTextSize(14);
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
        SecondSpinner.setAdapter(adapter2);

        SecondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(ColorStateList.valueOf(R.color.blue));
                ((TextView) adapterView.getChildAt(0)).setTextSize(14);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        done.setOnClickListener(view -> {
            SharedPreferences myPref1 = getSharedPreferences("wallet", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor e = myPref1.edit();
            float m = LoginActivity.Wallet;
            if (m >= 15) {
                e.putFloat("Amount", m - 15);
                e.apply();
                Random random = new Random();
                RandomCode = String.format("%04d", random.nextInt(10000));
                            OrdersModel data = new OrdersModel();
                            data.setFrom(FirstSpinner.getSelectedItem().toString());
                            data.setTo(SecondSpinner.getSelectedItem().toString());
                            data.setName(LoginActivity.Username);
                            data.setReceiver(theOther.getText().toString());
                            data.setReceiveCode(RandomCode);
                            data.setDate(currentDate);
                            data.setTime(currentTime);
                            ref.child("users").child(LoginActivity.Token).child("orders").child("order"+String.format("%03d", random.nextInt(1000))).setValue(data);
                CustomDialog customDialog = new CustomDialog(NewOrders.this, RandomCode, 2);
                customDialog.show();
                customDialog.setOnDismissListener(dialog -> finish());
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