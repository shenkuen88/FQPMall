package com.fengqipu.mall.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.fengqipu.mall.R;


public class RefreshListView extends ListView {
    //底部View
    private View footerView;
    //是否加载标示
    boolean isLoading = false;
//    private ImageView footload;
//    private Animation roteanim;

    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 初始化ListView
     */
    private void initView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        footerView = mInflater.inflate(R.layout.footer, null);
//        roteanim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        roteanim.setDuration(1500);
//        roteanim.setInterpolator(new LinearInterpolator());// 不停顿
//        roteanim.setRepeatCount(-1);
//        roteanim.setFillAfter(true);// 停在最后
//        footload = (ImageView) footerView.findViewById(MResource.getIdByName(context, "footload"));
        footerView.setVisibility(View.GONE);
        //添加底部View
        this.addFooterView(footerView);
    }

    /**
     * 显示加载页
     */
    public void showload() {
        isLoading = true;
//        footload.startAnimation(roteanim);
        footerView.setVisibility(View.VISIBLE);
    }

    /**
     * 数据加载完成
     */
    public void loadComplete() {
        footerView.setVisibility(View.GONE);
//        footload.clearAnimation();
        isLoading = false;
        this.invalidate();
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
