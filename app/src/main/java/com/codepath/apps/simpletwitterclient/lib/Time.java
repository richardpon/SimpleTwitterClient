package com.codepath.apps.simpletwitterclient.lib;


import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Time {


    /**
     * Copied directly from:
     *      https://gist.github.com/nesquena/f786232f5ef72f6e10a7
     * getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
     */
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static String getTimeAgo(String rawJsonDate) {
        String longFormat = Time.getRelativeTimeAgo(rawJsonDate);

        Log.i("debug", "       "+longFormat);

        String[] parts = longFormat.split(" ");

        return parts[0] + parts[1].substring(0,1);
    }

}