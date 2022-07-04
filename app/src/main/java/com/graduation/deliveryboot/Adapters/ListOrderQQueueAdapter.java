package com.graduation.deliveryboot.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.graduation.deliveryboot.Models.OrdersModel;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


public class ListOrderQQueueAdapter extends ArrayAdapter<OrdersModel> {
    List<OrdersModel> dataQueue;
    Context context;
    String out;

    public ListOrderQQueueAdapter(@NonNull Context context, int resource, List<OrdersModel> dataQueue) {
        super(context, resource, dataQueue);
        this.context = context;
        this.dataQueue = dataQueue;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        track.setText(dataQueue.get(position).getFrom() + " To " + dataQueue.get(position).getTo());
        username.setText(LoginActivity.Username);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm aa");
        try {
            Date time = dateFormat2.parse(dataQueue.get(position).getTime());

            assert time != null;
            out = dateFormat2.format(time);
            Log.e("Time", out);
        } catch (ParseException ignored) {
        }
        date.setText(out);

        if (position == 0)
            lamp.setImageResource(R.drawable.green_lamp);

        else if (position == 1)
            lamp.setImageResource(R.drawable.yellow_lamp);

        else if (position == 2)
            lamp.setImageResource(R.drawable.red_lamp);


        return convertView;
    }
}
