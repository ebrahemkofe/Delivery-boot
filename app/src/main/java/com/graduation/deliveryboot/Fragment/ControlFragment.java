package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.LivePhoto;
import com.graduation.deliveryboot.ui.ManualControlActivity;
import com.graduation.deliveryboot.ui.OrdersQueue;


public class ControlFragment extends Fragment {
    LinearLayout liveCamera, manualControl, track, callBack, orders, shutdown, alarm;
    public static boolean state = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, container, false);
        liveCamera = v.findViewById(R.id.liveCamera_id);
        manualControl = v.findViewById(R.id.manualLayout_id);
        track = v.findViewById(R.id.trackLayout_id);
        callBack = v.findViewById(R.id.callBackLayout_id);
        orders = v.findViewById(R.id.ordersLayout_id);
        shutdown = v.findViewById(R.id.shutdownLayout_id);
        alarm = v.findViewById(R.id.alarmLayout_id);

        liveCamera.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LivePhoto.class);
            startActivity(intent);
        });

        manualControl.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ManualControlActivity.class);
            startActivity(intent);
        });

        track.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), LivePhoto.class);
            startActivity(intent);
        });

        callBack.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(requireContext(), "Call Back the Boot?", 4);
            customDialog.show();
            customDialog.setOnDismissListener(dialogInterface -> {
                if (state)
                    Toast.makeText(requireContext(), "Calling Back", Toast.LENGTH_SHORT).show();
            });
        });

        orders.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), OrdersQueue.class);
            startActivity(intent);
        });

        shutdown.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(requireContext(), "ShutDown the Boot?", 4);
            customDialog.show();
            customDialog.setOnDismissListener(dialogInterface -> {
                if (state)
                    Toast.makeText(requireContext(), "Shutting Down", Toast.LENGTH_SHORT).show();
            });
        });

        alarm.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(requireContext(), "Turn on the Alarm?", 4);
            customDialog.show();
            customDialog.setOnDismissListener(dialogInterface -> {
                if (state)
                    Toast.makeText(requireContext(), "Alarm is on!", Toast.LENGTH_SHORT).show();
            });
        });

        return v;
    }

}