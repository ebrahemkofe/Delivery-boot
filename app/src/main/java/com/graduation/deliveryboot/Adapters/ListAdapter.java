package com.graduation.deliveryboot.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.Models.OrdersModel;

import java.util.List;

public class ListAdapter extends ArrayAdapter<OrdersModel> {
    List<OrdersModel> data;
    Context context;


    public ListAdapter(@NonNull Context context, List<OrdersModel> list) {
        super(context, R.layout.list_item,list);
        this.context = context;
        this.data = list;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = ly.inflate(R.layout.list_item, parent, false);

        TextView num = convertView.findViewById(R.id.order_item);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);

        num.setText("From  " + data.get(position).getFrom() + "  To  " + data.get(position).getTo());
        date.setText(data.get(position).getDate());
        time.setText(data.get(position).getTime());




        return convertView;
    }
}
