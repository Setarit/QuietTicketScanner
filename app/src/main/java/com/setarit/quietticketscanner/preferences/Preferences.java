package com.setarit.quietticketscanner.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Setarit on 15/10/2017.
 * Contains the specification of the shared preferences
 */

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface Preferences {
    String scanFileLocation();
    String eventName();
    String imageBase64();
}
