package com.fengqipu.mall.main.fragment.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.WebViewUtil;

/**
 * 产品详情
 * community_zone_item 动态展示item
 * community_zone_item 动态展示item
 */
public class ProductBottomWebFragment extends BaseFragment implements View.OnClickListener {


    private View mView;
    private WebView webViewOne;
    private String urlOne = "";


    public static ProductBottomWebFragment newInstance() {
        ProductBottomWebFragment fragment = new ProductBottomWebFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_bottom_product_web, null);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    public void onEventMainThread(BaseResponse event) {

        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_WEB_VIEW_ERROR.equals(tag)) {
            }
            if (NotiTag.TAG_WEB_VIEW_START.equals(tag)) {

            }
            if (NotiTag.TAG_WEB_VIEW_FINISH.equals(tag)) {

            }

//            if (NotiTag.TAG_ERROR_VIEW_DETAIL.equals(tag))
//            {
//                webViewOne.loadUrl(urlOne);// 载入网页
//                webViewOne.postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        webViewOne.clearHistory();
//                    }
//                }, 500);
//
//            }
        if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
        }
    }

    if(event instanceof NetResponseEvent)

    {
        NetLoadingDialog.getInstance().dismissDialog();
        String tag = ((NetResponseEvent) event).getTag();
        String result = ((NetResponseEvent) event).getResult();
    }

}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_tv:
                break;
        }
    }

    private void initData(int mPageNo) {
        NetLoadingDialog.getInstance().loading(getActivity());
//        UserServiceImpl.instance().getCommunityList(getActivity(), communityId, mPageNo, pageSize, PublicCommunityResponse.class.getName());
    }

    private void initView() {
        webViewOne = (WebView) mView.findViewById(R.id.web_view_helper_web_one);
        WebViewUtil.initWebView(getActivity(), webViewOne, urlOne);
    }


    public void loadUrl(String urlOneNew){
        if (GeneralUtils.isNotNullOrZeroLenght(urlOneNew)){
            webViewOne.loadUrl(urlOneNew);// 载入网页
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        webViewOne.clearHistory();
    }

}
