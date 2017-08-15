package com.training.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenqiuyi on 17/1/11.
 */

public class LinePieView extends View {
//    private List<Integer> colors;
//    private List<String> labels;
//    private List<Float> values;

    private float value1 = 200, value2 = 100;
    private int viewWidth, viewHeight;

    private int ringColor = 0xff000000;

    public LinePieView(Context context) {
        this(context, null);
    }

    public LinePieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView(canvas);
    }

    private void initView(Canvas canvas) {
        viewWidth = getWidth();
        viewHeight = getHeight();
        float cirX = viewWidth / 2;
        float cirY = viewHeight / 2;
        float radius = 300;
        RectF rectF = new RectF(cirX - radius, cirY - radius, cirX + radius, cirY + radius);
        RectF ringRectF = new RectF(cirX - radius - 20, cirY - radius - 20, cirX + radius + 20, cirY + radius + 20);

        float percent1 = value1 / (value1 + value2) * 360;
        percent1 = (float) Math.round(percent1 * 100) / 100;
        float percent2 = value2 / (value1 + value2) * 360;
        percent2 = (float) Math.round(percent2 * 100) / 100;

        Paint paintRing = new Paint();

        Paint paintArc = new Paint();
        paintArc.setColor(0xff000000);
        canvas.drawArc(ringRectF, 0, 360, true, paintArc);


        paintArc.setColor(0xffe7ea87);
        canvas.drawArc(rectF, 0, percent1, true, paintArc);
        paintArc.setColor(0xff14adab);
        canvas.drawArc(rectF, percent1, percent2, true, paintArc);


        int lx1;
        int ly1;
        if (percent1 > 180) {
            lx1 = (int) getDegress(90, 0, radius);
            ly1 = (int) getDegress(90, 1, radius);
        } else {
            lx1 = (int) getDegress(percent1, 0, radius);
            ly1 = (int) getDegress(percent1, 1, radius);
        }
        int lx2 = (int) getDegress(percent1, 0, radius);
        int ly2 = (int) getDegress(percent1, 1, radius);

        paintArc.setColor(0xff000000);

        lx1 = (int) (cirX + (lx1 * 5 / 6));
        ly1 = (int) (cirY + (ly1 * 5 / 6));

//        lx2 = (int) (cirX - (lx2 * 3 / 4));
//        ly2 = (int) (cirY - (ly2 * 3 / 4));
//        lx2 = (int) (cirX - (radius * 5 / 6));
//        ly2 = (int) (cirY);

        int linex1 = 80;
        int liney1 = 120;
        int linex2 = 120;
        int liney2 = 120;

        canvas.drawLine(lx1, ly1, lx1 + linex1, ly1 + liney1, paintArc);
        canvas.drawLine(lx1 + linex1, ly1 + liney1, lx1 + linex1 + linex2, ly1 + liney1, paintArc);

        canvas.drawLine(lx2, ly2, lx2 - linex2, ly2 + liney2, paintArc);
        canvas.drawLine(lx2 - linex2, ly2 + liney2, lx2 - linex1 - linex2, ly2 + liney2, paintArc);

    }

    private double getDegress(double mDegress, int type, double radius) {
        if (mDegress < 180) {
            if (type == 0) {
                return Math.cos(Math.PI * (mDegress) / 180 / 2) * radius;
            }
        } else {
            if (type == 0) {
                return Math.cos(Math.PI * (mDegress) / 180 / 2) * radius;
            }
        }
        return Math.abs(Math.sin(Math.PI * (mDegress) / 180 / 2) * radius);
    }
}
