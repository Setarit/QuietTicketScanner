package com.setarit.quietticketscanner.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Setarit on 13/10/2017.
 * Visitor object
 */

public class Visitor implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private long bankingCode;
    private boolean went;
    private Collection<Seat> seats;

    public Visitor(String id, String firstName, String lastName, long bankingCode, boolean went, Collection<Seat> seats) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankingCode = bankingCode;
        this.went = went;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBankingCode() {
        return bankingCode;
    }

    public void setBankingCode(long bankingCode) {
        this.bankingCode = bankingCode;
    }

    public boolean isWent() {
        return went;
    }

    public void setWent(boolean went) {
        this.went = went;
    }

    public Collection<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<Seat> seats) {
        this.seats = seats;
    }
}
