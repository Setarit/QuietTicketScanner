package com.setarit.quietticketscanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.setarit.quietticketscanner.async.AsyncServerConnectionChecker;
import com.setarit.quietticketscanner.permission.CameraPermission;
import com.setarit.quietticketscanner.permission.PermissionRequestable;

public class LoadingController extends AppCompatActivity {

    private PermissionRequestable requestable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_controller);
        setTitle(R.string.title_activity_loading_controller);
        verifyServerConnection();
        requestable = new CameraPermission(this);
    }

    private void verifyServerConnection() {
        AsyncServerConnectionChecker serverConnectionChecker = new AsyncServerConnectionChecker(this);
        serverConnectionChecker.execute();
    }

    public void showNoNetworkConnectionDialog() {
        showErrorDialog(R.string.network, R.string.cannotConnectToServer);
    }

    private void showErrorDialog(int titleId, int messageId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadingCompleted(){
        if(requestable.hasPermission()){
            toScanningCodeActivity();
        }else {
            requestable.requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){//camera permission
            processCameraPermissionResult(grantResults);
        }
    }

    private void processCameraPermissionResult(int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            toScanningCodeActivity();
        }else{
            // insufficient permissions
            showErrorDialog(R.string.insufficientPermissions, R.string.insufficientCameraPermissions);
        }
    }

    public void toScanningCodeActivity() {
        Intent intent = new Intent();
        startActivity(new Intent(this, ScanFileLoaderController_.class));
        finish();
    }
}
