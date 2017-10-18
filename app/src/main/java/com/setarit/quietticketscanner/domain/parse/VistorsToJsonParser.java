package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.setarit.quietticketscanner.domain.Visitor;

import java.util.Collection;
import java.util.List;

/**
 * Created by Setarit on 18/10/2017.
 * Converts the visitors to a JSON string
 */

public class VistorsToJsonParser {
    private final Gson gson;
    private Collection<Visitor> visitors;

    public VistorsToJsonParser(Collection<Visitor> visitors) {
        this.visitors = visitors;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Converts the list of visitors to Json
     * @return The Visitor JSON as string
     */
    public String convertToJson(){
        return gson.toJson(visitors);
    }
}
