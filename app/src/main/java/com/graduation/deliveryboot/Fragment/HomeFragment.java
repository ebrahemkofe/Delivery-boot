package com.graduation.deliveryboot.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Adapters.ListAdapter;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.Models.DataOnList;
import com.graduation.deliveryboot.ui.LoginActivity;
import com.graduation.deliveryboot.ui.NewOrders;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    static int pos;
    ListView listView;
    Button newOrder;
    ListAdapter listAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    List<DataOnList> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        listView = v.findViewById(R.id.list);
        newOrder = v.findViewById(R.id.new_order);

        listAdapter = new ListAdapter(requireContext(),data);
        listView.setAdapter(listAdapter);

        ref.child("users").child(LoginActivity.Token).child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DataOnList dataOnList = snapshot.getValue(DataOnList.class);
                            assert dataOnList != null;
                            data.add(dataOnList);
                        }
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        Toast.makeText(requireContext(), data.size()+"", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(HomeFragment.this);

        newOrder.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), NewOrders.class);
            startActivity(i);
        });

        return v;
    }

    public static void reOrder(Context context, boolean b) {
        if (b)
            Toast.makeText(context, "" + pos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CustomDialog customDialog = new CustomDialog(requireContext(), "ReOrder this?", 3);
        customDialog.show();
        pos = i;
    }
}