package com.setarit.quietticketscanner.async;

import android.os.AsyncTask;

import com.setarit.quietticketscanner.network.api.ApiRequest;
import com.setarit.quietticketscanner.network.api.ServerResponseParsable;

import org.json.JSONException;

/**
 * Created by Setarit on 03/10/2016.
 */

public class AsyncTokenValidation extends AsyncTask<Void, Void, Boolean> {
    private final ServerResponseParsable serverResponseParsable;
    private String token;
    private ApiRequest request;

    public AsyncTokenValidation(String token, ServerResponseParsable serverResponseParsable) {
        this.token = token;
        this.serverResponseParsable = serverResponseParsable;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        this.request = new ApiRequest();
        try {
            this.request.sendPost(token, "tickets/start");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        try {
            serverResponseParsable.handleResponse(request.getResponse());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
