package com.graduation.deliveryboot.ui;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.graduation.deliveryboot.Helper.CustomDialogClass;
import com.graduation.deliveryboot.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button save;
    ImageButton up,down;
    BluetoothAdapter bAdapter;
    List<String> devices = new ArrayList<>();
    public static int ind=0;
    BluetoothDevice[] bdevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        findViewByIds();
        StartSearching();
        BluetoothSearch();
        onClicks();



    }

    private void sendDataToPairedDevice(String message , BluetoothDevice device){
        byte[] toSend = message.getBytes();
        try {
            UUID applicationUUID = UUID.fromString("00000000-1111-2222-3333-000000000011");
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
            Toast.makeText(MainActivity.this, "sent", Toast.LENGTH_SHORT).show();
            // Your Data is sent to  BT connected paired device ENJOY.
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    public void BluetoothSearch(){
        if (bAdapter == null) {
            Toast.makeText(MainActivity.this, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        } else {
            // List all the bonded devices(paired)
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            int index=0;
            if (pairedDevices.size() > 0) {
                devices.clear();
                for (BluetoothDevice device : pairedDevices) {

                    devices.add(device.getName());
                    bdevices[index] =device;
                    String deviceName = device.getName();
                    String macAddress = device.getAddress();
                    index++;
                }
                CustomDialogClass cdd=new CustomDialogClass(MainActivity.this,devices);
                cdd.show();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onClicks(){

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Set<BluetoothDevice> bt=bAdapter.getBondedDevices();
                if (bt.size() > 0) {
                    sendDataToPairedDevice("t",bdevices[ind]);
                    Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findViewByIds(){
        save=findViewById(R.id.save_btn);
        up=findViewById(R.id.up_arrow);
        down=findViewById(R.id.down_arrow);
    }

    public void StartSearching(){
        bAdapter=BluetoothAdapter.getDefaultAdapter();
        if(!bAdapter.isEnabled())
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,1);
        }

        bAdapter.startDiscovery();

    }

    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d("TAG", "BroadcastReceiver: BOND_BONDED.");
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d("TAG", "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d("TAG", "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    public void Pairing(){
        //first cancel discovery because its very memory intensive.
        bAdapter.cancelDiscovery();

        Log.d("TAG", "onItemClick: You Clicked on a device.");
        String deviceName = bdevices[ind].getName();
        String deviceAddress = bdevices[ind].getAddress();

        Log.d("TAG", "onItemClick: deviceName = " + deviceName);
        Log.d("TAG", "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d("TAG", "Trying to pair with " + deviceName);
            bdevices[ind].createBond();
        }
    }

}
