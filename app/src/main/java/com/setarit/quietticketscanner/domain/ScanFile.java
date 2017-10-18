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
    private Collection<Visitor> visitors;

    public ScanFile(Event event, Collection<Visitor> visitors) {
        this.event = event;
        this.visitors = visitors;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Collection<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Collection<Visitor> visitors) {
        this.visitors = visitors;
    }
}
