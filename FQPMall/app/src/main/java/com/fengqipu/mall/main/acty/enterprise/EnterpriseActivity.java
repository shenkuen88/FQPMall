package com.fengqipu.mall.main.acty.enterprise;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.BannerListBean;
import com.fengqipu.mall.bean.shop.AddShopResponse;
import com.fengqipu.mall.bean.shop.ShopDetailResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.main.fragment.enterprise.EnterpriseGoodsListFragment;
import com.fengqipu.mall.main.fragment.enterprise.OntheNewFragment;
import com.fengqipu.mall.main.fragment.enterprise.PromotionFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
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

/*
 *企业页面
 */
public class EnterpriseActivity extends BaseActivity implements View.OnClickListener {

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
    @Bind(R.id.tv_gz_num)
    TextView tvGzNum;

    public String sid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise);
        ButterKnife.bind(this);
        sid = getIntent().getStringExtra("sid");
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        bannerFirstInit();
    }

    @Override
    public void initViewData() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(1);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        initData();
    }

    @Override
    public void initEvent() {
        tvGz.setOnClickListener(this);
        btnZxkf.setOnClickListener(this);
        btnYjbh.setOnClickListener(this);
    }
    /**
     * 调用拨号功能
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
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
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_gz:
                if(tvGz.getText().equals("已关注"))return;
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().addFavour(this,"2", sid, AddShopResponse.class.getName());
            break;
            case R.id.btn_yjbh:
                String phone =shopDetailResponse.getShop().getPhone();
                if(phone!=null&&!phone.equals("")){
                    call(phone);
                }
                break;
            case R.id.btn_zxkf:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new IntentBuilder(EnterpriseActivity.this)
                            .setServiceIMNumber("kefuchannelimid_021199") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
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
                    startActivity(new Intent(EnterpriseActivity.this, LoginActy.class));
                }
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
                            GeneralUtils.setImageViewWithUrl(EnterpriseActivity.this, shopDetailResponse.getShop().getPicUrlRequestUrl(), ivImg, R.drawable.default_bg);
                        }
                        tvShopname.setText(shopDetailResponse.getShop().getShopName() + "");
                        tvNotice.setText(shopDetailResponse.getShop().getNotice() + "");
                        tvGz.setText("+关注");
                        tvGz.setBackground(getResources().getDrawable(R.drawable.yollew_rec_click));
                        tvGz.setTextColor(Color.parseColor("#ffffff"));
                        if (shopDetailResponse.getIsFavorite().equals("1")) {
                            tvGz.setText("已关注");
                            tvGz.setBackground(getResources().getDrawable(R.drawable.white_rec_click));
                            tvGz.setTextColor(Color.parseColor("#394257"));
                        }
                        if(GeneralUtils.isLogin()){
                            tvGz.setVisibility(View.VISIBLE);
                        }else{
                            tvGz.setVisibility(View.GONE);
                        }
                        tvGzNum.setText(shopDetailResponse.getShop().getFavoriteCount()+"人");
                        btnQyjs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(shopDetailResponse.getShop().getDescriptionLink()!=null&&!shopDetailResponse.getShop().getDescriptionLink().equals("")) {
                                    Intent intentExplain = new Intent(EnterpriseActivity.this, CommonWebViewActivity.class);
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, shopDetailResponse.getShop().getShopName());
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, shopDetailResponse.getShop().getDescriptionLink());
                                    startActivity(intentExplain);
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
                    } else {
                        ErrorCode.doCode(this, shopDetailResponse.getResultCode(), shopDetailResponse.getDesc());
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
                    return new EnterpriseGoodsListFragment();
                case 1:
                    return new PromotionFragment();
                case 2:
                    return new OntheNewFragment();
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
        localImages.add(R.drawable.default_bg);//默认图片
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
