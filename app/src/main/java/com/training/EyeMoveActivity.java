package com.training;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class EyeMoveActivity extends Activity {

    private MainView mainView;
    private int tableWidth;
    private int tableHeight;
    private double moveX, moveY;
    private Rect rect;
    private RectF rectF;
    private static final int MOVE = 0;
    private static final int CHANGE = 1;
    private boolean isWingOpen = false;
    private static final int PERIOD = 20;
    private static final int ONE_TURN_SENCOND = 2000;
    private static double constant_value;
    private static final int TOTAL_MOVE = 6;
    int bitmapW = 120;
    int bitmapH = 90;
    private int count = 0;
    private boolean need_change = false;
    private boolean ischanged = false;
    private int[] ic_wings = {R.drawable.ic_wing_1, R.drawable.ic_wing_1, R.drawable.ic_wing_2, R.drawable.ic_wing_2, R.drawable.ic_wing_3, R.drawable.ic_wing_3, R.drawable.ic_wing_4, R.drawable.ic_wing_4};
    private int n = 0;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MOVE:
                    if (count < 6) {
                        initDrawingVal();
                        mainView.invalidate();
                    }
                    break;
                case CHANGE:
                    int t = bitmapH;
                    bitmapH = bitmapW;
                    bitmapW = t;
                    initdata();
                    initView();
                    initDrawingVal();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainView = new MainView(this);
        initdata();
        initView();
        initDrawingVal();
        setContentView(mainView);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //切换为竖屏
//        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
//        }
//        //切换为横屏
//        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//            if (need_change) {
//                ischanged = true;
//                initView();
//                initDrawingVal();
//
//            }
//        }
//    }


    public void initDrawingVal() {
        rectF = new RectF((int) moveX, (int) moveY, bitmapW + (int) moveX, bitmapH + (int) moveY);
    }

    private void initdata() {
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 获得屏幕宽和高


        tableWidth = metrics.widthPixels - 100;
        tableHeight = metrics.heightPixels - 100;
        constant_value = (TOTAL_MOVE * Math.PI) / tableWidth;
        rect = new Rect(0, 0, tableWidth, tableHeight);
        moveX = 0;
        moveY = 0;
        count = 0;
    }

    public void initView() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() //
                       {
                           @Override
                           public void run() {

                               if (moveX >= 0 & moveX < tableWidth) {
                                   if (count < 3 & count % 2 == 0) {
                                       moveX += (PERIOD * tableWidth) / (TOTAL_MOVE * ONE_TURN_SENCOND);
                                   } else if (count < 3 & count % 2 != 0) {
                                       moveX -= (PERIOD * tableWidth) / (TOTAL_MOVE * ONE_TURN_SENCOND);
                                   }
                                   moveY = (int) (tableHeight * Math.sin((constant_value * moveX) - (Math.PI / 2)));
                                   moveY = (moveY + tableHeight) / 2;
                               } else {
                                   count++;
                                   if (count >= 6 & !ischanged) {
                                       Looper.prepare();
                                       Toast.makeText(EyeMoveActivity.this, "请切换到横屏", Toast.LENGTH_LONG).show();
                                       handler.sendEmptyMessage(CHANGE);
                                       ischanged = true;
                                       Looper.loop();
                                       timer.cancel();
                                       return;
                                   }
                                   if (count >= 3 & !ischanged) {

                                   }
                                   if (count < 3 & count % 2 == 0) {
                                       moveX += (PERIOD * tableWidth) / (TOTAL_MOVE * ONE_TURN_SENCOND);
                                   } else if (count < 3 & count % 2 != 0) {
                                       moveX -= (PERIOD * tableWidth) / (TOTAL_MOVE * ONE_TURN_SENCOND);
                                   }
                               }
                               handler.sendEmptyMessage(MOVE);
                           }
                       }

                , 1000, PERIOD);
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
                        ic_wings[n]));
            } else {
                bitmapDrawable = ((BitmapDrawable) getResources().getDrawable(
                        ic_wings[n]));
            }
            n++;
            n = n % 8;
            if (ischanged & bitmapDrawable != null) {
                if (bitmapDrawable.getBitmap() != null) {
                    Bitmap dstbmp = rotateBitmap(bitmapDrawable.getBitmap(), 90);
                    canvas.drawBitmap(dstbmp, rect, rectF, null);
                    return;
                }
            }
            canvas.drawBitmap(bitmapDrawable.getBitmap(), rect, rectF, null);
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, float degrees) {

        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(1f, 1f);
        // 向左旋转degrees度，参数为正则向右旋转
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
}
