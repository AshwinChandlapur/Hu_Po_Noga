package com.stickercamera.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.stickercamera.app.utility.FbFeed;
import com.stickercamera.app.utility.FeedAdapter;
import com.stickercamera.app.utility.onFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private static String TAG = PostsFragment.class.getCanonicalName();
    private onFragmentInteractionListener mListener;
    private TextView noPosts;
    private RecyclerView postData;

    public PostsFragment() {
    }

    public onFragmentInteractionListener getActivityInstance(){
        return mListener;
    }

    public static PostsFragment newInstance() {
        PostsFragment fragment = new PostsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getStory(ARG_PARAM1);
//            mParam2 = getArguments().getStory(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        noPosts = (TextView) view.findViewById(R.id.noContentText);
        postData = (RecyclerView) view.findViewById(R.id.postsData);
        getPostsData();
        return view;
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction();
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener) {
            mListener = (onFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void  getPostsData () {
        AccessToken aT = null;
        String url = getString(R.string.fb_page_id);
        String fields = "fields=created_time,message,description,story,id,type,picture,full_picture,from,likes&limit=10";
        if (AccessToken.getCurrentAccessToken() != null){
            aT = AccessToken.getCurrentAccessToken();
        } else {
            aT = new AccessToken(getString(R.string.fb_page_accessToken), getString(R.string.facebook_app_id), getString(R.string.fb_page_id), null, null, null, null, null);
        }
        Log.e(TAG, "getPostsData: url /" + url + "/feed");
        new GraphRequest(
                aT,
                "/" + url + "/feed?" + fields,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.e(TAG, "onCompleted: " + response);
                        try {
                            JSONObject result = response.getJSONObject();
                            JSONArray dataArr = result.getJSONArray("data");
                            List<FbFeed> feeds = new ArrayList<FbFeed>();
                            for (int i = 0; i < dataArr.length(); i++) {
                                FbFeed feed = FbFeed.fromJSONObj(dataArr.getJSONObject(i));
                                feeds.add(feed);
                            }
                            postData.setLayoutManager(new LinearLayoutManager(getActivity()));
                            postData.setAdapter(new FeedAdapter(PostsFragment.this, feeds));
                            postData.setItemAnimator(new DefaultItemAnimator());
                            postData.setVisibility(View.VISIBLE);
                            noPosts.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
