package com.fengqipu.mall.main.acty.enterprise;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.search.CityResponse;
import com.fengqipu.mall.bean.shop.GoodsEnterpriseResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.dialog.ShaiXuanDialog;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.acty.index.zfb.NoticeListActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.ScrollBottomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.fengqipu.mall.R.id.btn_list_type;
import static com.fengqipu.mall.R.id.btn_sx;
import static com.fengqipu.mall.R.id.index_banner;

/*
 *企业商品页面
 */
public class GoodsEnterpriseActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.btn_zh)
    TextView btnZh;
    @Bind(R.id.btn_xl)
    TextView btnXl;
    @Bind(R.id.btn_jg)
    TextView btnJg;
    @Bind(btn_sx)
    TextView btnSx;
    @Bind(R.id.btn_list_type)
    ImageView btnListType;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.my_gridview)
    MyGridView myGridview;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.scrollView)
    ScrollBottomScrollView scrollView;
    @Bind(index_banner)
    ImageView indexBanner;
    @Bind(R.id.content_name)
    TextView contentName;

    private CommonAdapter<GoodsEnterpriseResponse.ContentListBean> lAdapter;
    private CommonAdapter<GoodsEnterpriseResponse.ContentListBean> gAdapter;
    private List<GoodsEnterpriseResponse.ContentListBean> goodsList = new ArrayList<>();

    int pageNum = 1;
    int pageSize = 10;
    private boolean isloading = false;
    private int tolalNum = 0;

    String contentType;
    String category2;
    String model;
    String picurl;
    String name;
    String contentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsenterprise);
        ButterKnife.bind(this);
        contentType = getIntent().getStringExtra("contentType");
        category2 = getIntent().getStringExtra("category2");
        model = getIntent().getStringExtra("model");
        picurl = getIntent().getStringExtra("picurl");
        name = getIntent().getStringExtra("name");
        contentID = getIntent().getStringExtra("contentID");
        initAll();
    }

    private void initData() {
        pageNum = 1;
        NetLoadingDialog.getInstance().loading(this);
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

    private String city="";
    private String minPrice="";
    private String maxPrice="";

    private void initBtmList() {
//        myLoading.setVisibility(View.GONE);
//        myListview.loadComplete();
//        GoodsBean g1=new GoodsBean();
//        GoodsBean g2=new GoodsBean();
//        GoodsBean g3=new GoodsBean();
//        GoodsBean g4=new GoodsBean();
//        GoodsBean g5=new GoodsBean();
//        goodsList.add(g1); goodsList.add(g2);
//        goodsList.add(g3); goodsList.add(g4);
//        goodsList.add(g5);
//        lAdapter.notifyDataSetChanged();
//        gAdapter.notifyDataSetChanged();
        UserServiceImpl.instance().getShopsGList(city,minPrice,maxPrice, contentType, category2, model, order + "", jgtype + "", pageNum, pageSize, GoodsEnterpriseResponse.class.getName());
    }

    @Override
    public void initView() {
        initTitle();
    }

    @Override
    public void initViewData() {
        if (picurl != null && !picurl.equals("")) {
            GeneralUtils.setImageViewWithUrl(mContext, picurl, indexBanner, R.drawable.bg_banner_classification);
        }
        if (name != null && !name.equals("")) {
            contentName.setText(name+"");
        }
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
//                if (myListview.getVisibility() == View.VISIBLE) {
//                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, myListview, header);
//                } else {
//                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, myGridview, header);
//                }
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }
        });
        lAdapter = new CommonAdapter<GoodsEnterpriseResponse.ContentListBean>(GoodsEnterpriseActivity.this, goodsList, R.layout.item_his_g) {
            @Override
            public void convert(ViewHolder helper, GoodsEnterpriseResponse.ContentListBean item) {
                helper.setText(R.id.goods_info, item.getContentName());
                helper.setText(R.id.goods_price, "￥" + item.getPrice());
                helper.setText(R.id.goods_time,""+item.getCreateTime());
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl1RequestUrl())) {
                    ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                    GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1RequestUrl(), img, R.drawable.bg_image_classification);
                }
            }
        };
        gAdapter = new CommonAdapter<GoodsEnterpriseResponse.ContentListBean>(GoodsEnterpriseActivity.this, goodsList, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, GoodsEnterpriseResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(GoodsEnterpriseActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.bg_image_classification);
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
        myListview.setAdapter(lAdapter);
        myGridview.setAdapter(gAdapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsEnterpriseResponse.ContentListBean item=(GoodsEnterpriseResponse.ContentListBean)adapterView.getItemAtPosition(i);
                Intent intent=new Intent(GoodsEnterpriseActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID",item.getId());
                startActivity(intent);
            }
        });
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsEnterpriseResponse.ContentListBean item=(GoodsEnterpriseResponse.ContentListBean)adapterView.getItemAtPosition(i);
                Intent intent=new Intent(GoodsEnterpriseActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID",item.getId());
                startActivity(intent);
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
        indexBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsEnterpriseActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID",contentID);
                startActivity(intent);
            }
        });
        initData();
    }

    @Override
    public void initEvent() {
        btnListType.setOnClickListener(this);
        btnZh.setOnClickListener(this);
        btnXl.setOnClickListener(this);
        btnJg.setOnClickListener(this);
        btnSx.setOnClickListener(this);
    }

    int jgtype = 0;
    private int order = 1;
    ShaiXuanDialog shaiXuanDialog;
    private int listtype=1;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_list_type:
                if (myListview.getVisibility() == View.VISIBLE) {
                    listtype=0;
                    btnListType.setImageResource(R.mipmap.search_sort_lv);
                    myListview.setVisibility(View.GONE);
                    myGridview.setVisibility(View.VISIBLE);
                } else {
                    listtype=1;
                    btnListType.setImageResource(R.mipmap.search_sort_gv);
                    myListview.setVisibility(View.VISIBLE);
                    myGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_zh:
                initTopBtn();
                btnZh.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw3 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw3.setBounds(0, 0, arraw3.getMinimumWidth(), arraw3.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw3, null);
                jgtype = 0;
                Drawable nav_original = getResources().getDrawable(R.mipmap.price_original);
                nav_original.setBounds(0, 0, nav_original.getMinimumWidth(), nav_original.getMinimumHeight());
                btnJg.setCompoundDrawables(null, null, nav_original, null);
                order = 1;
                initData();
                break;
            case R.id.btn_xl:
                initTopBtn();
                btnXl.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw2 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw2.setBounds(0, 0, arraw2.getMinimumWidth(), arraw2.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw2, null);
                jgtype = 0;
                Drawable nav_original1 = getResources().getDrawable(R.mipmap.price_original);
                nav_original1.setBounds(0, 0, nav_original1.getMinimumWidth(), nav_original1.getMinimumHeight());
                btnJg.setCompoundDrawables(null, null, nav_original1, null);
                order = 2;
                initData();
                break;
            case R.id.btn_jg:
                initTopBtn();
                btnJg.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw1 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw1.setBounds(0, 0, arraw1.getMinimumWidth(), arraw1.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw1, null);
                if (jgtype == 1) {
                    jgtype = 2;
                    Drawable nav_up = getResources().getDrawable(R.mipmap.price_up);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_up, null);
                } else if (jgtype == 2) {
                    jgtype = 1;
                    Drawable nav_down = getResources().getDrawable(R.mipmap.price_down);
                    nav_down.setBounds(0, 0, nav_down.getMinimumWidth(), nav_down.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_down, null);
                } else {
                    jgtype = 1;
                    Drawable nav_down = getResources().getDrawable(R.mipmap.price_down);
                    nav_down.setBounds(0, 0, nav_down.getMinimumWidth(), nav_down.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_down, null);
                }
                order = 4;
                initData();
                break;
            case btn_sx://筛选
