package com.stickercamera.app.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.github.skykai.stickercamera.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.stickercamera.app.utility.FbFeed;
import com.stickercamera.app.utility.FeedAdapter;
import com.stickercamera.app.utility.onFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private static String TAG = PostsFragment.class.getCanonicalName();
    private onFragmentInteractionListener mListener;
    private TextView noPosts;
    private RecyclerView postData;
    private ProgressDialog progressDialog;
    private InterstitialAd interstitial;
    int i=0;
    public PostsFragment() {
        // Required empty public constructor
    }

    public onFragmentInteractionListener getActivityInstance(){
        return mListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        noPosts = (TextView) view.findViewById(R.id.noContentText);
        postData = (RecyclerView) view.findViewById(R.id.postsData);
        getPostsData();


        MobileAds.initialize(getActivity(), getString(R.string.app_id));

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37").build();
        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit));
        interstitial.loadAd(adRequests);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
                if(Math.random()>0.5){
                    displayInterstitial();
                }

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
//                interstitial.loadAd(new AdRequest.Builder().build());
                Log.i("Ads", "onAdClosed");
            }
        });
        //Interstitial ad space




        return view;
//        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    private void  getPostsData () {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Brewing Content...");
        progressDialog.show();
        AccessToken aT = null;
        String url = getString(R.string.fb_page_id);
        String fields = "fields=created_time,message,description,story,id,type,picture,full_picture,from,likes.limit(1).summary(true)&limit=10";
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
                        progressDialog.dismiss();
                    }
                }
        ).executeAsync();
    }



    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if ((interstitial.isLoaded()) && (interstitial!=null)) {
            interstitial.show();
        }
    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    Intent i = new Intent(getActivity(),ParentActivity.class);
                    startActivity(i);

                    return true;

                }

                return false;
            }
        });
    }


}
