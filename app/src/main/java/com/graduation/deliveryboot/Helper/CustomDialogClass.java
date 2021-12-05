package com.graduation.deliveryboot.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.graduation.deliveryboot.Adapters.DialogListViewAdapter;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.ui.ManualControlActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    ListView listView;
    DialogListViewAdapter adapter;
    List<String> Device = new ArrayList<>();
    ManualControlActivity manualControlActivity;


    public CustomDialogClass(Activity a, List<String> num) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Device = num;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        listView = findViewById(R.id.dialogList);

        adapter = new DialogListViewAdapter(getContext(), R.layout.dialog_item, Device);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ManualControlActivity.ind = position;
                CustomDialogClass.this.cancel();

                manualControlActivity.Pairing();

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}