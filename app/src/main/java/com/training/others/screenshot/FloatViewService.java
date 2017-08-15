package com.training.others.screenshot;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.subtlefalsehood.base.utils.DimenUtils;
import com.training.R;

public class FloatViewService extends Service {
    private WindowManager.LayoutParams layoutParams;
    private GestureDetector mGestureDetector;
    private WindowManager windowManager;
    private View floatView;
    private static FloatViewService floatViewService = null;

    public static FloatViewService getFloatViewService() {
        return floatViewService;
    }

    public FloatViewService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
        floatViewService = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createFloatView() {
        mGestureDetector = new GestureDetector(getApplicationContext(), new FloatGestureTouchListener());
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", getPackageName()));
        if (permission) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.x = metrics.widthPixels;
        layoutParams.y = 100;

        layoutParams.width = (int) DimenUtils.dip2px(getApplicationContext(), 30);
        layoutParams.height = (int) DimenUtils.dip2px(getApplicationContext(), 30);

        floatView = new View(this);
        floatView.setBackgroundResource(R.drawable.bg_float);
        windowManager.addView(floatView, layoutParams);

        floatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    class FloatGestureTouchListener implements GestureDetector.OnGestureListener {
        int lastX, lastY;
        int paramX, paramY;

        @Override
        public boolean onDown(MotionEvent e) {
            lastX = (int) e.getRawX();
            lastY = (int) e.getRawY();
            paramX = layoutParams.x;
            paramY = layoutParams.y;
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Intent intent = new Intent(getApplicationContext(), ScreenCaptureActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int dx = (int) (e2.getRawX() - lastX);
            int dy = (int) (e2.getRawY() - lastY);
            layoutParams.x = paramX + dx;
            layoutParams.y = paramY + dy;
            windowManager.updateViewLayout(floatView, layoutParams);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatView != null) {
            setFloatViewVisibility(View.GONE);
            windowManager.removeView(floatView);
        }
        floatViewService = null;
    }

    public void setFloatViewVisibility(int visibility) {
        if (floatView != null) {
            floatView.setVisibility(visibility);
        }
    }
}