package com.example.pictake_preview;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity{

//    @Override
//    public void onCameraOpened(CameraDevice cameraDevice, String cameraId, Size previewSize, int displayOrientation, boolean isMirror) {
//
//    }
//
//    @Override
//    public void onCameraClosed() {
//
//    }
//
//    @Override
//    public void onCameraError(Exception e) {
//
//    }
//
//    @Override
//    public void onPreview(byte[] y, byte[] u, byte[] v, Size previewSize, int stride) {
//
//    }
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 88888;
    private TextureView textureView;
    private Camera2Helper camera2Helper;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        checkPermission();
        textureView = findViewById(R.id.texture_preview);
        context = getApplicationContext();
        setUpCamera2Helper();
    }

    @Override
    protected void onResume() {
        super.onResume();

        camera2Helper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera2Helper.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera2Helper.release();
    }

    private void setUpCamera2Helper(){
        Camera2Listener camera2Listener = new Camera2Listener() {
            @Override
            public void onCameraOpened(CameraDevice cameraDevice, String cameraId, Size previewSize, int displayOrientation, boolean isMirror) {

            }

            @Override
            public void onPreview(byte[] y, byte[] u, byte[] v, Size previewSize, int stride) {

            }

            @Override
            public void onCameraClosed() {

            }

            @Override
            public void onCameraError(Exception e) {

            }
        };
        Camera2Helper.Builder mBuilder = new Camera2Helper.Builder().previewOn(textureView)
                .isMirror(false)
                .previewViewSize(new Point(960,720))
                .specificCameraId(Camera2Helper.CAMERA_ID_BACK)
                .previewSize(new Point(960,720))
                .maxPreviewSize(new Point(1440,1080))
                .minPreviewSize(new Point(640,480))
                .context(context)
                .cameraListener(camera2Listener);

        camera2Helper = mBuilder.build();
    }



    private void checkPermission() {

        // request camera permission if it has not been grunted.
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
//            Log.d("checkPermission: false");
        }

//        Log.d("checkPermission: true");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "camera permission has been grunted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "[WARN] camera permission is not grunted.", Toast.LENGTH_SHORT).show();
            }
        }
    }





}
