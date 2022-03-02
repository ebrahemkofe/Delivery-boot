package com.graduation.deliveryboot.ui;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.graduation.deliveryboot.R;
import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LivePhoto extends AppCompatActivity implements HBRecorderListener{


    private static final int SCREEN_RECORD_REQUEST_CODE = 100;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 101;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = 102;
    Button btnStart, btnStop;
    ImageView image;
    HBRecorder hbRecorder;
    ContentValues contentValues;
    ContentResolver resolver;
    Uri mUri;
    boolean hasPermissions;
    boolean Recording=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_photo);
        FindViewsByIDs();
        hbRecorder = new HBRecorder(this,this);
        hbRecorder.setVideoEncoder("H264");

        OnClicks();
    }

    public void FindViewsByIDs(){
        btnStart = findViewById(R.id.Record);
        btnStop = findViewById(R.id.ScreenShot);
        image =findViewById(R.id.imageToRecord);
    }

    public void OnClicks(){
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Recording) {
                    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE)) {
                        hasPermissions = true;
                        startRecordingScreen();
                        btnStart.setText("Stop");
                        Recording=false;
                    } else
                        Toast.makeText(LivePhoto.this, "Please Allow Permissions.", Toast.LENGTH_SHORT).show();
                }
                else{
                    hbRecorder.stopScreenRecording();
                    btnStart.setText("Record");
                    Recording=true;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE)) {
                    Drawable drawable = image.getDrawable();
                    // Get the bitmap from drawable object
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // Save image to gallery
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Bird", "Image of bird");
                    Toast.makeText(LivePhoto.this, "Saved!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(LivePhoto.this, "Please Allow Permissions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //region Screen Record Methods


        public void HBRecorderOnStart () {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

        public void HBRecorderOnComplete () {
        Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();

        if (hbRecorder.wasUriSet())
            updateGalleryUri();
        else
            refreshGalleryFile();


    }

        public void HBRecorderOnError ( int errorCode, String reason){
        Toast.makeText(this, errorCode + ": " + reason, Toast.LENGTH_SHORT).show();
    }

        private void startRecordingScreen () {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE);
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Start screen recording
                hbRecorder.startScreenRecording(data, resultCode, this);

            }
        }
    }
        //For Android 10> we will pass a Uri to HBRecorder
        //This is not necessary - You can still use getExternalStoragePublicDirectory
        //But then you will have to add android:requestLegacyExternalStorage="true" in your Manifest
        //IT IS IMPORTANT TO SET THE FILE NAME THE SAME AS THE NAME YOU USE FOR TITLE AND DISPLAY_NAME

        private void setOutputPath () {
        String filename = generateFileName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver = getContentResolver();
            contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "SpeedTest/" + "SpeedTest");
            contentValues.put(MediaStore.Video.Media.TITLE, filename);
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
            mUri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            //FILE NAME SHOULD BE THE SAME
            hbRecorder.setFileName(filename);
            hbRecorder.setOutputUri(mUri);
        } else {
            createFolder();
            hbRecorder.setOutputPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/HBRecorder");
        }
    }

        //Check if permissions was granted
        private boolean checkSelfPermission (String permission,int requestCode){
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

        private void updateGalleryUri () {
        contentValues.clear();
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
        getContentResolver().update(mUri, contentValues, null, null);
    }

        private void refreshGalleryFile () {
        MediaScannerConnection.scanFile(this,
                new String[]{hbRecorder.getFilePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

        //Generate a timestamp to be used as a file name
        private String generateFileName () {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate).replace(" ", "");
    }

        //drawable to byte[]
        private byte[] drawable2ByteArray ( @DrawableRes int drawableId){
        Bitmap icon = BitmapFactory.decodeResource(getResources(), drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

        //Create Folder
        //Only call this on Android 9 and lower (getExternalStoragePublicDirectory is deprecated)
        //This can still be used on Android 10> but you will have to add android:requestLegacyExternalStorage="true" in your Manifest
        private void createFolder () {
        File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SpeedTest");
        if (!f1.exists()) {
            if (f1.mkdirs()) {
                Log.i("Folder ", "created");
            }
        }
    }

//endregion
@Override
public void onBackPressed(){
    super.onBackPressed();
    this.finish();
}
}