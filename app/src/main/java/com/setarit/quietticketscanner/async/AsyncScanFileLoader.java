package com.setarit.quietticketscanner.async;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.parse.FromJsonParser;
import com.setarit.quietticketscanner.domain.parse.VistorsToJsonParser;
import com.setarit.quietticketscanner.domain.pattern.Subject;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

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
    private String visitorsAsJsonString;

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
            visitorsAsJsonString = convertVisitorsToJson();
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
        preferences.visitorsJson().put(visitorsAsJsonString);
    }

    @SupposeBackground
    public ScanFile parseScanFile(String rawJson) {
        FromJsonParser parser = new FromJsonParser(rawJson);
        return parser.parse();
    }

    @NonNull
    @SupposeBackground
    public String loadFileToString(Uri uri) throws IOException {
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

    @SupposeBackground
    public String convertVisitorsToJson() {
        VistorsToJsonParser parser = new VistorsToJsonParser(loadingResult.getVisitors());
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
