package com.graduation.deliveryboot.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduation.deliveryboot.Models.OrderQueueData;
import com.graduation.deliveryboot.R;

import java.util.List;

public class ListOrderQQueueAdapter extends ArrayAdapter<OrderQueueData> {
    List<OrderQueueData> dataqueue;
    Context context;

    public ListOrderQQueueAdapter(@NonNull Context context, int resource, List<OrderQueueData> dataqueue) {
        super(context, resource, dataqueue);
        this.context = context;
        this.dataqueue = dataqueue;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = ly.inflate(R.layout.list_order_queue, parent, false);
        ImageView lamp = (ImageView) convertView.findViewById(R.id.lamp);
        TextView track = (TextView) convertView.findViewById(R.id.from_to_txt);
        TextView username = (TextView) convertView.findViewById(R.id.username_txt);
        TextView date = (TextView) convertView.findViewById(R.id.date_txt);

        track.setText(dataqueue.get(position).tracktxt + " To " + dataqueue.get(position).Totxt);
        username.setText(dataqueue.get(position).usernametxt);
        date.setText(dataqueue.get(position).datetxt);

        if (position == 0)
            lamp.setImageResource(R.drawable.green_lamp);

        else if (position == 1)
            lamp.setImageResource(R.drawable.yellow_lamp);

        else if (position == 2)
            lamp.setImageResource(R.drawable.red_lamp);


        return convertView;
    }
}
