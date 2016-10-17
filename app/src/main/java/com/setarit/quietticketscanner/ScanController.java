package com.setarit.quietticketscanner;

import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.setarit.quietticketscanner.network.verification.TicketVerificator;
import com.setarit.quietticketscanner.view.listeners.FlashCheckboxChanged;
import com.setarit.quietticketscanner.view.listeners.FrontCameraCheckboxChanged;

public class ScanController extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView mydecoderview;
    private TicketVerificator ticketVerificator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_controller);
        ticketVerificator = new TicketVerificator(this);
        initCheckboxes();
        //init QR reading
        initQRReading();
        setTitle(R.string.scan);
    }

    private void initCheckboxes() {
        CheckBox flash = (CheckBox) findViewById(R.id.use_flashlight);
        //CheckBox front = (CheckBox) findViewById(R.id.use_front_camera);
        if(flash != null && getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            flash.setVisibility(View.VISIBLE);
            flash.setOnCheckedChangeListener(new FlashCheckboxChanged(this));
        }
        /*if(front != null && getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
            front.setVisibility(View.VISIBLE);
            front.setOnCheckedChangeListener(new FrontCameraCheckboxChanged(this));
        }*/
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
        if (text.contains("quietticket.com")){
            vibrate();
            ticketVerificator.validate(text.substring(text.lastIndexOf("/")+1));
            mydecoderview.setQRDecodingEnabled(false);
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(400);
    }

    public void useFrontCamera() {
        mydecoderview.stopCamera();
        mydecoderview.setFrontCamera();
        mydecoderview.startCamera();
    }

    public void useBackCamera() {
        mydecoderview.stopCamera();
        mydecoderview.setBackCamera();
        mydecoderview.startCamera();
    }

    public void useFlash() {
        mydecoderview.setTorchEnabled(true);
    }

    public void disableFlash() {
        mydecoderview.setTorchEnabled(false);
    }
}
