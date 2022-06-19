package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
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
      View v= inflater.inflate(R.layout.fragment_help, container, false);
      helpImg=v.findViewById(R.id.helplogo);
        ((AnimationDrawable) helpImg.getBackground()).start();
        emailUs= v.findViewById(R.id.emailUs_btn);

        emailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:abc@gmail.com")));
            }
        });

        return v;
    }
}