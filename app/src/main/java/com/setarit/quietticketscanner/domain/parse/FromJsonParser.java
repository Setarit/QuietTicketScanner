package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.adapters.BooleanTypeAdapter;

/**
 * Created by Setarit on 13/10/2017.
 * Parses the JSON to a ScanFile object
 */

public class FromJsonParser {
    private final GsonBuilder gsonBuilder;
    private final Gson gson;
    private final String rawJson;

    public FromJsonParser(String rawJson) {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        gsonBuilder.registerTypeAdapter(boolean.class, new BooleanTypeAdapter());
        gson = gsonBuilder.create();
        this.rawJson = rawJson;
    }

    public ScanFile parse() {
        return gson.fromJson(rawJson, ScanFile.class);
    }
}
