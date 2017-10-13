package com.stickercamera.app.camera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;


import com.common.util.ImageLoaderUtils;
import com.github.skykai.stickercamera.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.stickercamera.app.model.Addon;

import java.util.List;

/**
 * 
 * 贴纸适配器
 * @author tongqian.ni
 */
public class StickerToolAdapter extends BaseAdapter {

    List<Addon> filterUris;
    Context     mContext;

    public StickerToolAdapter(Context context, List<Addon> effects) {
        filterUris = effects;
        mContext = context;
    }

    @Override
    public int getCount() {
        return filterUris.size();
    }

    @Override
    public Object getItem(int position) {
        return filterUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EffectHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_bottom_tool, null);
            holder = new EffectHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.effect_image);
            holder.container = (ImageView) convertView.findViewById(R.id.effect_background);
            //holder.navImage.setOnClickListener(holder.clickListener);
            convertView.setTag(holder);
        } else {
            holder = (EffectHolder) convertView.getTag();
        }

        final Addon effect = (Addon) getItem(position);

        return showItem(convertView, holder, effect);
    }

    private View showItem(View convertView, EffectHolder holder, final Addon sticker) {

        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loader).showImageForEmptyUri(R.drawable.loader).showImageOnFail(R.drawable.loader).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        holder.container.setVisibility(View.GONE);
        ImageLoaderUtils.displayDrawableImage(sticker.getId() + "", holder.logo, options);//Instead of options null was there.THis is your change.
//        Toast.makeText(mContext.getApplicationContext(),String.valueOf(holder.logo),Toast.LENGTH_LONG).show();
//        Toast.makeText(mContext.getApplicationContext(),String.valueOf(sticker.getId()),Toast.LENGTH_LONG).show();
        return convertView;
    }

    class EffectHolder {
        ImageView logo;
        ImageView container;
    }

}
