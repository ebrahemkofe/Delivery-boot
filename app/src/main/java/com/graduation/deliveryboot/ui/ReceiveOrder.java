package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Path;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.deliveryboot.R;

public class ReceiveOrder extends AppCompatActivity {

    Button Open , Close , Check , Done;
    EditText Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);

        FindViewByIDs();
        onClicks();
        Open.setClickable(false);
        Close.setClickable(false);
        Done.setClickable(false);
    }

    public void FindViewByIDs(){
        Open = findViewById(R.id.OpenButton);
        Close = findViewById(R.id.CloseButton);
        Done = findViewById(R.id.DoneButton);
        Check = findViewById(R.id.CodeCheckButton);
        Code=findViewById(R.id.CodeEditText);
    }

    public void onClicks(){
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Code.getText().toString().equals("1234")){
                    Open.setClickable(true);
                    Open.setBackgroundResource(R.drawable.rounded_button);

                    Close.setClickable(true);
                    Close.setBackgroundResource(R.drawable.rounded_button);

                    Done.setClickable(true);
                    Done.setBackgroundResource(R.drawable.rounded_button);
                }

                else{
                    Code.setError("Please Enter the Right Code");
                    Code.setText(null);
                }
            }
        });


        Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReceiveOrder.this, "Opened", Toast.LENGTH_SHORT).show();
            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReceiveOrder.this, "Closed", Toast.LENGTH_SHORT).show();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiveOrder.this.finish();
            }
        });
    }
}