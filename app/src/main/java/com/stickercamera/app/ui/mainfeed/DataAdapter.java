package com.stickercamera.app.ui.mainfeed;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.skykai.stickercamera.R;
import com.stickercamera.app.ui.MainActivity;
import com.stickercamera.app.ui.ParentActivity;

import java.util.ArrayList;

/**
 * Created by ashwinchandlapur on 04/10/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    FragmentTransaction ft;

    public DataAdapter(ArrayList<AndroidVersion> android) {
        this.android = android;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_feed, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        String name = android.get(i).getName();
        String url = android.get(i).getUrl();
        String con = android.get(i).getCon();

        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("url",url);
        bundle.putString("con",con);
        MainFeed mainFeed = new MainFeed();
        mainFeed.setArguments(bundle);



        viewHolder.tv_name.setText(android.get(i).getName());
        viewHolder.tv_url.setText(android.get(i).getUrl());
        viewHolder.tv_content.setText(android.get(i).getCon());
        Glide.with(viewHolder.feedimage.getContext())
                .load(android.get(i).getUrl())
                .into(viewHolder.feedimage);




        viewHolder.feedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.feedimage.getContext(), "inside viewholder position = " + i, Toast.LENGTH_SHORT).show();
                MainFeed mainFeed = new MainFeed();
                mainFeed.setArguments(bundle);
                ft = ((AppCompatActivity)viewHolder.feedimage.getContext()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, mainFeed);
                ft.addToBackStack(null);
                ft.commit();


//                Intent intent = new Intent(viewHolder.feedimage.getContext(),MainActivity.class);
//                intent.putExtra("name",name);
//                intent.putExtra("url",url);
//                intent.putExtra("con",con);
//                viewHolder.feedimage.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_name,tv_url,tv_content;
        public ImageView feedimage;
        private LinearLayout feeds;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_url = (TextView)view.findViewById(R.id.tv_url);
            tv_content = (TextView)view.findViewById(R.id.tv_content);
            feedimage = (ImageView)view.findViewById(R.id.feedimage);

        }
    }

}
