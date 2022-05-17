package com.graduation.deliveryboot.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduation.deliveryboot.R;


public class EditProfileActivity extends AppCompatActivity {

    EditText fullName, email, password, phoneNumper;
    String FName, Email, pass, phone;
    ImageView Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        email = (EditText) findViewById(R.id.emailtext);
        password = (EditText) findViewById(R.id.passwordtext);
        fullName = (EditText) findViewById(R.id.name);
        phoneNumper = (EditText) findViewById(R.id.phonenumpertext);
        Exit = findViewById(R.id.EditcancelIcon);

        Exit.setOnClickListener(view -> finish());

    }

    public void btn_save(View view) {
        FName = fullName.getText().toString().trim();
        Email = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        phone = phoneNumper.getText().toString().trim();
    }

}