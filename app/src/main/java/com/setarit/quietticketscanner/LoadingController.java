package com.setarit.quietticketscanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.setarit.quietticketscanner.async.AsyncScanFileLoader;
import com.setarit.quietticketscanner.async.AsyncServerConnectionChecker;
import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.pattern.Observer;
import com.setarit.quietticketscanner.permission.CameraPermission;
import com.setarit.quietticketscanner.permission.PermissionRequestable;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class LoadingController extends AppCompatActivity implements Observer{

    private PermissionRequestable requestable;
    private AsyncScanFileLoader scanFileLoader;

    @Pref
    Preferences_ preferences;

    public LoadingController(){
        scanFileLoader = new AsyncScanFileLoader(this);
        scanFileLoader.addObserver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_controller);
        setTitle(R.string.title_activity_loading_controller);
        requestable = new CameraPermission(this);
        loadPreviousScanFile();
    }

    @Background
    public void loadPreviousScanFile() {
        if(preferences.scanFileLocation().exists()){
            scanFileLoader.setPreferences(preferences);
            scanFileLoader.loadJson(Uri.parse(preferences.scanFileLocation().get()));
        }else {
            loadingCompleted();
        }
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

    @UiThread
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
        Intent intent = new Intent(this, ScanFileLoaderController_.class);
        if(scanFileLoader.getLoadingResult() != null){
            intent.putExtra("scanFile", scanFileLoader.getLoadingResult());
        }
        startActivity(intent);
        finish();
    }


    /**
     * Called when the scan file loading is completed
     */
    @Override
    @UiThread
    public void update() {
        loadingCompleted();
    }
}
