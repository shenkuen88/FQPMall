package com.fengqipu.mall.main.fragment.index;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.AppraiseListBean;
import com.fengqipu.mall.bean.index.ContentDetailResponse;
import com.fengqipu.mall.bean.index.ContentListBean;
import com.fengqipu.mall.bean.index.ContentStyleListBean;
import com.fengqipu.mall.bean.index.ProductCommentResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.index.ProductCommentActy;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.ProductDialogUtil;
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
 * 产品详情
 */
public class ProductFragment extends BaseFragment implements View.OnClickListener {


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
     * 选择
     */
    private TextView tvChoose;
    /**
     * 评论人名
     */
    private TextView tvCommentName;
    /**
     * 评论内容
     */
    private TextView tvComment;
    /**
     * 评论诗句
     */
    private TextView tvCommentTime;
    /**
     * 评论头像
     */
    private ImageView ivCommentHead;

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
    /**
     * 价格
     */
    private TextView tvPrice;
    private PtrClassicFrameLayout refresh;
    /**
     * 现价
     */
    private TextView tvPriceNew;
    /**
     * 款式
     */
    private List<ContentStyleListBean> styleList;
    /**
     * 已选择
     */
    private RelativeLayout rlChoose;
    private ContentDetailResponse mContentDetailResponse;
    private Dialog styleDialog;
    private RelativeLayout rlComment;
    private static String mContentId;

    public static ProductFragment newInstance(String contentId) {
        ProductFragment fragment = new ProductFragment();
        mContentId = contentId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.product_detai_top, null);
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
            } else if (NotiTag.TAG_CHANGE_STYLE.equals(tag)) {
                String buyStyle = ((NoticeEvent) event).getText();
                tvChoose.setText(buyStyle);
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
                        ContentListBean product = mContentDetailResponse.getContent();
                        List<String> bannerList = new ArrayList<>();
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl1())) {
                            bannerList.add(product.getThumPicUrl1());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl2())) {
                            bannerList.add(product.getThumPicUrl2());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl3())) {
                            bannerList.add(product.getThumPicUrl3());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl4())) {
                            bannerList.add(product.getThumPicUrl4());
                        }
                        initBanner(bannerList);
                        tvName.setText(product.getContentName());
                        tvPrice.setText(product.getPrice());
                        //已选
                        styleList = mContentDetailResponse.getContentStyleList();
                        if (styleList.size() > 0) {
                            tvChoose.setText(styleList.get(0).getStyle());
                            tvPrice.setText("¥" + styleList.get(0).getPrice());
                        }
                        styleDialog = ProductDialogUtil.productChoose(getActivity(), mContentDetailResponse);
                    } else {
                        ErrorCode.doCode(getActivity(), mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            //评论
            if (tag.equals(ProductCommentResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ProductCommentResponse mProductCommentResponse = GsonHelper.toType(result, ProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mProductCommentResponse.getResultCode())) {
                        if (mProductCommentResponse.getAppraiseList().size() > 0) {
                            mView.findViewById(R.id.comment_detail_rl).setVisibility(View.VISIBLE);
                            mView.findViewById(R.id.no_comment_tv).setVisibility(View.GONE);
                            AppraiseListBean appraiseItem = mProductCommentResponse.getAppraiseList().get(0);
                            tvComment.setText(appraiseItem.getText());
                            tvCommentName.setText(appraiseItem.getUserNickName());
                            tvCommentTime.setText(appraiseItem.getCreateTime());
                            GeneralUtils.setRoundImageViewWithUrl(getActivity(), appraiseItem.getUserPortrait(), ivCommentHead, R.drawable.default_head);
                        }else {
                            mView.findViewById(R.id.comment_detail_rl).setVisibility(View.GONE);
                            mView.findViewById(R.id.no_comment_tv).setVisibility(View.VISIBLE);
                        }
                    } else {
                        ErrorCode.doCode(getActivity(), mProductCommentResponse.getResultCode(), mProductCommentResponse.getDesc());
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
            case R.id.comment_rl:
            case R.id.comment_detail_rl:
                startActivity(new Intent(getActivity(), ProductCommentActy.class));
                break;
            case R.id.share_tv:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " " + mContentDetailResponse.getContent().getContentName());
                intent.putExtra(Intent.EXTRA_TEXT, mContentDetailResponse.getContent().getSpecificationLink());
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
                break;
            case R.id.choose_rl:
                if (styleDialog != null) {
                    styleDialog.show();
                }
                break;
            case R.id.btn_fanhui:
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CLOSE_ACTIVITY));
                break;
        }
    }

    private void initData() {
        UserServiceImpl.instance().getContentDetail(mContentId, ContentDetailResponse.class.getName());
        UserServiceImpl.instance().getProductCommentList(mContentId, ProductCommentResponse.class.getName());
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
        rlComment = (RelativeLayout) mView.findViewById(R.id.comment_rl);
        tvChoose = (TextView) mView.findViewById(R.id.choose_tv);
        tvName = (TextView) mView.findViewById(R.id.name_tv);
        tvPrice = (TextView) mView.findViewById(R.id.price_tv);
        tvPriceNew = (TextView) mView.findViewById(R.id.price1_tv);
        tvCommentName = (TextView) mView.findViewById(R.id.comment_name_tv);
        tvCommentTime = (TextView) mView.findViewById(R.id.comment_time_tv);
        tvComment = (TextView) mView.findViewById(R.id.comment_content_tv);
        rlChoose = (RelativeLayout) mView.findViewById(R.id.choose_rl);
        ivCommentHead = (ImageView) mView.findViewById(R.id.comment_head_iv);
        tvShare.setOnClickListener(this);
        mView.findViewById(R.id.comment_detail_rl).setOnClickListener(this);
        rlChoose.setOnClickListener(this);
        rlComment.setOnClickListener(this);
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
