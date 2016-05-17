package com.laole918.picturepicker.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.laole918.picturepicker.R;
import com.laole918.picturepicker.core.ScanPictureAsyncTask;
import com.laole918.picturepicker.model.Atlas;
import com.laole918.picturepicker.view.adapter.PictureAdapter;
import com.laole918.picturepicker.view.adapter.AtlasAdapter;
import com.laole918.picturepicker.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/4/25 0025.
 */
public class PicturePickerActivity extends Activity {

    public static final int grid_columns = 3;

    private View btnShowAtlas;

    private View maskView;
    private RecyclerView mRecyclerView;
    private PictureAdapter adapter;

    private PopupWindow popup;

    private List<Atlas> mAtlas = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        initView();
        initEvent();
        scanPicture();
    }

    private void scanPicture() {
        ScanPictureAsyncTask asyncTask = new ScanPictureAsyncTask();
        asyncTask.setOnScanListener(new ScanPictureAsyncTask.OnScanListener() {
            @Override
            public void onScanComplete(List<Atlas> as) {
                mAtlas.addAll(as);
                Atlas a = mAtlas.get(0);
                a.setChecked(true);
                initPopupWindow(mAtlas);
                selectAtlas(a);
            }
        });
        asyncTask.execute(this);
    }

    private void initPopupWindow(List<Atlas> as) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.layout_popup_atlas_list, null, false);
        RecyclerView mRecyclerView = (RecyclerView) contentView.findViewById(R.id.image_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        AtlasAdapter adapter = new AtlasAdapter(this, screenWidth / 4);
        adapter.addAll(as);
        mRecyclerView.setAdapter(adapter);
        int barHeight = getResources().getDimensionPixelSize(R.dimen.bar_height);
        int height = (int) ((screenHeight - (2 * barHeight) - getStatusBarHeight()) * 0.9);
        popup = new PopupWindow(contentView, screenWidth, height);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                maskView.setVisibility(View.GONE);
            }
        });
        adapter.setOnItemSelectedListener(new AtlasAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                selectAtlas(mAtlas.get(position));
                popup.dismiss();
            }
        });
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initView() {
        maskView = findViewById(R.id.mask_view);
        maskView.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.image_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, grid_columns));//这里用线性宫格显示 类似于grid view
        btnShowAtlas = findViewById(R.id.btn_show_atlas);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int itemHeight = width / grid_columns;
        adapter = new PictureAdapter(this, itemHeight);
        mRecyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        maskView.setClickable(true);
        maskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        btnShowAtlas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popup.isShowing()) {
                    popup.dismiss();
                } else {
                    popup.showAsDropDown(btnShowAtlas);
                    maskView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void selectAtlas(Atlas a) {
        adapter.clear();
        adapter.addAll(a.getPictures());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(popup.isShowing()) {
            popup.dismiss();
            return;
        }
        super.onBackPressed();
    }

}
