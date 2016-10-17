package com.setarit.quietticketscanner.async;

import android.os.AsyncTask;

import com.setarit.quietticketscanner.network.api.ApiRequest;
import com.setarit.quietticketscanner.network.api.ServerResponseParsable;
import com.setarit.quietticketscanner.network.verification.TicketVerificator;

import org.json.JSONException;

/**
 * Created by Setarit on 17/10/2016.
 */

public class AsyncTicketValidation extends AsyncTask<String, Void, Void> {
    private final ServerResponseParsable verificator;
    private ApiRequest request;

    public AsyncTicketValidation(TicketVerificator verificator){
        this.verificator = verificator;
    }

    @Override
    protected Void doInBackground(String... params) {
        request = new ApiRequest();
        try {
            request.sendPost("accessCode="+params[0]+"&ticketCode="+params[1],  "tickets/scan");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            verificator.handleResponse(request.getResponse());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
