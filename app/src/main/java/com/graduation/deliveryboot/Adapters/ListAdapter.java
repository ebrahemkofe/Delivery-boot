package com.graduation.deliveryboot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.Models.DataOnList;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DataOnList> {


    public ListAdapter(@NonNull Context context, ArrayList<DataOnList> arrayList) {
        super(context, R.layout.list_item, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataOnList data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView num = convertView.findViewById(R.id.order_item);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);

        num.setText(data.num);
        date.setText(data.date);
        time.setText(data.time);

        return convertView;
    }
}
