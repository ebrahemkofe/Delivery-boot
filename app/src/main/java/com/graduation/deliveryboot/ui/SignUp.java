package com.graduation.deliveryboot.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.graduation.deliveryboot.Models.SignUpModel;
import com.graduation.deliveryboot.R;

import java.util.Random;


public class SignUp extends AppCompatActivity {
    EditText fullName, email, password, phoneNumber;
    ImageView Exit;
    Button joinUs;
    String FName, Email, pass, phone;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        fullName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberText);
        joinUs = (Button) findViewById(R.id.saveButton);
        Exit = findViewById(R.id.cancelIcon);

        Exit.setOnClickListener(view -> finish());
        joinUs.setOnClickListener(view -> {

            SignUpModel data=new SignUpModel();
            data.setID(String.valueOf(new Random().nextInt()));
            data.setEmail("hh@gmail.com");
            data.setName("hh");
            data.setPhoneNumber("01014451446");
            data.setPassword("211442555");
            ref.child("users").child(data.getID()).setValue(data);
        });
    }

    public void btn_join(View view) {
        FName = fullName.getText().toString().trim();
        Email = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        phone = phoneNumber.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
