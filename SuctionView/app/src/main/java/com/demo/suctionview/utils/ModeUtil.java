package com.demo.suctionview.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ModeUtil {

	//修改MIUI
	public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
		Class<? extends Window> clazz = activity.getWindow().getClass();
		try {
			int darkModeFlag = 0;
			Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

    @TargetApi(19)
    protected void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
