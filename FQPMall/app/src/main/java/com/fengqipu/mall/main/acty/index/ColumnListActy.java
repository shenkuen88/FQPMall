package com.fengqipu.mall.main.acty.index;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.BrandListBean;
import com.fengqipu.mall.bean.index.ColumnContentListResponse;
import com.fengqipu.mall.bean.index.ContentListBean;
import com.fengqipu.mall.bean.index.GetBrandListResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.FlowLayout;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 综合搜索页面
 */
public class ColumnListActy extends BaseActivity implements View.OnClickListener {

    /**
     * 展示ListView  or GridView
     */
    private boolean isList = true;
    private TextView sort_tv, sell_sort_tv, zh_sort_tv, price_sort_tv;
    /**
     * 列表展示orGridView显示
     */
    private ImageView show_type_iv;

    /**
     * 综合排序的弹框
     */
    private PopupWindow popupWindow;
    /**
     * 筛选的弹框
     */
    private PopupWindow choosePW;
    /**
     * 价格的上下箭头
     */
    private Drawable priceDown, priceUp;

    /******************************/
    private String searchWords;
    //输入内容，搜索，清空所有列表之前的列表信息，确定当前页面，进行搜索——>发送通知，搜索关键字

    //点击标签，获取当前的搜索关键字，根据该标签当中的信息，获取数据

    /***************************/
    private MyGridView gvShow;
    private MyListView lvShow;
    private boolean isLoadingMoreData;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private int pageTotal = 0;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private View noDataView;
    private ArrayList<ContentListBean> datas = new ArrayList<>();
    private ArrayList<ContentListBean> datasMore = new ArrayList<>();

    private int sort = 1; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格

    /**
     * 适配器
     */
    private CommonAdapter<ContentListBean> lvAdapter;
    private CommonAdapter<ContentListBean> gvAadapter;
    /**
     * 下拉刷新
     */
    private PtrClassicFrameLayout refreshLayout;
    private LinearLayout llg, llv;
    private String brandId = "";
    private String order = "1";
    private LinearLayout llprice;

    private FlowLayout flBrand;
    private ArrayList<Object> hotList;
    private List<BrandListBean> brandList = new ArrayList<>();

    private int priceOrder = 1;// 1升序，2 降序

