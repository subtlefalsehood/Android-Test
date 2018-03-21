package com.training.main;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * Created by chenqiuyi on 2017/12/25.
 */

public class MainItemBean implements Serializable {
    private @StringRes int title;
    private @IdRes int id;

    public MainItemBean(int title, int id) {
        this.title = title;
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
