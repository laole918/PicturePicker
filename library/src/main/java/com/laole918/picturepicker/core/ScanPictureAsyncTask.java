package com.laole918.picturepicker.core;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.laole918.picturepicker.model.Atlas;
import com.laole918.picturepicker.model.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laole918 on 2016/4/26 0026.
 */
public class ScanPictureAsyncTask extends AsyncTask<Context, Object, List<Atlas>> {

    private OnScanListener mOnScanListener;

    public void setOnScanListener(OnScanListener listener) {
        mOnScanListener = listener;
    }

    public interface OnScanListener {
        void onScanComplete(List<Atlas> as);
    }

    @Override
    protected List<Atlas> doInBackground(Context[] params) {
        List<Atlas> as = new ArrayList<>();
        ContentResolver contentResolver = params[0].getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.SIZE, MediaStore.Images.ImageColumns.DATE_MODIFIED}
                , null, null, MediaStore.Images.Media.DATE_MODIFIED);
        Atlas allA = new Atlas();
        allA.setName("所有图片");
        allA.setPictures(new ArrayList<Picture>());
        if (cursor != null) {
            Map<String, Atlas> mas = new HashMap<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));//图片所在目录名称
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));//图片路径
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));//图片大小
                long dateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED));//图片大小
                Atlas a;
                if (mas.containsKey(name)) {
                    a = mas.get(name);
                } else {
                    a = new Atlas();
                    a.setName(name);
                    a.setPictures(new ArrayList<Picture>());
                    a.setDateModified(dateModified);
                    mas.put(name, a);
                }
                if (a.getDateModified() < dateModified) {
                    a.setDateModified(dateModified);
                }
                if (allA.getDateModified() < dateModified) {
                    allA.setDateModified(dateModified);
                }
                List<Picture> allPs = allA.getPictures();
                List<Picture> ps = a.getPictures();
                Picture p = new Picture();
                p.setPath(path);
                p.setChecked(false);
                p.setSize(size);
                p.setDateModified(dateModified);
                allPs.add(0, p);
                ps.add(0, p);
            }
            cursor.close();
            as.addAll(mas.values());
            sort(as);//按修改时间降序
        }
        as.add(0, allA);
        return as;
    }

    private void sort(List<Atlas> as) {
        Collections.sort(as, new Comparator<Atlas>() {
            @Override
            public int compare(Atlas lhs, Atlas rhs) {
                long lhsdm = lhs.getDateModified();
                long rhsdm = rhs.getDateModified();
                return lhsdm < rhsdm ? 1 : (lhsdm == rhsdm ? 0 : -1);
            }
        });
    }

    @Override
    protected void onPostExecute(List<Atlas> as) {
        if(mOnScanListener != null) {
            mOnScanListener.onScanComplete(as);
        }
    }

}