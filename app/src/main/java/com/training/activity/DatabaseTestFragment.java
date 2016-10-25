package com.training.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.training.MyFragment;
import com.training.R;
import com.training.utils.ContextUtils;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class DatabaseTestFragment extends MyFragment implements View.OnClickListener {
    Button[] buttons;
    private int[][] ids = {{R.id.btn_service, R.string.service},
            {R.id.btn_data, R.string.data}};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.database);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setupView(LinearLayout layout) {
        buttons = new Button[ids.length];
        for (int i = 0; i < ids.length; i++) {
            buttons[i] = new Button(getActivity());
            buttons[i].setId(ids[i][0]);
            buttons[i].setText(ids[i][1]);
            buttons[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1);
            layout.addView(buttons[i], params);
            ContextUtils.setClick(this, buttons[i]);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
