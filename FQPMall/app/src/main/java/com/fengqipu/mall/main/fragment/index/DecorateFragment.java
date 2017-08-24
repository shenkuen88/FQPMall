package com.fengqipu.mall.main.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.ContentDetailResponse;
import com.fengqipu.mall.bean.index.ContentListBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.banner.CusConvenientBanner;
import com.fengqipu.mall.view.banner.demo.LocalImageHolderView;
import com.fengqipu.mall.view.banner.demo.NetworkImageHolderView;
import com.fengqipu.mall.view.banner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 装修详情 头部内容
 */
public class DecorateFragment extends BaseFragment implements View.OnClickListener {


    private View mView;
    /**
     * Banner
     */
    private CusConvenientBanner banner;
    /**
     * 分享
     */
    private TextView tvShare;


    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();
    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    /**
     * 商品名称
     */
    private TextView tvName;

    private PtrClassicFrameLayout refresh;

    private ContentDetailResponse mContentDetailResponse;
    /**
     * 参考报价
     */
    private TextView tvPrice;
    /**
     * 摘要
     */
    private TextView tvAbstract;

    private static String mContentId;

    public static DecorateFragment newInstance(String contentId) {
        DecorateFragment fragment = new DecorateFragment();
        mContentId = contentId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_decorate_detai_top, null);
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
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            refresh.refreshComplete();
            if (tag.equals(ContentDetailResponse.class.getName())) {
                mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode())) {
                        CMLog.e(Constants.HTTP_TAG, result);
                        ContentListBean product = mContentDetailResponse.getContent();
                        tvName.setText(product.getContentName());
                        tvPrice.setText("¥"+product.getPrice());
                        tvAbstract.setText(product.getAbstracts());
                        List<String> bannerList = new ArrayList<>();
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getThumPicUrl1())) {
                            bannerList.add(product.getThumPicUrl1());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getThumPicUrl2())) {
                            bannerList.add(product.getThumPicUrl2());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getThumPicUrl3())) {
                            bannerList.add(product.getThumPicUrl3());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getThumPicUrl4())) {
                            bannerList.add(product.getThumPicUrl4());
                        }
                        initBanner(bannerList);
                    } else {
                        ErrorCode.doCode(getActivity(), mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }

        }
    }


    /**
     * Banner展示网络数据
     *
     * @param ad
     */
    private synchronized void initBanner(final List<String> ad) {
        if (ad == null || ad.size() < 1) {
            return;
        }
        networkImages.clear();
        for (int i = 0; i < ad.size(); i++) {
            if (!networkImages.contains(ad.get(i))) {
                networkImages.add(ad.get(i));
            }
        }
        banner.stopTurning();
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_tv:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " " + mContentDetailResponse.getContent().getContentName());
                intent.putExtra(Intent.EXTRA_TEXT, mContentDetailResponse.getContent().getSpecificationLink());
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
                break;
            case R.id.btn_fanhui:
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CLOSE_ACTIVITY));
                break;
        }
    }

    private void initData() {
        UserServiceImpl.instance().getContentDetail(mContentId, ContentDetailResponse.class.getName());
    }

    private void initView() {
        mView.findViewById(R.id.btn_fanhui).setOnClickListener(this);
//        if (!DisplayUtil.checkDeviceHasNavigationBar(getActivity())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                mView.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//            }
//        }
        refresh = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_grid_view_frame);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                refresh.autoRefresh();
            }
        }, 200);
        banner = (CusConvenientBanner) mView.findViewById(R.id.product_banner);
        tvShare = (TextView) mView.findViewById(R.id.share_tv);
        tvPrice = (TextView) mView.findViewById(R.id.price_tv);
        tvName = (TextView) mView.findViewById(R.id.name_tv);
        tvAbstract = (TextView) mView.findViewById(R.id.abstract_tv);
        tvShare.setOnClickListener(this);
        bannerFirstInit();
        refresh.setLastUpdateTimeRelateObject(this);
        refresh.setResistance(1.7f);
        refresh.setRatioOfHeaderHeightToRefresh(1.2f);
        refresh.setDurationToClose(200);
        refresh.setDurationToCloseHeader(1000);
        // default is false
        refresh.setPullToRefresh(false);
        // default is true
        refresh.setKeepHeaderWhenRefresh(true);

        refresh.disableWhenHorizontalMove(true);

        refresh.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit() {
        //第一次展示默认本地图片
        localImages.add(R.drawable.bg_banner_classification);//默认图片
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        banner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        banner.stopTurning();
    }

}
