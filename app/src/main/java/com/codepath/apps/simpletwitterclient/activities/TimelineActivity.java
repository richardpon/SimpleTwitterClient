package com.codepath.apps.simpletwitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.simpletwitterclient.listeners.EndlessScrollListener;
import com.codepath.apps.simpletwitterclient.models.SignedInUser;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.codepath.apps.simpletwitterclient.models.User;
import com.codepath.apps.simpletwitterclient.networking.TwitterApplication;
import com.codepath.apps.simpletwitterclient.networking.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private final static String TAG = "TimelineActivity";
    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private long minTweetId; //Long.MAX_VALUE;
    private final int REQUEST_CODE_COMPOSE = 323;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //find the list view
        lvTweets = (ListView) findViewById(R.id.lvTweets);

        //create datasource
        tweets = new ArrayList<>();

        // construct the adapter
        aTweets = new TweetsArrayAdapter(this, tweets);

        //connect listview to adapter
        lvTweets.setAdapter(aTweets);

        // Tried to use Long.MAX_VALUE here, but didn't work. Instead used Long.MAX_VALUE/10
        // This should be large enough
        minTweetId = Long.parseLong("922337203685477580");

        // Set up infinite scroll
        setScrollListener();

        //get the client
        client = TwitterApplication.getRestClient(); //singleton client


        //fetchSignedInUsersProfile();
        //fetchTweetsIntoTimeline(minTweetId);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_twitter);
        actionBar.setDisplayUseLogoEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Send API request to get the timeline json
     * Fill the ListView by creating the tweet objects from json
     */
    private void fetchTweetsIntoTimeline(long maxTweetId) {

        client.getHomeTimeline(maxTweetId, new JsonHttpResponseHandler() {

            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                // deserialize json
                // create models and add then to the adapter
                // load model data into listview
                ArrayList<Tweet> tweets = Tweet.fromJsonArray(json);
                updateMinTweetIdFromTweetList(tweets);
                aTweets.addAll(tweets);
            }


            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "ERROR==="+errorResponse.toString());
                Toast.makeText(TimelineActivity.this, "GET request failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Keeps track of the minimum tweet id so that new non-dupe tweets can be fetched
     */
    private void updateMinTweetIdFromTweetList(ArrayList<Tweet> tweetsArray) {
        long curTweetId;

        for(int i = 0; i < tweetsArray.size() ; i++) {
            curTweetId = tweetsArray.get(i).getUid();
            if (curTweetId < minTweetId) {
                minTweetId = curTweetId;
            }
        }

    }

    private void setScrollListener() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                fetchTweetsIntoTimeline(minTweetId);
            }
        });
    }

    /**
     * Called when user wants to compose a tweet
     * @param menuItem MenuItem
     */
    public void actionCompose(MenuItem menuItem) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE_COMPOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {

        }
    }

    /**
     * This fetch's the signed in user's profile
     */
    private void fetchSignedInUsersProfile() {

        client.getUserProfile(new JsonHttpResponseHandler() {

            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                User signedInUser = User.fromJson(json);
                SignedInUser.persistSignedInUser(TimelineActivity.this, signedInUser);
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "ERROR getting Signed in User===" + errorResponse.toString());
                Toast.makeText(TimelineActivity.this, "Failed to get signed in user's info", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
