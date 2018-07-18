package com.demo.suctionview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * Created by Administrator on 2017/3/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public View mRootView;
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = mInflater.inflate(getLayoutId(), null);
        setContentView(mRootView);
        mActivity = this;
        initView();
    }

    public abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
