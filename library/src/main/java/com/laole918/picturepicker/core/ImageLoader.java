package com.laole918.picturepicker.core;

import android.widget.ImageView;

/**
 * Created by laole918 on 2016/5/16 0016.
 */
public abstract class ImageLoader {

    private static ImageLoader mInstances;

    public static void setImageLoader(ImageLoader imageLoader) {
        mInstances = imageLoader;
    }

    public static ImageLoader getImageLoader() {
        if(mInstances == null) {
            setImageLoader(new DefaultImageLoader(1, DefaultImageLoader.Type.LIFO));
        }
        return  mInstances;
    }

    public abstract void loadImage(String path, ImageView imageView);
}
