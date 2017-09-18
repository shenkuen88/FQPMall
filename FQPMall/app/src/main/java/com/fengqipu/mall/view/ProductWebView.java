package com.fengqipu.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by huqing on 2017/9/12.
 */


public class ProductWebView extends WebView
{

    private boolean mDispachOverScrollEvent = true;
    private boolean mClampedX = false;

    public ProductWebView(Context context) {
        super(context);
        init();
    }

    public ProductWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    public void setDispachOverScrollEvent(boolean should){
        mDispachOverScrollEvent = should;
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        mClampedX = clampedX;// X 轴到边界
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(shouldIntercaptTouchEvent()){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean shouldIntercaptTouchEvent() {
        if(mDispachOverScrollEvent && mClampedX){
            return true;
        }
        return false;
    }
    public OnScrollChangeListener listener;
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
            listener.onPageTop(l, t, oldl, oldt);
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        public void onPageEnd(int l, int t, int oldl, int oldt);
        public void onPageTop(int l, int t, int oldl, int oldt);
        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }
}
