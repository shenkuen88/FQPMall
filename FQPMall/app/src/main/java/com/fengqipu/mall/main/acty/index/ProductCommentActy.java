package com.fengqipu.mall.main.acty.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.AppraiseListBean;
import com.fengqipu.mall.bean.index.ProductCommentResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.MyListView;

import java.util.ArrayList;

/**
 * Created by qing on 2016/7/24.
 * 商品评论列表
 */
public class ProductCommentActy extends BaseActivity {

    private MyListView comment_lv;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private int pageSize = Constants.LIST_NUM;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private boolean isLoadingMoreData;
    private int pageTotal = 0;
    /**
     * 适配器
     */
    private CommonAdapter<AppraiseListBean> adapter;
    /**
     * 存放数据记录
     */
    private ArrayList<AppraiseListBean> datas = new ArrayList<>();
    /**
     * 加载更多 数据
     */
    private ArrayList<AppraiseListBean> datasMore;
    /**
     * 下拉刷新
     */
    private String contentId = "1";
    private View loadingView;
    private int lastVisibileItem;

    private View noCommunity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_comment);
        initTitle();
        initEmtyView();
        initAll();
        initAdapter();
        initData(1);

    }
    //初始化空View
    private void initEmtyView() {
        noCommunity = V.f(this, R.id.no_history);
        ImageView tips_pic = V.f(this, R.id.tips_pic);
        TextView tips = V.f(this, R.id.tips);
        tips.setText("暂无评论~");
        tips_pic.setImageResource(R.mipmap.no_post_icon);
        noCommunity.setVisibility(View.GONE);
    }
    private void initAdapter() {
        adapter = new CommonAdapter<AppraiseListBean>(mContext, datas, R.layout.item_product_comment) {
            @Override
            public void convert(ViewHolder helper, final AppraiseListBean item) {
                ImageView ivHead = helper.getView(R.id.comment_head_iv);
                GeneralUtils.setRoundImageViewWithUrl(mContext, item.getUserPortrait(), ivHead, R.drawable.default_head);
                helper.setText(R.id.comment_name_tv, item.getUserNickName());
                helper.setText(R.id.comment_time_tv, item.getCreateTime());
                helper.setText(R.id.comment_content_tv, item.getText());
                MyGridView my_grid_view = helper.getView(R.id.my_grid_view);
                CommonAdapter<String> gadapter = new CommonAdapter<String>(mContext, item.getPicUrlList()                                                                                                                                                                                          , R.layout.item_pic) {
                    @Override
                    public void convert(ViewHolder helper, String url) {
                        ImageView iv_pic = helper.getView(R.id.iv_pic);
                        if (GeneralUtils.isNotNullOrZeroLenght(url)) {
                            GeneralUtils.setImageViewWithUrl(mContext, url, iv_pic, R.drawable.bg_image_classification);
                        }
                    }
                };
                my_grid_view.setAdapter(gadapter);
            }
        };
        comment_lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(comment_lv);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            tvLoadMore.setVisibility(View.VISIBLE);
            llLoading.setVisibility(View.GONE);
            //获取评论列表
            if (tag.equals(ProductCommentResponse.class.getName())) {
                llLoading.setVisibility(View.GONE);
                tvLoadMore.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ProductCommentResponse mGetCommonCommentResponse = GsonHelper.toType(result, ProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetCommonCommentResponse.getResultCode())) {
                        pageTotal = mGetCommonCommentResponse.getTotalCount();
                        if (loadPageNo == 1) {
                            datas = new ArrayList<>();
                            datasMore = new ArrayList<>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<AppraiseListBean>) mGetCommonCommentResponse.getAppraiseList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            comment_lv.setVisibility(View.GONE);
                            noCommunity.setVisibility(View.VISIBLE);
                        } else {//有记录
                            adapter.setData(datas);
                            noCommunity.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                                isLoadingMoreData = false;
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mGetCommonCommentResponse.getResultCode(), mGetCommonCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("评论");
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        comment_lv = (MyListView) findViewById(R.id.comment_lv);
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        tvLoadMore = (TextView) loadingView.findViewById(R.id.load_more_tv);
        comment_lv.addFooterView(loadingView);
        llLoading.setVisibility(View.VISIBLE);
        tvLoadMore.setVisibility(View.GONE);

        comment_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == comment_lv.getCount())
                    loadMore();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });
    }

    private void loadMore() {
        if (pageTotal != 0 && pageNo < pageTotal && tvLoadMore.getText().equals("加载更多")) {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
//            UserServiceImpl.instance().getProductCommentList(contentId, loadPageNo, ProductCommentResponse.class.getName());
        } else {
            tvLoadMore.setText("已加载完毕");
        }
    }

    private void initData(int loadPageNo) {
        NetLoadingDialog.getInstance().loading(mContext);
//        UserServiceImpl.instance().getProductCommentList(contentId, loadPageNo, ProductCommentResponse.class.getName());

    }


    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }
}
