package com.setarit.quietticketscanner.network.api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.setarit.quietticketscanner.network.state.NetworkStateChecker.SERVER_ROOT_ADDRESS;

/**
 * Created by Setarit on 03/10/2016.
 * Makes a request to the server api
 */

public class ApiRequest {
    private static final String API_SERVER_ROOT_ADDRESS = SERVER_ROOT_ADDRESS+"/api/";

    private JSONObject response;
    private int responseCode;

    /**
     * Sends a POST request to the server
     * @param parameters The parameters formatted as param1=value1&param2=value2
     * @param actionUrl The url of the endpoint
     * @throws Exception
     */
    public void sendPost(String parameters, String actionUrl) throws Exception {

        String url = API_SERVER_ROOT_ADDRESS+actionUrl;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = new JSONObject(response.toString());

    }

    /**
     * Sends a GET request to the server
     * @param parameters The parameters formatted as param1=value1&param2=value2
     * @param actionUrl The url of the endpoint
     * @throws Exception
     */
    public void sendGet(String parameters, String actionUrl) throws Exception {
        String url = (parameters == null)? API_SERVER_ROOT_ADDRESS+actionUrl:
                API_SERVER_ROOT_ADDRESS+actionUrl+"?"+parameters;

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = new JSONObject(response.toString());

    }

    public JSONObject getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
