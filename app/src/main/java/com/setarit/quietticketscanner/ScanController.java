package com.setarit.quietticketscanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.setarit.quietticketscanner.camera.Camera;
import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.permission.CameraPermission;
import com.setarit.quietticketscanner.permission.PermissionRequestable;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

@EActivity(R.layout.activity_scan_controller)
@Fullscreen
public class ScanController extends FragmentActivity implements ZXingScannerView.ResultHandler {
    private PermissionRequestable cameraPermission;
    private int currentCameraId = -1;
    private boolean useFlash;
    private boolean hasFlash;

    @Pref
    public Preferences_ preferences;

    private Camera camera;

    @ViewById
    public RelativeLayout scanContainer;
    private ZXingScannerView scanningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadValidators();
        verifyCameraPermission();
        this.camera = new Camera((CameraManager) getSystemService(Context.CAMERA_SERVICE));
        hasFlash = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Background
    public void loadValidators() {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        useFlash = savedInstanceState.getBoolean("flash");
        currentCameraId = savedInstanceState.getInt("camera");
        super.onRestoreInstanceState(savedInstanceState);
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

    @AfterViews
    public void loadScanningView(){
        scanningView = new ZXingScannerView(this);
        setUpFormats();
        scanningView.startCamera(currentCameraId);
        scanningView.setAutoFocus(true);
        scanContainer.addView(scanningView);
    }

    private void setUpFormats() {
        List<BarcodeFormat> formats = new ArrayList<>(2);
        formats.add(BarcodeFormat.CODE_93);
        formats.add(BarcodeFormat.QR_CODE);
        scanningView.setFormats(formats);
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

    @Override
    protected void onResume() {
        super.onResume();
        scanningView.startCamera();
        scanningView.setResultHandler(this);
        if(hasFlash){
            scanningView.setFlash(useFlash);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanningView.stopCamera();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("flash", useFlash);
        outState.putInt("camera", currentCameraId);
    }

    @Override
    public void handleResult(Result result) {
        if(result.getText().startsWith("SQT-")){
            vibrate();
            checkCode(result.getText().substring(4).replaceAll("-",""));
        }
        scanningView.resumeCameraPreview(this);
    }

    /**
     * Checks the code and starts the appropriate activity to show the result
     * @param code The code to verify
     */
    private void checkCode(String code) {
       //TODO
    }

    @Background
    public void updateCurrentScanStatusToPreferences(List<Visitor> updatedVisitors, boolean hasScanned) {

        preferences.hasScanned().put(hasScanned);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibrator.vibrate(500);
        }

    }

    //Todo: sound

    @Click(R.id.openKeyboard)
    public void openCodeActivity(){
        Intent intent = new Intent(this, CodeController_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
