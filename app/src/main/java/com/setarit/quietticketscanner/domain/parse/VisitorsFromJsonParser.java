package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.setarit.quietticketscanner.domain.Visitor;

import java.util.Collection;
import java.util.List;

/**
 * Created by Setarit on 22/10/2017.
 * Parses a collection of visitors to native Vistors
 */

public class VisitorsFromJsonParser {
    private final String visitors;
    private final Gson gson;

    public VisitorsFromJsonParser(String visitors) {
        this.visitors = visitors;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Converts the visitors to a List of visitors
     * @return The list of native visitors
     */
    public List<Visitor> convertFromJson(){
        return gson.fromJson(visitors, new TypeToken<List<Visitor>>(){}.getType());
    }
}
