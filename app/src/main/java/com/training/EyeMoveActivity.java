package com.training;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class EyeMoveActivity extends AppCompatActivity {

    private MainView mainView;
    private int tableWidth;
    private int tableHeight = 0;
    private double moveX, moveY;
    private Rect rect;
    private RectF rectF;
    private static final int MOVE = 0;
    private boolean isWingOpen = false;
    private static final int PERIOD = 10;
    private static final int ONE_TURN_SENCOND = 2000;
    private static double constant_value;
    private static final int TOTAL_MOVE = 6;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MOVE) {
                initDrawingVal();
                mainView.invalidate();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDrawingVal();
        setContentView(mainView);
    }

    public void initDrawingVal() {
        rectF = new RectF((int) moveX, (int) moveY, 120 + (int) moveX, 90 + (int) moveY);
    }

    public void initView() {
        mainView = new MainView(this);

        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 获得屏幕宽和高
        tableWidth = metrics.widthPixels - 120;
        tableHeight = metrics.heightPixels - 400;
        constant_value = (TOTAL_MOVE * Math.PI) / tableWidth;
        rect = new Rect(0, 0, tableWidth, tableHeight);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() //
        {
            @Override
            public void run() {
                if (moveX >= 0 & moveX < tableWidth) {
                    moveX += (PERIOD * tableWidth) / (TOTAL_MOVE * ONE_TURN_SENCOND);
                    moveY = (int) (tableHeight * Math.sin((constant_value * moveX) - (Math.PI / 2)));
                    moveY = (moveY + tableHeight) / 2;
                    handler.sendEmptyMessage(MOVE);
                } else {
                    timer.cancel();
                }
            }
        }, 1000, PERIOD);
    }

    private class MainView extends View {
        public MainView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            BitmapDrawable bitmapDrawable;
            if (isWingOpen) {
                bitmapDrawable = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.ic_open_wing));
                isWingOpen = false;
            } else {
                bitmapDrawable = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.ic_close_wing));
                isWingOpen = true;
            }
            if (bitmapDrawable != null) {
                if (bitmapDrawable.getBitmap() != null) {
                    canvas.drawBitmap(bitmapDrawable.getBitmap(), rect, rectF, null);
                }
            }
        }
    }


}
