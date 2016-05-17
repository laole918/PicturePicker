package com.laole918.picturepicker.core;

import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by laole918 on 2016/5/16 0016.
 */
public abstract class ImageLoader {

    private static ImageLoader mInstances;

    public static void setImageLoader(ImageLoader imageLoader) {
        mInstances = imageLoader;
    }

    public static ImageLoader getImageLoader() {
        if (mInstances == null) {
            synchronized (ImageLoader.class) {
                if(mInstances == null) {
                    setImageLoader(new DefaultImageLoader(1, DefaultImageLoader.Type.LIFO));
                }
            }
        }
        return mInstances;
    }

    public abstract void loadImage(String path, ImageView imageView, int width, int height);

    public final void loadImage(String path, ImageView imageView) {
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0) {
            width = params.width; // Get layout width parameter
        }
        if (width <= 0) {
            width = getImageMaxWidth(imageView);
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getHeight(); // Get actual image height
        if (height <= 0) {
            height = params.height; // Get layout height parameter
        }
        if (height <= 0) {
            height = getImageMaxHeight(imageView);
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        loadImage(path, imageView, width, height);
    }

    private static int getImageMaxWidth(ImageView imageView) {
        if (Build.VERSION.SDK_INT > 15) {
            return imageView.getMaxWidth();
        } else {
            return getImageViewFieldValue(imageView, "mMaxWidth");
        }
    }

    private static int getImageMaxHeight(ImageView imageView) {
        if (Build.VERSION.SDK_INT > 15) {
            return imageView.getMaxHeight();
        } else {
            return getImageViewFieldValue(imageView, "mMaxHeight");
        }
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
        }
        return value;
    }
}
