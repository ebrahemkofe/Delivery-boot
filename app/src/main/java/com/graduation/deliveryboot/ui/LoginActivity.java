package com.graduation.deliveryboot.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;

public class LoginActivity extends AppCompatActivity {

    Button Login;
    TextView Signup;
    EditText Email, Password;
    CheckBox Signed;
    String prefPass, prefEmail;
    boolean intent;
    boolean admin;
    boolean validEmail = false;
    boolean validPass = false;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindViewsByID();
        OnClicks();
        Login.setClickable(false);

        if (!Email.getText().toString().equals("") || !Password.getText().toString().equals("")) {
            Login.setClickable(true);
            Login.setEnabled(true);
            Login.setBackgroundResource(R.drawable.rounded_button);
        }
        SharedPreferences pref = getSharedPreferences("remember", 0);
        prefEmail = pref.getString("email", "");
        prefPass = pref.getString("password", "");
        intent = pref.getBoolean("intent", false);
        admin = pref.getBoolean("admin", false);


        if (intent) {
            if (admin)
                MainActivity.admin = true;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void FindViewsByID() {
        Login = findViewById(R.id.loginButton);
        Signup = findViewById(R.id.signupText);
        Email = findViewById(R.id.EmailText);
        Password = findViewById(R.id.PasswordText);
        Signed = findViewById(R.id.SignedInCheckBox);
    }

    public void OnClicks() {
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Password.getText().toString().equals(""))
                    validPass = true;
                else {
                    validPass = false;
                    Login.setClickable(false);
                    Login.setEnabled(false);
                    Login.setBackgroundResource(R.drawable.unclickable_rounded_button);
                }

                if (Password.getText().toString().equals("1") && Email.getText().toString().equals("1")) {
                    validPass = true;
                    validEmail = true;
                }

                if (validPass && validEmail) {
                    Login.setClickable(true);
                    Login.setEnabled(true);
                    Login.setBackgroundResource(R.drawable.rounded_button);
                }
            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                    validEmail = true;
                else {
                    validEmail = false;
                    Login.setClickable(false);
                    Login.setEnabled(false);
                    Login.setBackgroundResource(R.drawable.unclickable_rounded_button);
                }

                if (Password.getText().toString().equals("1") && Email.getText().toString().equals("1")) {
                    validPass = true;
                    validEmail = true;
                }

                if (validPass && validEmail) {
                    Login.setClickable(true);
                    Login.setEnabled(true);
                    Login.setBackgroundResource(R.drawable.rounded_button);
                }

            }
        });
        Login.setOnClickListener(view -> {
            String email, password;
            email = Email.getText().toString();
            password = Password.getText().toString();

            if (email.equals("1") && password.equals("1")) {
                MainActivity.admin = true;
                if (Signed.isChecked()) {
                    SharedPreferences myPref = getSharedPreferences("remember", MODE_PRIVATE);
                    SharedPreferences.Editor e = myPref.edit();
                    e.putString("email", email);
                    e.putString("password", password);
                    e.putBoolean("intent", true);
                    e.putBoolean("admin", true);
                    e.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
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
            }
        });

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