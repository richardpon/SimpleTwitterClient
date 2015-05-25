package com.codepath.apps.simpletwitterclient.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.models.SignedInUser;
import com.codepath.apps.simpletwitterclient.models.User;
import com.squareup.picasso.Picasso;

public class ComposeActivity extends ActionBarActivity {

    private final static String TAG = "ComposeActivity";

    TextView tvComposeName;
    TextView tvComposeScreenName;
    ImageView ivComposeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        // Enable Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        renderSignedInUser();
    }

    /**
     * Get references to all the views in the layout
     */
    private void initViews() {
        tvComposeName = (TextView) findViewById(R.id.tvComposeName);
        tvComposeScreenName = (TextView) findViewById(R.id.tvComposeScreenName);
        ivComposeUser = (ImageView) findViewById(R.id.ivComposeUser);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void renderSignedInUser() {
        User user = SignedInUser.getSignedInUser(this);

        Log.wtf(TAG, "screenname="+user.getScreenName());

        tvComposeScreenName.setText(user.getScreenName());
        tvComposeName.setText(user.getName());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivComposeUser);
    }
}
