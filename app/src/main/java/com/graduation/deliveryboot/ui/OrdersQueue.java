package com.graduation.deliveryboot.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Adapters.ListOrderQQueueAdapter;
import com.graduation.deliveryboot.Models.OrdersModel;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;

import java.util.List;

public class OrdersQueue extends AppCompatActivity {

    List<OrdersModel> dataQueue = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_queue);
        ListView orderList = findViewById(R.id.list_queue);
        ListOrderQQueueAdapter orderAdapter = new ListOrderQQueueAdapter(OrdersQueue.this, R.layout.list_order_queue, dataQueue);
        orderList.setAdapter(orderAdapter);
        ref.child("users").child(LoginActivity.Token).child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            OrdersModel ordersModel = snapshot.getValue(OrdersModel.class);
                            assert ordersModel != null;
                            dataQueue.add(ordersModel);
                        }
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
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