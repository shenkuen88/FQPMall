package com.fengqipu.mall.main.fragment.goods;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.conmunity.ImageBean;
import com.fengqipu.mall.bean.goods.GoodsCommentResponse;
import com.fengqipu.mall.bean.goods.GoodsDetailResponse;
import com.fengqipu.mall.bean.index.BannerListBean;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.ShareSDKMethod;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.MyScrollView;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.banner.ConvenientBanner;
import com.fengqipu.mall.view.banner.demo.LocalImageHolderView;
import com.fengqipu.mall.view.banner.demo.NetworkImageHolderView;
import com.fengqipu.mall.view.banner.holder.CBViewHolderCreator;
import com.fengqipu.mall.view.banner.listener.OnItemClickListener;
import com.fengqipu.mall.view.photopicker.view.CommunityImageZoomActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.fengqipu.mall.R.id.goods_title;
import static com.fengqipu.mall.R.id.tv_gg;


/**
 * Created by Administrator on 2016/6/13.
 */
public class GoodsFragment extends BaseFragment implements View.OnClickListener {
    private static GoodsDetailActivity goodsDetailActivity;
    @Bind(R.id.index_banner)
    ConvenientBanner indexBanner;
    @Bind(goods_title)
    TextView goodsTitle;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.or_price)
    TextView orPrice;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.scrollView)
    MyScrollView scrollView;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.already_sale)
    TextView alreadySale;
    @Bind(R.id.tv_freight)
    TextView tvFreight;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(tv_gg)
    TextView tvGg;
    @Bind(R.id.tv_commentcount)
    TextView tvCommentcount;
    @Bind(R.id.btn_comment)
    LinearLayout btnComment;
    @Bind(R.id.share_goods)
    LinearLayout shareGoods;

    private CommonAdapter<GoodsCommentResponse.AppraiseListBean> mAdapter;
    private List<GoodsCommentResponse.AppraiseListBean> comentList = new ArrayList<>();

    public GoodsFragment() {
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
        goodsDetailActivity = (GoodsDetailActivity) getActivity();
        View v = LayoutInflater.from(goodsDetailActivity).inflate(R.layout.fragment_goods, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }
    int lastVisibileItem = 0;
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
        mAdapter = new CommonAdapter<GoodsCommentResponse.AppraiseListBean>(goodsDetailActivity, comentList, R.layout.item_product_comment) {
            @Override
            public void convert(ViewHolder helper, GoodsCommentResponse.AppraiseListBean item) {
                ImageView comment_head_iv = helper.getView(R.id.comment_head_iv);
                if (item.getUserPortrait() != null && !item.getUserPortrait().equals("")) {
                    GeneralUtils.setImageViewWithUrl(goodsDetailActivity, item.getUserPortrait(), comment_head_iv, R.drawable.default_head);
                }
                helper.getView(R.id.rl_comment).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        goodsDetailActivity.chang2Comment();
                    }
                });
                TextView comment_name_tv = helper.getView(R.id.comment_name_tv);
                comment_name_tv.setText(item.getUserNickName() + "");
                TextView comment_content_tv = helper.getView(R.id.comment_content_tv);
                comment_content_tv.setText(item.getText() + "");
                TextView comment_time_tv = helper.getView(R.id.comment_time_tv);
                comment_time_tv.setText(item.getCreateTimeShow());
                MyGridView my_grid_view = helper.getView(R.id.my_grid_view);
                my_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        goodsDetailActivity.chang2Comment();
                    }
                });
                CommonAdapter<String> gadapter = new CommonAdapter<String>(goodsDetailActivity, item.getPicUrlList(), R.layout.item_pic) {
                    @Override
                    public void convert(ViewHolder helper, String url) {
                        ImageView iv_pic = helper.getView(R.id.iv_pic);
                        if (GeneralUtils.isNotNullOrZeroLenght(url)) {
                            GeneralUtils.setImageViewWithUrl(goodsDetailActivity, url, iv_pic, R.drawable.bg_image_classification);
                        }
                    }
                };
                my_grid_view.setAdapter(gadapter);
            }
        };
        myListview.setAdapter(mAdapter);
