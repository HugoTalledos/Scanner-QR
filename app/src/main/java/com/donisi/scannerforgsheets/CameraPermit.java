package com.donisi.scannerforgsheets;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.CameraSource;
import java.io.IOException;



public class CameraPermit implements SurfaceHolder.Callback {

    private CameraSource cameraSource;
    private Context context;
    private SurfaceView cameraView;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public CameraPermit(CameraSource cameraSource, Context context, SurfaceView cameraView){
        this.cameraSource = cameraSource;
        this.context = context;
        this.cameraView = cameraView;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Activity parse_context = (Activity) context;
            if (parse_context.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA));

                parse_context.requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            return;
        } else {
            try {
                cameraSource.start(cameraView.getHolder());
            } catch (IOException ie) {
                Log.e("CAMERA SOURCE", ie.getMessage());
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }
}
