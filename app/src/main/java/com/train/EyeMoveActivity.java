package com.train;

import android.content.Context;
import android.graphics.Bitmap;
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
    private int moveX, moveY;
    private int moveTop, moveBottom;
    private Rect rect;
    private RectF rectF;
    private static final int MOVE = 0;
    private boolean isWingOpen = false;
    private int count = 0;
    private static final int PERIOD = 10;
    private static final int DRAW = 2000 / PERIOD;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
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
        rectF = new RectF(0 + moveX, 0 + moveY, 120 + moveX, 90 + moveY);
    }

    public void initView() {
        mainView = new MainView(this);

        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 获得屏幕宽和高
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels - 20;
        rect = new Rect(0, 0, tableWidth, tableHeight);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() //
        {
            @Override
            public void run() {
                if (count > 5) {
                    timer.cancel();
                }
                if (moveX >= 0 & moveX < tableWidth) {
                    moveX += tableWidth / DRAW / 6;
                } else {
//                    timer.cancel();
                }
                if (moveY >= 0 & moveY < tableHeight) {
                    if (count % 2 == 0) {
                        moveTop = tableHeight / DRAW;
                        moveY += moveTop;
                    } else {
                        moveBottom = 0 - tableHeight / DRAW;
                        moveY += moveBottom;
                    }
                } else {
                    count += 1;
                    if (count % 2 == 0) {
                        moveTop = tableHeight / DRAW;
                        moveY += moveTop;
                    } else {
                        moveBottom = 0 - tableHeight / DRAW;
                        moveY += moveBottom;
                    }
                }
                handler.sendEmptyMessage(MOVE);
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
            Bitmap bitmap1;
            if (isWingOpen) {
                bitmap1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.ic_open_wing)).getBitmap();
                isWingOpen = false;
            } else {
                bitmap1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.ic_close_wing)).getBitmap();
                isWingOpen = true;
            }
            canvas.drawBitmap(bitmap1, rect, rectF, null);
        }
    }


}
