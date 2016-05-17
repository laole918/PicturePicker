package com.laole918.picturepicker.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.laole918.picturepicker.R;
import com.laole918.picturepicker.core.ImageLoader;
import com.laole918.picturepicker.model.Picture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by laole918 on 2016/4/25 0025.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Picture> ps = new ArrayList<>();

    private int itemHeight;

    public PictureAdapter(Context context, int itemHeight) {
        mLayoutInflater = LayoutInflater.from(context);
        this.itemHeight = itemHeight;
    }

    public void clear() {
        ps.clear();
    }

    public void add(Picture p) {
        ps.add(p);
    }

    public void addAll(Collection<Picture> ps) {
        this.ps.addAll(ps);
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_image_item, parent, false);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = itemHeight;
        view.setLayoutParams(lp);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.bg_no_image);
        holder.imageView.setMaxHeight(itemHeight);
        holder.imageView.setMaxWidth(itemHeight);
        ImageLoader.getImageLoader().loadImage(ps.get(position).getPath(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ps.size();
    }

    public static class PictureHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        PictureHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }
}
