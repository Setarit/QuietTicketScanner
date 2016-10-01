package com.setarit.quietticketscanner.network.state;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Setarit on 01/10/2016.
 * Verifies if the device has a connection to the server
 */

public class NetworkStateChecker {
    private Context context;

    public NetworkStateChecker(Context callingContext){
        this.context = callingContext;
    }

    /**
     * Verifies if the device has a connection with the server
     * @return True if the server is reachable
     */
    public boolean hasConnectionWithServer(){
        return isOnline() && canConnectWithServer();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean canConnectWithServer(){
        return false;
    }
}
