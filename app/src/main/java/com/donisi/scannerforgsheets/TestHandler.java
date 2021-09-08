package com.donisi.scannerforgsheets;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import 	android.os.Handler;

public class TestHandler extends Handler {

    private Context context;

    TestHandler(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                Toast.makeText(getContext(), "Producto RETIRADO con éxito", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getContext(), "Producto REGISTRADO con éxito!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private Context getContext() {
        return this.context;
    }

}