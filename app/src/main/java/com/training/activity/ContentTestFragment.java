package com.training.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.training.MyFragment;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class ContentTestFragment extends MyFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setupView(LinearLayout layout) {
    }

    @Override
    public void onClick(View v) {

    }
}
