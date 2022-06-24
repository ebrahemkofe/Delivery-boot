package com.graduation.deliveryboot.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
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
    boolean validcardnum = false;
    boolean validdate = false;
    boolean validcvv = false;
    boolean validname = false;
    boolean validamount = false;
    int num ;
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

                    wallet += Float.parseFloat(amount.getText().toString());
                    e.putFloat("Amount", wallet);
                    e.apply();

                    amount.setText("");
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
                        validcardnum = true;
                    } else {
                        validcardnum = false;
                        confirm.setClickable(false);
                        confirm.setEnabled(false);
                        confirm.setBackgroundResource(R.drawable.unclickable_rounded_button);
                    }
                    if (validcardnum && validdate && validcvv && validamount) {
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
                    if (expiry_date.getText().toString().matches("[0-9]+\\/+[0-9]")) {
                        validdate = true;
                    } else {
                        validdate = false;
                        confirm.setClickable(false);
                        confirm.setEnabled(false);
                        confirm.setBackgroundResource(R.drawable.unclickable_rounded_button);
                    }
                    if (validcardnum && validdate && validcvv && validamount) {
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
                        validcvv = true;
                    } else {
                        validcvv = false;
                        confirm.setClickable(true);
                        confirm.setEnabled(false);
                        confirm.setBackgroundResource(R.drawable.unclickable_rounded_button);
                    }
                    if (validcardnum && validdate && validcvv && validamount) {
                        confirm.setClickable(true);
                        confirm.setEnabled(true);
                        confirm.setBackgroundResource(R.drawable.rounded_button);
                    }
                }
            });
            card_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (validcardnum && validdate && validcvv && validname && validamount) {
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
               num = Integer.parseInt(amount.getText().toString());
               if(num >= 5 && num <=500){
                      validamount = true;
                  }
               else{
                   validamount = false;
                   confirm.setClickable(false);
                   confirm.setEnabled(false);
                   confirm.setBackgroundResource(R.drawable.unclickable_rounded_button);
               }
               if (validcardnum && validdate && validcvv && validname && validamount) {
                   confirm.setClickable(true);
                   confirm.setEnabled(true);
                   confirm.setBackgroundResource(R.drawable.rounded_button);
               }
               }
           });

            return view;
        }
}