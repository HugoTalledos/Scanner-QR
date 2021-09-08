package com.donisi.scannerforgsheets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Switch;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity{

    private SurfaceView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.camera_view);
        initQR(getApplicationContext());
    }

    private void initQR(Context context) {
        // creo el detector qr
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE).build();

        // creo la camara
        CameraSource cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .build();

        // Permisos para hacer uso de la camara
        CameraPermit permissions = new CameraPermit(cameraSource,context,cameraView);
        cameraView.getHolder().addCallback(permissions);

        // Preparo el detector de QR

        Switch switch_mode = findViewById(R.id.switch_mode);
        QRDetector qrDetector = new QRDetector(switch_mode, this);
        barcodeDetector.setProcessor(qrDetector);
    }
}
