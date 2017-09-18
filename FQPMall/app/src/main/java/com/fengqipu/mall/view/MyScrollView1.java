package com.fengqipu.mall.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView1 extends ScrollView {

    private boolean isScrolledToTop;
    private boolean isScrolledToBottom;
    private IScrollChangedListener mScrollChangedListener;

    public MyScrollView1(Context context) {
        super(context);
    }

    public MyScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 解决 由于子控件的大小导致ScrollView滚动到底部的问题
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
    public void setScrollViewListener(IScrollChangedListener scrollViewListener) {
        this.mScrollChangedListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // We take the last son in the scrollview
        View view = getChildAt(0);
        if (view == null)
            return;
        int diff = (view.getBottom() - (getHeight() + getScrollY()));
//        System.out.println("diff= " + diff
//                + ",view.getBottom()=" + view.getBottom()
//                + ",this.getHeight()=" + getHeight()
//                + ",this.getScrollY()=" + getScrollY());
        // if diff is zero, then the bottom has been reached
        isScrolledToBottom = false;
        isScrolledToTop = false;
        if (diff == 0) {
            isScrolledToBottom = true;
            if (mScrollChangedListener != null)
                mScrollChangedListener.onScrolledToBottom();
        } else if (getScrollY() == 0) {
            isScrolledToTop = true;
            if (mScrollChangedListener != null)
                mScrollChangedListener.onScrolledToTop();
        }
    }

    public boolean isTopEnd() {
        return isScrolledToTop;
    }

    public boolean isBottomEnd() {
        return isScrolledToBottom;
    }

    /**
     * 定义监听接口
     */
    public interface IScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }
}
