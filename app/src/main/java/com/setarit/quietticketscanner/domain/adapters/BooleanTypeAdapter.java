package com.setarit.quietticketscanner.domain.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Setarit on 15/10/2017.
 * Converts the int representations of the scan file to booleans
 */

public class BooleanTypeAdapter implements JsonDeserializer<Boolean>{
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsInt();
        return value != 0;
    }
}
