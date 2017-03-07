package com.training.others.drag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.training.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqiuyi on 17/3/1.
 */

public class DragTestActivity extends AppCompatActivity implements View.OnLongClickListener {
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.v5)
    View v5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_test);
        ButterKnife.bind(this);
        init();
    }

    private String dragViewName = "";
    private String dropViewName = "";

    private void init() {
        v1.setTransitionName("v1");
        v2.setTransitionName("v2");
        v3.setTransitionName("v3");
        v4.setTransitionName("v4");
        v5.setTransitionName("v5");

        v1.setOnLongClickListener(this);
        v1.setOnDragListener(new MyOnDragListener());
        v2.setOnLongClickListener(this);
        v2.setOnDragListener(new MyOnDragListener());
        v3.setOnLongClickListener(this);
        v3.setOnDragListener(new MyOnDragListener());
        v4.setOnLongClickListener(this);
        v4.setOnDragListener(new MyOnDragListener());
        v5.setOnLongClickListener(this);
        v5.setOnDragListener(new MyOnDragListener());
    }

    private String initAndStartDrag(View v) {
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        ClipData dragData = new ClipData((CharSequence) v.getTag(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(dragData, shadowBuilder, null, 0);
        return v.getTransitionName();
    }

    @Override
    public boolean onLongClick(View v) {
        dragViewName = initAndStartDrag(v);
        return false;
    }

    private FrameLayout.LayoutParams dragParams, dropParams;

    private View dragView, dropView;

    private class MyOnDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    dropParams = null;
                    if (v.getTransitionName().equals(dragViewName)) {
                        dragView = v;
                        dragParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                        v.setVisibility(View.GONE);
                    }
                    showLogger(v.getTransitionName() + "start");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    showLogger(v.getTransitionName() + "entered");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    showLogger(v.getTransitionName() + "location");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    showLogger(v.getTransitionName() + "exited");
                    break;
                case DragEvent.ACTION_DROP:
                    dropViewName = v.getTransitionName();
                    if (!dragViewName.equals(dropViewName)) {
                        dropParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                        dropView = v;
                        showLogger(v.getTransitionName() + "drop");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (v.getTransitionName().equals(dragViewName)) {
                        v.setVisibility(View.VISIBLE);
                    }
                    if (dropParams != null) {
                        if (v.getTransitionName().equals(dragViewName)) {
                            v.setLayoutParams(dropParams);
                        } else if (v.getTransitionName().equals(dropViewName)) {
//                            v.setLayoutParams(dragParams);
                            startAnimation();
                        }
                    }
                    showLogger(v.getTransitionName() + "ended");
                    break;
            }
            return false;
        }
    }

    private void showLogger(String message) {
        Logger.e(message);
    }

    private void startAnimation() {
        if (dropView != null & dragView != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, dragView.getX() - dropView.getX(), 0, dragView.getY() - dropView.getY());
            Logger.e("dragX: " + dragView.getX() + " dragY: " + dragView.getX() + " dropX: " + dropView.getX() + " dropY: " + dropView.getY());
            translateAnimation.setDuration(100);
            dropView.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dropView.setLayoutParams(dragParams);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
