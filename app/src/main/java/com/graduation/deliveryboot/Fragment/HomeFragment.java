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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Adapters.ListAdapter;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.Models.OrdersModel;
import com.graduation.deliveryboot.ui.LoginActivity;
import com.graduation.deliveryboot.ui.NewOrders;
import com.graduation.deliveryboot.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    static int pos;
    ListView listView;
    Button newOrder;
    ListAdapter listAdapter;
    TextView listText;
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    static List<OrdersModel> data = new ArrayList<>();
    static String currentDate, currentTime;
    static String RandomCode;
    static Random random = new Random();
    @SuppressLint("StaticFieldLeak")
    static Context context;


    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        context = requireContext();
        data.clear();
        RandomCode = String.format("%04d", random.nextInt(10000));
        currentDate = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("h:mm aa", Locale.getDefault()).format(new Date());

        listView = v.findViewById(R.id.list);
        newOrder = v.findViewById(R.id.new_order);
        listText = v.findViewById(R.id.listText);

        listAdapter = new ListAdapter(requireContext(), data);
        listView.setAdapter(listAdapter);

        ref.child("users").child(LoginActivity.Token).child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            OrdersModel ordersModel = snapshot.getValue(OrdersModel.class);
                            assert ordersModel != null;
                            data.add(ordersModel);
                        }
                        listAdapter.notifyDataSetChanged();
                        if (data.isEmpty()) {
                            listText.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                        else{
                            listText.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        listView.setOnItemClickListener(HomeFragment.this);

        newOrder.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), NewOrders.class);
            startActivity(i);
        });

        return v;
    }

    @SuppressLint("DefaultLocale")
    public static void reOrder(Context context, boolean b) {
        if (b) {

            OrdersModel d = new OrdersModel();
            d.setFrom(data.get(pos).getFrom());
            d.setTo(data.get(pos).getTo());
            d.setName(LoginActivity.Username);
            d.setReceiver(data.get(pos).getReceiver());
            d.setReceiveCode(RandomCode);
            d.setDate(currentDate);
            d.setTime(currentTime);
            ref.child("users").child(LoginActivity.Token).child("orders").child("order" + String.format("%03d", random.nextInt(1000))).setValue(d);
            CustomDialog customDialog = new CustomDialog(context, RandomCode, 2);
            customDialog.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CustomDialog customDialog = new CustomDialog(requireContext(), "ReOrder this?", 3);
        customDialog.show();
        pos = i;
    }
}