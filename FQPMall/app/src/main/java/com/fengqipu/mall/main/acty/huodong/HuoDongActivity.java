package com.fengqipu.mall.main.acty.huodong;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.huodong.HuodongResponse;
import com.fengqipu.mall.bean.index.ActivityListBean;
import com.fengqipu.mall.bean.index.BannerListBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.ConversationListActivity;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
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
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.ScrollBottomScrollView;
import com.fengqipu.mall.view.banner.ConvenientBanner;
import com.fengqipu.mall.view.banner.demo.LocalImageHolderView;
import com.fengqipu.mall.view.banner.demo.NetworkImageHolderView;
import com.fengqipu.mall.view.banner.holder.CBViewHolderCreator;
import com.fengqipu.mall.view.banner.listener.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HuoDongActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.index_banner)
    ConvenientBanner indexBanner;
    @Bind(R.id.sy_time)
    TextView syTime;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.my_scrollview)
    ScrollBottomScrollView myScrollview;
    private ActivityListBean activityListBean = null;
    int pageNum = 1;
    int pageSize = 10;
    private CommonAdapter<HuodongResponse.ContentListBean> mAdapter;
    private List<HuodongResponse.ContentListBean> goodsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_dong);
        ButterKnife.bind(this);
        activityListBean = (ActivityListBean) getIntent().getSerializableExtra("ActivityListBean");
        initAll();
    }

    boolean flag = true;

    @Override
    public void initView() {
        initTitle();
        bannerFirstInit();
        List<BannerListBean> bannerListBeen = new ArrayList<>();
        BannerListBean b = new BannerListBean();
        b.setCoverRequestUrl(activityListBean.getPicUrlRequestUrl());
        bannerListBeen.add(b);
        initBanner(bannerListBeen);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (flag) {
                        if (huodongResponse != null) {
                            final String time = doEndTime(activityListBean.getEndTime(), activityListBean.getEndClock());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    syTime.setText("距离本场结束:" + time);
                                }
                            });
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    private void initData() {
        UserServiceImpl.instance().getHuoDongList(this, activityListBean.getActivityID(), pageNum, pageSize, HuodongResponse.class.getName());
    }

    int totalCount = 0;

    @Override
    public void initViewData() {
        mAdapter = new CommonAdapter<HuodongResponse.ContentListBean>(this, goodsList, R.layout.item_huodong_goods) {
            @Override
            public void convert(ViewHolder helper, final HuodongResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(HuoDongActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
                }
                TextView goods_info = helper.getView(R.id.goods_info);
                TextView goods_price = helper.getView(R.id.goods_price);
                TextView goods_old_price = helper.getView(R.id.goods_old_price);
                TextView goods_xl = helper.getView(R.id.goods_xl);
                Button btn_msq = helper.getView(R.id.btn_msq);
                goods_info.setText(item.getContentName() + "");
                goods_price.setText("￥" + item.getPrice() + "");
                goods_old_price.setText("￥" + item.getOriginalPrice() + "");
                goods_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
                String num=item.getSales();
                if(num==null||num.equals("")){
                    num="0";
                }
                goods_xl.setText("销量" + item.getSales() + "件");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HuoDongActivity.this, GoodsDetailActivity.class);
                        intent.putExtra("contentID", item.getId());
                        startActivity(intent);
                    }
                });

            }
        };
        myListview.setAdapter(mAdapter);
//        myListview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == myListview.getCount())
//                if (pageNum * pageSize >= totalCount) return;
//                pageNum = pageNum + 1;
//                initData();
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
//            }
//        });
        myScrollview.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom() {
                if (pageNum * pageSize >= totalCount) return;
                pageNum = pageNum + 1;
                initData();
            }
        });
        initData();
    }

    private int lastVisibileItem;

    @Override
    public void initEvent() {
    }

    @Override
    public void onClick(View view) {

    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("限时抢购");
        headView.setRightImage(R.mipmap.btn_information_sort);
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
//                BannerListBean bean = ad.get(position);
//                if (bean != null && GeneralUtils.isNotNullOrZeroLenght(bean.getLink())) {
//                    //页面跳转
//                    Intent intentExplain = new Intent(HuoDongActivity.this, CommonWebViewActivity.class);
//                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, bean.getTitle());
//                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
//                    startActivity(intentExplain);
//                }
            }
        });
    }

    HuodongResponse huodongResponse;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                startActivity(new Intent(HuoDongActivity.this, ConversationListActivity.class));
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(HuodongResponse.class.getName())) {
                huodongResponse = GsonHelper.toType(result, HuodongResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(huodongResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        totalCount = huodongResponse.getTotalCount();
                        if (huodongResponse.getContentList() != null && huodongResponse.getContentList().size() > 0) {
                            goodsList.addAll(huodongResponse.getContentList());
                        }
                        mAdapter.notifyDataSetChanged();
                        CommonMethod.setListViewHeightBasedOnChildren(myListview);
                    } else {
                        ErrorCode.doCode(this, huodongResponse.getResultCode(), huodongResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }

    /**
     * @param startTime  "2017-06-16",
     * @param startClock "08:00",
     */
    private String doEndTime(String startTime, String startClock) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(startTime + " " + startClock + ":00");
            Date d2 = df.parse(df.format(new Date()));
            //Date   d2 = new   Date(System.currentTimeMillis());//你也可以获取当前时间
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long ss = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
            if (days > 0) {
                return "  " + days + "天" + hours + "时" + minutes + "分" + ss + "秒";
            } else {
                return "  " + hours + "时" + minutes + "分" + ss + "秒";
            }
        } catch (Exception e) {
            return "";
        }
    }

}
