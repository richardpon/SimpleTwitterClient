package com.codepath.apps.simpletwitterclient.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.lib.Logger;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Taking tweet objects and turning them into Views. Displayed in the list
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    AssetManager assets;

    private final static String TAG = "TweetsArrayAdapter";

    private static class ViewHolder {
        ImageView profileImage;
        TextView username;
        TextView name;
        TextView body;
        TextView createdAt;
        ImageView mediaImage;
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.body = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.createdAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            viewHolder.mediaImage = (ImageView) convertView.findViewById(R.id.ivMediaImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.username.setText(tweet.getUser().getScreenName());
        viewHolder.name.setText(tweet.getUser().getName());
        viewHolder.createdAt.setText(tweet.getCreatedAt());
        viewHolder.body.setText(tweet.getBody());
        viewHolder.profileImage.setImageResource(android.R.color.transparent); //clear out image for recycled view

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.profileImage);

        // Conditionally load MediaImage if it exists
        viewHolder.mediaImage.setImageResource(android.R.color.transparent); //clear out image for recycled view
        viewHolder.mediaImage.setVisibility(View.GONE);
        if (tweet.getMediaUrl().length() != 0) {
            Logger.log(TAG, "setting image to ="+tweet.getMediaUrl());
            Picasso.with(getContext()).load(tweet.getMediaUrl()).into(viewHolder.mediaImage);
            viewHolder.mediaImage.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
