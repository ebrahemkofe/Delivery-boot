package com.graduation.deliveryboot.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.erz.joysticklibrary.JoyStick;
import com.graduation.deliveryboot.Adapters.DialogListViewAdapter;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ManualControlActivity extends AppCompatActivity implements JoyStick.JoyStickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    Button save, cancel, BTon, OpenClose;
    static JoyStick joyStick;
    RelativeLayout control, connect;
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    public final ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DialogListViewAdapter mDeviceListAdapter;
    ListView lvNewDevices;
    BluetoothDevice deviceToSent;
    public static boolean state;
    OutputStream outputStream;
    InputStream inputStream;
    BluetoothSocket mmSocket;
    boolean Open_Close = true;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        joyStick.setVisibility(View.GONE);
                        connect.setVisibility(View.VISIBLE);
                        enableDisableBT();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        startDiscover();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };


    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DialogListViewAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    joyStick.setVisibility(View.VISIBLE);
                    connect.setVisibility(View.GONE);
                    lvNewDevices.setEnabled(true);
                    try {
                        ConnectSocket(deviceToSent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                    lvNewDevices.setEnabled(false);
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                    lvNewDevices.setEnabled(true);
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver1);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver2);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver3);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver4);

        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }

    @SuppressLint({"MissingPermission", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_control_activity);

        findViewByIds();
        onClicks();
        joyStick.setType(JoyStick.TYPE_8_AXIS);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothAdapter.startDiscovery();
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);



    }


    @SuppressLint("SetTextI18n")
    public void onClicks(){
        lvNewDevices.setOnItemClickListener(ManualControlActivity.this);
        joyStick.setListener(this);
        BTon.setOnClickListener(view -> enableDisableBT());
        cancel.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog(this, "You want to Cancel?", 1);
            customDialog.show();
            customDialog.setOnDismissListener(dialogInterface -> {
                if (state){
                    try {
                        mmSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.finish();
                }
            });
        });
        OpenClose.setOnClickListener(view -> {
            if (Open_Close) {
                SendData("O");
                OpenClose.setText("Close Box");
                Open_Close=false;
            }
            else {
                SendData("C");
                OpenClose.setText("Open Box");
                Open_Close = true;
            }
        });
    }
    @SuppressLint("MissingPermission")
    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
//        if (mBluetoothAdapter.isEnabled()) {
//            Log.d(TAG, "enableDisableBT: disabling BT.");
//            mBluetoothAdapter.disable();
//
//            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//            registerReceiver(mBroadcastReceiver1, BTIntent);
//        }

    }


    @SuppressLint("MissingPermission")
    public void Cancel_connect(View view) {
//        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");
//
//        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(discoverableIntent);
//
//        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        registerReceiver(mBroadcastReceiver2, intentFilter);

        CustomDialog customDialog = new CustomDialog(this, "You want to Cancel?", 1);
        customDialog.show();
        customDialog.setOnDismissListener(dialogInterface -> {
            if (state)
                this.finish();
        });

    }


    @SuppressLint("MissingPermission")
    public void startDiscover() {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
        if (mDeviceListAdapter != null)
            mDeviceListAdapter.clear();

        if (mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "btnDiscover: Canceling discovery.");
        checkBTPermissions();

        mBluetoothAdapter.startDiscovery();
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
    }

    @SuppressLint("MissingPermission")
    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if (mDeviceListAdapter != null)
            mDeviceListAdapter.clear();

        if (mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "btnDiscover: Canceling discovery.");
        checkBTPermissions();

        mBluetoothAdapter.startDiscovery();
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);

    }


    private void checkBTPermissions() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();
        String PIN = "1234";
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(mBTDevices.get(i).getName())) {
                    joyStick.setVisibility(View.VISIBLE);
                    connect.setVisibility(View.GONE);
                    if(mBTDevices.get(i).getName().equals("HC-05"))
                        mBTDevices.get(i).setPin(PIN.getBytes());
                    deviceToSent = mBTDevices.get(i);
                    try {
                        ConnectSocket(mBTDevices.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.d(TAG, "onItemClick: You Clicked on a device.");
            String deviceName = mBTDevices.get(i).getName();
            String deviceAddress = mBTDevices.get(i).getAddress();

            Log.d(TAG, "onItemClick: deviceName = " + deviceName);
            Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

            //create the bond.
            Log.d(TAG, "Trying to pair with " + deviceName);
            deviceToSent = mBTDevices.get(i);
            if(mBTDevices.get(i).getName().equals("HC-05"))
                mBTDevices.get(i).setPin(PIN.getBytes());
            mBTDevices.get(i).createBond();
        }
    }

    public void findViewByIds() {
        save = findViewById(R.id.save_btn);
        joyStick = findViewById(R.id.JoystickControl);
        control = findViewById(R.id.controlLayout);
        connect = findViewById(R.id.activity_main);
        BTon = findViewById(R.id.btnEnableBT);
        cancel = findViewById(R.id.cancel_btn);
        btnEnableDisable_Discoverable = findViewById(R.id.Cancel_connectBtn);
        lvNewDevices = findViewById(R.id.lvNewDevices);
        OpenClose = findViewById(R.id.OpenClose_btn);
    }

    //region JoyStick Handle
    @SuppressLint("SetTextI18n")
    @Override
    public void onMove(JoyStick joyStick, double angle, double power, int direction) {
        switch (direction) {
            case -1:
                save.setText("S");
                break;

            case 0:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else {
                    SendData("H");
                    save.setText("Left");
                }
                break;

            case 1:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else {
                    SendData("I");
                    save.setText("Left - Up");
                }
                break;

            case 2:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("A");
                save.setText("Up");
                break;
            case 3:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("B");
                save.setText("Up - Right");
                break;
            case 4:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("D");
                save.setText("Right");
                break;
            case 5:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("E");
                save.setText("Right - Down");
                break;
            case 6:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("F");
                save.setText("Down");
                break;
            case 7:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
                    SendData("G");
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

    @SuppressLint("MissingPermission")
    private void ConnectSocket(final BluetoothDevice device) throws IOException {

        ////        byte[] toSend = message.getBytes();
//        try {
//            if(device != null) {
        UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
////            UUID applicationUUID = UUID.fromString("fc5ffc49-00e3-4c8b-9cf1-6b72aad1001a");
//                BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
//                socket.connect();
////                OutputStream mmOutStream = socket.getOutputStream();
////                InputStream mmInputStream = socket.getInputStream();
////                mmOutStream.write(toSend);
////                Log.d(TAG, "onItemClick: sent");
//            }
//        } catch (IOException e) {
//            Log.e(TAG,e.getMessage());
//        }
        if(mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        if (mmSocket != null) {
            mmSocket.close();
            Log.d("Socket", "Closed");
        }

        try {
            mmSocket = device.createRfcommSocketToServiceRecord(applicationUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Connection", "Created");
        try {
            outputStream = mmSocket.getOutputStream();
            inputStream = mmSocket.getInputStream();
            mmSocket.connect();
            Log.d("Connection", "Connected");


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection", "General Error " + e.getMessage());
        }
    }

    private void SendData(String s) {
        byte[] toSend = s.getBytes();
        if (mmSocket.isConnected()) {
            Log.d(TAG, "Socket is Connected");
            try {
                outputStream.write(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Log.e(TAG, "Socket is not Connected");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

