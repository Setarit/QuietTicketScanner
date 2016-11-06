package com.setarit.quietticketscanner.network.state;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.setarit.quietticketscanner.network.factory.HttpsUrlConnectionFactory;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
        URL url;
        try {
            url = new URL(SERVER_ROOT_ADDRESS);
            HttpsURLConnection connection = HttpsUrlConnectionFactory.createConnection(url);
            connection.setRequestProperty( "Accept-Encoding", "" );//prevent android bug in HEAD request
            connection.setRequestMethod("HEAD");
            connection.connect();
            int r = connection.getResponseCode();
            return (connection.getResponseCode() != 404 && connection.getResponseCode() != 503);
        } catch (Exception e) {
            e.printStackTrace();
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
