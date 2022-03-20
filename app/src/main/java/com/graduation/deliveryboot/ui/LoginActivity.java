package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;

public class LoginActivity extends AppCompatActivity {

    Button Login;
    ImageView Exit;
    TextView Signup;
    EditText Email, Password;
    CheckBox Signed;
    String prefPass, prefEmail;
    Boolean intent;
    boolean validEmail = false;
    boolean validPass = false;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindViewsByID();
        OnClicks();

        SharedPreferences pref = getSharedPreferences("remember", 0);
        prefEmail = pref.getString("email", "");
        prefPass = pref.getString("password", "");
        intent = pref.getBoolean("intent", false);

        if (intent) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void FindViewsByID() {
        Login = findViewById(R.id.loginButton);
        Exit = findViewById(R.id.ExitIcon);
        Signup = findViewById(R.id.signupText);
        Email = findViewById(R.id.EmailText);
        Password = findViewById(R.id.PasswordText);
        Signed = findViewById(R.id.SignedInCheckBox);
    }

    public void OnClicks() {
        Login.setOnClickListener(view -> {
            String email, password;
            email = Email.getText().toString();
            password = Password.getText().toString();

            if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                Email.setError("Invalid Email Address");
            else
                validEmail = true;



            if (password.length() < 8)
                Password.setError("Check your Password");
            else
                validPass = true;


            if (validEmail && validPass) {
                if (Signed.isChecked()) {

                    SharedPreferences myPref = getSharedPreferences("remember", MODE_PRIVATE);
                    SharedPreferences.Editor e = myPref.edit();
                    e.putString("email", email);
                    e.putString("password", password);
                    e.putBoolean("intent", true);
                    e.apply();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(this, "Please Check Email and Password", Toast.LENGTH_SHORT).show();
        });

        Exit.setOnClickListener(view -> finish());

        Signup.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, SignUp.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }

}