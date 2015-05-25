package com.codepath.apps.simpletwitterclient.models;

import com.codepath.apps.simpletwitterclient.lib.Logger;
import com.codepath.apps.simpletwitterclient.lib.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Parse the JSON + Store the data
// Encapsulate state logic or display logic
public class Tweet {

    private final static String TAG = "Tweet";

    // List the attributes
    private String body;
    private long uid; //unique id for the tweet
    private User user;
    private String createdAt;
    private String mediaUrl;

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return Time.getTimeAgo(createdAt);
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    // Deserialize the JSON
    // Tweet.fromJson("{ .. }" => Tweet
    public static Tweet fromJson(JSONObject jsonObject) {


        Tweet tweet = new Tweet();
        // Extract values from json and store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.mediaUrl = getMediaUrlfromJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    /**
     * Extracts the media url from a tweet if it exists
     */
    public static String getMediaUrlfromJson(JSONObject jsonObject) {
        String mediaUrl;
        try {
            mediaUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
            Logger.log(TAG, "found media! ="+mediaUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            mediaUrl = "";
        }
        return mediaUrl;
    }

    // Tweet.fromJsonArray([...]) => list<Tweet>
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0 ; i < jsonArray.length() ; i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

    
}
