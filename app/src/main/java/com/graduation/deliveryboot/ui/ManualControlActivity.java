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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.erz.joysticklibrary.JoyStick;
import com.graduation.deliveryboot.Adapters.DialogListViewAdapter;
import com.graduation.deliveryboot.Helper.CustomDialog;
import com.graduation.deliveryboot.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ManualControlActivity extends AppCompatActivity implements JoyStick.JoyStickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    Button save,BTon;
    static JoyStick joyStick;
    RelativeLayout control, connect;
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DialogListViewAdapter mDeviceListAdapter;
    ListView lvNewDevices;

    public static boolean state;

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
                        control.setVisibility(View.GONE);
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

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
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
                    control.setVisibility(View.VISIBLE);
                    connect.setVisibility(View.GONE);
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver1);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver2);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver3);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver4);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_control_activity);
        findViewByIds();

        joyStick.setType(JoyStick.TYPE_8_AXIS);
        joyStick.setListener(this);

        btnEnableDisable_Discoverable = (Button) findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvNewDevices.setOnItemClickListener(ManualControlActivity.this);

        BTon.setOnClickListener(view -> enableDisableBT());


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
    public void btnEnableDisable_Discoverable(View view) {
//        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");
//
//        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(discoverableIntent);
//
//        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        registerReceiver(mBroadcastReceiver2, intentFilter);

        CustomDialog customDialog=new CustomDialog(this,"You want to Cancel?",1);
        customDialog.show();
        customDialog.setOnDismissListener(dialogInterface -> {
            if(state)
                this.finish();
        });


    }


    @SuppressLint("MissingPermission")
    public void startDiscover(){
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

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
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

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        int index = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(mBTDevices.get(i).getName())) {
                    control.setVisibility(View.VISIBLE);
                    connect.setVisibility(View.GONE);
                }
                index++;
            }
        } else {
            Log.d(TAG, "onItemClick: You Clicked on a device.");
            String deviceName = mBTDevices.get(i).getName();
            String deviceAddress = mBTDevices.get(i).getAddress();

            Log.d(TAG, "onItemClick: deviceName = " + deviceName);
            Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

            //create the bond.
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();
        }
    }

    public void findViewByIds() {
        save = findViewById(R.id.save_btn);
        joyStick = findViewById(R.id.JoystickControl);
        control = findViewById(R.id.controlLayout);
        connect = findViewById(R.id.activity_main);
        BTon = findViewById(R.id.btnEnableBT);



    }

    //region JoyStick Handle
    @SuppressLint("SetTextI18n")
    @Override
    public void onMove(JoyStick joyStick, double angle, double power, int direction) {
        switch (direction) {
            case -1:
                save.setText("Center");
                break;

            case 0:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();

                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Left");
                break;

            case 1:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Left - Up");
                break;

            case 2:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Up");
                break;
            case 3:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Up - Right");
                break;
            case 4:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Right");
                break;
            case 5:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Right - Down");
                break;
            case 6:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
                    save.setText("Down");
                break;
            case 7:
                if (!mBluetoothAdapter.isEnabled()) {
                    enableDisableBT();
                } else
//                    sendDataToPairedDevice("f", bDevices[ind]);
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
    private void sendDataToPairedDevice(String message, BluetoothDevice device) {
        byte[] toSend = message.getBytes();
        try {
            UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//            UUID applicationUUID = UUID.fromString("fc5ffc49-00e3-4c8b-9cf1-6b72aad1001a");
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
            Toast.makeText(ManualControlActivity.this, Arrays.toString(toSend) + "", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(ManualControlActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}

