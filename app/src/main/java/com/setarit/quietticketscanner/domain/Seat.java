package com.setarit.quietticketscanner.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Setarit on 13/10/2017.
 * Contains the info of the seat
 */

public class Seat implements Serializable {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private boolean scanned;
    @Expose
    private String priceName;

    public Seat(String id, String name, boolean scanned, String priceName) {
        this.id = id;
        this.name = name;
        this.scanned = scanned;
        this.priceName = priceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof Seat){
            Seat seat = (Seat) obj;
            equal = seat.getId().equals(this.getId());
        }
        return equal;
    }
}
