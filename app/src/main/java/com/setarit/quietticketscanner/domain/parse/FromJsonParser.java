package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.setarit.quietticketscanner.domain.ScanFile;
import com.setarit.quietticketscanner.domain.deserializer.ScanFileDeserializer;

/**
 * Created by Setarit on 13/10/2017.
 * Parses the JSON to a ScanFile object
 */

public class FromJsonParser {
    private final GsonBuilder gsonBuilder;

    public FromJsonParser(String rawJson) {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ScanFile.class, new ScanFileDeserializer());
    }

    public ScanFile parse() {
        return null;
    }
}
