package com.setarit.quietticketscanner.domain.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.setarit.quietticketscanner.domain.ScanFile;

import java.lang.reflect.Type;

/**
 * Created by Setarit on 13/10/2017.
 * Deserializes the scan file json
 */

public class ScanFileDeserializer implements JsonDeserializer<ScanFile> {
    @Override
    public ScanFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
