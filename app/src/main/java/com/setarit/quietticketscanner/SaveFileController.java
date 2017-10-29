package com.setarit.quietticketscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

@EActivity(R.layout.activity_save_file_controller)
@Fullscreen
public class SaveFileController extends AppCompatActivity {
    @ViewById(R.id.saveJsonContainer)
    public RelativeLayout saveJsonContainer;
    @Pref
    public Preferences_ preferences;
    private Snackbar loadingSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click
    public void openFileSave(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        intent.putExtra(Intent.EXTRA_TITLE, preferences.eventName().get()+"_"+preferences.eventId().get());
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK){
            showSavingSnackbar();
            try {
                writeFile(data.getData());
            } catch (IOException e) {
                showSavingFailedSnackbar();
                e.printStackTrace();
            }
        }
    }

    private void writeFile(Uri uri) throws IOException {
        ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
        FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
        fileOutputStream.write(preferences.seatsJson().get().getBytes());
        fileOutputStream.close();
        pfd.close();
    }

    private void showSavingFailedSnackbar() {
        if(loadingSnackbar != null) loadingSnackbar.dismiss();
        loadingSnackbar = Snackbar.make(saveJsonContainer, R.string.saving_failed, Snackbar.LENGTH_SHORT);
        loadingSnackbar.show();
    }

    private void showSavingSnackbar() {
        loadingSnackbar = Snackbar.make(saveJsonContainer, R.string.saving, Snackbar.LENGTH_LONG);
        loadingSnackbar.show();
    }
}