package com.setarit.quietticketscanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

import java.io.FileOutputStream;
import java.io.IOException;

@EActivity(R.layout.activity_save_file_controller)
@Fullscreen
public class SaveFileController extends FragmentActivity {
    @ViewById(R.id.saveJsonContainer)
    public FrameLayout saveJsonContainer;
    @ViewById
    ImageView backgroundImage;
    @Pref
    public Preferences_ preferences;
    private Snackbar loadingSnackbar;

    private static final int SAVE_DIALOG = 2;

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
        startActivityForResult(intent, SaveFileController.SAVE_DIALOG);
    }

    @OnActivityResult(SaveFileController.SAVE_DIALOG)
    public void outputFileSelected(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
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
        String json = preferences.seatsJson().get();
        fileOutputStream.write(json.getBytes());
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

    @AfterViews
    public void loadbackgroundImage(){
        if(preferences.imageBase64().exists()){
            loadEventBackground();
        }
    }

    @AfterViews
    public void updateActiveController(){
        findViewById(R.id.saveJsonButton).setBackgroundColor(ContextCompat.getColor(this, R.color.colorActiveButton));
    }

    @Background
    public void loadEventBackground(){
        String rawImage = preferences.imageBase64().get();
        String data = rawImage.substring(rawImage.indexOf(",")+1);
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        showEventBackgroundImage(decodedByte);
    }

    @UiThread
    public void showEventBackgroundImage(Bitmap decodedByte) {
        if(decodedByte != null) {
            backgroundImage.setImageBitmap(decodedByte);
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
