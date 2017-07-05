package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.fengqipu.mall.bean.mine.DelHistoryGoodsResponse;
import com.fengqipu.mall.bean.mine.HistoryGoodsResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.index.ProductActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.DisplayUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.MySwipeMenuListView;
import com.fengqipu.mall.view.swipemenulist.SwipeMenu;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuCreator;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuItem;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

/**
 * create jwei by 2016/7/10
 * 浏览历史
 */
public class HistoryGoodsActivity extends BaseActivity {
    private HeadView headView;
    private LinearLayout no_history;//空页面
    //    private ListView his_goods_list;
    private MySwipeMenuListView his_goods_list;
    private CommonAdapter<HistoryGoodsResponse.UserOperationListBean> hisgoodsAdapter;
    private List<HistoryGoodsResponse.UserOperationListBean> hglist = new ArrayList<HistoryGoodsResponse.UserOperationListBean>();
    private int page = 1;
    private String num = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_goods);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        his_goods_list = V.f(this, R.id.his_goods_list);
        initEmtyView();
    }

    @Override
    public void initViewData() {
//        hisgoodsAdapter=new CommonAdapter<HistoryGoodsResult>(mContext,hglist,R.layout.his_goods_item) {
//            @Override
//            public void convert(ViewHolder helper, HistoryGoodsResult item) {
//                helper.setText(R.id.title, item.getName());
//                CommonAdapter<HistoryGoodsResult.Goods> mAdapter =
//                        new CommonAdapter<HistoryGoodsResult.Goods>(mContext,item.getGoods(),R.layout.his_g_item) {
//                            @Override
//                            public void convert(ViewHolder helper, HistoryGoodsResult.Goods item) {
//                                helper.setText(R.id.goods_info,item.getName());
//                                helper.setText(R.id.goods_price,"￥"+item.getPrice());
//                                if(GeneralUtils.isNotNullOrZeroLenght(item.getPic())){
//                                    ImageView img=helper.getView(R.id.img);
//                                    ImageLoaderUtil.getInstance().initImage(mContext, item.getPic(), img, Constants.DEFAULT_IMAGE_F_LOAD);
//                                }
//                            }
//                        };
//                MySwipeMenuListView my_sub_list=helper.getView(R.id.my_sub_list);
//                my_sub_list.setAdapter(mAdapter);
//                GeneralUtils.setListViewHeightBasedOnChildrenExtend(my_sub_list);
//                initLeftSlideList(my_sub_list);
//            }
//        };
        hisgoodsAdapter =
                new CommonAdapter<HistoryGoodsResponse.UserOperationListBean>(mContext, hglist, R.layout.item_his_g) {
                    @Override
                    public void convert(ViewHolder helper, HistoryGoodsResponse.UserOperationListBean item) {
                        helper.setText(R.id.goods_info, item.getContentName());
                        helper.setText(R.id.goods_price, "￥" + item.getPrice());
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getThumPicUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mContext, item.getThumPicUrl(), img, R.drawable.default_head);
                        }
                    }
                };
        his_goods_list.setAdapter(hisgoodsAdapter);
        his_goods_list.setEmptyView(no_history);
        initLeftSlideList(his_goods_list);
        page = 1;
        getHistoryGoods();
    }

    //获取浏览历史
    private void getHistoryGoods() {
//        hglist.clear();
//        for(int i=0;i<2;i++){
//            List<HistoryGoodsResult.Goods> templist=new ArrayList<HistoryGoodsResult.Goods>();
//            for(int j=0;j<4;j++){
//                HistoryGoodsResult.Goods goods=new HistoryGoodsResult.Goods(""+j,"视频"+j,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3704122693,1924714915&fm=21&gp=0.jpg","100");
//                templist.add(goods);
//            }
//            hglist.add(new HistoryGoodsResult(templist,""+i,"商品"+i));
//            hisgoodsAdapter.setData(hglist);
//            hisgoodsAdapter.notifyDataSetChanged();
//        }
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getGoodsHistory("5", page + "", num, HistoryGoodsResponse.class.getName());
    }

    private boolean isloading = false;
    private int count = 0;

    @Override
    public void initEvent() {
        his_goods_list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (!isloading) {
                            isloading=true;
                            if (count > page * Integer.valueOf(num)) {
                                page++;
                                getHistoryGoods();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });
        his_goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryGoodsResponse.UserOperationListBean item=(HistoryGoodsResponse.UserOperationListBean)parent.getItemAtPosition(position);
                Intent intent = new Intent();
                if (item.getOperationType()==1) {
//                    intent.setClass(mContext, EduOlineVideoActivity.class);
                } else if (item.getOperationType()==2) {
                    intent.setClass(mContext, ProductActy.class);
                } else if (item.getOperationType()==3) {
//                    intent.setClass(mContext, DecorateActy.class);
                }
                intent.putExtra(IntentCode.contentID, item.getOperationID());
                startActivity(intent);
            }
        });
    }

    private HistoryGoodsResponse.UserOperationListBean delitem = null;

    //初始化向左滑动出现删除按钮
    private void initLeftSlideList(MySwipeMenuListView listview) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.app_color)));
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(mContext, 60));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu

                menu.addMenuItem(openItem);

            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        delitem = hglist.get(position);
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().DelHistoryGoods(delitem.getOperationID(),"5", DelHistoryGoodsResponse.class.getName());
                    }
                },300);
               return false;
            }
        });
    }

    //初始化标题
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("我的足迹");
        headView.setRightText("清空");
    }

    //初始化空View
    private void initEmtyView() {
        no_history = V.f(this, R.id.no_history);
        ImageView tips_pic = V.f(this, R.id.tips_pic);
        tips_pic.setImageResource(R.mipmap.collectx);
        TextView tips = V.f(this, R.id.tips);
        tips.setText("暂无足迹~");
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
                DialogUtil.showNoTipTwoBnttonDialog(mContext
                        , "你确定要清空足迹吗?"
                        , "取消"
                        , "确定"
                        , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
            }
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                delitem = null;
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().DelHistoryGoods("","5", DelHistoryGoodsResponse.class.getName());
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(HistoryGoodsResponse.class.getName())) {
                isloading=false;
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    HistoryGoodsResponse historyGoodsResponse = GsonHelper.toType(result, HistoryGoodsResponse.class);
                    if (Constants.SUCESS_CODE.equals(historyGoodsResponse.getResultCode())) {
                        count = historyGoodsResponse.getTotalCount();
                        if (page == 1) {
                            hglist.clear();
                        }
                        if (historyGoodsResponse.getUserOperationList() != null && historyGoodsResponse.getUserOperationList().size() > 0) {
                            hglist.addAll(historyGoodsResponse.getUserOperationList());
                        }
                        hisgoodsAdapter.setData(hglist);
                        hisgoodsAdapter.notifyDataSetChanged();

                    } else {
                        ErrorCode.doCode(mContext, historyGoodsResponse.getResultCode(), historyGoodsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(DelHistoryGoodsResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    DelHistoryGoodsResponse delHistoryGoodsResponse = GsonHelper.toType(result, DelHistoryGoodsResponse.class);
                    if (Constants.SUCESS_CODE.equals(delHistoryGoodsResponse.getResultCode())) {
                        if(delitem!=null){
                            hglist.remove(delitem);
                            hisgoodsAdapter.setData(hglist);
                            hisgoodsAdapter.notifyDataSetChanged();
                        }else{
                            page=1;
                            getHistoryGoods();
                        }
                    } else {
                        ErrorCode.doCode(mContext, delHistoryGoodsResponse.getResultCode(), delHistoryGoodsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
}