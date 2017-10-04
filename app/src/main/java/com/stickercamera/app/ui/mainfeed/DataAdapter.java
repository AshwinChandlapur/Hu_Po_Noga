package com.stickercamera.app.ui.mainfeed;

import android.content.Context;
import android.content.Intent;
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
//                Intent intent = new Intent(mContext,DetailActivity.class);
//                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_url,tv_content;
        private ImageView feedimage;
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
