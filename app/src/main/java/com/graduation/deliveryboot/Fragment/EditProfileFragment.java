package com.graduation.deliveryboot.Fragment;

import android.os.Bundle;

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


public class EditProfileFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        email = (EditText)view.findViewById(R.id.emailtext) ;
        password = (EditText)view.findViewById(R.id.passwordtext);
        fullName = (EditText)view.findViewById(R.id.name);
        phoneNumper = (EditText)view.findViewById(R.id.phonenumpertext);
        Button save= (Button) view.findViewById(R.id.save_btn);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "Saved", Toast.LENGTH_SHORT).show();


            }


        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        

        return view;

    }
    
}