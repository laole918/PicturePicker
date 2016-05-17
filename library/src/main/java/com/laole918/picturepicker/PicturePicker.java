package com.laole918.picturepicker;

import android.content.Context;
import android.content.Intent;

import com.laole918.picturepicker.core.ImageLoader;
import com.laole918.picturepicker.view.PicturePickerActivity;

/**
 * Created by laole918 on 2016/4/25 0025.
 */
public class PicturePicker {

    public static void setImageLoader(ImageLoader imageLoader) {
        ImageLoader.setImageLoader(imageLoader);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PicturePickerActivity.class);
        context.startActivity(intent);
    }

}
