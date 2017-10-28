package com.setarit.quietticketscanner;

import android.content.Intent;
import android.net.Uri;
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
            writeFile(data.getData());
        }
    }

    private void writeFile(Uri uri) {
        File file = new File(uri.getPath());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.write(preferences.seatsJson().get());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showSavingSnackbar() {
        loadingSnackbar = Snackbar.make(saveJsonContainer, R.string.saving, Snackbar.LENGTH_LONG);
        loadingSnackbar.show();
    }
}
