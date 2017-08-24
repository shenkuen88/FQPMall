package com.fengqipu.mall.main.fragment.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.shop.OntheNewShopListResponse;
import com.fengqipu.mall.bean.shop.ZongHeShopListResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.enterprise.EnterpriseActivity;
import com.fengqipu.mall.main.acty.enterprise.GoodsEnterpriseActivity;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.ScrollBottomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by Administrator on 2016/6/13.促销
 */
public class OntheNewFragment extends BaseFragment implements View.OnClickListener {
    private static EnterpriseActivity enterpriseActivity;
    @Bind(R.id.my_gridview)
    MyGridView myGridview;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.scrollView)
    ScrollBottomScrollView scrollView;

    int pageNum = 1;
    int pageSize = 10;
    @Bind(R.id.emtry_ll)
    LinearLayout emtryLl;
    private boolean isloading = false;
    private int tolalNum = 0;

    private CommonAdapter<OntheNewShopListResponse.ContentListBean> gAdapter;
    private List<OntheNewShopListResponse.ContentListBean> goodsList = new ArrayList<>();

    public OntheNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
//            if (scrollview != null) {
//                scrollview.scrollTo(0, 0);
//            }
        }

        //   scrollview.scrollTo(0,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        enterpriseActivity = (EnterpriseActivity) getActivity();
        View v = LayoutInflater.from(enterpriseActivity).inflate(R.layout.fragment_onthenewgoods, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }
        });
        gAdapter = new CommonAdapter<OntheNewShopListResponse.ContentListBean>(enterpriseActivity, goodsList, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, OntheNewShopListResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(enterpriseActivity, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
                }
                title.setText("" + item.getContentName());
                location.setText("" + item.getShopProvince() + " " + item.getShopCity());
                if (item.getMonthSales() != null && !item.getMonthSales().equals("")) {
                    xl.setText("月销量" + item.getMonthSales() + "笔");
                } else {
                    xl.setText("月销量0笔");
                }
                price.setText("￥" + item.getPrice());
//                hpd.setText("好评度"+item.getAppraiseCount());
            }
        };
        myGridview.setAdapter(gAdapter);
        myGridview.setEmptyView(emtryLl);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZongHeShopListResponse.ContentListBean item = (ZongHeShopListResponse.ContentListBean) adapterView.getItemAtPosition(i);
                if (Global.getuserType().equals("1")) {
                    Intent intent = new Intent(enterpriseActivity, GoodsEnterpriseActivity.class);
                    intent.putExtra("contentID", item.getId());
                    intent.putExtra("contentType", item.getContentType());
                    intent.putExtra("category2", item.getCategory2());
                    intent.putExtra("model", item.getModel());
                    intent.putExtra("picurl", item.getPicUrl1RequestUrl());
                    intent.putExtra("name", item.getContentName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(enterpriseActivity, GoodsDetailActivity.class);
                    intent.putExtra("contentID", item.getId());
                    startActivity(intent);
                }
            }
        });
        scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom() {
                if (isloading) return;
                if (pageNum * pageSize >= tolalNum) return;
                isloading = true;
                pageNum = pageNum + 1;
                initBtmList();
            }
        });
        initData();
    }

    private void initData() {
        //请求底部列表接口
        pageNum = 1;
        initBtmList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    refreshLayout.refreshComplete();
                } catch (Exception e) {
                }
            }
        }, 2000);
    }

    private void initBtmList() {
//        myLoading.setVisibility(View.GONE);
//        GoodsBean g1 = new GoodsBean();
//        GoodsBean g2 = new GoodsBean();
//        GoodsBean g3 = new GoodsBean();
//        GoodsBean g4 = new GoodsBean();
//        GoodsBean g5 = new GoodsBean();
//        goodsList.add(g1);
//        goodsList.add(g2);
//        goodsList.add(g3);
//        goodsList.add(g4);
//        goodsList.add(g5);
//        gAdapter.notifyDataSetChanged();
        NetLoadingDialog.getInstance().loading(enterpriseActivity);
        UserServiceImpl.instance().getShopsList(enterpriseActivity, enterpriseActivity.sid, "3", "", "", pageNum, pageSize, OntheNewShopListResponse.class.getName());
    }

    public float scaleWidth;
    public float scaleHeight;
    public int windowWidth = 0;
    public int windowHeight = 0;

    public void setWindow() {
        if (windowWidth > 0 && windowHeight > 0) {
            return;
        }
        try {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            windowWidth = dm.widthPixels;
            windowHeight = dm.heightPixels;
            scaleWidth = (float) windowWidth / 720f;
            scaleHeight = (float) windowHeight / 1280f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(OntheNewShopListResponse.class.getName())) {
                OntheNewShopListResponse ontheNewShopListResponse = GsonHelper.toType(result, OntheNewShopListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(ontheNewShopListResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        isloading = false;
                        tolalNum = ontheNewShopListResponse.getTotalCount();
                        if (ontheNewShopListResponse.getContentList() != null && ontheNewShopListResponse.getContentList().size() > 0) {
                            goodsList.addAll(ontheNewShopListResponse.getContentList());
                            gAdapter.setData(goodsList);
                            gAdapter.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myGridview);
                        }
                    } else {
                        ErrorCode.doCode(enterpriseActivity, ontheNewShopListResponse.getResultCode(), ontheNewShopListResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(enterpriseActivity);
                }
            }
        }
    }
}
