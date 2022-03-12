package com.graduation.deliveryboot.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.graduation.deliveryboot.Adapters.DialogListViewAdapter;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.ManualControlActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog{

    public Context c;
    public Dialog d;
    ListView listView;
    TextView textView;
    Button YesButton , NoButton;
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
            adapter = new DialogListViewAdapter(getContext(), R.layout.dialog_item, Device);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    ManualControlActivity.ind = position;
                    CustomDialog.this.cancel();

                    manualControlActivity.Pairing();

                }
            });
        }

        else if(Case == 1){
            setContentView(R.layout.control_dialog);
            textView = findViewById(R.id.DialogText);
            YesButton = findViewById(R.id.YesButton);
            NoButton = findViewById(R.id.NoButton);

            textView.setText(textView.getText()+text);

            YesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog.this.cancel();
                }
            });

            NoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog.this.cancel();
                }
            });
        }
    }

}