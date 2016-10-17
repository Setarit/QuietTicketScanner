package com.setarit.quietticketscanner.network.verification;

import android.content.SharedPreferences;

import com.setarit.quietticketscanner.ScanController;
import com.setarit.quietticketscanner.async.AsyncTicketValidation;
import com.setarit.quietticketscanner.network.api.ServerResponseParsable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Setarit on 17/10/2016.
 */

public class TicketVerificator implements ServerResponseParsable {
    private final ScanController scanController;

    public TicketVerificator(ScanController scanController) {
        this.scanController = scanController;
    }

    public void validate(String ticketId){
        AsyncTicketValidation ticketValidation = new AsyncTicketValidation(this);
        String[] params = new String[2];
        params[0] = fetchAccessCode();
        params[1] = ticketId;

        ticketValidation.execute(params);
    }

    private String fetchAccessCode() {
        SharedPreferences preferences = scanController.getApplicationContext().getSharedPreferences("PREFS", 0);
        return preferences.getString("accessToken","noTokenAvailable");
    }

    @Override
    public void handleResponse(JSONObject response) throws JSONException {
        int i = 0;
    }
}
