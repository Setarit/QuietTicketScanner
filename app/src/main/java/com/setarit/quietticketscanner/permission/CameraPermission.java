package com.setarit.quietticketscanner.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Setarit on 17/10/2016.
 * Checks if the Camera is allowed in the app
 */

public class CameraPermission implements PermissionRequestable{
    private Activity activity;

    public CameraPermission(Activity activity){
        this.activity = activity;
    }

    @Override
    public void requestPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            //if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            //} else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, 1);
            //}
        }
    }

    @Override
    public boolean hasPermission() {
        return ContextCompat.checkSelfPermission(activity,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
