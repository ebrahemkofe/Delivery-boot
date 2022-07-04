package com.graduation.deliveryboot.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Models.OrdersModel;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;

public class ReceiveOrder extends AppCompatActivity {

    Button Open, Close, Check, Done;
    EditText Code;
    ImageView Cancel;
    List<OrdersModel> data=new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    boolean d=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);

        FindViewByIDs();
        onClicks();
        Open.setClickable(false);
        Close.setClickable(false);
        Done.setClickable(false);

        ref.child("users").child(LoginActivity.Token).child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            OrdersModel order = snapshot.getValue(OrdersModel.class);
                            data.add(order);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

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
            for (int i =0 ; i<data.size();i++){
                if (Code.getText().toString().equals(data.get(i).getReceiveCode())) {
                    d=true;
                    Open.setClickable(true);
                    Open.setBackgroundResource(R.drawable.rounded_button);

                    Close.setClickable(true);
                    Close.setBackgroundResource(R.drawable.rounded_button);

                    Done.setClickable(true);
                    Done.setBackgroundResource(R.drawable.rounded_button);
            }

            } if(!d) {
                Code.setError("Please Enter the Right Code");
                Code.setText("");
            }
        });

        Code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Code.getText().length()!=4){
                    d=false;

                    Open.setClickable(false);
                    Open.setBackgroundResource(R.drawable.un_clickable_rounded_button);

                    Close.setClickable(false);
                    Close.setBackgroundResource(R.drawable.un_clickable_rounded_button);

                    Done.setClickable(false);
                    Done.setBackgroundResource(R.drawable.un_clickable_rounded_button);
                }
            }
        });

        Open.setOnClickListener(view -> Toast.makeText(ReceiveOrder.this, "Opened", Toast.LENGTH_SHORT).show());

        Close.setOnClickListener(view -> Toast.makeText(ReceiveOrder.this, "Closed", Toast.LENGTH_SHORT).show());

        Done.setOnClickListener(view -> ReceiveOrder.this.finish());

        Cancel.setOnClickListener(view -> ReceiveOrder.this.finish());
    }

}