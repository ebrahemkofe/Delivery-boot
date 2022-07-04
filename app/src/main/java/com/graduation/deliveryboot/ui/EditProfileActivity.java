package com.graduation.deliveryboot.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Models.LoginModel;
import com.graduation.deliveryboot.Models.SignUpModel;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EditProfileActivity extends AppCompatActivity {

    EditText fullName, email, password, phoneNumber;
    String FName, Email, pass, phone;
    ImageView Exit;
    SignUpModel user;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        fullName = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberText);
        Exit = findViewById(R.id.EditCancelIcon);

        ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals(LoginActivity.Token)) {
                        user = snapshot.getValue(SignUpModel.class);
                    }
                }

                assert user != null;
                email.setText(user.getEmail());
                password.setText(user.getPassword());
                fullName.setText(user.getName());
                phoneNumber.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Exit.setOnClickListener(view -> finish());

    }

    public void btn_save(View view) {
        FName = fullName.getText().toString().trim();
        Email = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        phone = phoneNumber.getText().toString().trim();

        SignUpModel data = new SignUpModel();
        data.setID(LoginActivity.Token);
        data.setEmail(Email);
        data.setName(FName);
        data.setPhoneNumber(phone);
        data.setPassword(pass);
        ref.child("users").child(LoginActivity.Token).setValue(data);

    }

}