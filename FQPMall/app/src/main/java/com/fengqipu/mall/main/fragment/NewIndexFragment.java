package com.fengqipu.mall.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.ActivityListBean;
import com.fengqipu.mall.bean.index.BannerListBean;
import com.fengqipu.mall.bean.index.IndexBannerResponse;
import com.fengqipu.mall.bean.index.NoticeListBean;
import com.fengqipu.mall.bean.index.ShopListBean;
import com.fengqipu.mall.bean.index.ShopsPromoteResponse;
import com.fengqipu.mall.bean.shop.AlreadyAppliedResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.ConversationListActivity;
import com.fengqipu.mall.main.acty.KuaiXiuActivity;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.acty.enterprise.EnterpriseActivity;
import com.fengqipu.mall.main.acty.enterprise.EnterpriseListActivity;
import com.fengqipu.mall.main.acty.huodong.HuoDongActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.acty.mine.OneButtonShopActivity;
import com.fengqipu.mall.main.acty.search.NewSearchActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.PublicNoticeView;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.ScrollBottomScrollView;
import com.fengqipu.mall.view.banner.ConvenientBanner;
import com.fengqipu.mall.view.banner.demo.LocalImageHolderView;
import com.fengqipu.mall.view.banner.demo.NetworkImageHolderView;
import com.fengqipu.mall.view.banner.holder.CBViewHolderCreator;
import com.fengqipu.mall.view.banner.listener.OnItemClickListener;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by Administrator on 2016/6/13.
 */
public class NewIndexFragment extends BaseFragment implements View.OnClickListener {
    private static MainActivity mainActivity;
    @Bind(R.id.index_banner)
    ConvenientBanner indexBanner;
    @Bind(R.id.scrollView)
    ScrollBottomScrollView scrollView;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.ll_top_bg)
    LinearLayout llTopBg;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.btn_info)
    ImageView btnInfo;
    @Bind(R.id.btn_info_point)
    ImageView btnInfoPoint;
    @Bind(R.id.notice_view)
    PublicNoticeView noticeView;
    @Bind(R.id.btn_qy)
    LinearLayout btnQy;
    @Bind(R.id.btn_sp)
    LinearLayout btnSp;
    @Bind(R.id.btn_kx)
    LinearLayout btnKx;
    @Bind(R.id.btn_yjkd)
    LinearLayout btnYjkd;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.tv_huodong)
    TextView tvHuodong;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_huodong2)
    TextView tvHuodong2;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.tv_huodong3)
    TextView tvHuodong3;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.tv_huodong4)
    TextView tvHuodong4;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.ed_ss)
    TextView edSs;
    @Bind(R.id.rl_huodong)
    RelativeLayout rlHuodong;
    @Bind(R.id.rl2_huodong)
    RelativeLayout rl2Huodong;
    @Bind(R.id.rl3_huodong)
    RelativeLayout rl3Huodong;
    @Bind(R.id.rl4_huodong)
    RelativeLayout rl4Huodong;
    @Bind(R.id.iv_top)
    ImageView ivTop;
    //    @Bind(R.id.my_loading)
