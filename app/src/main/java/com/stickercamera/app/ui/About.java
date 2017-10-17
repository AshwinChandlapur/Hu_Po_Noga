package com.stickercamera.app.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.skykai.stickercamera.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {

    CircleImageView p1,p2,p3,p4,p5,p6,p7,p8,p9;

    public About() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        p1=(CircleImageView)rootView.findViewById(R.id.profile_image1);
        p2=(CircleImageView)rootView.findViewById(R.id.profile_image2);
        p3=(CircleImageView)rootView.findViewById(R.id.profile_image3);
        p4=(CircleImageView)rootView.findViewById(R.id.profile_image4);
        p5=(CircleImageView)rootView.findViewById(R.id.profile_image5);
        p6=(CircleImageView)rootView.findViewById(R.id.profile_image6);
        p7=(CircleImageView)rootView.findViewById(R.id.profile_image7);
        p8=(CircleImageView)rootView.findViewById(R.id.profile_image8);
        p9=(CircleImageView)rootView.findViewById(R.id.profile_image9);

        Glide.with(getActivity())
                .load("https://in.bmscdn.com/Artist/35531.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p1);


        Glide.with(getActivity())
                .load("https://in.bmscdn.com/iedb/artist/images/website/poster/large/sruthi-hariharan-41580-24-03-2017-17-32-38.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p2);


        Glide.with(getActivity())
                .load("https://in.bmscdn.com/iedb/artist/images/website/poster/large/vijay-chendoor-1054834-21-08-2017-15-34-21.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p3);


        Glide.with(getActivity())
                .load("https://in.bmscdn.com/iedb/artist/images/website/poster/large/rakshit-shetty-28480-02-10-2017-01-11-36.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p4);


        Glide.with(getActivity())
                .load("https://in.bmscdn.com/iedb/artist/images/website/poster/large/saad-khan-30974-24-03-2017-17-54-58.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p5);


        Glide.with(getActivity())
                .load("https://in.bmscdn.com/iedb/artist/images/website/poster/large/pushkara-mallikarjunaiah-1077079-02-10-2017-01-07-37.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p6);


        Glide.with(getActivity())
                .load("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/hpn/niki.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p7);


        Glide.with(getActivity())
                .load("https://yt3.ggpht.com/-msMxVCtCEVo/AAAAAAAAAAI/AAAAAAAAAAA/1-GpzG41dgo/s900-c-k-no-mo-rj-c0xffffff/photo.jpg")
                .thumbnail(0.5f)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p8);


        Glide.with(getActivity())
                .load("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/nikith.jpg")
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(p9);







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
