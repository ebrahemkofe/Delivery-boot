package com.graduation.deliveryboot.Helper;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.deliveryboot.Fragment.ControlFragment;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.ManualControlActivity;
import com.graduation.deliveryboot.ui.ReceiveOrder;

public class CustomDialog extends Dialog {

    public Context c;


    String text;
    int Case;

    public CustomDialog(Context a, String s, int Case) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.text = s;
        this.Case = Case;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Case == 1) {
            setContentView(R.layout.control_dialog);
            Button YesButton, NoButton;
            TextView textView;
            textView = findViewById(R.id.DialogText);
            YesButton = findViewById(R.id.YesButton);
            NoButton = findViewById(R.id.NoButton);

            textView.setText(textView.getText() + text);

            YesButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                ManualControlActivity.state = true;
            });

            NoButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                ManualControlActivity.state = false;
            });


        } else if (Case == 2) {
            setContentView(R.layout.code_dialog);
            Button Done;
            TextView CodeText;
            ImageView Copy, Share;
            Done = findViewById(R.id.DoneDialogButton);
            Copy = findViewById(R.id.CopyIcon);
            Share = findViewById(R.id.ShareIcon);
            CodeText = findViewById(R.id.CodeText);

            CodeText.setText(text);
            Done.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                Intent i = new Intent(c, ReceiveOrder.class);
                c.startActivity(i);
            });

            Copy.setOnClickListener(view -> {
                ClipboardManager clipboard = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", CodeText.getText());

                clipboard.setPrimaryClip(clip);

                Toast.makeText(c, "Copied", Toast.LENGTH_SHORT).show();
            });

            Share.setOnClickListener(view -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is Receiving Code!  " + CodeText.getText());
                sendIntent.setType("text/plain");
                c.startActivity(sendIntent);
            });
        } else if (Case == 3) {
            setContentView(R.layout.control_dialog);
            Button YesButton, NoButton;
            TextView textView;
            textView = findViewById(R.id.DialogText);
            YesButton = findViewById(R.id.YesButton);
            NoButton = findViewById(R.id.NoButton);

            textView.setText(textView.getText() + text);

            YesButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                HomeFragment.reOrder(c, true);
            });

            NoButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                HomeFragment.reOrder(c, false);
            });

        } else if (Case == 4) {
            setContentView(R.layout.control_dialog);
            Button YesButton, NoButton;
            TextView textView;
            textView = findViewById(R.id.DialogText);
            YesButton = findViewById(R.id.YesButton);
            NoButton = findViewById(R.id.NoButton);

            textView.setText(textView.getText() + text);

            YesButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                ControlFragment.state = true;
            });

            NoButton.setOnClickListener(view -> {
                CustomDialog.this.cancel();
                ControlFragment.state = false;
            });


        }
    }
}