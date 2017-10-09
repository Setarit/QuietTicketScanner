package com.setarit.quietticketscanner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

@EActivity(R.layout.activity_scan_file_loader_controller)
@Fullscreen
public class ScanFileLoaderController extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
