package com.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.training.common.utlis.ContextUtils;
import com.training.douban.MovieTestActivity;
import com.training.mdtest.MDTestActivity;
import com.training.network.activity.NetWorkTestActivity;
import com.training.others.screenshot.FloatViewService;
import com.training.service.activity.ServiceTestActivity;
import com.training.storage.activity.DataTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    @BindView(R.id.activity_main)
    LinearLayout layout;
    Button[] buttons;
    private int[][] ids = {{R.id.btn_service, R.string.service},
            {R.id.btn_data, R.string.data,}, {R.id.btn_network, R.string.network}
            , {R.id.btn_douban, R.string.douban}, {R.id.btn_md, R.string.md}
            , {R.id.btn_touch_event, R.string.touch_event}
//            , {R.id.btn_screenshot, R.string.screenshot}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupView();
        intent = new Intent(this, FloatViewService.class);
        startService(intent);
    }

    public void setupView() {
        buttons = new Button[ids.length];
        for (int i = 0; i < ids.length; i++) {
            buttons[i] = new Button(this);
            buttons[i].setId(ids[i][0]);
            buttons[i].setText(ids[i][1]);
            buttons[i].setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 0, 1);
            layout.addView(buttons[i], params);
            ContextUtils.setClick(this, buttons[i]);
        }
    }

    private Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service:
                ContextUtils.jumpActivity(this, ServiceTestActivity.class, false);
                break;
            case R.id.btn_data:
                ContextUtils.jumpActivity(this, DataTestActivity.class, false);
                break;
            case R.id.btn_network:
                ContextUtils.jumpActivity(this, NetWorkTestActivity.class, false);
                break;
            case R.id.btn_douban:
                ContextUtils.jumpActivity(this, MovieTestActivity.class, false);
                break;
            case R.id.btn_md:
                ContextUtils.jumpActivity(this, MDTestActivity.class, false);
                break;
            case R.id.btn_touch_event:
                ContextUtils.jumpActivity(this, TouchEventActivity.class, false);
                break;
            case R.id.btn_screenshot:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(intent);
    }
}
