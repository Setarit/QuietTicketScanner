package com.setarit.quietticketscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScanController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_controller);
        setTitle(R.string.scan);
    }
}
