package com.fengqipu.mall.main.acty.enterprise;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
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
import com.fengqipu.mall.bean.index.BannerListBean;
import com.fengqipu.mall.bean.shop.AddShopResponse;
import com.fengqipu.mall.bean.shop.OntheNewShopListResponse;
import com.fengqipu.mall.bean.shop.PromotionShopListResponse;
import com.fengqipu.mall.bean.shop.ShopDetailResponse;
import com.fengqipu.mall.bean.shop.ZongHeShopListResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
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
import com.fengqipu.mall.view.banner.ConvenientBanner;
import com.fengqipu.mall.view.banner.demo.LocalImageHolderView;
import com.fengqipu.mall.view.banner.demo.NetworkImageHolderView;
import com.fengqipu.mall.view.banner.holder.CBViewHolderCreator;
import com.fengqipu.mall.view.banner.listener.OnItemClickListener;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.fengqipu.mall.R.id.btn_qyjs;
import static com.fengqipu.mall.R.id.tv_gz_num;

/*
 *企业页面
 */
public class EnterpriseNewActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.index_banner)
    ConvenientBanner indexBanner;

    @Bind(R.id.mTabs)
    TabLayout mTabs;

    @Bind(R.id.mContainer)
    ViewPager mContainer;

    @Bind(btn_qyjs)
    TextView btnQyjs;

    @Bind(R.id.btn_yjbh)
    TextView btnYjbh;

    @Bind(R.id.btn_zxkf)
    TextView btnZxkf;

    @Bind(R.id.iv_img)
    ImageView ivImg;

    @Bind(R.id.tv_shopname)
    TextView tvShopname;

    @Bind(R.id.tv_notice)
    TextView tvNotice;

    @Bind(R.id.tv_gz)
    TextView tvGz;

    @Bind(tv_gz_num)
    TextView tvGzNum;

    public String sid = "";

    @Bind(R.id.btn_zh)
    TextView btnZh;
    @Bind(R.id.btn_xl)
    TextView btnXl;
    @Bind(R.id.btn_jg)
    TextView btnJg;
    @Bind(R.id.btn_list_type)
    ImageView btnListType;
    @Bind(R.id.title_1)
    LinearLayout title1;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.my_gridview)
    MyGridView myGridview;
    @Bind(R.id.emtry_ll)
    LinearLayout emtryLl;

    int pageNum = 1;
    int pageSize = 10;
    @Bind(R.id.scroll_view)
    ScrollBottomScrollView scrollView;
    @Bind(R.id.son_top)
    LinearLayout sonTop;
    @Bind(R.id.mTabs2)
    TabLayout mTabs2;
    @Bind(R.id.btn_zh_2)
    TextView btnZh2;
    @Bind(R.id.btn_xl_2)
    TextView btnXl2;
    @Bind(R.id.btn_jg_2)
    TextView btnJg2;
    @Bind(R.id.btn_list_type_2)
    ImageView btnListType2;
    @Bind(R.id.son_top_2)
    LinearLayout sonTop2;
    @Bind(R.id.title_2)
    LinearLayout title2;
    private boolean isloading = false;
    private int tolalNum = 0;
    private CommonAdapter<ZongHeShopListResponse.ContentListBean> lAdapter;
    private CommonAdapter<ZongHeShopListResponse.ContentListBean> gAdapter;
    private List<ZongHeShopListResponse.ContentListBean> goodsList = new ArrayList<>();

    int jgtype = 0;

    private int order = 1;
    private int listtype = 1;

    private CommonAdapter<OntheNewShopListResponse.ContentListBean> gAdapter1;
    private List<OntheNewShopListResponse.ContentListBean> goodsList1 = new ArrayList<>();


    private CommonAdapter<PromotionShopListResponse.ContentListBean> gAdapter2;
    private List<PromotionShopListResponse.ContentListBean> goodsList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_new);
        ButterKnife.bind(this);
        sid = getIntent().getStringExtra("sid");
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        bannerFirstInit();
    }
    int  titleHeight=0;
    @Override
    public void initViewData() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(1);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        mTabs2.setupWithViewPager(mContainer);
        mTabs2.setVisibility(View.VISIBLE);
        mContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        initZhongHePager();
                        break;
                    case 1:
                        initOntheNewPager();
                        break;
                    case 2:
                        initPromotionPager();
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initZhongHePager();
        Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
