package com.laole918.picturepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.laole918.picasso.PicassoImageloader;
import com.laole918.picturepicker.PicturePicker;
import com.laole918.universalimageloader.UniversaIimageloader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        if(mButton != null) {
            mButton.setOnClickListener(this);
        }

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 3);
//        config.threadPoolSize(10);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

//        PicturePicker.setImageLoader(new UniversaIimageloader());
        PicturePicker.setImageLoader(new PicassoImageloader());
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mButton)) {
            PicturePicker.start(this);
        }
    }
}
