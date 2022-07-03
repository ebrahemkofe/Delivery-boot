package com.graduation.deliveryboot.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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
    boolean validCardNum = false;
    boolean validDate = false;
    boolean validCvv = false;
    boolean validAmount = false;
    double num;

    @SuppressLint("SetTextI18n")
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

        confirm.setEnabled(false);

        SharedPreferences myPref = requireContext().getSharedPreferences("wallet", 0);
        wallet = myPref.getFloat("Amount", 0.0f);
        Wallet.setText(wallet + " EGP");
        MainActivity.WalletValue.setText(wallet + " EGP");


        confirm.setOnClickListener(view1 -> {
            SharedPreferences myPref1 = requireContext().getSharedPreferences("wallet", MODE_PRIVATE);
            SharedPreferences.Editor e = myPref1.edit();

            if (!amount.getText().toString().equals(""))
                wallet += Float.parseFloat(amount.getText().toString());

            e.putFloat("Amount", wallet);
            e.apply();

            amount.getText().clear();
            card_num.getText().clear();
            expiry_date.getText().clear();
            cvv.getText().clear();
            card_name.getText().clear();

            Wallet.setText(wallet + " EGP");
            MainActivity.WalletValue.setText(wallet + " EGP");
        });

        card_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (card_num.getText().toString().length() >= 14) {
                    validCardNum = true;
                } else {
                    validCardNum = false;
                    confirm.setClickable(false);
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.un_clickable_rounded_button);
                }
                if (validCardNum && validDate && validCvv && validAmount) {
                    confirm.setClickable(true);
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rounded_button);
                }
            }
        });
        expiry_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (expiry_date.getText().toString().matches("[0-9]+/+[0-9]+")) {
                    validDate = true;
                } else {
                    validDate = false;
                    confirm.setClickable(false);
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.un_clickable_rounded_button);
                }
                if (validCardNum && validDate && validCvv && validAmount) {
                    confirm.setClickable(true);
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rounded_button);
                }
            }
        });
        cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (cvv.getText().toString().length() == 3 || cvv.getText().toString().length() == 4) {
                    validCvv = true;
                } else {
                    validCvv = false;
                    confirm.setClickable(true);
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.un_clickable_rounded_button);
                }
                if (validCardNum && validDate && validCvv && validAmount) {
                    confirm.setClickable(true);
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rounded_button);
                }
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!amount.getText().toString().equals(""))
                    num = Double.parseDouble(amount.getText().toString());
                if (num >= 5 && num <= 1000) {
                    validAmount = true;
                } else {
                    validAmount = false;
                    confirm.setClickable(false);
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.un_clickable_rounded_button);
                }
                if (validCardNum && validDate && validCvv && validAmount) {
                    confirm.setClickable(true);
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rounded_button);
                }
            }
        });

        return view;
    }
}