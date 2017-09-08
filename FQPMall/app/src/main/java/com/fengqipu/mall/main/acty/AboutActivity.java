package com.fengqipu.mall.main.acty;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.R;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.HeadView;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initTitle();
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("关于我们");
        headView.setHiddenRight();
        headView.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void initView() {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }
}
