package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;

import java.util.List;

/**
 * Created by Setarit on 22/10/2017.
 * Parses a collection of seats to native Vistors
 */

public class SeatsFromJsonParser {
    private final String seats;
    private final Gson gson;

    public SeatsFromJsonParser(String seats) {
        this.seats = seats;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Converts the seats to a List of seats
     * @return The list of native seats
     */
    public List<Seat> convertFromJson(){
        return gson.fromJson(seats, new TypeToken<List<Seat>>(){}.getType());
    }
}
