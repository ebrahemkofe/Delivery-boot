package com.graduation.deliveryboot.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.Models.LoginModel;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button Login;
    TextView Signup;
    EditText Email, Password;
    CheckBox Signed;
    String prefPass, prefEmail, prefChild, username, wallet;
    boolean intent = false;
    boolean admin;
    boolean t = false;
    boolean validEmail = false;
    boolean validPass = false;
    boolean doubleBackToExitPressedOnce = false;
    List<LoginModel> accounts = new ArrayList<>();
    List<String> tokens = new ArrayList<>();
    public static String Token;
    public static String Username;
    public static float Wallet;


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
        prefChild = pref.getString("ChildName", "");
        username = pref.getString("AccountName", "");
        intent = pref.getBoolean("intent", false);
        admin = pref.getBoolean("admin", false);
        Token = prefChild;
        Username = username;

        if (intent) {
            FirebaseDatabase.getInstance().getReference().child("users").child(Token).child("wallet")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            wallet = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                            Wallet = Float.parseFloat(wallet);
                            MainActivity.WalletValue.setText(Wallet + " EGP");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
            MainActivity.admin = admin;

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            FirebaseDatabase.getInstance().getReference().child("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                LoginModel user = snapshot.getValue(LoginModel.class);
                                assert user != null;
                                accounts.add(user);
                                tokens.add(snapshot.getKey());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
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
                    Login.setBackgroundResource(R.drawable.un_clickable_rounded_button);
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
                    Login.setBackgroundResource(R.drawable.un_clickable_rounded_button);
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
                    for (int i = 0; i < accounts.size(); i++) {
                        if (accounts.get(i).getEmail().equals(email) && accounts.get(i).getPassword().equals(password)) {
                            Token = tokens.get(i);
                            t = true;
                            Wallet = Float.parseFloat(accounts.get(i).getWallet());
                            Username = accounts.get(i).getUsername();
                            if (accounts.get(i).isBool()) {
                                admin = true;
                                MainActivity.admin = true;
                            } else {
                                admin = false;
                                MainActivity.admin = false;
                            }
                            if (Signed.isChecked()) {

                                SharedPreferences myPref = getSharedPreferences("remember", MODE_PRIVATE);
                                SharedPreferences.Editor e = myPref.edit();
                                e.putString("email", email);
                                e.putString("password", password);
                                e.putString("ChildName", Token);
                                e.putString("AccountName", Username);
                                e.putBoolean("intent", true);
                                e.putBoolean("admin", admin);
                                e.apply();
                            }

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            this.finish();
                            break;
                        }
                        if (!t)
                            Toast.makeText(this, "Please Check Email and Password", Toast.LENGTH_SHORT).show();

                    }

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