package com.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.subtlefalsehood.base.utils.ContextUtils;
import com.training.douban.MovieActivity;
import com.training.mdtest.MDTestActivity;
import com.training.network.activity.NetWorkTestActivity;
import com.training.notification.NotificationListenActivity;
import com.training.others.screenshot.FloatViewService;
import com.training.service.activity.ServiceTestActivity;
import com.training.storage.activity.DataTestActivity;
import com.training.touch.TouchEventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.activity_main)
    LinearLayout layout;
    private Button[] buttons;
    private int[][] ids = {{R.id.btn_service, R.string.service},
            {R.id.btn_data, R.string.data,}, {R.id.btn_network, R.string.network}
            , {R.id.btn_douban, R.string.douban}, {R.id.btn_md, R.string.md}
//            , {R.id.btn_touch_event, R.string.touch_event}
//            , {R.id.btn_qr, R.string.qr}
//            , {R.id.btn_screenshot, R.string.screenshot}
            , {R.id.btn_drag, R.string.drag}
            , {R.id.btn_notification, R.string.notification_listen}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKnife = ButterKnife.bind(this);
        setupView();
    }

    private void setupView() {
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

    private boolean isStartFloat = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service:
                ContextUtils.startActivity(this, ServiceTestActivity.class, false);
                break;
            case R.id.btn_data:
                ContextUtils.startActivity(this, DataTestActivity.class, false);
                break;
            case R.id.btn_network:
                ContextUtils.startActivity(this, NetWorkTestActivity.class, false);
                break;
            case R.id.btn_douban:
                ContextUtils.startActivity(this, MovieActivity.class, false);
                break;
            case R.id.btn_md:
                ContextUtils.startActivity(this, MDTestActivity.class, false);
                break;
            case R.id.btn_touch_event:
                ContextUtils.startActivity(this, TouchEventActivity.class, false);
                break;
            case R.id.btn_screenshot:
                if (!isStartFloat) {
                    startService(new Intent(this, FloatViewService.class));
                    isStartFloat = true;
                }
                break;
            case R.id.btn_drag:
//                overridePendingTransition(0, 0);
                ContextUtils.startActivity(this, MDTestActivity.class, false);
                break;
            case R.id.btn_notification:
                ContextUtils.startActivity(this, NotificationListenActivity.class, false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
