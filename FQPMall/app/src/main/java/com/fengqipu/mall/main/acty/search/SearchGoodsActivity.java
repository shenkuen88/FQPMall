package com.fengqipu.mall.main.acty.search;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.search.CityResponse;
import com.fengqipu.mall.bean.search.SearchGoodsResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.dialog.ShaiXuanDialog;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.ScrollBottomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.fengqipu.mall.R.id.btn_list_type;
import static com.fengqipu.mall.R.id.btn_sx;

public class SearchGoodsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.finish_iv)
    ImageView finishIv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @Bind(R.id.btn_ss)
    TextView btnSs;
    @Bind(R.id.btn_zh)
    TextView btnZh;
    @Bind(R.id.btn_xl)
    TextView btnXl;
    @Bind(R.id.btn_jg)
    TextView btnJg;
    @Bind(R.id.btn_sx)
    TextView btnSx;
    @Bind(R.id.btn_list_type)
    ImageView btnListType;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.my_gridview)
    GridView myGridview;
    @Bind(R.id.scrollView)
    ScrollBottomScrollView scrollView;
    @Bind(R.id.emtry_ll)
    LinearLayout emtryLl;
    private CommonAdapter<SearchGoodsResponse.ContentListBean> lAdapter;
    private CommonAdapter<SearchGoodsResponse.ContentListBean> gAdapter;
    private List<SearchGoodsResponse.ContentListBean> goodsList = new ArrayList<>();
    String keyword = "";
    int searchType = 0;

    int pageNum = 1;
    int pageSize = 10;
    private boolean isloading = false;
    int totalCount = 0;
    private String searchtypestr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        ButterKnife.bind(this);
        keyword = getIntent().getStringExtra(IntentCode.SEARCH_KEYORD);
        category2= getIntent().getStringExtra("SearchType");
        if(category2==null){
            category2="";
        }
        initAll();
    }

    @Override
    public void initView() {
    }

    private void initData() {
        pageNum = 1;
        NetLoadingDialog.getInstance().loading(SearchGoodsActivity.this);
        initBtmList();
    }

    private String contentType = "";

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
        UserServiceImpl.instance().getSearchGList(selCityName, minPrice, maxPrice, category2, contentType, etSearch.getText().toString(), order + "", jgtype + "", pageNum, pageSize, SearchGoodsResponse.class.getName());
    }

    private int order = 1;
    String minPrice = "";
    String maxPrice = "";
    String selCityName = "";

    @Override
    public void initViewData() {
        lAdapter = new CommonAdapter<SearchGoodsResponse.ContentListBean>(this, goodsList, R.layout.item_his_g) {
            @Override
            public void convert(ViewHolder helper, SearchGoodsResponse.ContentListBean item) {
                helper.setText(R.id.goods_info, item.getContentName());
                helper.setText(R.id.goods_price, "￥" + item.getPrice());
                helper.setText(R.id.goods_time, "" + item.getCreateTime());
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl1RequestUrl())) {
                    ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                    GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1RequestUrl(), img, R.drawable.bg_image_classification);
                }
            }
        };
        gAdapter = new CommonAdapter<SearchGoodsResponse.ContentListBean>(this, goodsList, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, SearchGoodsResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(SearchGoodsActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.bg_image_classification);
                }
                title.setText("" + item.getContentName());
                location.setText("" + item.getShopProvince() + " " + item.getShopCity());
                if (item.getMonthSales() != null && !item.getMonthSales().equals("")) {
                    xl.setText("月销量" + item.getMonthSales() + "笔");
                } else {
                    xl.setText("月销量0笔");
                }
                price.setText("￥" + item.getPrice());
            }
        };
        myListview.setAdapter(lAdapter);
        myGridview.setAdapter(gAdapter);
        WindowManager wm = getWindowManager();
        emtryLl.setLayoutParams(new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(),(int)(wm.getDefaultDisplay().getHeight()*8.5/10)));
        myListview.setEmptyView(emtryLl);
        myGridview.setEmptyView(emtryLl);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchGoodsResponse.ContentListBean item = (SearchGoodsResponse.ContentListBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SearchGoodsActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID", item.getId());
                startActivity(intent);
            }
        });
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchGoodsResponse.ContentListBean item = (SearchGoodsResponse.ContentListBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SearchGoodsActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID", item.getId());
                startActivity(intent);
            }
        });
        scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom() {
                if (isloading) return;
                if (pageNum * pageSize >= totalCount) return;
                isloading = true;
                pageNum = pageNum + 1;
                initBtmList();
            }
        });
        if (keyword != null) {
            etSearch.setText(keyword);
            ivSearchClear.setVisibility(View.VISIBLE);
        }
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserServiceImpl.instance().getHotCity(CityResponse.class.getName());
    }

    @Override
    public void initEvent() {
        btnListType.setOnClickListener(this);
        btnZh.setOnClickListener(this);
        btnXl.setOnClickListener(this);
        btnJg.setOnClickListener(this);
        btnSx.setOnClickListener(this);
        finishIv.setOnClickListener(this);
        ivSearchClear.setOnClickListener(this);
        btnSs.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyWord();
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    ivSearchClear.setVisibility(View.VISIBLE);
                } else {
                    ivSearchClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchKeyWord() {
        if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString())) {
            Global.addSearchHistory(searchType, etSearch.getText().toString());
            initData();
        } else {
            ToastUtil.makeText(mContext, "请输入搜索内容");
        }
    }

    int jgtype = 0;
    private int listtype=1;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_iv:
                finish();
                break;
            case R.id.btn_ss:
                searchKeyWord();
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;
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
                if (shaiXuanDialog == null && cityResponse != null) {
                    shaiXuanDialog = new ShaiXuanDialog(SearchGoodsActivity.this, cityResponse, 0);
                }
                if (shaiXuanDialog != null) {
                    shaiXuanDialog.show();
                    shaiXuanDialog.setBtnCzListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaiXuanDialog.minPrice.setText("");
                            shaiXuanDialog.maxPrice.setText("");
                            shaiXuanDialog.fenleiTv.setText("全部");
                            category2 = "";
                            minPrice = "";
                            maxPrice = "";
                            shaiXuanDialog.selID = "";
                            shaiXuanDialog.selName = "";
                            shaiXuanDialog.mAdapter.notifyDataSetChanged();
                        }
                    });
                    shaiXuanDialog.setBtnConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            minPrice = shaiXuanDialog.minPrice.getText().toString();
                            maxPrice = shaiXuanDialog.maxPrice.getText().toString();
                            selCityName = shaiXuanDialog.selName;
                            initData();
                            shaiXuanDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }

    ShaiXuanDialog shaiXuanDialog;

    private void initTopBtn() {
        btnZh.setTextColor(Color.parseColor("#4A4A4A"));
        btnXl.setTextColor(Color.parseColor("#4A4A4A"));
        btnJg.setTextColor(Color.parseColor("#4A4A4A"));
        btnSx.setTextColor(Color.parseColor("#4A4A4A"));
    }

    CityResponse cityResponse;
    private String category2 = "";
    private LocationClient mLocationClient;//定位SDK的核心类

    private void startLocation() {
        if (((BaseApplication) getApplication()).mLocationClient != null) {
            mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
            InitLocation();//初始化
            mLocationClient.start();
        }
    }

    /**
     * 定位初始化设置
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
            if (tag.equals(NotiTag.TAG_LOCATION_SUCCESS) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (mLocationClient != null) {
                    mLocationClient.stop();
                }
            }
            if ("SearchCategory".equals(tag)) {
                String str = ((NoticeEvent) event).getUrl1();
                String str2 = ((NoticeEvent) event).getUrl2();
                category2 = str;
                if (shaiXuanDialog != null) {
                    shaiXuanDialog.fenleiTv.setText(str2 + "");
                }
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(SearchGoodsResponse.class.getName())) {
                SearchGoodsResponse searchGoodsResponse = GsonHelper.toType(result, SearchGoodsResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(searchGoodsResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        isloading = false;
                        totalCount = searchGoodsResponse.getTotalCount();
                        if (searchGoodsResponse.getContentList() != null && searchGoodsResponse.getContentList().size() > 0) {
                            goodsList.addAll(searchGoodsResponse.getContentList());
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
                        ErrorCode.doCode(this, searchGoodsResponse.getResultCode(), searchGoodsResponse.getDesc());
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
}
