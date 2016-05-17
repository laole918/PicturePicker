package com.laole918.picturepicker.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.laole918.picturepicker.R;
import com.laole918.picturepicker.core.ImageLoader;
import com.laole918.picturepicker.model.Atlas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by laole918 on 2016/4/25 0025.
 */
public class AtlasAdapter extends RecyclerView.Adapter<AtlasAdapter.AtlasHolder> {

    private OnItemSelectedListener mOnItemSelectedListener;
    private LayoutInflater mLayoutInflater;
    private List<Atlas> as = new ArrayList<>();

    private int itemWH;

    public interface OnItemSelectedListener {
        void onItemSelected(View view, int position);
    }

    public AtlasAdapter(Context context, int itemWH) {
        mLayoutInflater = LayoutInflater.from(context);
        this.itemWH = itemWH;
    }

    public void add(Atlas a) {
        as.add(a);
    }

    public void addAll(Collection<Atlas> as) {
        this.as.addAll(as);
    }

    @Override
    public AtlasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_atlas_item, parent, false);
        return new AtlasHolder(view, itemWH);
    }

    @Override
    public void onBindViewHolder(AtlasHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.bg_no_image);
        Atlas a = as.get(position);
        ImageLoader.getImageLoader().loadImage(a.getPictures().get(0).getPath(), holder.imageView);
        holder.setOnClickListener(position, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                for (int i = 0; i < getItemCount(); i++) {
                    if(as.get(i).isChecked()) {
                        as.get(i).setChecked(false);
                        notifyItemChanged(i);
                    }
                }
                as.get(position).setChecked(true);
                notifyItemChanged(position);
                if(null != mOnItemSelectedListener) {
                    mOnItemSelectedListener.onItemSelected(view, position);
                }
            }
        });
        holder.name.setText(a.getName());
        holder.num.setText(a.getPictures().size() + "张");
        holder.checkBox.setChecked(a.isChecked());
    }

    @Override
    public int getItemCount() {
        return as.size();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.mOnItemSelectedListener = listener;
    }

    public static class AtlasHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView num;
        CheckBox checkBox;

        AtlasHolder(View view, int itemWH) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            lp.height = itemWH;
            lp.width = itemWH;
            imageView.setLayoutParams(lp);
            name = (TextView) view.findViewById(R.id.name);
            num = (TextView) view.findViewById(R.id.num);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

        public void setOnClickListener(final int position, final OnItemSelectedListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != listener) {
                        listener.onItemSelected(itemView, position);
                    }
                }
            });
        }
    }
}
