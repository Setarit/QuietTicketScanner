package com.setarit.quietticketscanner.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
    @SerializedName("price_name")
    private String priceName;
    @Expose
    private Visitor visitor;

    public Seat(String id, String name, boolean scanned, String priceName, Visitor visitor) {
        this.id = id;
        this.name = name;
        this.scanned = scanned;
        this.priceName = priceName;
        this.visitor = visitor;
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

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
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
