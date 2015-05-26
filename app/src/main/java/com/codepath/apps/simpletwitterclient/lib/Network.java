package com.codepath.apps.simpletwitterclient.lib;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInto = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInto != null && activeNetworkInto.isConnectedOrConnecting();
    }

    public static Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            Logger.log("network", e.toString());
            e.printStackTrace();
        }
        return false;
    }


}
