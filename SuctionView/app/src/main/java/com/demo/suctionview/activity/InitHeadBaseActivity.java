package com.demo.suctionview.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.demo.suctionview.R;
import com.demo.suctionview.utils.ModeUtil;
import com.demo.suctionview.utils.SystemBarTintManager;


/**
 * Created by Administrator on 2017/3/2.
 */
public abstract class InitHeadBaseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHead(true);
    }

    protected void initHead(boolean hasStates) {
        if (!hasStates) {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                // headlayout.setPadding(0, AppGlobal.StatusHeight, 0, 0);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.col_transparent);
            // 修改MIUI 状态栏
            ModeUtil.setMiuiStatusBarDarkMode(this, true);
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
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
