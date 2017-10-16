package com.setarit.quietticketscanner.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Setarit on 16/10/2017.
 * Checks if the app has the permission to read from the storage
 */

public class ReadPermission implements PermissionRequestable {
    private Activity activity;

    public ReadPermission(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void requestPermission() {
        if(!hasPermission()){
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 2);
        }
    }

    @Override
    public boolean hasPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
