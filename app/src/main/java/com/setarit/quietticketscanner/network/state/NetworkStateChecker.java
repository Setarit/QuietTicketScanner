package com.setarit.quietticketscanner.network.state;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by Setarit on 01/10/2016.
 * Verifies if the device has a connection to the server
 */

public class NetworkStateChecker {
    private Context context;
    private boolean connectedWithServer;

    public static final String SERVER_ROOT_ADDRESS = "https://www.quietticket.com";

    public NetworkStateChecker(Context callingContext){
        this.context = callingContext;
    }

    /**
     * Verifies if the device has a connection with the server
     * @return True if the server is reachable
     */
    private boolean hasConnectionWithServer(){
        return isOnline() && canConnectWithServer();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean canConnectWithServer(){
        URL url = null;
        try {
            url = new URL(SERVER_ROOT_ADDRESS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return (connection.getResponseCode() != 404 && connection.getResponseCode() != 503);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifies if the device has a connection with the server
     */
    public void verifyConnectionWithServer() {
        this.connectedWithServer = hasConnectionWithServer();
    }

    /**
     * Getter. Call {@link NetworkStateChecker#verifyConnectionWithServer()} first
     * @return True if a connection with the server
     */
    public boolean isConnectedWithServer() {
        return connectedWithServer;
    }
}
