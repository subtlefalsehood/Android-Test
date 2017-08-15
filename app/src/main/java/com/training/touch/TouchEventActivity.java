package com.training.touch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.training.R;

public class TouchEventActivity extends AppCompatActivity {
    private String TAG = "TouchEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, TAG + ":dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, TAG + ":onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));
//        return super.onTouchEvent(event);
        return false;
    }
}
