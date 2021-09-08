package com.donisi.scannerforgsheets;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Switch;
import 	android.os.Handler;


import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;




public class QRDetector implements Detector.Processor {

    private String token;
    private String token_anterior;
    private String status_switch;
    private Switch switch_mode;
    private Context context;
    private Handler handler;

    QRDetector(Switch switch_mode, Context context) {
        this.token = "";
        this.token_anterior = "";
        this.switch_mode = switch_mode;
        this.status_switch = "";
        this.context = context;
        handler = new TestHandler(getContext());
    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() > 0) {
            setStatus_switch(switch_mode.isChecked()?",1":",0"); //Se revisa si esta en modo de entrada o salida
            setToken( barcodes.valueAt(0).displayValue);    // obtenemos el token
            if (!getToken().equals(getToken_anterior())) {
                setToken_anterior(getToken());
                Log.i("token", getToken()+getStatus_switch());

                //Preparación y envio de mensaje para notificación de usuario
                Message msg = new Message();
                msg.what = switch_mode.isChecked()?1:0;
                handler.sendMessage(msg);

                //Preparación para envio a google sheets
                SendRequest send_request = new SendRequest(getToken()+getStatus_switch(), getContext());
                send_request.execute(); //Hilo que realiza la petición y envio de información a google Sheets

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(4000);
                                // limpiamos el token
                                setToken_anterior("");
                                setStatus_switch(switch_mode.isChecked()?",1":",0");
                            }
                        } catch (InterruptedException e) {
                            /* TODO Auto-generated catch block */
                            Log.e("Error", "Waiting didnt work!!");
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    //----------------------------------Getters and Settes -----------------------

    private void setToken(String token) { this.token = token; }

    private String getToken() { return this.token; }

    private void setToken_anterior(String tokenanterior) { this.token_anterior = tokenanterior; }

    private String getToken_anterior(){return this.token_anterior;}

    private String getStatus_switch(){ return this.status_switch; }

    private void setStatus_switch(String switch_mode){ this.status_switch = switch_mode;  }

    private Context getContext(){ return this.context; }



    //---------------------------------Métodos de interfaz ----------------------------

    @Override
    public void release() { }
}
