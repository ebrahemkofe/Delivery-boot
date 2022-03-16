package com.graduation.deliveryboot.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.graduation.deliveryboot.R;


public class SignUp extends AppCompatActivity {
    EditText fullName, email, password,phoneNumper;
    ImageView Exit;
    Button joinUs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        fullName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.emailtext);
        password = (EditText) findViewById(R.id.passwordtext);
        phoneNumper = (EditText) findViewById(R.id.phonenumpertext);
        joinUs = (Button) findViewById(R.id.savebutton);
        Exit=findViewById(R.id.cancelIcon);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this, "Join Us!", Toast.LENGTH_SHORT).show();;
            }
        });
    }

    public void btn_join(View view) {
        String Fname = fullName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String phone = phoneNumper.getText().toString().trim();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}
