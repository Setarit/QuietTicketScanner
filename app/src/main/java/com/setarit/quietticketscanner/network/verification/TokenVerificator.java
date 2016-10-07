package com.setarit.quietticketscanner.network.verification;

import android.content.SharedPreferences;

import com.setarit.quietticketscanner.TokenController;
import com.setarit.quietticketscanner.async.AsyncTokenValidation;
import com.setarit.quietticketscanner.network.api.ServerResponseParsable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Setarit on 03/10/2016.
 * Verifies if the token was correct
 */

public class TokenVerificator implements ServerResponseParsable {
    private TokenController context;
    private String token;

    public TokenVerificator(TokenController callingContext){
        this.context = callingContext;
    }

    /**
     * Verifies a token and saves it to the internal store
     * @param token The token to verify
     * @return True if the token is valid
     */
    public void verify(String token){
        this.token = token;
        AsyncTokenValidation validation = new AsyncTokenValidation("accessCode="+token, this);
        validation.execute();
    }


    @Override
    public void handleResponse(JSONObject response) throws JSONException {
        if(response == null || response.getInt("code") == 1){//server will return 200 without message if the code is valid
            SharedPreferences settings = context.getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accessCode", token).apply();
            context.showScanActivity();
        }else{
            try {
                JSONArray validationMessages = response.getJSONObject("message").getJSONArray("accessCode");
                String message = "";
                for (int idx = 0; idx < validationMessages.length(); idx++) {
                    message += validationMessages.get(idx) + "\n";
                }
                context.showErrorMessage(message);
            }catch (JSONException e){
                context.showErrorMessage(response.getString("message"));//if message is string instead of JSONArray
            }
        }
    }
}
