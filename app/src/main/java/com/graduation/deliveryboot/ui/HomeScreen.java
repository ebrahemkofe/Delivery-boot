package com.graduation.deliveryboot.ui;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.graduation.deliveryboot.Adapters.ListAdabter;
import com.graduation.deliveryboot.R;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

String []num={"From 301 to 309"};
        String []date={"12/3"};
        String []time={"8:55 PM"};
        ArrayList<DataOnList> dataArrayList=new ArrayList<>();

        for(int i=0;i< num.length;++i){
   DataOnList dataOnList=new DataOnList(num[i],date[i],time[i]);
    dataArrayList.add(dataOnList);

        }

        ListAdabter listAdabter=new ListAdabter(HomeScreen.this,dataArrayList);

    }

    public void btn_new_order (View view){

    }
}
