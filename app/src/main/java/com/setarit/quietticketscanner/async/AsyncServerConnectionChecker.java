package com.setarit.quietticketscanner.async;

import android.os.AsyncTask;

import com.setarit.quietticketscanner.LoadingController;
import com.setarit.quietticketscanner.network.state.NetworkStateChecker;

/**
 * Created by Setarit on 02/10/2016.
 * Checks in a asynchronous task if the device has a connection to the server
 */

public class AsyncServerConnectionChecker extends AsyncTask<Void, Void, Void> {
    private LoadingController controller;
    private NetworkStateChecker networkStateChecker;

    public AsyncServerConnectionChecker(LoadingController controller){
        this.controller = controller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.networkStateChecker = new NetworkStateChecker(controller);
        networkStateChecker.verifyConnectionWithServer();
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if(networkStateChecker.isConnectedWithServer()){
            controller.toScanFileLoaderController();
        }else {
            controller.showNoNetworkConnectionDialog();
        }
    }
}
