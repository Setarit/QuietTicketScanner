package com.setarit.quietticketscanner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

@Fullscreen
@EActivity(R.layout.activity_visitor_scan_result_controller)
public class VisitorScanResultController extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void showResult(){

    }
}
