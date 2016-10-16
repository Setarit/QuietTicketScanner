package com.setarit.quietticketscanner;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class ScanController extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView mydecoderview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_controller);
        //init QR reading
        initQRReading();
    }

    private void initQRReading() {
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        if(mydecoderview != null) {
            mydecoderview.setOnQRCodeReadListener(this);
            mydecoderview.setQRDecodingEnabled(true);// enable decoding
            mydecoderview.setBackCamera();//use back camera

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {

    }
}
