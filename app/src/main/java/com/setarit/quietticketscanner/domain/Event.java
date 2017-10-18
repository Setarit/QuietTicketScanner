package com.setarit.quietticketscanner.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Setarit on 13/10/2017.
 * Model for the event
 */

public class Event implements Serializable {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose(serialize = false)
    private String image;

    public Event(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
