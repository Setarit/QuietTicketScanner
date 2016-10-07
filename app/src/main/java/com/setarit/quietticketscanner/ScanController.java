package com.setarit.quietticketscanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.camera2.*;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.setarit.quietticketscanner.view.listeners.CameraSourceChangeListener;

public class ScanController extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_controller);
        setTitle(R.string.scan);

        try {
            getAllCameras();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void getAllCameras() throws CameraAccessException {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIds = manager.getCameraIdList();
        Spinner cameraList = (Spinner) findViewById(R.id.camera_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cameraIds);
        if(cameraList != null) {
            cameraList.setAdapter(adapter);
        }
        cameraList.setOnItemClickListener(new CameraSourceChangeListener(this, adapter));
        return ;
    }
}
