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
    EditText fullName, email, password, phoneNumper;
    ImageView Exit;
    Button joinUs;
    String FName, Email, pass, phone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        fullName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.emailtext);
        password = (EditText) findViewById(R.id.passwordtext);
        phoneNumper = (EditText) findViewById(R.id.phonenumpertext);
        joinUs = (Button) findViewById(R.id.savebutton);
        Exit = findViewById(R.id.cancelIcon);

        Exit.setOnClickListener(view -> finish());
        joinUs.setOnClickListener(view -> Toast.makeText(SignUp.this, "Join Us!", Toast.LENGTH_SHORT).show());
    }

    public void btn_join(View view) {
        FName = fullName.getText().toString().trim();
        Email = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        phone = phoneNumper.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
