package com.demo.suctionview.activity;

import android.view.View;
import android.widget.Toast;

import com.demo.suctionview.R;
import com.demo.suctionview.widgets.DragViewLayout;

/**
 * Created by Administrator on 2017/3/2.
 */
public class MainActivity extends InitHeadBaseActivity implements View.OnClickListener {

    private DragViewLayout dragView;

    public int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        findViews();
        initEvent();
    }

    private void findViews() {
        this.dragView = findViewById(R.id.draglayout);
    }

    protected void initEvent() {
        this.dragView.mTestSuction.setOnClickListener(this);
        this.dragView.mTestPosition.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_suction:
                Toast.makeText(MainActivity.this, getString(R.string.show_number), Toast.LENGTH_LONG).show();
                return;
            case R.id.tv_position:
                Toast.makeText(MainActivity.this, getString(R.string.show_location), Toast.LENGTH_LONG).show();
                return;
            default:
                return;
        }
    }

}
