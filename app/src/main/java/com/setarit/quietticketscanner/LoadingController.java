package com.setarit.quietticketscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

@EActivity
public class LoadingController extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_controller);
        setTitle(R.string.title_activity_loading_controller);
        toScanFileLoaderController();
    }

    public void toScanFileLoaderController() {
        Intent intent = new Intent(this, ScanFileLoaderController_.class);
        startActivity(intent);
        finish();
    }
}
