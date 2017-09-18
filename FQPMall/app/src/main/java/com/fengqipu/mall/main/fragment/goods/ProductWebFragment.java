package com.fengqipu.mall.main.fragment.goods;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.tools.WebViewUtil;
import com.fengqipu.mall.view.ProductWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

//产品详情webview
public class ProductWebFragment extends BaseFragment {
    @Bind(R.id.web_view_helper_web_one)
    ProductWebView webViewHelperWebOne;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    private View mView;
    private static GoodsDetailActivity goodsDetailActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        goodsDetailActivity = (GoodsDetailActivity) getActivity();
        mView = inflater.inflate(R.layout.fragment_product_web, null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initError();
    }

    private void init() {
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setResistance(1.7f);
        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshLayout.setDurationToClose(200);
        refreshLayout.setDurationToCloseHeader(1000);
        // default is false
        refreshLayout.setPullToRefresh(false);
        // default is true
        refreshLayout.setKeepHeaderWhenRefresh(true);

        refreshLayout.disableWhenHorizontalMove(true);

        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, webViewHelperWebOne, header);
            }
        });
//        initData();
        webViewHelperWebOne.setOnScrollChangeListener(new ProductWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goodsDetailActivity.changePager(2);
                    }
                },200);

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    refreshLayout.refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
        goodsDetailActivity.initData();

    }

    private void initError() {

    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if(tag.equals("REFRESH")){
                String url=goodsDetailActivity.goodsDetailResponse.getContent().getDescriptionLink();
                if(url!=null&&!url.equals("")) {
                    WebViewUtil.initWebView(getActivity(), webViewHelperWebOne, url);
                }
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
