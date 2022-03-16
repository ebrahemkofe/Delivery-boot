package com.graduation.deliveryboot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.LoginActivity;
import com.graduation.deliveryboot.ui.SignUp;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfileFragment extends AppCompatActivity {

    EditText fullName, email, password,phoneNumper;
    ImageView Exit;
    Button save;



    public EditProfileFragment() {
        // Required empty public constructor
    }




    public void btn_save(View view) {
        String Fname = fullName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        int phone = Integer.parseInt(phoneNumper.getText().toString().trim());
    }

    @Override
    protected void onCreate(
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        email = (EditText)findViewById(R.id.emailtext);
        password = (EditText)findViewById(R.id.passwordtext);
        fullName = (EditText)findViewById(R.id.name);
        phoneNumper = (EditText)findViewById(R.id.phonenumpertext);
        Button save= (Button) findViewById(R.id.savebutton);


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileFragment.this , "Saved", Toast.LENGTH_SHORT).show();
            }

        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        


    }
    
}