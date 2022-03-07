package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;

public class LoginActivity extends AppCompatActivity {

    Button Login;
    ImageView Exit;
    TextView Signup;
    EditText Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindViewsByID();
        OnClicks();

    }

    public void FindViewsByID(){
        Login = findViewById(R.id.loginButton);
        Exit=findViewById(R.id.ExitIcon);
        Signup = findViewById(R.id.signupText);
        Email =findViewById(R.id.EmailText);
        Password = findViewById(R.id.PasswordText);
    }

    public void OnClicks(){
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Done!", Toast.LENGTH_SHORT).show();
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(LoginActivity.this,SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}