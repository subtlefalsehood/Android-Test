package com.training.storage.model;

import android.graphics.Bitmap;

/**
 * Created by chenqiuyi on 17/1/4.
 */

public class RecipeInfo {
    private String bitmapPath;
    private Bitmap bitmap;
    private String name;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBitmapPath() {
        return bitmapPath;
    }

    public void setBitmapPath(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }
}
