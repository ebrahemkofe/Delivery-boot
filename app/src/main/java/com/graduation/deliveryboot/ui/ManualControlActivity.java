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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.erz.joysticklibrary.JoyStick;
import com.graduation.deliveryboot.Helper.CustomDialogClass;
import com.graduation.deliveryboot.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ManualControlActivity extends AppCompatActivity implements JoyStick.JoyStickListener {

    Button save;
    BluetoothAdapter bAdapter;
    List<String> devices = new ArrayList<>();
    public static int ind =0;
    BluetoothDevice[] bdevices;
    String deviceId = "";
    JoyStick joyStick;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_control_activity);

//        if (Build.VERSION.SDK_INT >= 26) {
//            deviceId = getSystemService(TelephonyManager.class).getImei();
//        }
//        else{
//            deviceId = getSystemService(TelephonyManager.class).getDeviceId();
//        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        findViewByIds();
        StartSearching();
        BluetoothSearch();

        save.setOnClickListener(view -> Toast.makeText(ManualControlActivity.this, "Saved", Toast.LENGTH_SHORT).show());

        joyStick.setType(JoyStick.TYPE_8_AXIS);
        joyStick.setListener(this);


    }

    public void findViewByIds() {
        save = findViewById(R.id.save_btn);
//        up = findViewById(R.id.up_arrow);
//        down = findViewById(R.id.down_arrow);
//        left = findViewById(R.id.left_arrow);
//        right = findViewById(R.id.right_arrow);
        joyStick = findViewById(R.id.JoystickControl);

    }


    // region Bluetooth Handle
    @SuppressLint("MissingPermission")
    private void sendDataToPairedDevice(String message, BluetoothDevice device) {
        byte[] toSend = message.getBytes();
        try {
            UUID applicationUUID = UUID.fromString(deviceId);
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
            Toast.makeText(ManualControlActivity.this, "sent", Toast.LENGTH_SHORT).show();
            // Your Data is sent to  BT connected paired device ENJOY.
        } catch (IOException e) {
            Toast.makeText(ManualControlActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void BluetoothSearch() {
        if (bAdapter == null)
            Toast.makeText(ManualControlActivity.this, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        else {
            // List all the bonded devices(paired)
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            int index = 0;
            if (pairedDevices.size() > 0) {
                devices.clear();
                for (BluetoothDevice device : pairedDevices) {

                    devices.add(device.getName());
                    bdevices[index] = device;
                    String deviceName = device.getName();
                    String macAddress = device.getAddress();
                    index++;
                }
                CustomDialogClass cdd = new CustomDialogClass(ManualControlActivity.this, devices);
                cdd.show();
            }
        }
    }


    @SuppressLint("MissingPermission")
    public void StartSearching() {
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
        }

        bAdapter.startDiscovery();

    }

    @SuppressLint("MissingPermission")
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
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

    @SuppressLint("MissingPermission")
    public void Pairing() {
        //first cancel discovery because its very memory intensive.
        bAdapter.cancelDiscovery();

        Log.d("TAG", "onItemClick: You Clicked on a device.");
        String deviceName = bdevices[ind].getName();
        String deviceAddress = bdevices[ind].getAddress();

        Log.d("TAG", "onItemClick: deviceName = " + deviceName);
        Log.d("TAG", "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d("TAG", "Trying to pair with " + deviceName);
            bdevices[ind].createBond();
        }
    }
//endregion


    //region JoyStick Handle
    @Override
    public void onMove(JoyStick joyStick, double angle, double power, int direction) {
        switch (direction) {
            case -1:
                // Toast.makeText(this, "Center", Toast.LENGTH_SHORT).show();
                //    sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Center");
                break;

            case 0:
                // Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
                //  sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Left");
                break;

            case 1:
                //Toast.makeText(this, "Left - Up", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Left - Up");
                break;

            case 2:
                // Toast.makeText(this, "Up", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Up");
                break;
            case 3:
                //Toast.makeText(this, "Up - Right", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Up - Right");
                break;
            case 4:
                //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Right");
                break;
            case 5:
                //Toast.makeText(this, "Right - Down", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Right - Down");
                break;
            case 6:
                //  Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Down");
                break;
            case 7:
                // Toast.makeText(this, "Down - Left", Toast.LENGTH_SHORT).show();
                //sendDataToPairedDevice("f", bdevices[ind]);
                save.setText("Down - Left");
                break;
            default:
                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTap() {

    }

    @Override
    public void onDoubleTap() {

    }
//endregion
}
