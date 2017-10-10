package com.stickercamera.app.ui.mainfeed;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.skykai.stickercamera.R;
import com.stickercamera.app.ui.ParentActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFeed extends Fragment {

    String name,url,content,videoUrl;
    TextView newsname,newscontent;
    ImageView newsimage,youtube;

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
                    .into(newsimage);

        }else{
            youtube.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(url)
                    .into(newsimage);

            youtube.setOnClickListener(new View.OnClickListener() {
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
