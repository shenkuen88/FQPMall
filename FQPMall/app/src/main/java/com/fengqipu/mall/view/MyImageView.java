package com.fengqipu.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fengqipu.mall.constant.Constants;


public class MyImageView extends ImageView
{

    public MyImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = width * Constants.HEIGHT_BANNER / Constants.WIDTH_BANNER;
        setMeasuredDimension(width, height);
    }

}