//        myListview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == myListview.getCount()){
//                    goodsDetailActivity.changePager(1);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
//            }
//        });
//        scrollView.setScrollViewListener(new MyScrollView1.IScrollChangedListener() {
//            @Override
//            public void onScrolledToBottom() {
//                Intent intent =new Intent(getActivity(), GoodsWebActivity.class);
//                intent.putExtra("url",goodsDetailActivity.goodsDetailResponse.getContent().getDescriptionLink());
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.anim_down_in, R.anim.anim_down_out);
//            }
//
//            @Override
//            public void onScrolledToTop() {
//
//            }
//        });
//        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                GoodsBean item = (GoodsBean) adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(goodsDetailActivity, GoodsDetailActivity.class);
//                startActivity(intent);
//            }
//        });
        bannerFirstInit();
//        initData();
        tvGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsDetailActivity.showGuiGeDialog();
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsDetailActivity.chang2Comment();
            }
        });
        shareGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(goodsDetailActivity.goodsDetailResponse!=null) {
                    ShareSDKMethod.showShare(goodsDetailActivity,
                            goodsDetailActivity.goodsDetailResponse.getContent().getContentName()
                    ,goodsDetailActivity.goodsDetailResponse.getContent().getDescriptionLink()
                            ,goodsDetailActivity.goodsDetailResponse.getContent().getDescriptionLink());
                }
            }
        });
    }

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
        imgbeans.clear();
        for (int i = 0; i < ad.size(); i++) {
            if (!networkImages.contains(ad.get(i).getCoverRequestUrl())) {
                networkImages.add(ad.get(i).getCoverRequestUrl());
                imgbeans.add(new ImageBean(ad.get(i).getCoverRequestUrl(),0,0));
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
                Intent intent=new Intent(getActivity(), CommunityImageZoomActivity.class);
                intent.putExtra(IntentCode.COMMUNITY_IMAGE_DATA,(Serializable) imgbeans);
                intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION,position);
                startActivity(intent);
            }
        });
    }
    private List<ImageBean> imgbeans=new ArrayList<>();
    private void initData() {
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
//        initBtmList();
        goodsDetailActivity.initData();
//        goodsDetailActivity.getProComment();
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

    private void initBtmList() {
//        myLoading.setVisibility(View.GONE);
//        comentList.clear();
//        myListview.loadComplete();
//        CommentListBean g1 = new CommentListBean();
//        comentList.add(g1);
//        mAdapter.notifyDataSetChanged();
//        CommonMethod.setListViewHeightBasedOnChildren(myListview);
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
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (tag.equals("REFRESH")) {
                GoodsDetailResponse goodsDetailResponse = goodsDetailActivity.goodsDetailResponse;
                List<BannerListBean> blist = new ArrayList<>();
                if (goodsDetailResponse.getContent().getPicUrlList() != null && goodsDetailResponse.getContent().getPicUrlList().size() > 0) {
                    for (String s : goodsDetailResponse.getContent().getPicUrlList()) {
                        BannerListBean b = new BannerListBean();
                        b.setCoverRequestUrl(s);
                        blist.add(b);
                    }
                }
                initBanner(blist);
                goodsTitle.setText(goodsDetailResponse.getContent().getContentName() + "");
                price.setText("￥" + goodsDetailResponse.getContent().getPrice());
                orPrice.setText("￥" + goodsDetailResponse.getContent().getOriginalPrice());
                orPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String num=goodsDetailResponse.getContent().getSales();
                if(num==null||num.equals("")){
                    num="0";
                }
                alreadySale.setText("已成交" + num + "件");
                if (goodsDetailResponse.getFreight() == 0) {
                    tvFreight.setText("免运费");
                } else {
                    tvFreight.setText("快递:" + goodsDetailResponse.getFreight() + "元");
                }
                tvLocation.setText("发货地:" + goodsDetailResponse.getContent().getShopProvince() + goodsDetailResponse.getContent().getShopCity());
                try {
                    String str = "";
                    if (goodsDetailResponse.getContentStyleList().get(0).getStyle() != null
                            && !goodsDetailResponse.getContentStyleList().get(0).getStyle().equals("")) {
                        str = goodsDetailResponse.getContentStyleList().get(0).getStyle();
                        goodsDetailActivity.style = goodsDetailResponse.getContentStyleList().get(0).getStyle();
                    }
                    if (goodsDetailResponse.getContentStyleList().get(0).getColor() != null
                            && !goodsDetailResponse.getContentStyleList().get(0).getColor().equals("")) {
                        str = str + "、" + goodsDetailResponse.getContentStyleList().get(0).getColor();
                        goodsDetailActivity.color = goodsDetailResponse.getContentStyleList().get(0).getColor();
                    }
                    str = str + "、1件";
                    tvGg.setText(str);
                    price.setText("￥" + goodsDetailResponse.getContentStyleList().get(0).getPrice());
                    goodsDetailActivity.curprice = goodsDetailResponse.getContentStyleList().get(0).getPrice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tag.equals("GUIGEREFRESH")) {
                if (goodsDetailActivity.guiGeBtmDialog != null) {
                    String[] strs = goodsDetailActivity.guiGeBtmDialog.tv_guige.getText().toString().split("、");
                    goodsDetailActivity.style="";
                    goodsDetailActivity.color="";
                    try {
                        if (strs.length == 3) {
                            goodsDetailActivity.style = strs[0];
                            goodsDetailActivity.color = strs[1];
                            try {
                                goodsDetailActivity.num = Integer.getInteger(strs[2].replace("件", ""));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            goodsDetailActivity.style = strs[0];
                            try {
                                goodsDetailActivity.num = Integer.getInteger(strs[1].replace("件", ""));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("sub",goodsDetailActivity.guiGeBtmDialog.stylestrs.size()+","+goodsDetailActivity.style+","+goodsDetailActivity.guiGeBtmDialog.colorstrs.size()+","+goodsDetailActivity.color);
                    if((goodsDetailActivity.guiGeBtmDialog.stylestrs.size()>0&&goodsDetailActivity.style.equals(""))||(goodsDetailActivity.guiGeBtmDialog.colorstrs.size()>0&&goodsDetailActivity.color.equals(""))){
                        goodsDetailActivity.GUIGEERROR=true;
                    }else{
                        goodsDetailActivity.GUIGEERROR=false;
                    }
                    Log.e("sub","goodsDetailActivity.GUIGEERROR="+goodsDetailActivity.GUIGEERROR);
                    tvGg.setText(goodsDetailActivity.guiGeBtmDialog.tv_guige.getText().toString());
                    price.setText(goodsDetailActivity.guiGeBtmDialog.gg_price.getText().toString());
                    goodsDetailActivity.curprice = Double.valueOf(goodsDetailActivity.guiGeBtmDialog.gg_price.getText().toString().replace("￥", ""));
                }
            }
            if (tag.equals("COMMENTREFRESH")) {
                tvCommentcount.setText("其他小伙伴怎么说(" + goodsDetailActivity.goodsCommentResponse.getTotalCount() + ")");
                if (goodsDetailActivity.goodsCommentResponse.getAppraiseList() != null
                        && goodsDetailActivity.goodsCommentResponse.getAppraiseList().size() > 0) {
                    comentList.clear();
                    myListview.loadComplete();
                    comentList.add(goodsDetailActivity.goodsCommentResponse.getAppraiseList().get(0));
                    mAdapter.notifyDataSetChanged();
                    CommonMethod.setListViewHeightBasedOnChildren(myListview);
                }
            }
        }
    }
}
