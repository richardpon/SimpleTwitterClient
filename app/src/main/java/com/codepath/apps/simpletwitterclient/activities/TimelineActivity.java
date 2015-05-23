package com.codepath.apps.simpletwitterclient.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.simpletwitterclient.models.Tweet;
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

        //get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeLine();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Send API request to get the timeline json
     * Fill the ListView by creating the tweet objects from json
     */
    private void populateTimeLine() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d(TAG, json.toString());

                // deserialize json
                // create models and add then to the adapter
                // load model data into listview

                ArrayList<Tweet> tweets = Tweet.fromJsonArray(json);
                aTweets.addAll(tweets);
                Log.i(TAG, aTweets.toString());
            }


            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }


        });
    }

}
