package com.setarit.quietticketscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setarit.quietticketscanner.async.AsyncScanFileLoader;
import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.pattern.Observer;
import com.setarit.quietticketscanner.fragments.DataFragment;
import com.setarit.quietticketscanner.fragments.loader.DataFragmentLoader;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_scan_file_loader_controller)
@Fullscreen
public class ScanFileLoaderController extends FragmentActivity implements Observer {
    private static final int OPEN_SCAN_FILE = 1;

    @ViewById(R.id.fileLoaderContainer)
    RelativeLayout container;
    @ViewById
    TextView eventName;
    @ViewById
    ImageView backgroundImage;

    @Pref
    Preferences_ preferences;

    private Snackbar loadingSnackbar;
    private AsyncScanFileLoader scanFileLoader;
    private DataFragment dataFragment;

    public ScanFileLoaderController() {
        scanFileLoader = new AsyncScanFileLoader(this);
        scanFileLoader.addObserver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataFragment();
    }

    private void loadDataFragment() {
        DataFragmentLoader loader = new DataFragmentLoader(getFragmentManager());
        dataFragment = loader.loadDataFragment();
    }

    @AfterViews
    public void setOpenJsonButtonAsActive(){
        findViewById(R.id.openJsonButton).setBackgroundColor(ContextCompat.getColor(this, R.color.colorActiveButton));
        if(preferences.eventName().exists()){
            displayEventName(preferences.eventName().get());
        }
        if(preferences.imageBase64().exists()){
            loadEventBackground();
        }
    }

    private void displayEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    @Click(R.id.findJsonScanFile)
    public void startOpenDocumentIntent(){
        if(preferences.hasScanned().get()){
            showWarningDialog();
        }else {
            openFileSelectorActivity();
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.warning_overriding_previous_scans)
            .setPositiveButton(R.string.yes_override_previous_scans, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    openFileSelectorActivity();
                }
            })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        builder.create().show();
    }

    private void openFileSelectorActivity() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        startActivityForResult(intent, OPEN_SCAN_FILE);
    }

    @OnActivityResult(ScanFileLoaderController.OPEN_SCAN_FILE)
    public void fileSelected(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            showLoadingSnackbar();
            scanFileLoader.setPreferences(preferences);
            scanFileLoader.loadJson(data.getData());
        }
    }

    private void showLoadingSnackbar() {
        loadingSnackbar = Snackbar.make(container, R.string.loading, Snackbar.LENGTH_INDEFINITE);
        loadingSnackbar.show();
    }

    @UiThread
    public void hideLoadingSnackbar(){
        if(loadingSnackbar != null){
            loadingSnackbar.dismiss();
        }
    }

    @UiThread
    public void changeLoadingBarTextToFailure() {
        loadingSnackbar.setText(R.string.loadingFailed);
        loadingSnackbar.setDuration(Snackbar.LENGTH_SHORT);
    }

    @UiThread
    public void displayEventNameAndHideSnackbar() {
        hideLoadingSnackbar();
        displayEventName(preferences.eventName().get());
    }

    @Override
    @UiThread
    public void update() {
        if(this.scanFileLoader.getLoadingResult() == null){
            changeLoadingBarTextToFailure();
        }else{
            dataFragment.setScanFile(scanFileLoader.getLoadingResult());
            displayEventNameAndHideSnackbar();
            loadEventBackground();
        }
    }

    @Background
    public void loadEventBackground(){
        String rawImage = preferences.imageBase64().get();
        String data = rawImage.substring(rawImage.indexOf(",")+1);
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        dataFragment.setEventBackground(decodedByte);
        showEventBackgroundImage();
    }

    @UiThread
    public void showEventBackgroundImage() {
        if(dataFragment.getEventBackground() != null) {
            backgroundImage.setImageBitmap(dataFragment.getEventBackground());
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
