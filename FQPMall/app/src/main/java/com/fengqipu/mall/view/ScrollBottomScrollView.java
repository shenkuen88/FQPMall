package com.fengqipu.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollBottomScrollView extends ScrollView {
  
    private ScrollBottomListener scrollBottomListener;  

    private OnScrollChangeListener scrollChangeListener;

    public ScrollBottomScrollView(Context context) {
        super(context);  
    }  
  
    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
    public ScrollBottomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);  
    }
//    /**
//     * 解决 由于子控件的大小导致ScrollView滚动到底部的问题
//     *
//     * @param rect
//     * @return
//     */
//    @Override
//    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
//        return 0;
//    }
    @Override  
    protected void onScrollChanged(int l, int t, int oldl, int oldt){

        if(scrollBottomListener!=null){
            try {
                scrollChangeListener.scrollChange(t);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        if(t + getHeight() >=  computeVerticalScrollRange()){  
            //ScrollView滑动到底部了
            try {
                scrollBottomListener.scrollBottom();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener){
        this.scrollBottomListener = scrollBottomListener;  
    }


    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.scrollChangeListener = onScrollChangeListener;
    }

    public interface ScrollBottomListener{
        void scrollBottom();

    }

    public interface OnScrollChangeListener{
        void scrollChange(int y);
    }
}  
