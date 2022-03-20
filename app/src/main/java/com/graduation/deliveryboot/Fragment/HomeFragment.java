package com.graduation.deliveryboot.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.graduation.deliveryboot.Adapters.ListAdapter;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.ui.NewOrders;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.Models.DataOnList;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

   static int pos;

    ArrayList<DataOnList> dataArrayList = new ArrayList<>();
    String[] num = {"From 301 to 309", "From 305 to 312", "From 301 to 306"};
    String[] date = {"12/3", "15/3", "20/3"};
    String[] time = {"8:55 PM", "5:00 PM","8:00 AM"};
    ListView listView;
    Button newOrder;
    ListAdapter listAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        listView = v.findViewById(R.id.list);
        newOrder =v.findViewById(R.id.new_order);


        for (int i = 0; i < num.length; ++i) {
            DataOnList dataOnList = new DataOnList(num[i], date[i], time[i]);
            dataArrayList.add(dataOnList);

        }

        listAdapter = new ListAdapter(requireContext(), dataArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(HomeFragment.this);
        
        newOrder.setOnClickListener(view ->  {
                Intent i = new Intent(requireContext(), NewOrders.class);
                startActivity(i);
        });

        return v;
    }

    public static void reOrder (Context context,boolean b){
        if(b)
            Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(requireContext(), "clicked" , Toast.LENGTH_SHORT).show();
        CustomDialog customDialog=new CustomDialog(requireContext(),"ReOrder this?",3);
        customDialog.show();
        pos=i;
    }
}