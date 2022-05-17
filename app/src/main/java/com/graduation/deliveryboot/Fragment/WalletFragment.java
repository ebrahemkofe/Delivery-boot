package com.graduation.deliveryboot.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.MainActivity;

public class WalletFragment extends Fragment {
    EditText card_num, expiry_date, cvv, card_name, amount;
    TextView Wallet;
    Button confirm;
    float wallet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        card_num = view.findViewById(R.id.card_num);
        expiry_date = view.findViewById(R.id.expiry_date);
        cvv = view.findViewById(R.id.cvv);
        card_name = view.findViewById(R.id.card_name);
        amount = view.findViewById(R.id.amount);
        Wallet = view.findViewById(R.id.WalletAmount);
        confirm = view.findViewById(R.id.confirm_btn);

        SharedPreferences myPref = requireContext().getSharedPreferences("wallet", 0);
        wallet = myPref.getFloat("Amount", 0.0f);
        Wallet.setText(wallet + " EGP");
        MainActivity.WalletValue.setText(wallet + " EGP");


        confirm.setOnClickListener(view1 -> {
            SharedPreferences myPref1 = requireContext().getSharedPreferences("wallet", MODE_PRIVATE);
            SharedPreferences.Editor e = myPref1.edit();
            wallet = wallet + Float.parseFloat(amount.getText().toString());
            e.putFloat("Amount", wallet);
            e.apply();

            amount.setText("");
            Wallet.setText(wallet + " EGP");
            MainActivity.WalletValue.setText(wallet + " EGP");
        });


        return view;
    }
}