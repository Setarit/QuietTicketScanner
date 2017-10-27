package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;

import java.util.Collection;

/**
 * Created by Setarit on 27/10/2017.
 * Parses the seats to json
 */

public class ToJsonParser {
    private final Gson gson;
    private Collection<Seat> seats;

    public ToJsonParser(Collection<Seat> seats) {
        this.seats = seats;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Converts the list of visitors to Json
     * @return The Visitor JSON as string
     */
    public String convertToJson(){
        return gson.toJson(seats);
    }
}
