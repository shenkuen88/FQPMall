package com.fengqipu.mall.main.fragment.goods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.bean.goods.GoodsCommentResponse;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.RefreshListView;

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
public class CommentFragment extends BaseFragment implements View.OnClickListener {
    private static GoodsDetailActivity goodsDetailActivity;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.tv_commentcount)
    TextView tvCommentcount;


    private CommonAdapter<GoodsCommentResponse.AppraiseListBean> mAdapter;
    private List<GoodsCommentResponse.AppraiseListBean> comentList = new ArrayList<>();

    public CommentFragment() {
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
        View v = LayoutInflater.from(goodsDetailActivity).inflate(R.layout.fragment_comment, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    int totalCount = 0;
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, myListview, header);
            }
        });
        mAdapter = new CommonAdapter<GoodsCommentResponse.AppraiseListBean>(goodsDetailActivity, comentList, R.layout.item_product_comment) {
            @Override
            public void convert(ViewHolder helper, GoodsCommentResponse.AppraiseListBean item) {
                ImageView comment_head_iv=helper.getView(R.id.comment_head_iv);
                if (item.getUserPortrait() != null && !item.getUserPortrait().equals("")) {
                    GeneralUtils.setImageViewWithUrl(goodsDetailActivity, item.getUserPortrait(), comment_head_iv, R.drawable.default_head);
                }
                TextView comment_name_tv=helper.getView(R.id.comment_name_tv);
                comment_name_tv.setText(item.getUserNickName()+"");
                TextView comment_content_tv=helper.getView(R.id.comment_content_tv);
                comment_content_tv.setText(item.getText()+"");
                TextView comment_time_tv=helper.getView(R.id.comment_time_tv);
                comment_time_tv.setText(item.getCreateTimeShow());
                MyGridView my_grid_view=helper.getView(R.id.my_grid_view);
                CommonAdapter<String> gadapter=new CommonAdapter<String>(goodsDetailActivity,item.getPicUrlList(),R.layout.item_pic) {
                    @Override
                    public void convert(ViewHolder helper, String item) {
                        ImageView iv_pic=helper.getView(R.id.iv_pic);
                        if (item != null && !item.equals("")) {
                            GeneralUtils.setImageViewWithUrl(goodsDetailActivity, item, iv_pic, R.drawable.default_bg);
                        }
                    }
                };
                my_grid_view.setAdapter(gadapter);
            }
        };
        myListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == myListview.getCount())
                    goodsDetailActivity.pageNum = goodsDetailActivity.pageNum + 1;
                if (goodsDetailActivity.pageNum * goodsDetailActivity.pageSize >= totalCount)
                    return;
                goodsDetailActivity.getProComment();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        myListview.setAdapter(mAdapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsBean item = (GoodsBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(goodsDetailActivity, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
//        initData();
    }


    private void initData() {
        //请求底部列表接口
        goodsDetailActivity.pageNum = 1;
        goodsDetailActivity.getProComment();
//        initBtmList();
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
//        myListview.loadComplete();
//        CommentListBean g1 = new CommentListBean();
//        comentList.add(g1);
//        CommentListBean g2 = new CommentListBean();
//        comentList.add(g2);
//        CommentListBean g3 = new CommentListBean();
//        comentList.add(g3);
//        CommentListBean g4 = new CommentListBean();
//        comentList.add(g4);
//        CommentListBean g5 = new CommentListBean();
//        comentList.add(g5);
//        CommentListBean g6 = new CommentListBean();
//        comentList.add(g6);
//        CommentListBean g7 = new CommentListBean();
//        comentList.add(g7);
//        CommentListBean g8 = new CommentListBean();
//        comentList.add(g8);
//        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (tag.equals("COMMENTREFRESH")) {
                tvCommentcount.setText("其他小伙伴怎么说(" + goodsDetailActivity.goodsCommentResponse.getTotalCount() + ")");
                if(goodsDetailActivity.goodsCommentResponse.getAppraiseList()!=null
                        &&goodsDetailActivity.goodsCommentResponse.getAppraiseList().size()>0){
                    comentList.clear();
                    myListview.loadComplete();
                    comentList.addAll(goodsDetailActivity.goodsCommentResponse.getAppraiseList());
                    mAdapter.notifyDataSetChanged();
                    CommonMethod.setListViewHeightBasedOnChildren(myListview);
                }
            }
        }
    }
}
