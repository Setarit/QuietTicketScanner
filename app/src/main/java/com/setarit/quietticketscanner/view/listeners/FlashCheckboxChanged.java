package com.setarit.quietticketscanner.view.listeners;

import android.widget.CompoundButton;

import com.setarit.quietticketscanner.ScanController;

/**
 * Created by Setarit on 17/10/2016.
 */
public class FlashCheckboxChanged implements CompoundButton.OnCheckedChangeListener {
    private final ScanController scanController;

    public FlashCheckboxChanged(ScanController scanController) {
        this.scanController = scanController;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            scanController.useFlash();
        }else{
            scanController.disableFlash();
        }
    }
}
