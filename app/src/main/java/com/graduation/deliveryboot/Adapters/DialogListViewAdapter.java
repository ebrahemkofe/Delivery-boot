package com.graduation.deliveryboot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.graduation.deliveryboot.R;

import java.util.List;

public class DialogListViewAdapter extends ArrayAdapter {
    List<String> items;
    private Context mcontext;

    public DialogListViewAdapter(@NonNull Context context, int resource, List<String> text) {
        super(context, resource, text);
        this.items = text;
        mcontext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_item, parent, false);

        TextView textView = v.findViewById(R.id.dialog_text);
        textView.setText(items.get(position));


        return v;
    }

}