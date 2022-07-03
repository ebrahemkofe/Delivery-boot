package com.graduation.deliveryboot.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduation.deliveryboot.R;


public class EditProfileActivity extends AppCompatActivity {

    EditText fullName, email, password, phoneNumber;
    String FName, Email, pass, phone;
    ImageView Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        fullName = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberText);
        Exit = findViewById(R.id.EditCancelIcon);

        Exit.setOnClickListener(view -> finish());

    }

    public void btn_save(View view) {
        FName = fullName.getText().toString().trim();
        Email = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        phone = phoneNumber.getText().toString().trim();
    }

}