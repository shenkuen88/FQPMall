package com.fengqipu.mall.main.acty.goods;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.tools.WebViewUtil;
import com.fengqipu.mall.view.ProductWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class GoodsWebActivity extends BaseActivity {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.web_view_helper_web_one)
    ProductWebView webViewHelperWebOne;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        if (url == null) {
            url = "";
        }
        initAll();
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("商品图文介绍");
        headView.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        initTitle();
        View view = getLayoutInflater().inflate(R.layout.head_view_2, null);
        refreshLayout.setHeaderView(view);
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
                finish();
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, webViewHelperWebOne, header);
            }
        });
        if(url!=null&&!url.equals("")) {
            WebViewUtil.initWebView(this, webViewHelperWebOne, url);
        }
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_down_in, R.anim.anim_down_out);
    }
}
