package com.stickercamera.app.ui.mainfeed;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.skykai.stickercamera.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.stickercamera.app.ui.ParentActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFeed extends Fragment {

    String name,url,content,videoUrl;
    TextView newsname,newscontent;
    ImageView newsimage,youtube;
    private InterstitialAd interstitial;
    int i = 0;

    public MainFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_feed, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             name = bundle.getString("name");
             url = bundle.getString("url");
             content = bundle.getString("con");
             videoUrl = bundle.getString("videoUrl");
        }


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
//                Log.i("Ads", "onAdLoaded");
//                if(Math.random()>0.9){


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayInterstitial();
                    }
                }, 2000);


//                }

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




//        rootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                i++;
//                if(i>20)
//                {displayInterstitial();}
//                Log.d("Touching","Touching");
//                return false;
//            }
//        });

        //Interstitial ad space





        newsname = (TextView)rootView.findViewById(R.id.newsname);
        newscontent = (TextView)rootView.findViewById(R.id.newscontent);
        newsimage = (ImageView)rootView.findViewById(R.id.newsimage);
        youtube = (ImageView)rootView.findViewById(R.id.youtube);


        newsname.setText(name);
        newscontent.setText(content);





        if( videoUrl.endsWith("n/a"))
        {
            youtube.setVisibility(View.GONE);
            Glide.with(getActivity())
                    .load(url)
                    .thumbnail(0.5f)
                    .crossFade()
                    .error(R.drawable.bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(newsimage);

        }else{
            youtube.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(url)
                    .thumbnail(0.5f)
                    .error(R.drawable.bg)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(newsimage);

            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
                }
            });

            newsimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
                }
            });

        }



//        if ( url.endsWith(".jpg") || url.endsWith(".png")){
//
//            simpleVideoView.setVisibility(View.GONE);
//            youtube.setVisibility(View.GONE);
//
//
//        } else if (url.endsWith(".mp4")) {
//
//              youtube.setVisibility(View.VISIBLE);
//            simpleVideoView.setVisibility(View.GONE);
////            Uri uri = Uri.parse(url);
////            MediaController mediaController = new MediaController(getContext());
////            simpleVideoView.setVideoURI(uri);
////            simpleVideoView.start();
////            simpleVideoView.setMediaController(mediaController);
//        }


        return rootView;
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
