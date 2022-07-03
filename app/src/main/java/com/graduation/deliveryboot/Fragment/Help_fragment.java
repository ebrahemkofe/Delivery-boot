package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.graduation.deliveryboot.R;


public class Help_fragment extends Fragment {
    ImageView helpImg;
    Button emailUs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        helpImg = v.findViewById(R.id.helpLogo);
        ((AnimationDrawable) helpImg.getBackground()).start();
        emailUs = v.findViewById(R.id.emailUs_btn);

        emailUs.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Test321@gmail.com"});
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        });

        return v;
    }
}