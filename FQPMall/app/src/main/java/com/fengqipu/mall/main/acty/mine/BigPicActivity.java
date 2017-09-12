package com.fengqipu.mall.main.acty.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fengqipu.mall.R;
import com.fengqipu.mall.main.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BigPicActivity extends BaseActivity {

    @Bind(R.id.my_viewpager)
    ViewPager myViewpager;
    @Bind(R.id.my_dots)
    LinearLayout myDots;
    @Bind(R.id.activity_big_pic)
    FrameLayout activityBigPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        activityBigPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
