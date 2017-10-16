package com.setarit.quietticketscanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.setarit.quietticketscanner.async.AsyncScanFileLoader;
import com.setarit.quietticketscanner.domain.pattern.Observer;
import com.setarit.quietticketscanner.fragments.DataFragment;
import com.setarit.quietticketscanner.fragments.loader.DataFragmentLoader;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class LoadingController extends AppCompatActivity implements Observer{

    private AsyncScanFileLoader scanFileLoader;
    private DataFragment dataFragment;

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
        loadDataFragment();
        loadPreviousScanFile();
    }

    private void loadDataFragment() {
        DataFragmentLoader loader = new DataFragmentLoader(getFragmentManager());
        dataFragment = loader.loadDataFragment();
    }

    @Background
    public void loadPreviousScanFile() {
        if(preferences.scanFileLocation().exists()){
            scanFileLoader.setPreferences(preferences);
            scanFileLoader.loadJson(Uri.parse(preferences.scanFileLocation().get()));
        }else {
            toScanFileLoaderController();
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

    public void toScanFileLoaderController() {
        Intent intent = new Intent(this, ScanFileLoaderController_.class);
        startActivity(intent);
        finish();
    }

    /**
     * Called when the scan file loading is completed
     */
    @Override
    @UiThread
    public void update() {
        if(scanFileLoader.getLoadingResult() != null){
            dataFragment.setScanFile(scanFileLoader.getLoadingResult());
        }
        toScanFileLoaderController();
    }
}
