package com.common.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.github.skykai.stickercamera.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

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
     */
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


    public static void displayDrawableImage(String uri, ImageView imageView, DisplayImageOptions options) {
        DisplayImageOptions option = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loader).showImageForEmptyUri(R.drawable.loader).showImageOnFail(R.drawable.loader).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage("drawable://" + uri, new ImageViewAware(imageView), option, null, null);
    }



}
