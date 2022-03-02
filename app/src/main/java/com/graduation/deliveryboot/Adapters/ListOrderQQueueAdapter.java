package com.graduation.deliveryboot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduation.deliveryboot.Models.DataOnList;
import com.graduation.deliveryboot.Models.OrderQueueData;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class ListOrderQQueueAdapter extends ArrayAdapter {
    List<OrderQueueData> dataqueue = new ArrayList<>();
    Context context;
    public ListOrderQQueueAdapter(@NonNull Context context, int resource , List<OrderQueueData> dataqueue) {
        super(context,resource, dataqueue);
        this.context = context;
        this.dataqueue = dataqueue;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = ly.inflate(R.layout.list_order_queue,parent,false);
        ImageView bag =(ImageView) convertView.findViewById(R.id.orderimage);
        TextView track =(TextView) convertView.findViewById(R.id.from_to_txt);
        TextView username =(TextView) convertView.findViewById(R.id.username_txt);
        TextView date =(TextView) convertView.findViewById(R.id.date_txt);
        ImageView clear =(ImageView) convertView.findViewById(R.id.clear_btn);

        track.setText(dataqueue.get(position).tracktxt + " To " + dataqueue.get(position).Totxt);
        username.setText(dataqueue.get(position).usernametxt);
        date.setText(dataqueue.get(position).datetxt);
       /* clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dataqueue.remove(position);
            }
        });*/
        return convertView;
    }
}
