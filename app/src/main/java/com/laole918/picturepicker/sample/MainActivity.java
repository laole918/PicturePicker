package com.laole918.picturepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.laole918.picturepicker.PicturePicker;

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
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mButton)) {
            PicturePicker.start(this);
        }
    }
}
