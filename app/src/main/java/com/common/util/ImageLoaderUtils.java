package com.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.customview.drawable.StickerDrawable;
import com.github.skykai.stickercamera.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.stickercamera.app.model.Addon;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * UIL 工具类
 * Created by sky on 15/7/26.
 */
public class ImageLoaderUtils {


    /**
     * display local image
     * @param uri
     * @param imageView
     * @param options
     *
     *
     */


    public static final int[] imageurl = new int[]{
            R.drawable.coat,
            R.drawable.humble,
            R.drawable.humbly,
            R.drawable.mestru,
            R.drawable.royal,
            R.drawable.royally,
            R.drawable.dppic,
            R.drawable.topi,
            R.drawable.facts,
            R.drawable.glasses,
            R.drawable.namaskara,
            R.drawable.mike,
            R.drawable.speaker,
            R.drawable.goldtopi,
            R.drawable.ladooframe
    };

    public static void displayLocalImage(String uri, ImageView imageView, DisplayImageOptions options) {
        DisplayImageOptions option = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loader).showImageForEmptyUri(R.drawable.loader).showImageOnFail(R.drawable.loader).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage("file://" + uri, new ImageViewAware(imageView), option, null, null);
    }

    /**
     * display Drawable image
     * @param uri
     * @param imageView
     * @param options
     */


    public static void  displayDrawableImage(String uri, ImageView imageView, DisplayImageOptions options) {

//        imageView.post(() -> {
//
//
//
////            DisplayImageOptions option = new DisplayImageOptions.Builder()
////                    .resetViewBeforeLoading(true)
////                    .showImageOnLoading(R.drawable.loader)
////                    .showImageForEmptyUri(R.drawable.loader)
////                    .showImageOnFail(R.drawable.loader)
////                    .cacheInMemory(false)
////                    .cacheOnDisk(false)
////                    .considerExifParams(true)
////                    .bitmapConfig(Bitmap.Config.RGB_565)
////                    .displayer(new SimpleBitmapDisplayer())
////                    .build();
////
////            ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
////                    .defaultDisplayImageOptions(option)
////                    .denyCacheImageMultipleSizesInMemory()
////                    .threadPoolSize(10)
////                    .build();
//
//
////            ImageAware imageAware = new ImageViewAware(imageView, false);
////            ImageLoader imageLoader = ImageLoader.getInstance();
////            imageLoader.init(imageLoaderConfiguration);
//            imageView.setImageResource(Integer.valueOf(uri));//This line Did the Trick
//            //imageLoader.displayImage("drawable://" + uri,imageAware , option, null, null);//new ImageViewAware(imageView) instead of imageAware
//
//        });

        imageView.setImageResource(Integer.valueOf(uri));


    }



}
