package com.toughen.libs.tools;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Toast  utils
 * Created by lijianjian on 2018/4/2.
 */

public class ToastUtils {
    private static Toast shortTast;
    private static Toast longTast;
    private static Toast userToast;//用户自定义Toast

    private ToastUtils() {
    }

    public static void showShort(Context context, String showValue) {
        if (shortTast != null) {
            shortTast.cancel();
            shortTast = null;
        }
        shortTast = Toast.makeText(context, showValue, Toast.LENGTH_SHORT);
        shortTast.show();
    }

    public static void showLong(Context context, String showValue) {
        if (longTast != null) {
            longTast.cancel();
            longTast = null;
        }
        longTast = Toast.makeText(context, showValue, Toast.LENGTH_LONG);
        longTast.show();
    }

    public static void showUserToast(Context context, View view, int gravity, int xOffset, int yOffset, int showDuration) {
        if (userToast != null) {
            userToast.cancel();
            userToast = null;
        }
        userToast = new Toast(context);
        userToast.setView(view);
        userToast.setGravity(gravity, xOffset, yOffset);
        userToast.setDuration(showDuration);
        userToast.show();
    }

    public static void destory() {
        if (userToast != null) {
            userToast.cancel();
            userToast = null;
        }
        if (longTast != null) {
            longTast.cancel();
            longTast = null;
        }
        if (shortTast != null) {
            shortTast.cancel();
            shortTast = null;
        }
    }
}
