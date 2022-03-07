package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.ListAdabter;
import com.graduation.deliveryboot.Models.DataOnList;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class FullOrders extends AppCompatActivity {
    ArrayList<DataOnList> fullorders = new ArrayList<>();
    String[] num = {"From 301 to 309", "From 301 to 309", "From 301 to 309"};
    String[] date = {"12/3", "12/3", "12/3"};
    String[] time = {"8:55 PM", "8:55 PM","8:55 PM"};
    Button neworder ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_orders);
        ListView fullorderlist = findViewById(R.id.full_orders);
        ListAdabter listAdabter = new ListAdabter(FullOrders.this ,fullorders);
        for(int i=0 ; i < num.length ;i++){
            DataOnList fullorder = new DataOnList(num[i],date[i],time[i]);
            fullorders.add(fullorder);
        }
        fullorderlist.setAdapter(listAdabter);
        neworder = findViewById(R.id.new_order);
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FullOrders.this,NewOrders.class);
                startActivity(i);
            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }

    public void cancel_btn(View view) {
        this.finish();
    }

}