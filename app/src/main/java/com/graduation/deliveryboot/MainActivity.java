package com.graduation.deliveryboot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton up,down,right,left;
    Button cancel, save;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definition();


//
//        up.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });
//

    }

    public void definition(){
        cancel=findViewById(R.id.cancel_btn);
        save =findViewById(R.id.save_btn);
        up=findViewById(R.id.up_arrow);
        down=findViewById(R.id.down_arrow);
        right=findViewById(R.id.right_arrow);
        left=findViewById(R.id.left_arrow);
    }

}