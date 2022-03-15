package com.graduation.deliveryboot.ui;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.graduation.deliveryboot.R;


public class EditProfileFragment extends AppCompatActivity {

    EditText fullName, email, password,phoneNumper;
    ImageView Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        email = (EditText)findViewById(R.id.emailtext);
        password = (EditText)findViewById(R.id.passwordtext);
        fullName = (EditText)findViewById(R.id.name);
        phoneNumper = (EditText)findViewById(R.id.phonenumpertext);
        Exit = findViewById(R.id.EditcancelIcon);

        Exit.setOnClickListener(view ->  finish());

    }

    public void btn_save(View view) {
        String Fname = fullName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        int phone = Integer.parseInt(phoneNumper.getText().toString().trim());
    }

}