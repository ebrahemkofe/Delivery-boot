package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.ListOrderQQueueAdapter;
import com.graduation.deliveryboot.Models.DataOnList;
import com.graduation.deliveryboot.Models.OrderQueueData;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersQueue extends AppCompatActivity {

    List<OrderQueueData> dataqueue = new ArrayList<>();
    ImageView clear ;
    String[] track = {"301","301","301"};
    String[] ToTrack = {"308","304","306"};
    String[] username = {"username","username","username"};
    String[] date = {"9 : 24", " 9 ; 24","9 : 24"} ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_queue);
        ListView orderlist = findViewById(R.id.list_queue);
        ListOrderQQueueAdapter orderadapter = new  ListOrderQQueueAdapter(OrdersQueue.this,R.layout.list_order_queue,dataqueue);
        for (int i = 0; i < track.length; i++) {
            OrderQueueData dataqueueList = new OrderQueueData(track[i], username[i], date[i],ToTrack[i]);
            dataqueue.add(dataqueueList);
        }
        orderlist.setAdapter(orderadapter);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}