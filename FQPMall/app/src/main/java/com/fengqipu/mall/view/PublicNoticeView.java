package com.fengqipu.mall.view;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.index.NoticeListBean;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.index.zfb.NoticeListActivity;
import com.fengqipu.mall.main.base.CommonWebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class PublicNoticeView extends LinearLayout {

    private static final String TAG = "PUBLICNOTICEVIEW";
    private Context mContext;
    private ViewFlipper mViewFlipper;
    private View mScrollTitleView;

    public PublicNoticeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PublicNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        bindLinearLayout();
    }

    /**
     * 初始化自定义的布局
     */
    private void bindLinearLayout() {
        mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.scrollnoticebar, null);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(mScrollTitleView, params);
        mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.id_scrollNoticeTitle);
        mScrollTitleView.findViewById(R.id.right_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticeList1.size()>0) {
                    mContext.startActivity(new Intent(mContext, NoticeListActivity.class));
                }
            }
        });
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        mViewFlipper.startFlipping();
    }

    /**
     * 网络请求内容后进行适配
     */
    List<NoticeListBean> noticeList1 =new ArrayList<>();
    public void bindNotices(final List<NoticeListBean> noticeList) {
        mViewFlipper.removeAllViews();
        noticeList1.clear();
        noticeList1.addAll(noticeList);
        int i = 0;
        while (i < noticeList.size()) {
            RelativeLayout showView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_notice_view, null);
            TextView btv = (TextView) showView.findViewById(R.id.b_tv);
            btv.setText(noticeList.get(i).getTitle());
            btv.setTextSize(14);
            final NoticeListBean bean = noticeList.get(i);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            mViewFlipper.addView(showView, layoutParams);
            showView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (bean.getType().equals("1") || bean.getType().equals("2")) {
                        //打开webView
                        Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, bean.getTitle());
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
                        mContext.startActivity(intent);
//                    } else if (bean.getType().equals("3")) {
//                        bean.getContentID();
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, ProductActy.class);
//                        intent.putExtra(IntentCode.contentID, bean.getContentID());
//                        mContext.startActivity(intent);
//                    }
                }
            });
            i++;
        }
    }

}
