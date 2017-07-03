package com.fengqipu.mall.main.acty.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.SystemMessageResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.OrderDetailActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 商品的消息
 * item_ststem_notice
 */
public class ProductNoticeActivity extends BaseActivity implements View.OnClickListener {

    private ListView comment_lv;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private int pageSize = Constants.LIST_NUM;
    private boolean isLoadingMoreData;

    /**
     * 适配器
     */
    private CommonAdapter<SystemMessageResponse.MessageListBean> adapter;
    /**
     * 存放数据记录
     */
    private ArrayList<SystemMessageResponse.MessageListBean> datas = new ArrayList<>();
    /**
     * 加载更多 数据
     */
    private ArrayList<SystemMessageResponse.MessageListBean> datasMore;
    /**
     * 下拉刷新
     */
    private PtrClassicFrameLayout refreshLayout;
    private SystemMessageResponse mSystemMessageResponse;
    private View loadingView;
    private int lastVisibileItem;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private View loadView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_notice);
        initTitle();
        initAll();
        initAdapter();
        initData(1);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<SystemMessageResponse.MessageListBean>(mContext, datas, R.layout.item_system_notice) {
            @Override
            public void convert(ViewHolder helper, final SystemMessageResponse.MessageListBean item) {
                helper.setText(R.id.name_tv, item.getMessage());
                helper.setText(R.id.intro_tv, item.getMessage());
                helper.setText(R.id.time_tv, item.getCreateTime());
                ImageView iv = helper.getView(R.id.product_iv);
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl())) {
                    GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl(), iv, R.drawable.default_bg);
                }
                if (item.getMessageType() == 1) {
                    helper.getView(R.id.item_ll).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, OrderDetailActivity.class);
                            intent.putExtra(IntentCode.ORDER_STATE, "1");
                            intent.putExtra(IntentCode.C_ORDER_ID, item.getOrderID());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        comment_lv.setAdapter(adapter);
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
            refreshLayout.refreshComplete();
            NetLoadingDialog.getInstance().dismissDialog();
            llLoading.setVisibility(View.GONE);
            //获取评论列表
            if (tag.equals(SystemMessageResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    mSystemMessageResponse = GsonHelper.toType(result, SystemMessageResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mSystemMessageResponse.getResultCode())) {
                        if (loadPageNo == 1) {
                            datas = new ArrayList<>();
                            datasMore = new ArrayList<>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<SystemMessageResponse.MessageListBean>) mSystemMessageResponse.getMessageList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            comment_lv.setVisibility(View.GONE);
                            tvLoadMore.setVisibility(View.GONE);
                        } else {//有记录
                            comment_lv.setVisibility(View.VISIBLE);
                            adapter.setData(datas);
                            adapter.notifyDataSetChanged();
                            loadView.setVisibility(View.VISIBLE);
                            if (datas.size() >= mSystemMessageResponse.getTotalCount()) {
                                tvLoadMore.setText("已加载完毕");
                            }
                            tvLoadMore.setVisibility(View.VISIBLE);
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                                isLoadingMoreData = false;
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mSystemMessageResponse.getResultCode(), mSystemMessageResponse.getDesc());
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
        headView.setTitleText("消息");
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        loadView = findViewById(R.id.loading_test);
        llLoading = (LinearLayout) findViewById(R.id.loading_test_ll);//正在加载
        tvLoadMore = (TextView) findViewById(R.id.load_more_tv);//加载更多
        llLoading.setVisibility(View.GONE);
        tvLoadMore.setVisibility(View.GONE);
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
                initData(loadPageNo);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        comment_lv = (ListView) findViewById(R.id.comment_lv);
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
        if (mSystemMessageResponse != null && mSystemMessageResponse.getTotalCount() > datas.size() && tvLoadMore.getText().equals("加载更多")) {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
            initData(loadPageNo);
        } else {
            tvLoadMore.setText("已加载完毕");
        }
    }

    private void initData(int loadPageNo) {
        NetLoadingDialog.getInstance().loading(mContext);
        if (loadPageNo != 1) {
            NetLoadingDialog.getInstance().loading(mContext);
        }
        UserServiceImpl.instance().getSystemMessage(loadPageNo, SystemMessageResponse.class.getName());

    }


    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
