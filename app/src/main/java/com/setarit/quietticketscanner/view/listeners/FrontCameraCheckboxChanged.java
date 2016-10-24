package com.setarit.quietticketscanner.view.listeners;

import android.widget.CompoundButton;

import com.setarit.quietticketscanner.ScanController;

/**
 * Created by Setarit on 17/10/2016.
 */
public class FrontCameraCheckboxChanged implements CompoundButton.OnCheckedChangeListener {
    private final ScanController controller;

    public FrontCameraCheckboxChanged(ScanController scanController) {
        this.controller = scanController;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            controller.useFrontCamera();
        }else{
            controller.useBackCamera();
        }
    }
}