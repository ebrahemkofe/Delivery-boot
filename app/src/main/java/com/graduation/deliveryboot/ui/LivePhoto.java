package com.graduation.deliveryboot.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduation.deliveryboot.R;
import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;


public class LivePhoto extends AppCompatActivity implements HBRecorderListener {


    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 101;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = 102;
    Button btnStart, btnStop;
    ImageView image, Exit;
    HBRecorder hbRecorder;
    ContentValues contentValues;
    Uri mUri;
    boolean hasPermissions;
    boolean Recording = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_photo);
        FindViewsByIDs();
        hbRecorder = new HBRecorder(this, this);
        hbRecorder.setVideoEncoder("H264");

        OnClicks();
    }

    public void FindViewsByIDs() {
        btnStart = findViewById(R.id.Record);
        btnStop = findViewById(R.id.ScreenShot);
        image = findViewById(R.id.imageToRecord);
        Exit = findViewById(R.id.LiveExit);
    }

    @SuppressLint("SetTextI18n")
    public void OnClicks() {
        btnStart.setOnClickListener(view -> {
            if (Recording) {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE)) {
                    hasPermissions = true;
                    startRecordingScreen();
                    btnStart.setText("Stop");
                    Recording = false;
                } else
                    Toast.makeText(LivePhoto.this, "Please Allow Permissions.", Toast.LENGTH_SHORT).show();
            } else {
                hbRecorder.stopScreenRecording();
                btnStart.setText("Record");
                Recording = true;
            }
        });

        btnStop.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE)) {
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{hbRecorder.getFilePath()}, null, (path, uri) -> {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        });
                Toast.makeText(LivePhoto.this, "Saved!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(LivePhoto.this, "Please Allow Permissions.", Toast.LENGTH_SHORT).show();
        });

        Exit.setOnClickListener(view -> finish());
    }

    //region Screen Record Methods


    public void HBRecorderOnStart() {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    public void HBRecorderOnComplete() {
        Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();

        if (hbRecorder.wasUriSet())
            updateGalleryUri();
        else
            refreshGalleryFile();


    }

    public void HBRecorderOnError(int errorCode, String reason) {
        Toast.makeText(this, errorCode + ": " + reason, Toast.LENGTH_SHORT).show();
    }

    private void startRecordingScreen() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
        StartRecord.launch(permissionIntent);
    }

    ActivityResultLauncher<Intent> StartRecord = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        hbRecorder.startScreenRecording(result.getData(), result.getResultCode(), LivePhoto.this);
                    }
                }
            });

    //Check if permissions was granted
    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    @SuppressLint("InlinedApi")
    private void updateGalleryUri() {
        contentValues.clear();
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
        getContentResolver().update(mUri, contentValues, null, null);
    }

    private void refreshGalleryFile() {
        MediaScannerConnection.scanFile(this,
                new String[]{hbRecorder.getFilePath()}, null, (path, uri) -> {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                });
    }

    //endregion
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}