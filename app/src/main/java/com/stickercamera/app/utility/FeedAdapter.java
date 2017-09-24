package com.stickercamera.app.utility;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.stickercamera.app.ui.PostsFragment;

import java.util.List;

/**
 * Created by Nikith_Shetty on 10/09/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private String TAG = "FeedAdapter";
    private List<FbFeed> feedList;
    private PostsFragment parentFrag;

    public FeedAdapter (PostsFragment parent, List<FbFeed> list) {
        parentFrag = parent;
        feedList = list;
        Log.e(TAG, "FeedAdapter: " + feedList.size());
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView proPic;
        TextView name;
        TextView timeStamp;
        TextView statusMsg;
        ImageView feedImg;
        LinearLayout likeBtn;
        LinearLayout shareBtn;
        TextView likesCount;

        public FeedViewHolder(View itemView) {
            super(itemView);
            proPic = (ImageView) itemView.findViewById(R.id.profilePic);
            name = (TextView) itemView.findViewById(R.id.name);
            timeStamp = (TextView) itemView.findViewById(R.id.timestamp);
            statusMsg = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            feedImg = (ImageView) itemView.findViewById(R.id.feedImage1);
            likeBtn = (LinearLayout) itemView.findViewById(R.id.likeBtn);
            shareBtn = (LinearLayout) itemView.findViewById(R.id.shareBtn);
            likesCount = (TextView) itemView.findViewById(R.id.likesCount);
            //set default values
//            name.setText("Name");
//            timeStamp.setText("timeStamp-12:78-20/1/2013");
//            statusMsg.setText("statusMsg goes here");
        }
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_card, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        final FbFeed feed = feedList.get(position);
        try {
//            Log.e(TAG, "onBindViewHolder: " + feed.toString());
            setText(holder.name, feed.getFromName());
            holder.statusMsg.setText(Html.fromHtml(feed.getMessage()));
            setText(holder.timeStamp, feed.getCreate_time());
            if (feed.getFull_picture() != null || feed.getFull_picture() != ""){
                Glide.with(parentFrag)
                        .load(feed.getFull_picture())
                        .asBitmap()
                        .into(holder.feedImg);
                holder.feedImg.setVisibility(View.VISIBLE);
            } else {
                holder.feedImg.setVisibility(View.GONE);
            }
            Log.e(TAG, "onBindViewHolder: " + feed.getLikesCount());
//            setText(holder.likesCount, feed.getLikesCount());
            if (feed.getLikesCount() != null || feed.getLikesCount() != ""){
                holder.likesCount.setText(feed.getLikesCount() + " Likes");
            } else {
                holder.likesCount.setVisibility(View.GONE);
            }
            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        Log.e(TAG, "onClick: ");
                        likeObject(view, feed.getId());
                    } else {
                        parentFrag.getActivityInstance().login();
                    }
                }
            });
            holder.shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        Log.e(TAG, "onClick: ");
                        shareObject(feed.getId());
                    } else {
//                        LoginManager.getInstance().logInWithReadPermissions(parentFrag, "pro");
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.toString());
        }
    }

    private void setText (TextView view, String text) {
        if (text == "") view.setVisibility(View.GONE);
        else {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    private void likeObject(final View view, String id){
        Log.e(TAG, "likeObject: " + id);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + "/likes",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, "onCompleted: " + response);
                        LinearLayout ll = (LinearLayout) view;
                        TextView text = (TextView)ll.getChildAt(0);
                        text.setText("Liked");
                        ll.setClickable(false);
                    }
                }
        ).executeAsync();
    }

    private void shareObject(String id){
        Log.e(TAG, "shareObject: ");
        ShareLinkContent slc = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/humblepoliticiannograj/" + id))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#humblePoliticianNograj").build())
                .build();
    }
}
