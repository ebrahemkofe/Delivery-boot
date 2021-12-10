package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.MapsListViewAdapter;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.ManualControlActivity;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    List<String> text = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        ListView mapslist = (ListView) v.findViewById(R.id.map_list);
        ImageView add = (ImageView) v.findViewById(R.id.add);

        text.add("hello");
        text.add("hello");
        text.add("hello");
        MapsListViewAdapter adp = new MapsListViewAdapter(getContext(), R.layout.maps_list, text);
        mapslist.setAdapter(adp);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ManualControlActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}