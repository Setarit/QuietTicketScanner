package com.setarit.quietticketscanner.network.verification;

import android.content.SharedPreferences;

import com.setarit.quietticketscanner.ScanController;
import com.setarit.quietticketscanner.async.AsyncTicketValidation;
import com.setarit.quietticketscanner.network.api.ServerResponseParsable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Setarit on 17/10/2016.
 * Verifies if the token is valid to start scanning
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
        return preferences.getString("accessCode","noTokenAvailable");//returns default value
    }

    @Override
    public void handleResponse(JSONObject response) throws JSONException {
        switch (response.getInt("code")) {
            case 1:
                scanController.showValidTicketInfo(response.getString("seat"));
                break;
            case -1:
                scanController.showTicketAlreadyScanned(response.getString("message"));
                break;
            case -2:
                scanController.showNoPayementReceived(response.getString("message"));
                break;
            default:
                scanController.showGenericInvalidTicket(response.getString("message"));
                break;
        }
    }
}
