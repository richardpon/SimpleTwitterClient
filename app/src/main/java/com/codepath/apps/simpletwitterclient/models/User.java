package com.codepath.apps.simpletwitterclient.models;


import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public User() {

    }

    public User(String name, String screenName, long uid, String profileImageUrl) {
        this.name = name;
        this.uid = uid;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@"+screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // List Attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;


    //deserialize the user json => user
    public static User fromJson(JSONObject json) {
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }


}