//    LinearLayout myLoading;
    private CommonAdapter<ShopListBean> mAdapter;
    private List<ShopListBean> shopList = new ArrayList<>();

    int pageNum = 1;
    int pageSize = 10;

    public NewIndexFragment() {
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
        mainActivity = (MainActivity) getActivity();
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.fragment_index, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    boolean isloading = true;
    int tolalNum = 0;

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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mAdapter = new CommonAdapter<ShopListBean>(mainActivity, shopList, R.layout.index_btm_list) {
            @Override
            public void convert(ViewHolder helper, final ShopListBean item) {
                TextView tv_name = helper.getView(R.id.tv_name);
                tv_name.setText(item.getShopName() + "");
                TextView tv_notice = helper.getView(R.id.tv_notice);
                tv_notice.setText(item.getNotice() + "");
                TextView tv_name2 = helper.getView(R.id.tv_name2);
                tv_name2.setText(item.getShopName() + "");
                TextView tv_notice2 = helper.getView(R.id.tv_notice2);
                tv_notice2.setText(item.getNotice() + "");
                ImageView iv_pic1 = helper.getView(R.id.iv_pic1);
                ImageView iv2_pic1 = helper.getView(R.id.iv2_pic1);
                ImageView iv2_pic2 = helper.getView(R.id.iv2_pic2);
                ImageView iv2_pic3 = helper.getView(R.id.iv2_pic3);
                if (item.getAdvPicUrlList().size() != 3) {
                    try {
                        helper.getView(R.id.layout1).setVisibility(View.VISIBLE);
                        helper.getView(R.id.layout2).setVisibility(View.GONE);
                        if (item.getAdvPicUrlList().get(0) != null && !item.getAdvPicUrlList().get(0).equals("")) {
                            GeneralUtils.setImageViewWithUrl(mainActivity, item.getAdvPicUrlList().get(0), iv_pic1, R.drawable.bg_image_classification);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        helper.getView(R.id.layout1).setVisibility(View.GONE);
                        helper.getView(R.id.layout2).setVisibility(View.VISIBLE);
                        if (item.getAdvPicUrlList().get(0) != null && !item.getAdvPicUrlList().get(0).equals("")) {
                            GeneralUtils.setImageViewWithUrl(mainActivity, item.getAdvPicUrlList().get(0), iv2_pic1, R.drawable.bg_image_classification);
                        } else {
                            iv2_pic1.setVisibility(View.GONE);
                        }
                        if (item.getAdvPicUrlList().get(1) != null && !item.getAdvPicUrlList().get(1).equals("")) {
                            GeneralUtils.setImageViewWithUrl(mainActivity, item.getAdvPicUrlList().get(1), iv2_pic2, R.drawable.bg_image_classification);
                        } else {
                            iv2_pic2.setVisibility(View.GONE);
                        }
                        if (item.getAdvPicUrlList().get(2) != null && !item.getAdvPicUrlList().get(2).equals("")) {
                            GeneralUtils.setImageViewWithUrl(mainActivity, item.getAdvPicUrlList().get(2), iv2_pic3, R.drawable.bg_image_classification);
                        } else {
                            iv2_pic3.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), EnterpriseActivity.class);
                        intent.putExtra("sid", item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        myListview.setAdapter(mAdapter);
        scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom() {
                if (isloading) return;
                if (pageNum * pageSize >= tolalNum) return;
                isloading = true;
                myListview.showload();
                pageNum = pageNum + 1;
                initBtmList();
            }
        });
        scrollView.setOnScrollChangeListener(new ScrollBottomScrollView.OnScrollChangeListener() {
            @Override
            public void scrollChange(int y) {
                float f = y / 300f;
                if (f >= 1) {
                    btnInfo.setImageResource(R.mipmap.btn_information_sort);
                } else {
                    btnInfo.setImageResource(R.mipmap.btn_information);
                }
                llTopBg.setAlpha(f);
            }
        });
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                GoodsBean item = (GoodsBean) adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(mainActivity, GoodsDetailActivity.class);
//                startActivity(intent);
            }
        });
        btnQy.setOnClickListener(this);
        btnSp.setOnClickListener(this);
        btnKx.setOnClickListener(this);
        btnYjkd.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainActivity, ConversationListActivity.class));
            }
        });
        bannerFirstInit();
        initData();
    }

    int count = 0;
    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    private void bannerFirstInit() {
        //第一次展示默认本地图片
        localImages.add(R.drawable.bg_banner_classification);//默认图片
        indexBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
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
                    Intent intentExplain = new Intent(getActivity(), CommonWebViewActivity.class);
                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, bean.getTitle());
                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
                    getActivity().startActivity(intentExplain);
                }
            }
        });
    }

    private void initData() {
        UserServiceImpl.instance().getBannerList(mainActivity, "1", IndexBannerResponse.class.getName());
        //请求banner
//        List<BannerListBean> ad = new ArrayList<>();
//        BannerListBean b1 = new BannerListBean();
//        b1.setThumCover("http://img0.imgtn.bdimg.com/it/u=654028093,2888155613&fm=26&gp=0.jpg");
//        BannerListBean b2 = new BannerListBean();
//        b2.setThumCover("http://img1.imgtn.bdimg.com/it/u=3114982284,3799690247&fm=26&gp=0.jpg");
//        BannerListBean b3 = new BannerListBean();
//        b3.setThumCover("http://img3.imgtn.bdimg.com/it/u=1173185654,2750364115&fm=26&gp=0.jpg");
//        ad.add(b1);
//        ad.add(b2);
//        ad.add(b3);
//        initBanner(ad);
        //请求底部列表接口
        pageNum = 1;
        initBtmList();
        //请求快讯数据
//        List<NoticeListBean> nlist = new ArrayList<>();
//        NoticeListBean n1 = new NoticeListBean();
//        n1.setTitle("快讯快讯快讯快讯快讯快讯1");
//        NoticeListBean n2 = new NoticeListBean();
//        n2.setTitle("快讯快讯快讯快讯快讯快讯2");
//        NoticeListBean n3 = new NoticeListBean();
//        n3.setTitle("快讯快讯快讯快讯快讯快讯3");
//        nlist.add(n1);
//        nlist.add(n2);
//        nlist.add(n3);
//        initNotice(nlist);
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
    }

    /**
     * 展示活动通知
     *
     * @param noticeList
     */
    private synchronized void initNotice(final List<NoticeListBean> noticeList) {
        if (noticeList != null && noticeList.size() > 0) {
            noticeView.setVisibility(View.VISIBLE);
            noticeView.bindNotices(noticeList);
        }
    }

    private void initBtmList() {
//        myLoading.setVisibility(View.GONE);
//        myListview.loadComplete();
//        GoodsBean g1 = new GoodsBean();
//        g1.setState(1);
//        GoodsBean g2 = new GoodsBean();
//        g2.setState(2);
//        GoodsBean g3 = new GoodsBean();
//        g3.setState(1);
//        goodsList.add(g1);
//        goodsList.add(g2);
//        goodsList.add(g3);
//        mAdapter.notifyDataSetChanged();
//        CommonMethod.setListViewHeightBasedOnChildren(myListview);
        UserServiceImpl.instance().getShopPromote(mainActivity, pageNum, pageSize, ShopsPromoteResponse.class.getName());
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
            case R.id.btn_qy:
                Intent intent = new Intent(mainActivity, EnterpriseListActivity.class);
                intent.putExtra("categorytype", "2");
                mainActivity.startActivity(intent);
                break;
            case R.id.btn_sp:
                Intent intent2 = new Intent(mainActivity, EnterpriseListActivity.class);
                intent2.putExtra("categorytype", "3");
                mainActivity.startActivity(intent2);
                break;
            case R.id.btn_kx:
                if (Build.VERSION.SDK_INT >= 23) {
                    showContacts();
                } else {
                    startActivity(new Intent(mainActivity, KuaiXiuActivity.class));
                }
                break;
            case R.id.btn_yjkd:
                if (GeneralUtils.isLogin()) {
                    UserServiceImpl.instance().checkAlreadyApplied(AlreadyAppliedResponse.class.getName());
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.search_layout:
                Intent intent3 = new Intent(mainActivity, NewSearchActivity.class);
                intent3.putExtra("searchtype", 0);
                startActivity(intent3);
                break;
        }
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        } else {
            startActivity(new Intent(mainActivity, KuaiXiuActivity.class));
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    startActivity(new Intent(mainActivity, KuaiXiuActivity.class));
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getActivity(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        indexBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        indexBanner.stopTurning();
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
            } else if (NotiTag.TAG_CHOOSE_CITY.equals(tag)) {
            }
        }
        if (event instanceof NetResponseEvent) {
            refreshLayout.refreshComplete();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            //Banner图信息
            if (tag.equals(IndexBannerResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    final IndexBannerResponse mIndexBannerResponse = GsonHelper.toType(result, IndexBannerResponse.class);
                    if (Constants.SUCESS_CODE.equals(mIndexBannerResponse.getResultCode())) {
                        CMLog.e(Constants.HTTP_TAG, result);
                        SharePref.saveString(Constants.HOME_BANNER_RESULT, result);
                        initBanner(mIndexBannerResponse.getBannerList());
                        if (mIndexBannerResponse.getNoticeList() != null) {
                            initNotice(mIndexBannerResponse.getNoticeList());
                            initHuoDong(mIndexBannerResponse.getActivityList());
                        }
                        if(mIndexBannerResponse.getTop()!=null){
                            if(mIndexBannerResponse.getTop().getPicUrlRequestUrl()!=null
                                    &&!mIndexBannerResponse.getTop().getPicUrlRequestUrl().equals("")) {
                                GeneralUtils.setImageViewWithUrl(getActivity(),mIndexBannerResponse.getTop().getPicUrlRequestUrl(), ivTop, R.drawable.bg_banner_homepage_two);
                            }
                            if(mIndexBannerResponse.getTop().getId()!=null
                                    &&!mIndexBannerResponse.getTop().getId().equals("")) {
                                ivTop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent2=new Intent(getActivity(), EnterpriseActivity.class);
                                        intent2.putExtra("sid",mIndexBannerResponse.getTop().getId());
                                        startActivity(intent2);
                                    }
                                });
                            }
                        }

                    } else {
                        ErrorCode.doCode(getActivity(), mIndexBannerResponse.getResultCode(), mIndexBannerResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(ShopsPromoteResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ShopsPromoteResponse shopsPromoteResponse = GsonHelper.toType(result, ShopsPromoteResponse.class);
                    if (Constants.SUCESS_CODE.equals(shopsPromoteResponse.getResultCode())) {
                        CMLog.e(Constants.HTTP_TAG, result);
                        myListview.loadComplete();
                        isloading = false;
                        if (pageNum == 1) {
                            shopList.clear();
                        }
                        tolalNum = shopsPromoteResponse.getTotalCount();
                        if (shopsPromoteResponse.getShopList() != null && shopsPromoteResponse.getShopList().size() > 0) {
                            shopList.addAll(shopsPromoteResponse.getShopList());
                            mAdapter.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myListview);
                        }
                    } else {
                        ErrorCode.doCode(getActivity(), shopsPromoteResponse.getResultCode(), shopsPromoteResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(AlreadyAppliedResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    AlreadyAppliedResponse alreadyAppliedResponse = GsonHelper.toType(result, AlreadyAppliedResponse.class);
                    if (Constants.SUCESS_CODE.equals(alreadyAppliedResponse.getResultCode())) {
                        CMLog.e(Constants.HTTP_TAG, result);
                        if (alreadyAppliedResponse.getStatus() == 1) {
                            GeneralUtils.toActyOtherwiseLogin(getActivity(), OneButtonShopActivity.class);
                        } else if (alreadyAppliedResponse.getStatus() == 2) {
                            ToastUtils.showToast(getActivity(), "开店审核中");
                        } else if (alreadyAppliedResponse.getStatus() == 3) {
                            ToastUtils.showToast(getActivity(), "您的店铺已经审核通过");
                        }

                    } else {
                        ErrorCode.doCode(getActivity(), alreadyAppliedResponse.getResultCode(), alreadyAppliedResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    private void initHuoDong(List<ActivityListBean> activityList) {
        for (ActivityListBean item : activityList) {
            switch (item.getPos()) {
                case 1:
                    initHDData(item, rlHuodong, tvHuodong, textView, imageView, R.drawable.bg_image_classification);
                    break;
                case 2:
                    initHDData(item, rl2Huodong, tvHuodong2, textView2, imageView2, R.drawable.bg_image_classification);
                    break;
                case 3:
                    initHDData(item, rl3Huodong, tvHuodong3, textView3, imageView3, R.drawable.bg_image_classification);
                    break;
                case 4:
                    initHDData(item, rl4Huodong, tvHuodong4, textView4, imageView4, R.drawable.bg_image_classification);
                    break;
            }
        }
    }

    private void initHDData(final ActivityListBean item, RelativeLayout rlHuodong, TextView tvHuodong, TextView textView, ImageView imageView, int defaultpic) {
        if (item.getActivityID() != null && !item.getActivityID().equals("")) {
            tvHuodong.setText(item.getActivityName() + "");
            if (!item.getPicUrl().equals("")) {
                GeneralUtils.setImageViewWithUrl(mainActivity, item.getCoverRequestUrl(), imageView, defaultpic);
            }
            textView.setText(item.getWords());
            rlHuodong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getActivityID() != null && !item.getActivityID().equals("")) {
                        Intent intent = new Intent(mainActivity, HuoDongActivity.class);
                        intent.putExtra("ActivityListBean", (Serializable) item);
                        startActivity(intent);
                    }
                }
            });
        }
    }

}
