package com.laole918.picturepicker.model;

import java.util.List;

/**
 * Created by laole918 on 2016/4/25 0025.
 */
public class Atlas {

    private String name;
    private List<Picture> pictures;
    private boolean isChecked;
    private long dateModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> ps) {
        this.pictures = ps;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
}
