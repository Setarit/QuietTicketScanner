package com.setarit.quietticketscanner.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.io.Serializable;

/**
 * Created by Setarit on 15/10/2017.
 * Contains the specification of the shared preferences
 */

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface Preferences extends Serializable {
    String scanFileLocation();
    String eventName();
    String eventId();
    String imageBase64();
    String seatsJson();
    boolean hasScanned();
}
