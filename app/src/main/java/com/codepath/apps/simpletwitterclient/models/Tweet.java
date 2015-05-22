package com.codepath.apps.simpletwitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Parse the JSON + Store the data
// Encapsulate state logic or display logic
public class Tweet {

    // List the attributes
    private String body;
    private long uid; //unique id for the tweet
    private User user;
    private String createdAt;

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    // Deserialize the JSON
    // Tweet.fromJSON("{ .. }" => Tweet
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract values from json and store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    // Tweet.fromJsonArray([...]) => list<Tweet>
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0 ; i < jsonArray.length() ; i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
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
