package com.setarit.quietticketscanner.network.factory;

import android.os.Build;

import com.setarit.quietticketscanner.network.factory.tls.NoSSLv3SocketFactory;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by Setarit on 06/11/2016.
 * Creates the correct HttpsURLConnection based on the API version
 */

public class HttpsUrlConnectionFactory {
    public static HttpsURLConnection createConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        if(Build.VERSION.SDK_INT < 20){
            return createCompatibility(url);
        }else {
            return createNormal(url);
        }
    }

    private static HttpsURLConnection createNormal(URL url) throws IOException {
        return (HttpsURLConnection) url.openConnection();
    }

    private static HttpsURLConnection createCompatibility(URL url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContext.getInstance("TLSv1");

        sslcontext.init(null,null,null);
        SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
        HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);
        return (HttpsURLConnection) url.openConnection();
    }
}
