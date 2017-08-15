package com.training.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by chenqiuyi on 17/2/6.
 */

public class TouchEventFather extends LinearLayout {
    private String TAG = "TouchEventFather";

    public TouchEventFather(Context context) {
        super(context);
    }

    public TouchEventFather(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventFather(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, TAG + ":onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w(TAG, TAG + ":onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, TAG + ":dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
//        return super.dispatchTouchEvent(ev);
        return true;
    }
}
