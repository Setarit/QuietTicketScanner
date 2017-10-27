package com.setarit.quietticketscanner.async;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.parse.FromJsonParser;
import com.setarit.quietticketscanner.domain.parse.SeatsToJsonParser;
import com.setarit.quietticketscanner.domain.pattern.Subject;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Setarit on 16/10/2017.
 * Loads the scanning file in the background
 */

@EBean
public class AsyncScanFileLoader extends Subject {

    private Preferences_ preferences;

    private Context context;
    private ScanFile loadingResult;
    private String seatsAsJsonString;

    public AsyncScanFileLoader(Context context) {
        this.context = context;
    }

    public void setPreferences(Preferences_ preferences) {
        this.preferences = preferences;
    }

    @Background
    public void loadJson(Uri uri) {
        try {
            String rawJson = loadFileToString(uri);
            loadingResult = parseScanFile(rawJson);
            seatsAsJsonString = convertSeatsToJson();
            savePreferences(uri);
            this.notifyObservers();
        } catch (IOException e) {
            this.notifyObservers();
        }
    }



    @SupposeBackground
    public void savePreferences(Uri uri) {
        preferences.scanFileLocation().put(uri.toString());
        preferences.eventName().put(loadingResult.getEvent().getName());
        preferences.imageBase64().put(loadingResult.getEvent().getImage());
        preferences.eventId().put(loadingResult.getEvent().getId());
        preferences.seatsJson().put(seatsAsJsonString);
        preferences.hasScanned().put(false);
    }

    @SupposeBackground
    public ScanFile parseScanFile(String rawJson) {
        FromJsonParser parser = new FromJsonParser(rawJson);
        return parser.parse();
    }

    @NonNull
    @SupposeBackground
    public String loadFileToString(Uri uri) throws IOException {
        requestFilePermissions(uri);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
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

    /**
     * Reloads the permissions to open the file
     * @param uri The uri to the file
     */
    @TargetApi(19)
    private void requestFilePermissions(Uri uri) {
        context.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    @SupposeBackground
    public String convertSeatsToJson() {
        SeatsToJsonParser parser = new SeatsToJsonParser(loadingResult.getSeats());
        return parser.convertToJson();
    }

    /**
     * Returns the resulting ScanFile object after loading
     * @return ScanFile The result or null if the loading failed
     */
    public ScanFile getLoadingResult() {
        return loadingResult;
    }
}
