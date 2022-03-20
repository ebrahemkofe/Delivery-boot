package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.LivePhoto;
import com.graduation.deliveryboot.ui.ManualControlActivity;
import com.graduation.deliveryboot.ui.OrdersQueue;


public class ControlFragment extends Fragment {
    LinearLayout liveCamera,manualControl,track,callBack,orders,battery,shutdown,alarm;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_control, container, false);
        liveCamera=v.findViewById(R.id.livecamera_id);
        manualControl=v.findViewById(R.id.manuallayout_id);
        track=v.findViewById(R.id.tracklayout_id);
        callBack=v.findViewById(R.id.callbacklayout_id);
        orders=v.findViewById(R.id.orderslayout_id);
        battery=v.findViewById(R.id.batterylayout_id);
        shutdown=v.findViewById(R.id.shutdownlayout_id);
        alarm=v.findViewById(R.id.alarmlayout_id);

        liveCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LivePhoto.class);
                startActivity(intent);
            }
        });
        manualControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ManualControlActivity.class);
                startActivity(intent);
            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(getContext(), LivePhoto.class);
             //   startActivity(intent);
            }
        });
        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrdersQueue.class);
                startActivity(intent);
            }
        });
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        return v;
    }

}