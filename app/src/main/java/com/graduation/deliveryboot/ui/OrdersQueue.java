package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.ListOrderQQueueAdapter;
import com.graduation.deliveryboot.Models.OrderQueueData;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersQueue extends AppCompatActivity {

    List<OrderQueueData> dataQueue = new ArrayList<>();
    String[] track = {"301", "301", "301"};
    String[] ToTrack = {"308", "304", "306"};
    String[] username = {"username", "username", "username"};
    String[] date = {"15 min", "23 min", "41 min"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_queue);
        ListView orderList = findViewById(R.id.list_queue);
        ListOrderQQueueAdapter orderAdapter = new ListOrderQQueueAdapter(OrdersQueue.this, R.layout.list_order_queue, dataQueue);
        for (int i = 0; i < track.length; i++) {
            OrderQueueData dataQueueList = new OrderQueueData(track[i], username[i], date[i], ToTrack[i]);
            dataQueue.add(dataQueueList);
        }
        orderList.setAdapter(orderAdapter);

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