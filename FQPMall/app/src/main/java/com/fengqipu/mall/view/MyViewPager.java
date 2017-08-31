package com.fengqipu.mall.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.fengqipu.mall.R;

public class MyViewPager extends ViewPager
{
    private int nowPosition = 0;
    
    private int maxPosition = 0;
    
    private PointF startPoint;//起点手指坐标
    
    private PointF movePoint;//移动手指坐标
    
    public MyViewPager(Context context)
    {
        super(context);
    }
    
    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    @Override
    protected boolean canScroll (View v, boolean checkV, int dx, int x, int y ) {
        MyWebView view = ( MyWebView ) v.findViewById ( R.id.web_view_helper_web_one ) ; //res ID
        if ( view != null ) {
            return view.canScrollHorizontally ( -dx ) ;
        } else {
            return super.canScroll ( v, checkV, dx, x, y ) ;
        }
    }
//    public void getSelectoinPager(int position)
//    {
//        this.nowPosition = position;
//    }
//
//    public void getMaxSelectoinPager(int position)
//    {
//        this.maxPosition = position;
//        startPoint = new PointF();
//        movePoint = new PointF();
//    }
    
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event)
//    {
//        // TODO Auto-generated method stub
//        int action = event.getAction();
//        movePoint.set(event.getX(), event.getY());
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                nowPosition = this.getCurrentItem();
//                startPoint.set(event.getX(), event.getY());
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                if ((nowPosition == maxPosition - 1 && movePoint.x - startPoint.x < 0)
//                    || (nowPosition == 0 && movePoint.x - startPoint.x > 0)
//                    || (Math.abs(movePoint.y - startPoint.y) > Math.abs(movePoint.x - startPoint.x)))
//                {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                else
//                {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }
    
}