    private ImageView ivPriceOrder;
    private String columnId;
    private String titleName;
    private HeadView headView;
    private int serviceType=0;   // 1 在线教育  2 母婴儿童  3 装修  4 全部


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);
        columnId = getIntent().getStringExtra(IntentCode.SEARCH_KEYORD);
        titleName = getIntent().getStringExtra(IntentCode.SEARCH_TITLE);
        serviceType = getIntent().getIntExtra(IntentCode.EXTRA_SERVICETYPE,0);
        initTitle();
        initAll();
        if (GeneralUtils.isNotNullOrZeroLenght(columnId)) {
            llLoading.setVisibility(View.GONE);
            pageNo = 1;
            loadPageNo = pageNo;
            isLoadingMoreData = true;
            initListData();
        }
    }


    /**
     * 是否继续从服务器获取数据
     */
    private boolean ifGet = true;

    private void initDataAgain(boolean bool) {
        ifGet = true;
        pageNo = 1;
        loadPageNo = 1;
    }


    private void initAdapter() {
        lvAdapter = new CommonAdapter<ContentListBean>(mContext, datas, R.layout.item_search_baby_lv) {
            @Override
            public void convert(ViewHolder helper, final ContentListBean item) {
                ImageView good_iv = helper.getView(R.id.good_iv);
                good_iv.setImageResource(R.drawable.default_bg);
//                ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl1(), good_iv, Constants.DEFAULT_GOOD_ICON);
                GeneralUtils.setImageViewWithUrl(mContext, item.getThumPicUrl1(), good_iv, R.drawable.default_bg);
                helper.setText(R.id.name_tv, item.getContentName());
                if (serviceType==2){
                    helper.setText(R.id.price_tv, "¥ " + item.getPrice());
                }else {
                    helper.getView(R.id.price_tv).setVisibility(View.GONE);
                }
                helper.setText(R.id.comment_num_tv, item.getAppraiseCount() + "条评价");
                helper.setText(R.id.provider_tv, item.getShopName());
                helper.getView(R.id.item_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToDetail(item);

                    }
                });
            }
        };
        gvAadapter = new CommonAdapter<ContentListBean>(mContext, datas, R.layout.item_search_baby_gv) {
            @Override
            public void convert(ViewHolder helper, final ContentListBean item) {
                ImageView good_iv = helper.getView(R.id.good_iv);
                good_iv.setImageResource(R.drawable.default_bg);
//                ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl1(), good_iv, Constants.DEFAULT_GOOD_ICON);
                GeneralUtils.setImageViewWithUrl(mContext, item.getThumPicUrl1(), good_iv, R.drawable.default_bg);
                helper.setText(R.id.name_tv, item.getContentName());
                helper.setText(R.id.price_tv, "¥ " + item.getPrice() + "元");
                helper.setText(R.id.provider_tv, item.getShopName());
                helper.getView(R.id.item_cv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToDetail(item);
                    }
                });
            }
        };
        lvShow.setAdapter(lvAdapter);
        gvShow.setAdapter(gvAadapter);
    }

    private void jumpToDetail(ContentListBean item) {
        int type = item.getServiceType();
        Intent intent = new Intent();
        if (type == 1) {
//            intent.setClass(mContext, EduOlineVideoActivity.class);
        } else if (type == 2) {
            intent.setClass(mContext, ProductActy.class);
        } else if (type == 3) {
//            intent.setClass(mContext, DecorateActy.class);
        } else {

        }
        intent.putExtra(IntentCode.contentID, item.getContentID());
        mContext.startActivity(intent);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
                searchShowType();
            }
        }
        if (event instanceof NetResponseEvent) {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            llLoading.setVisibility(View.GONE);
            if (tag.equals(GetBrandListResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    GetBrandListResponse mGetBrandListResponse = GsonHelper.toType(result, GetBrandListResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetBrandListResponse.getResultCode())) {
                        sort_tv.setVisibility(View.GONE);
                        brandList = mGetBrandListResponse.getBrandList();
                        hotList = new ArrayList<>();
                        for (int i = 0; i < brandList.size(); i++) {
                            hotList.add(brandList.get(i).getBrandName());
                        }
                    } else {
                        ErrorCode.doCode(mContext, mGetBrandListResponse.getResultCode(), mGetBrandListResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(ColumnContentListResponse.class.getName())) {
                isLoadingMoreData = false;
                refreshLayout.refreshComplete();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ColumnContentListResponse mSearchResponse = GsonHelper.toType(result, ColumnContentListResponse.class);
                    if (Constants.SUCESS_CODE.equals(mSearchResponse.getResultCode())) {
                        pageTotal = mSearchResponse.getTotalCount();
                        datasMore.clear();
                        datasMore = (ArrayList<ContentListBean>) mSearchResponse.getContentList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            isLoadingMoreData = false;
                            llLoading.setVisibility(View.GONE);
                            tvLoadMore.setVisibility(View.GONE);
                            gvAadapter.notifyDataSetChanged();
                            lvAdapter.notifyDataSetChanged();
                        } else {//有记录
                            if (loadPageNo == 1) {
                                datas.clear();
                                datas.addAll(datasMore);
                            }
                            lvAdapter.setData(datas);
                            lvAdapter.notifyDataSetChanged();
                            gvAadapter.setData(datas);
                            gvAadapter.notifyDataSetChanged();
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                            }
                            if (datas.size() >= mSearchResponse.getTotalCount()) {
                                tvLoadMore.setText("已加载完毕");
                            }
                            findViewById(R.id.bellow_title_view).setVisibility(View.VISIBLE);
                        }
                    } else {
                        NetLoadingDialog.getInstance().dismissDialog();
                        ErrorCode.doCode(mContext, mSearchResponse.getResultCode(), mSearchResponse.getDesc());
                    }
                } else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
                if (datas.size() > 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llLoading.setVisibility(View.GONE);
                            tvLoadMore.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                } else {
                    noDataView.setVisibility(View.VISIBLE);
                    llLoading.setVisibility(View.GONE);
                    tvLoadMore.setVisibility(View.GONE);
                }
            }
        }
    }


    //初始化空View
    private void initEmtyView() {
        noDataView = findViewById(R.id.no_history);
        noDataView.setVisibility(View.GONE);
        ImageView tips_pic = (ImageView) findViewById(R.id.tips_pic);
        TextView tips = (TextView) findViewById(R.id.tips);
        tips.setText("~");
        lvShow.setEmptyView(noDataView);
        gvShow.setEmptyView(noDataView);
    }


    /**
     * 综合排序
     *
     * @param view
     */
    private void showZhPW(TextView view) {
        // 一个自定义的布局，作为显示的内容
        if (popupWindow == null) {
            View contentView = LayoutInflater.from(mContext).inflate(
                    R.layout.popupwindow_zh_sort, null);
            TextView tvZongHe = (TextView) contentView.findViewById(R.id.pw_zh_tv);
            TextView tvHaoPing = (TextView) contentView.findViewById(R.id.pw_hp_tv);
            TextView tvTime = (TextView) contentView.findViewById(R.id.pw_time_tv);
            tvTime.setOnClickListener(this);
            tvHaoPing.setOnClickListener(this);
            tvZongHe.setOnClickListener(this);

            popupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.pxtodp270), true);

            popupWindow.setTouchable(true);

            popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.app_white));
        }
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    /**
     * 综合排序
     *
     * @param view
     */
    private void showChoosePW(TextView view) {
        // 一个自定义的布局，作为显示的内容
        if (choosePW == null) {
            View contentView = LayoutInflater.from(mContext).inflate(
                    R.layout.pw_choose_brand, null);
            flBrand = (FlowLayout) contentView.findViewById(R.id.des_fl);
            initChildViews((String[]) hotList.toArray(new String[hotList.size()]));
            choosePW = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.pxtodp270), true);

            choosePW.setTouchable(true);

            choosePW.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            choosePW.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.app_white));
        }
        // 设置好参数之后再show
        choosePW.showAsDropDown(view);

    }


    private void initChildViews(String hotWords[]) {
        flBrand.setVisibility(View.VISIBLE);
        flBrand.removeAllViews();
        final List<TextView> tvList = new ArrayList<>();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 20;
        lp.rightMargin = 0;
        lp.topMargin = 15;
        lp.bottomMargin = 10;
        for (int i = 0; i < hotWords.length; i++) {
            final TextView view = new TextView(mContext);
            if (GeneralUtils.isNotNullOrZeroLenght(hotWords[i])) {
                view.setText(hotWords[i]);
                view.setTag(i);
                view.setTextColor(Color.GRAY);
                view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle));
                tvList.add(view);

                view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                sort = 1; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                                                if (brandList != null && brandList.size() > 0) {
                                                    try {
                                                        brandId = brandList.get((int) view.getTag()).getBrandID();
                                                        newListShow();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                clearOther(tvList);
                                                view.setTextColor(mContext.getResources().getColor(R.color.app_color));
                                                view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle_pink));
                                                choosePW.dismiss();

                                            }
                                        }

                );
                flBrand.addView(view, lp);
            }
        }
    }

    private void clearOther(List<TextView> tvList) {
        for (int i = 0; i < tvList.size(); i++) {
            tvList.get(i).setTextColor(Color.GRAY);
            tvList.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.app_style_circle));
        }
    }

    /**
     * 清空文字上的信息
     *
     * @param position
     */
    private void clearText(int position) {
        zh_sort_tv.setTextColor(getResources().getColor(R.color.register_black_text));
        sort_tv.setTextColor(getResources().getColor(R.color.register_black_text));
        sell_sort_tv.setTextColor(getResources().getColor(R.color.register_black_text));
        price_sort_tv.setTextColor(getResources().getColor(R.color.register_black_text));
        if (position == 0) {
            zh_sort_tv.setTextColor(getResources().getColor(R.color.app_color));
        } else if (position == 1) {
            sell_sort_tv.setTextColor(getResources().getColor(R.color.app_color));
        } else if (position == 2) {
            price_sort_tv.setTextColor(getResources().getColor(R.color.app_color));
        } else {
            sort_tv.setTextColor(getResources().getColor(R.color.app_color));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //综合选择
            case R.id.zh_sort_tv:
                clearText(0);
                if (!(popupWindow != null && popupWindow.isShowing())) {
                    showZhPW(zh_sort_tv);
                }
                break;

            //综合排序
            case R.id.pw_zh_tv:
                if (sort == 1) {
                    return;
                } else {
                    sort = 1; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                    zh_sort_tv.setText("综合排序");
                    clearText(0);
                    brandId = "";
                    popupWindow.dismiss();
                    newListShow();
                }
                break;
            //好评
            case R.id.pw_hp_tv: //传sotr=2 order 为降序
                if (sort == 2) {
                    return;
                } else {
                    sort = 2; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                    zh_sort_tv.setText("按好评排序");
                    clearText(0);
                    brandId = "";
                    popupWindow.dismiss();

                    newListShow();
                }
                break;
            //上架时间
            case R.id.pw_time_tv:
                if (sort == 3) {
                    return;
                } else {
                    sort = 3; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                    zh_sort_tv.setText("按上架时间排序");
                    clearText(0);
                    brandId = "";
                    popupWindow.dismiss();
                    newListShow();
                }
                break;
            //销量
            case R.id.sell_sort_tv:
                if (sort == 4) {
                    return;
                } else {
                    sort = 4; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                    clearText(1);
                    brandId = "";
                    newListShow();
                }
                break;
            //价格
            case R.id.price_sort_tv:
            case R.id.price_ll:
                sort = 5; //1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
                clearText(2);
                brandId = "";
                if (priceOrder == 1) {
                    priceOrder = 2;
                    ivPriceOrder.setImageResource(R.mipmap.price_down);
                } else {
                    priceOrder = 1;
                    ivPriceOrder.setImageResource(R.mipmap.price_up);
                }
                newListShow();
                break;
            //列表 or 网格展示
            case R.id.show_type_iv:

                break;
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setTitleText(titleName);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightImage(R.mipmap.search_sort_lv);
        show_type_iv = headView.getRightView();
    }

    private void searchShowType() {
        if (isList) { //展示网格的
            headView.setRightImage(R.mipmap.search_sort_gv);
            isList = false;
            llg.setVisibility(View.VISIBLE);
            llv.setVisibility(View.GONE);
        } else {
            headView.setRightImage(R.mipmap.search_sort_lv);
            isList = true;
            llv.setVisibility(View.VISIBLE);
            llg.setVisibility(View.GONE);
        }
    }

    private void newListShow() {
        datas.clear();
        datasMore.clear();
        pageNo = 1;
        loadPageNo = pageNo;
        lvAdapter.notifyDataSetChanged();
        gvAadapter.notifyDataSetChanged();
        initListData();
    }


    @Override
    public void initView() {
        llv = (LinearLayout) findViewById(R.id.lv_ll);
        llprice = (LinearLayout) findViewById(R.id.price_ll);
        llg = (LinearLayout) findViewById(R.id.gv_ll);
        zh_sort_tv = (TextView) findViewById(R.id.zh_sort_tv);
        ivPriceOrder = (ImageView) findViewById(R.id.price_order_iv);
        sell_sort_tv = (TextView) findViewById(R.id.sell_sort_tv);
        price_sort_tv = (TextView) findViewById(R.id.price_sort_tv);
        sort_tv = (TextView) findViewById(R.id.sort_tv);
        zh_sort_tv.setOnClickListener(this);
        llprice.setOnClickListener(this);
        sell_sort_tv.setOnClickListener(this);
        price_sort_tv.setOnClickListener(this);
        sort_tv.setOnClickListener(this);


        lvShow = (MyListView) findViewById(R.id.show_lv);
        gvShow = (MyGridView) findViewById(R.id.show_gv);
        initEmtyView();
        llLoading = (LinearLayout) findViewById(R.id.loading_test_ll);//正在加载
        tvLoadMore = (TextView) findViewById(R.id.load_more_tv);//加载更多
        llLoading.setVisibility(View.VISIBLE);
        tvLoadMore.setVisibility(View.GONE);


        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageTotal != 0 && pageNo < pageTotal && tvLoadMore.getText().equals("加载更多")) {
                    tvLoadMore.setVisibility(View.GONE);
                    llLoading.setVisibility(View.VISIBLE);
                    isLoadingMoreData = true;
                    loadPageNo++;
                    initListData();
                } else {
                    tvLoadMore.setText("已加载完毕");
                }
            }
        });
        refreshLayout = (PtrClassicFrameLayout) findViewById(R.id.refreshLayout);
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
                loadPageNo = 1;
                isLoadingMoreData = true;
                UserServiceImpl.instance().getColumnListContent(mContext, columnId, loadPageNo, Constants.LIST_NUM, ColumnContentListResponse.class.getName());
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        initAdapter();
    }

    private void initListData() {
        tvLoadMore.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        noDataView.setVisibility(View.GONE);
        isLoadingMoreData = true;

        UserServiceImpl.instance().getColumnListContent(mContext, columnId, loadPageNo, Constants.LIST_NUM, ColumnContentListResponse.class.getName());
    }


    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

}
