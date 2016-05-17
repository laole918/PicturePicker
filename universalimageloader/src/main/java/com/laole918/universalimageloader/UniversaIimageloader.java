package com.laole918.universalimageloader;

import android.widget.ImageView;

import com.laole918.picturepicker.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

/**
 * Created by laole918 on 2016/5/17 0017.
 */
public class UniversaIimageloader extends ImageLoader {
    @Override
    public void loadImage(String path, ImageView imageView, int width, int height) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("file://" + path, imageView, new ImageSize(width, height));
    }

}
