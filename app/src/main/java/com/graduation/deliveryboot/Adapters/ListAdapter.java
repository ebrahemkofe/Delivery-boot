package com.graduation.deliveryboot.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduation.deliveryboot.R;
import com.graduation.deliveryboot.Models.DataOnList;
import com.graduation.deliveryboot.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<DataOnList> {
    List<DataOnList> data =new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Context context;


    public ListAdapter(@NonNull Context context) {
        super(context, R.layout.list_item);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView num = convertView.findViewById(R.id.order_item);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);
        Toast.makeText(context, LoginActivity.child, Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance().getReference().child("users").child("orders").child(LoginActivity.child)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            int i=0;
                            DataOnList dataOnList = snapshot.getValue(DataOnList.class);
                            assert dataOnList != null;
                            data.add(dataOnList);
                            num.setText("From  " + data.get(i).getFrom() + "To" + data.get(i).getTo());
                            date.setText(data.get(i).getDate());
                            time.setText(data.get(i).getTime());
                            ++i;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });




        return convertView;
    }
}
