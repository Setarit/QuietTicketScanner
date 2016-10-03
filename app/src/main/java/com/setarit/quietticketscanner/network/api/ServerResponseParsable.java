package com.setarit.quietticketscanner.network.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Setarit on 03/10/2016.
 */
public interface ServerResponseParsable {
    void handleResponse(JSONObject response) throws JSONException;
}
