package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;

public class ReceiveOrder extends AppCompatActivity {

    Button Open, Close, Check, Done;
    EditText Code;
    ImageView Cancel;

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

    public void FindViewByIDs() {
        Open = findViewById(R.id.OpenButton);
        Close = findViewById(R.id.CloseButton);
        Done = findViewById(R.id.DoneButton);
        Check = findViewById(R.id.CodeCheckButton);
        Code = findViewById(R.id.CodeEditText);
        Cancel = findViewById(R.id.cancelIconReceiveActivity);
    }

    public void onClicks() {
        Check.setOnClickListener(view -> {
            if (Code.getText().toString().equals("1234")) {
                Open.setClickable(true);
                Open.setBackgroundResource(R.drawable.rounded_button_white);

                Close.setClickable(true);
                Close.setBackgroundResource(R.drawable.rounded_button_white);

                Done.setClickable(true);
                Done.setBackgroundResource(R.drawable.rounded_button_white);
            } else {
                Code.setError("Please Enter the Right Code");
                Code.setText(null);
            }
        });


        Open.setOnClickListener(view -> Toast.makeText(ReceiveOrder.this, "Opened", Toast.LENGTH_SHORT).show());

        Close.setOnClickListener(view -> Toast.makeText(ReceiveOrder.this, "Closed", Toast.LENGTH_SHORT).show());

        Done.setOnClickListener(view -> ReceiveOrder.this.finish());

        Cancel.setOnClickListener(view -> ReceiveOrder.this.finish());
    }
}