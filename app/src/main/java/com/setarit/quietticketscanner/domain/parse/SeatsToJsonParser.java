package com.setarit.quietticketscanner.domain.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.setarit.quietticketscanner.domain.Seat;

import java.util.Collection;

/**
 * Created by Setarit on 26/10/2017.
 * Parses the seats to json
 */
public class SeatsToJsonParser {
    private final Gson gson;
    private Collection<Seat> seats;

    public SeatsToJsonParser(Collection<Seat> seats) {
        this.seats = seats;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Converts the list of seats to Json
     * @return The Seats JSON as string
     */
    public String convertToJson(){
        return gson.toJson(seats);
    }
}
