package com.training.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.training.MyActivity;
import com.training.R;
import com.training.utils.ContextUtils;

import butterknife.BindView;

public class DataBaseTestActivity extends MyActivity implements View.OnClickListener {
    @BindView(R.id.layout_common)
    LinearLayout layout;
    Button[] buttons;
    private int[][] ids = {{R.id.btn_service, R.string.service},
            {R.id.btn_data, R.string.data}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_common);
        setupView();
    }

    public void setupView() {
        buttons = new Button[ids.length];
        for (int i = 0; i < ids.length; i++) {
            buttons[i] = new Button(this);
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
        switch (v.getId()) {
            case R.id.btn_service:
                ContextUtils.jumpActivity(mContext, ServiceTestActivity.class, false);
                break;
            case R.id.btn_data:
                ContextUtils.jumpActivity(mContext, DataTestActivity.class, false);
                break;

        }
    }
}
