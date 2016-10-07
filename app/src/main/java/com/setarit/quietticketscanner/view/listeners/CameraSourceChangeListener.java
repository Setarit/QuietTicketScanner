package com.setarit.quietticketscanner.view.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.setarit.quietticketscanner.ScanController;

/**
 * Created by Setarit on 07/10/2016.
 */
public class CameraSourceChangeListener implements AdapterView.OnItemClickListener {
    private final ScanController scanController;
    private final ArrayAdapter<String> adapter;

    public CameraSourceChangeListener(ScanController scanController, ArrayAdapter<String> adapter) {
        this.scanController = scanController;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String newCameraId = adapter.getItem(position);
    }
}
