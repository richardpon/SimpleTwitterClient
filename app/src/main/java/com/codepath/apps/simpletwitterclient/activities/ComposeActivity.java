package com.codepath.apps.simpletwitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.lib.Toaster;
import com.codepath.apps.simpletwitterclient.models.SignedInUser;
import com.codepath.apps.simpletwitterclient.models.User;
import com.codepath.apps.simpletwitterclient.networking.TwitterApplication;
import com.codepath.apps.simpletwitterclient.networking.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {

    private final static String TAG = "ComposeActivity";

    private TextView tvComposeName;
    private TextView tvComposeScreenName;
    private ImageView ivComposeUser;
    private EditText etCompose;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        // Enable Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        renderSignedInUser();

        client = TwitterApplication.getRestClient(); //singleton client
    }

    /**
     * Get references to all the views in the layout
     */
    private void initViews() {
        tvComposeName = (TextView) findViewById(R.id.tvComposeName);
        tvComposeScreenName = (TextView) findViewById(R.id.tvComposeScreenName);
        ivComposeUser = (ImageView) findViewById(R.id.ivComposeUser);
        etCompose = (EditText) findViewById(R.id.etCompose);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);

        // Show Twitter logo
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void renderSignedInUser() {
        User user = SignedInUser.getSignedInUser(this);

        tvComposeScreenName.setText(user.getScreenName());
        tvComposeName.setText(user.getName());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivComposeUser);
    }

    public void actionSendTweet(MenuItem menuItem) {
        String text = etCompose.getText().toString();
        client.sendTweet(text, new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Toaster.create(ComposeActivity.this, "Tweet Success!");

                Intent data = new Intent();
                data.putExtra("success", true);
                setResult(RESULT_OK, data);
                finish();
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toaster.create(ComposeActivity.this, "Sorry, tweet failed");

                Intent data = new Intent();
                data.putExtra("success", false);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

}
