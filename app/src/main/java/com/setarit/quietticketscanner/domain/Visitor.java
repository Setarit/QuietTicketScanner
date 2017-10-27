package com.setarit.quietticketscanner.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Setarit on 13/10/2017.
 * Visitor object
 */

public class Visitor implements Serializable {
    @Expose
    private String id;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private long bankingCode;
    @Expose
    private boolean went;

    public Visitor(String id, String firstName, String lastName, long bankingCode, boolean went) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankingCode = bankingCode;
        this.went = went;
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

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof Visitor){
            equal = ((Visitor) obj).getId().equals(this.getId());
        }
        return equal;
    }
}
