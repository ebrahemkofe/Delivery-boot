package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewOrders extends AppCompatActivity {
    Spinner firstspinner, secondspinner;
    Button done;
    ArrayList<String> track_from = new ArrayList<>();
    ArrayList<String> track_to = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

        firstspinner = (Spinner) findViewById(R.id.firstspinner);
        secondspinner = (Spinner) findViewById(R.id.secondspinner);
        done = (Button) findViewById(R.id.done);
        track_from.add("Room 101");
        track_from.add("Room 102");
        track_from.add("Room 103");
        track_from.add("Room 104");
        track_from.add("Room 105");
        track_to = track_from;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, track_from);
        firstspinner.setAdapter(adapter);

        firstspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(NewOrders.this, track_from.get(i) + "", Toast.LENGTH_SHORT).show();
                track_to.remove(i);
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
                Toast.makeText(NewOrders.this, track_from.get(i) + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        done.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(NewOrders.this, "make order", 2);
            customDialog.show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void cancel_btn(View view) {
        this.finish();
    }
}