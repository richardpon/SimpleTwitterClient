package com.codepath.apps.simpletwitterclient.lib;


import android.content.Context;
import android.util.Log;

public class Logger {

    public static void log(Context context, String message) {
        Log.wtf(context.getClass().getName(), "-->"+message);
    }

    public static void log(String tag, String message) {
        Log.wtf(tag, "-->"+message);
    }
}
