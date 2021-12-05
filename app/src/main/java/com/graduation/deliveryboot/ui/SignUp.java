package com.graduation.deliveryboot.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.graduation.deliveryboot.R;


public class SignUp extends AppCompatActivity {
    EditText fullName, email, password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        fullName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.emailtext);
        password = (EditText) findViewById(R.id.passwordtext);
    }

    public void btn_join(View view) {
        String Fname = fullName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
    }
}
