package com.graduation.deliveryboot.Helper;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.deliveryboot.Adapters.DialogListViewAdapter;
import com.graduation.deliveryboot.Fragment.HomeFragment;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.ManualControlActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog{

    public Context c;
    public Dialog d;

    ListView listView;
    TextView textView , CodeText;
    Button YesButton , NoButton , Done;
    ImageView Copy , Share;
    String text;
    DialogListViewAdapter adapter;
    List<String> Device = new ArrayList<>();
    ManualControlActivity manualControlActivity;
    int Case;

    public CustomDialog(Context a, List<String> num , int Case) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Device = num;
        this.Case = Case;
    }

    public CustomDialog(Context a, String s , int Case) {
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

        if (Case == 0) {
            setContentView(R.layout.bluetooth_dialog);

            listView = findViewById(R.id.dialogList);
            adapter = new DialogListViewAdapter(getContext(), android.R.layout.simple_list_item_1, Device);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((adapterView, view, position, l) -> {
                ManualControlActivity.ind = position;
                CustomDialog.this.cancel();

                manualControlActivity.Pairing();

            });
        }

        else if(Case == 1){
            setContentView(R.layout.control_dialog);
            textView = findViewById(R.id.DialogText);
            YesButton = findViewById(R.id.YesButton);
            NoButton = findViewById(R.id.NoButton);

            textView.setText(textView.getText()+text);

            YesButton.setOnClickListener(view -> CustomDialog.this.cancel());

            NoButton.setOnClickListener(view -> CustomDialog.this.cancel());

        }

        else if (Case == 2) {
            setContentView(R.layout.code_dialog);
            Done = findViewById(R.id.DoneDialogButton);
            Copy = findViewById(R.id.CopyIcon);
            Share = findViewById(R.id.ShareIcon);
            CodeText = findViewById(R.id.CodeText);

            Done.setOnClickListener(view -> CustomDialog.this.cancel());

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
        }

             else if(Case == 3){
                setContentView(R.layout.control_dialog);
                textView = findViewById(R.id.DialogText);
                YesButton = findViewById(R.id.YesButton);
                NoButton = findViewById(R.id.NoButton);

                textView.setText(textView.getText()+text);

                YesButton.setOnClickListener(view -> {
                    CustomDialog.this.cancel();
                 HomeFragment.reOrder(c,true);
                });

                NoButton.setOnClickListener(view -> {
                    CustomDialog.this.cancel();
                  HomeFragment.reOrder(c, false);
                });

            }

    }

}