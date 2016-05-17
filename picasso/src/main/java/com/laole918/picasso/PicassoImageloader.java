package com.laole918.picasso;

import android.widget.ImageView;

import com.laole918.picturepicker.core.ImageLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by laole918 on 2016/5/17 0017.
 */
public class PicassoImageloader extends ImageLoader {
    @Override
    public void loadImage(String path, ImageView imageView, int width, int height) {
//        path = "/storage/emulated/0/MagazineUnlock/Balance(magazine)-01-2.3.001-bigpicture_01_9.jpg";
//        Picasso.with(imageView.getContext()).load("file://" + path).centerCrop().resize(width, height).into(imageView);
        Picasso.with(imageView.getContext()).load("file://" + path).resize(width, height).centerCrop().into(imageView);
    }

}
