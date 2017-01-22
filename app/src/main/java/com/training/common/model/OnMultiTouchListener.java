package com.training.common.model;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chenqiuyi on 17/1/6.
 */

/**
 * 连续点击事件监听器 可以用作双击事件
 */
public abstract class OnMultiTouchListener implements View.OnTouchListener {
    private long lastTouchTime = 0;
    private int count = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            long now = System.currentTimeMillis();
            count++;
            if (now - lastTouchTime < 500) {
                if (onMultiTouch(count)) {
                    count = 0;
                    now = 0;
                }
            } else {
                if (count > 1) {
                    count = 0;
                    now = 0;
                }
            }
            lastTouchTime = now;
            onMultiTouch(count);

        }
        return true;
    }

    public abstract boolean onMultiTouch(int count);
}
