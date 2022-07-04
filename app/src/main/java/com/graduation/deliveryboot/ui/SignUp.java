package com.graduation.deliveryboot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.graduation.deliveryboot.Models.SignUpModel;
import com.graduation.deliveryboot.R;

import java.util.Random;


public class SignUp extends AppCompatActivity {
    EditText fullName, email, password, phoneNumber;
    ImageView Exit;
    Button joinUs;
    RadioButton admin;
    RadioButton customer;
    boolean person;
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
        admin=(RadioButton)findViewById(R.id.adminButton);
        customer=(RadioButton)findViewById(R.id.customerButton);


        Exit.setOnClickListener(view -> finish());
        joinUs.setOnClickListener(view -> {

            FName = fullName.getText().toString().trim();
            Email = email.getText().toString().trim();
            pass = password.getText().toString().trim();
            phone = phoneNumber.getText().toString().trim();
            if(admin.isSelected()){
                person=true;}
            else if(customer.isSelected()){

                person=false;
            }
            SignUpModel data = new SignUpModel();

            data.setbool(person);
            data.setID(String.valueOf(new Random().nextInt()));
            data.setEmail(Email);
            data.setName(FName);
            data.setPhoneNumber(phone);
            data.setPassword(pass);
            ref.child("users").child(data.getID()).setValue(data);
            Intent i=new Intent(SignUp.this,LoginActivity.class);
            startActivity(i);
            this.finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