//                initTopBtn();
//                btnSx.setTextColor(getResources().getColor(R.color.app_color));
//                Drawable arraw = getResources().getDrawable(R.mipmap.icon_arrow_red);
//                arraw.setBounds(0, 0, arraw.getMinimumWidth(), arraw.getMinimumHeight());
//                btnSx.setCompoundDrawables(null, null, arraw, null);
//                jgtype = 0;
//                Drawable nav_original2 = getResources().getDrawable(R.mipmap.price_original);
//                nav_original2.setBounds(0, 0, nav_original2.getMinimumWidth(), nav_original2.getMinimumHeight());
//                btnJg.setCompoundDrawables(null, null, nav_original2, null);
                if(shaiXuanDialog==null&&cityResponse!=null) {
                    shaiXuanDialog = new ShaiXuanDialog(GoodsEnterpriseActivity.this, cityResponse,1);
                }
                if(shaiXuanDialog!=null){
                    shaiXuanDialog.show();
                    shaiXuanDialog.setBtnCzListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaiXuanDialog.minPrice.setText("");
                            shaiXuanDialog.maxPrice.setText("");
                            shaiXuanDialog.fenleiTv.setText("全部");
                            category2="";
                            minPrice="";
                            maxPrice="";
                            shaiXuanDialog.selID="";
                            shaiXuanDialog.selName="";
                            shaiXuanDialog.mAdapter.notifyDataSetChanged();
                        }
                    });
                    shaiXuanDialog.setBtnConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            minPrice=shaiXuanDialog.minPrice.getText().toString();
                            maxPrice=shaiXuanDialog.maxPrice.getText().toString();
                            city=shaiXuanDialog.selName;
                            initData();
                            shaiXuanDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }
    private void initTopBtn() {
        btnZh.setTextColor(Color.parseColor("#4A4A4A"));
        btnXl.setTextColor(Color.parseColor("#4A4A4A"));
        btnJg.setTextColor(Color.parseColor("#4A4A4A"));
        btnSx.setTextColor(Color.parseColor("#4A4A4A"));
    }
    GoodsEnterpriseResponse goodsEnterpriseResponse;
    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isLogin()){
                    startActivity(new Intent(GoodsEnterpriseActivity.this, NoticeListActivity.class));
                }else {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(GoodsEnterpriseResponse.class.getName())) {
                goodsEnterpriseResponse = GsonHelper.toType(result, GoodsEnterpriseResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(goodsEnterpriseResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        isloading = false;
                        tolalNum = goodsEnterpriseResponse.getTotalCount();
                        if (goodsEnterpriseResponse.getContentList() != null && goodsEnterpriseResponse.getContentList().size() > 0) {
                            goodsList.addAll(goodsEnterpriseResponse.getContentList());
                            gAdapter.setData(goodsList);
                            lAdapter.setData(goodsList);
                            gAdapter.notifyDataSetChanged();
                            lAdapter.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myListview);
                            CommonMethod.setListViewHeightBasedOnChildren(myGridview);
                            if (listtype == 0) {
                                listtype=0;
                                btnListType.setImageResource(R.mipmap.search_sort_lv);
                                myListview.setVisibility(View.GONE);
                                myGridview.setVisibility(View.VISIBLE);
                            } else {
                                listtype=1;
                                btnListType.setImageResource(R.mipmap.search_sort_gv);
                                myListview.setVisibility(View.VISIBLE);
                                myGridview.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ErrorCode.doCode(this, goodsEnterpriseResponse.getResultCode(), goodsEnterpriseResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(CityResponse.class.getName())) {
                cityResponse = GsonHelper.toType(result, CityResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(cityResponse.getResultCode())) {
                    } else {
                        ErrorCode.doCode(this, cityResponse.getResultCode(), cityResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("企业商品");
        headView.setRightImage(R.mipmap.btn_information_sort);
    }

    CityResponse cityResponse;
    @Override
    public void onResume() {
        super.onResume();
        UserServiceImpl.instance().getHotCity(CityResponse.class.getName());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
