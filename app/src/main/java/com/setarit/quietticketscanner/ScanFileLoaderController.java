package com.setarit.quietticketscanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.parse.FromJsonParser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@EActivity(R.layout.activity_scan_file_loader_controller)
@Fullscreen
public class ScanFileLoaderController extends FragmentActivity {
    private static final int OPEN_SCAN_FILE = 1;

    @ViewById(R.id.fileLoaderContainer)
    RelativeLayout container;
    private Snackbar loadingSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void setOpenJsonButtonAsActive(){
        findViewById(R.id.openJsonButton).setBackgroundColor(ContextCompat.getColor(this, R.color.colorActiveButton));
    }

    @Click(R.id.findJsonScanFile)
    public void startOpenDocumentIntent(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        startActivityForResult(intent, OPEN_SCAN_FILE);
    }

    @OnActivityResult(ScanFileLoaderController.OPEN_SCAN_FILE)
    public void fileSelected(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            showLoadingSnackbar();
            loadJson(data.getData());
        }
    }

    private void showLoadingSnackbar() {
        loadingSnackbar = Snackbar.make(container, R.string.loading, Snackbar.LENGTH_INDEFINITE);
        loadingSnackbar.show();
    }

    @Background
    public void loadJson(Uri uri) {
        try {
            String rawJson = loadFileToString(uri);
            ScanFile scanFile = parseScanFile(rawJson);
            Log.i("JSON>>>>>>>>", rawJson);
        } catch (FileNotFoundException e) {
            Log.i("INFO", uri.toString());
            Log.e("ERROR", e.getMessage(), e);
            changeLoadingBarTextToFailure();
        } catch (IOException e) {
            e.printStackTrace();
            changeLoadingBarTextToFailure();
        }
    }

    private ScanFile parseScanFile(String rawJson) {
        FromJsonParser parser = new FromJsonParser(rawJson);
        return parser.parse();
    }

    @NonNull
    private String loadFileToString(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null){
            stringBuilder.append(line);
        }
        inputStream.close();
        reader.close();
        return stringBuilder.toString();
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
}
