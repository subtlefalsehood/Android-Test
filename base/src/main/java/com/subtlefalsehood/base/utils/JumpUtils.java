package com.subtlefalsehood.base.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class JumpUtils {
    public static void jumpFragment(FragmentManager fragmentManager, @IdRes int resId, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(resId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void jumpFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, "123");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void startActivity(Context context, Class cls, boolean isFinish) {
        Intent intent = new Intent(context, cls);
        if (isFinish) {
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            context.startActivity(intent);
        }
        context = null;
    }

    public static void setClick(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showSnack(View layout, String content) {
        Snackbar.make(layout, content, Snackbar.LENGTH_LONG).show();
    }
}
