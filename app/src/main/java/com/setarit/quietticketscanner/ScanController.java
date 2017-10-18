package com.setarit.quietticketscanner;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.setarit.quietticketscanner.permission.CameraPermission;
import com.setarit.quietticketscanner.permission.PermissionRequestable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

@EActivity(R.layout.activity_scan_controller)
@Fullscreen
public class ScanController extends FragmentActivity {
    private PermissionRequestable cameraPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyCameraPermission();
    }

    private void verifyCameraPermission() {
        cameraPermission = new CameraPermission(this);
        if(!cameraPermission.hasPermission()){
            cameraPermission.requestPermission();
        }
    }

    @AfterViews
    public void setScanButtonAsActive() {
        findViewById(R.id.scanButton).setBackgroundColor(ContextCompat.getColor(this, R.color.colorActiveButton));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){//camera permission
            processCameraPermissionResult(grantResults);
        }
    }

    private void processCameraPermissionResult(int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
            showCameraPermissionError();
        }
    }

    private void showCameraPermissionError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.insufficientCameraPermissions)
                .setTitle(R.string.camera);
        builder.setNeutralButton(R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                verifyCameraPermission();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
