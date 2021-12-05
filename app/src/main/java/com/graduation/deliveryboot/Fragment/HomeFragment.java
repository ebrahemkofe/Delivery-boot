package com.graduation.deliveryboot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.ListAdabter;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.Models.DataOnList;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ArrayList<DataOnList> dataArrayList = new ArrayList<>();
    String[] num = {"From 301 to 309", "From 301 to 309", "From 301 to 309"};
    String[] date = {"12/3", "12/3", "12/3"};
    String[] time = {"8:55 PM", "8:55 PM", "8:55 PM"};
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        listView = v.findViewById(R.id.list);

        for (int i = 0; i < num.length; ++i) {
            DataOnList dataOnList = new DataOnList(num[i], date[i], time[i]);
            dataArrayList.add(dataOnList);

        }

        ListAdabter listAdabter = new ListAdabter(requireContext(), dataArrayList);
        listView.setAdapter(listAdabter);


        return v;
    }


    public void btn_new_order(View view) {

    }
}