//        params.width = width;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final float scaleHeight=dm.heightPixels/1280f;
        ViewTreeObserver vto = title1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                title1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                titleHeight=title1.getMeasuredHeight();
            }
        });
        scrollView.setOnScrollChangeListener(new ScrollBottomScrollView.OnScrollChangeListener() {
            @Override
            public void scrollChange(int y) {
//                if(mContainer.getCurrentItem()==0) {
                    if(y>=titleHeight + (int) (180 * scaleHeight)){
                        title2.setVisibility(View.VISIBLE);
                    }else{
                        title2.setVisibility(View.GONE);
                    }
//                }else{
//                    if(y>=titleHeight + (int) (70 * scaleHeight)){
//                        title2.setVisibility(View.VISIBLE);
//                    }else{
//                        title2.setVisibility(View.GONE);
//                    }
//                }

            }
        });
        initData();
    }

    private void initZhongHePager() {
        sonTop.setVisibility(View.VISIBLE);
        sonTop2.setVisibility(View.VISIBLE);
        lAdapter = new CommonAdapter<ZongHeShopListResponse.ContentListBean>(this, goodsList, R.layout.item_his_g) {
            @Override
            public void convert(ViewHolder helper, ZongHeShopListResponse.ContentListBean item) {
                helper.setText(R.id.goods_info, item.getContentName());
                helper.setText(R.id.goods_price, "￥" + item.getPrice());
                helper.setText(R.id.goods_time, "" + item.getCreateTime());
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl1RequestUrl())) {
                    ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                    GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
                }
            }
        };
        gAdapter = new CommonAdapter<ZongHeShopListResponse.ContentListBean>(this, goodsList, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, ZongHeShopListResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(EnterpriseNewActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
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
        WindowManager wm = this.getWindowManager();
        emtryLl.setLayoutParams(new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight() / 2));
        myListview.setEmptyView(emtryLl);
        myGridview.setEmptyView(emtryLl);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZongHeShopListResponse.ContentListBean item = (ZongHeShopListResponse.ContentListBean) adapterView.getItemAtPosition(i);
                if (Global.getuserType().equals("1")) {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsEnterpriseActivity.class);
                    intent.putExtra("contentID", item.getId());
                    intent.putExtra("contentType", item.getContentType());
                    intent.putExtra("category2", item.getCategory2());
                    intent.putExtra("model", item.getModel());
                    intent.putExtra("picurl", item.getPicUrl1RequestUrl());
                    intent.putExtra("name", item.getContentName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentID", item.getId());
                    startActivity(intent);
                }
            }
        });
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZongHeShopListResponse.ContentListBean item = (ZongHeShopListResponse.ContentListBean) adapterView.getItemAtPosition(i);
                if (Global.getuserType().equals("1")) {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsEnterpriseActivity.class);
                    intent.putExtra("contentID", item.getId());
                    intent.putExtra("contentType", item.getContentType());
                    intent.putExtra("category2", item.getCategory2());
                    intent.putExtra("model", item.getModel());
                    intent.putExtra("picurl", item.getPicUrl1RequestUrl());
                    intent.putExtra("name", item.getContentName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentID", item.getId());
                    startActivity(intent);
                }
            }
        });
        scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom() {
                Log.e("sub", "onbtm");
                if (isloading) return;
                if (pageNum * pageSize >= tolalNum) return;
                isloading = true;
                pageNum = pageNum + 1;
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "1", order + "", jgtype + "", pageNum, pageSize, ZongHeShopListResponse.class.getName());

            }
        });
        scrollView.scrollTo(0, 0);
        btnListType.setOnClickListener(this);
        btnZh.setOnClickListener(this);
        btnXl.setOnClickListener(this);
        btnJg.setOnClickListener(this);
        btnListType2.setOnClickListener(this);
        btnZh2.setOnClickListener(this);
        btnXl2.setOnClickListener(this);
        btnJg2.setOnClickListener(this);
        pageNum = 1;
        NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
        UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "1", order + "", jgtype + "", pageNum, pageSize, ZongHeShopListResponse.class.getName());
    }

    private void initOntheNewPager() {
        sonTop.setVisibility(View.GONE);
        sonTop2.setVisibility(View.GONE);
        gAdapter1 = new CommonAdapter<OntheNewShopListResponse.ContentListBean>(EnterpriseNewActivity.this, goodsList1, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, OntheNewShopListResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(EnterpriseNewActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
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
        myGridview.setAdapter(gAdapter1);
        WindowManager wm = getWindowManager();
        emtryLl.setLayoutParams(new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight() / 2));
        myGridview.setEmptyView(emtryLl);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OntheNewShopListResponse.ContentListBean item = (OntheNewShopListResponse.ContentListBean) adapterView.getItemAtPosition(i);
                if (Global.getuserType().equals("1")) {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsEnterpriseActivity.class);
                    intent.putExtra("contentID", item.getId());
                    intent.putExtra("contentType", item.getContentType());
                    intent.putExtra("category2", item.getCategory2());
                    intent.putExtra("model", item.getModel());
                    intent.putExtra("picurl", item.getPicUrl1RequestUrl());
                    intent.putExtra("name", item.getContentName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsDetailActivity.class);
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
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "3", "", "", pageNum, pageSize, OntheNewShopListResponse.class.getName());

            }
        });
        pageNum = 1;
        NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
        UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "3", "", "", pageNum, pageSize, OntheNewShopListResponse.class.getName());

    }

    private void initPromotionPager() {
        sonTop.setVisibility(View.GONE);
        sonTop2.setVisibility(View.GONE);
        gAdapter2 = new CommonAdapter<PromotionShopListResponse.ContentListBean>(EnterpriseNewActivity.this, goodsList2, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, PromotionShopListResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(EnterpriseNewActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
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
        myGridview.setAdapter(gAdapter2);
        WindowManager wm = getWindowManager();
        emtryLl.setLayoutParams(new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight() / 2));
        myGridview.setEmptyView(emtryLl);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PromotionShopListResponse.ContentListBean item = (PromotionShopListResponse.ContentListBean) adapterView.getItemAtPosition(i);
                if (Global.getuserType().equals("1")) {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsEnterpriseActivity.class);
                    intent.putExtra("contentID", item.getId());
                    intent.putExtra("contentType", item.getContentType());
                    intent.putExtra("category2", item.getCategory2());
                    intent.putExtra("model", item.getModel());
                    intent.putExtra("picurl", item.getPicUrl1RequestUrl());
                    intent.putExtra("name", item.getContentName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EnterpriseNewActivity.this, GoodsDetailActivity.class);
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
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "2", "", "", pageNum, pageSize, PromotionShopListResponse.class.getName());

            }
        });
        pageNum = 1;
        NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
        UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "2", "", "", pageNum, pageSize, PromotionShopListResponse.class.getName());

    }

    @Override
    public void initEvent() {
        tvGz.setOnClickListener(this);
        btnZxkf.setOnClickListener(this);
        btnYjbh.setOnClickListener(this);
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private String curphone = "";

    private void call(String phone) {
        curphone = phone;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + curphone));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //代表用户同意了打电话的请求
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + curphone));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            } else {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initTopBtn() {
        btnZh.setTextColor(Color.parseColor("#4A4A4A"));
        btnXl.setTextColor(Color.parseColor("#4A4A4A"));
        btnJg.setTextColor(Color.parseColor("#4A4A4A"));
        btnZh2.setTextColor(Color.parseColor("#4A4A4A"));
        btnXl2.setTextColor(Color.parseColor("#4A4A4A"));
        btnJg2.setTextColor(Color.parseColor("#4A4A4A"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gz:
                if (GeneralUtils.isLogin()) {
                    if (tvGz.getText().equals("已关注")) {
                        return;
                    }
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().addFavour(this, "2", sid, AddShopResponse.class.getName());
                } else {
                    startActivity(new Intent(this, LoginActy.class));
                }

                break;
            case R.id.btn_yjbh:
                if (!GeneralUtils.isLogin()) {
                    startActivity(new Intent(mContext, LoginActy.class));
                } else {
                    String phone = shopDetailResponse.getShop().getPhone();
                    if (phone != null && !phone.equals("")) {
                        call(phone);
                    }
                }

                break;
            case R.id.btn_zxkf:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new IntentBuilder(EnterpriseNewActivity.this)
                            .setServiceIMNumber("kefuchannelimid_563950") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                            .setTitleName("")
                            .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                    .companyName("")
                                    .email(Global.getEmail())
                                    .qq("")
                                    .name(Global.getUserName())
                                    .nickName(Global.getNickName())
                                    .phone(Global.getPhone()))
                            .setShowUserNick(true)
                            .build();
                    startActivity(intent);
                } else {
                    startActivity(new Intent(EnterpriseNewActivity.this, LoginActy.class));
                }
                break;
            case  R.id.btn_list_type_2:
            case  R.id.btn_list_type:
                if (myListview.getVisibility() == View.VISIBLE) {
                    listtype = 0;
                    btnListType.setImageResource(R.mipmap.search_sort_lv);
                    btnListType2.setImageResource(R.mipmap.search_sort_lv);
                    myListview.setVisibility(View.GONE);
                    myGridview.setVisibility(View.VISIBLE);
                } else {
                    listtype = 1;
                    btnListType.setImageResource(R.mipmap.search_sort_gv);
                    btnListType2.setImageResource(R.mipmap.search_sort_gv);
                    myListview.setVisibility(View.VISIBLE);
                    myGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_zh_2:
            case R.id.btn_zh:
                initTopBtn();
                btnZh.setTextColor(getResources().getColor(R.color.app_color));
                btnZh2.setTextColor(getResources().getColor(R.color.app_color));
                jgtype = 0;
                Drawable nav_original = getResources().getDrawable(R.mipmap.price_original);
                nav_original.setBounds(0, 0, nav_original.getMinimumWidth(), nav_original.getMinimumHeight());
                btnJg.setCompoundDrawables(null, null, nav_original, null);
                btnJg2.setCompoundDrawables(null, null, nav_original, null);
                order = 1;
                pageNum = 1;
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "1", order + "", jgtype + "", pageNum, pageSize, ZongHeShopListResponse.class.getName());
                break;
            case R.id.btn_xl_2:
            case R.id.btn_xl:
                initTopBtn();
                btnXl.setTextColor(getResources().getColor(R.color.app_color));
                btnXl2.setTextColor(getResources().getColor(R.color.app_color));
                jgtype = 0;
                Drawable nav_original1 = getResources().getDrawable(R.mipmap.price_original);
                nav_original1.setBounds(0, 0, nav_original1.getMinimumWidth(), nav_original1.getMinimumHeight());
                btnJg.setCompoundDrawables(null, null, nav_original1, null);
                btnJg2.setCompoundDrawables(null, null, nav_original1, null);
                order = 2;
                pageNum = 1;
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "1", order + "", jgtype + "", pageNum, pageSize, ZongHeShopListResponse.class.getName());
                break;
            case R.id.btn_jg_2:
            case R.id.btn_jg:
                initTopBtn();
                btnJg.setTextColor(getResources().getColor(R.color.app_color));
                btnJg2.setTextColor(getResources().getColor(R.color.app_color));
                if (jgtype == 1) {
                    jgtype = 2;
                    Drawable nav_up = getResources().getDrawable(R.mipmap.price_up);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_up, null);
                    btnJg2.setCompoundDrawables(null, null, nav_up, null);
                } else if (jgtype == 2) {
                    jgtype = 1;
                    Drawable nav_down = getResources().getDrawable(R.mipmap.price_down);
                    nav_down.setBounds(0, 0, nav_down.getMinimumWidth(), nav_down.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_down, null);
                    btnJg2.setCompoundDrawables(null, null, nav_down, null);
                } else {
                    jgtype = 1;
                    Drawable nav_down = getResources().getDrawable(R.mipmap.price_down);
                    nav_down.setBounds(0, 0, nav_down.getMinimumWidth(), nav_down.getMinimumHeight());
                    btnJg.setCompoundDrawables(null, null, nav_down, null);
                    btnJg2.setCompoundDrawables(null, null, nav_down, null);
                }
                order = 4;
                pageNum = 1;
                NetLoadingDialog.getInstance().loading(EnterpriseNewActivity.this);
                UserServiceImpl.instance().getShopsList(EnterpriseNewActivity.this, sid, "1", order + "", jgtype + "", pageNum, pageSize, ZongHeShopListResponse.class.getName());
                break;
        }
    }

    ShopDetailResponse shopDetailResponse;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(ShopDetailResponse.class.getName())) {
                shopDetailResponse = GsonHelper.toType(result, ShopDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(shopDetailResponse.getResultCode())) {
                        if (shopDetailResponse.getShop().getAdvPicUrlList() != null && shopDetailResponse.getShop().getAdvPicUrlList().size() > 0) {
                            List<BannerListBean> bannerListBeen = new ArrayList<>();
                            for (String s : shopDetailResponse.getShop().getAdvPicUrlList()) {
                                BannerListBean b = new BannerListBean();
                                b.setCoverRequestUrl(s);
                                bannerListBeen.add(b);
                            }
                            initBanner(bannerListBeen);
                        }
                        if (shopDetailResponse.getShop().getPicUrlRequestUrl() != null && !shopDetailResponse.getShop().getPicUrlRequestUrl().equals("")) {
                            GeneralUtils.setImageViewWithUrl(EnterpriseNewActivity.this, shopDetailResponse.getShop().getPicUrlRequestUrl(), ivImg, R.drawable.bg_image_classification);
                        }
                        tvShopname.setText(shopDetailResponse.getShop().getShopName() + "");
                        tvNotice.setText(shopDetailResponse.getShop().getNotice() + "");
                        if (shopDetailResponse.getShop().getNotice() == null || shopDetailResponse.getShop().getNotice().equals("")) {
                            tvNotice.setVisibility(View.GONE);
                        }
                        tvGz.setText("+关注");
                        tvGz.setBackground(getResources().getDrawable(R.drawable.yollew_rec_click));
                        tvGz.setTextColor(Color.parseColor("#ffffff"));
                        if (shopDetailResponse.getIsFavorite().equals("1")) {
                            tvGz.setText("已关注");
                            tvGz.setBackground(getResources().getDrawable(R.drawable.white_rec_click));
                            tvGz.setTextColor(Color.parseColor("#394257"));
                        }
//                        if(GeneralUtils.isLogin()){
//                            tvGz.setVisibility(View.VISIBLE);
//                        }else{
//                            tvGz.setVisibility(View.GONE);
//                        }
                        tvGzNum.setText(shopDetailResponse.getShop().getFavoriteCount() + "人");
                        btnQyjs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                {
                                    if (shopDetailResponse.getShop().getDescriptionLink() != null && !shopDetailResponse.getShop().getDescriptionLink().equals("")) {
                                        Intent intentExplain = new Intent(EnterpriseNewActivity.this, CommonWebViewActivity.class);
                                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, shopDetailResponse.getShop().getShopName());
                                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, shopDetailResponse.getShop().getDescriptionLink());
                                        startActivity(intentExplain);
                                    }
                                }

                            }
                        });

                    } else {
                        ErrorCode.doCode(this, shopDetailResponse.getResultCode(), shopDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(AddShopResponse.class.getName())) {
                AddShopResponse addShopResponse = GsonHelper.toType(result, AddShopResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(shopDetailResponse.getResultCode())) {
                        tvGz.setText("已关注");
                        tvGz.setBackground(getResources().getDrawable(R.drawable.white_rec_click));
                        tvGz.setTextColor(Color.parseColor("#394257"));
                        int num = 0;
                        try {
                            num = Integer.valueOf(tvGzNum.getText().toString().replace("人", ""));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        num++;
                        tvGzNum.setText(num + "人");
                    } else {
                        ErrorCode.doCode(this, shopDetailResponse.getResultCode(), shopDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(ZongHeShopListResponse.class.getName())) {
                ZongHeShopListResponse zongHeShopListResponse = GsonHelper.toType(result, ZongHeShopListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(zongHeShopListResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        isloading = false;
                        tolalNum = zongHeShopListResponse.getTotalCount();
                        if (zongHeShopListResponse.getContentList() != null && zongHeShopListResponse.getContentList().size() > 0) {
                            goodsList.addAll(zongHeShopListResponse.getContentList());
                            gAdapter.setData(goodsList);
                            lAdapter.setData(goodsList);
                            gAdapter.notifyDataSetChanged();
                            lAdapter.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myListview);
                            CommonMethod.setListViewHeightBasedOnChildren(myGridview);
                            if (listtype == 0) {
                                listtype = 0;
                                btnListType.setImageResource(R.mipmap.search_sort_lv);
                                myListview.setVisibility(View.GONE);
                                myGridview.setVisibility(View.VISIBLE);
                            } else {
                                listtype = 1;
                                btnListType.setImageResource(R.mipmap.search_sort_gv);
                                myListview.setVisibility(View.VISIBLE);
                                myGridview.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ErrorCode.doCode(EnterpriseNewActivity.this, zongHeShopListResponse.getResultCode(), zongHeShopListResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(EnterpriseNewActivity.this);
                }
            }
            if (tag.equals(OntheNewShopListResponse.class.getName())) {
                OntheNewShopListResponse ontheNewShopListResponse = GsonHelper.toType(result, OntheNewShopListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(ontheNewShopListResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList1.clear();
                        }
                        isloading = false;
                        tolalNum = ontheNewShopListResponse.getTotalCount();
                        if (ontheNewShopListResponse.getContentList() != null && ontheNewShopListResponse.getContentList().size() > 0) {
                            goodsList1.addAll(ontheNewShopListResponse.getContentList());
                            gAdapter1.setData(goodsList1);
                            gAdapter1.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myGridview);
                        }
                    } else {
                        ErrorCode.doCode(EnterpriseNewActivity.this, ontheNewShopListResponse.getResultCode(), ontheNewShopListResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(EnterpriseNewActivity.this);
                }
            }
            if (tag.equals(PromotionShopListResponse.class.getName())) {
                PromotionShopListResponse promotionShopListResponse = GsonHelper.toType(result, PromotionShopListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(promotionShopListResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList2.clear();
                        }
                        isloading = false;
                        tolalNum = promotionShopListResponse.getTotalCount();
                        if (promotionShopListResponse.getContentList() != null && promotionShopListResponse.getContentList().size() > 0) {
                            goodsList2.addAll(promotionShopListResponse.getContentList());
                            gAdapter2.setData(goodsList2);
                            gAdapter2.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myGridview);
                        }
                    } else {
                        ErrorCode.doCode(EnterpriseNewActivity.this, promotionShopListResponse.getResultCode(), promotionShopListResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(EnterpriseNewActivity.this);
                }
            }
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("企业详情");
        headView.setHiddenRight();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
//                    return new ListFragment();
                    return new Fragment();
                case 1:
                    return new Fragment();
                case 2:
                    return new Fragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "全部商品";
                case 1:
                    return "促销";
                case 2:
                    return "上新";
            }
            return null;
        }
    }

    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    private void bannerFirstInit() {
        //第一次展示默认本地图片
        localImages.add(R.drawable.bg_banner_enterprisedetails);//默认图片
        indexBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.point_transparent, R.drawable.point_transparent});
    }

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();

    /**
     * Banner展示网络数据
     *
     * @param ad
     */
    private synchronized void initBanner(final List<BannerListBean> ad) {
        if (ad == null || ad.size() < 1) {
            return;
        }
        networkImages.clear();
        for (int i = 0; i < ad.size(); i++) {
            if (!networkImages.contains(ad.get(i).getCoverRequestUrl())) {
                networkImages.add(ad.get(i).getCoverRequestUrl());
            }
        }
        indexBanner.stopTurning();
        indexBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BannerListBean bean = ad.get(position);
                if (bean != null && GeneralUtils.isNotNullOrZeroLenght(bean.getLink())) {
                    //页面跳转
//                    if (bean.getType() == 1 || bean.getType() == 2) {
//
//                    }
                }
            }
        });
    }

    private void initData() {
        //请求banner
        UserServiceImpl.instance().getShopDetail(this, sid, ShopDetailResponse.class.getName());
    }
}
