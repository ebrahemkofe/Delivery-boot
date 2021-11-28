package com.graduation.deliveryboot.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduation.deliveryboot.Fragment.MapsFragment;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class MapsListViewAdapter extends ArrayAdapter {
    List<String> text = new ArrayList<>();
    Context context ;

    public MapsListViewAdapter(@NonNull Context context, int resource, @NonNull List text) {
        super(context, resource, text );
        this.context = context;
        this.text = text;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater ly =(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = ly.inflate(R.layout.maps_list,parent,false);
        TextView txt =(TextView)view.findViewById(R.id.text);
        txt.setText(text.get(position));
        return view ;
    }
}

