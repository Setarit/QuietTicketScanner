package com.setarit.quietticketscanner.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Setarit on 13/10/2017..
 * Wrapper of the scan file contents
 */

public class ScanFile implements Serializable{
    @Expose
    private Event event;
    @Expose
    private Collection<Seat> seats;

    public ScanFile(Event event, Collection<Seat> seats) {
        this.event = event;
        this.seats = seats;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Collection<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<Seat> seats) {
        this.seats = seats;
    }
}
