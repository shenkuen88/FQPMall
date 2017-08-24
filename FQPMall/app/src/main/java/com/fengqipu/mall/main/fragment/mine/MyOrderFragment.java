package com.fengqipu.mall.main.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.bean.cart.StoreBean;
import com.fengqipu.mall.bean.cart.StoreGoodsBean;
import com.fengqipu.mall.bean.mine.DelOrderResponse;
import com.fengqipu.mall.bean.mine.OrderResponse;
import com.fengqipu.mall.bean.mine.RemindDeliverResponse;
import com.fengqipu.mall.bean.mine.UpdataOrderResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.index.ConfirmOrderActivity;
import com.fengqipu.mall.main.acty.logistics.LogisticsActivity;
import com.fengqipu.mall.main.acty.mine.OrderDetailActivity;
import com.fengqipu.mall.main.acty.mine.OrderListActivity;
import com.fengqipu.mall.main.acty.mine.PublicCommentActy;
import com.fengqipu.mall.main.acty.mine.RefundActy;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

@SuppressLint("ValidFragment")
public class MyOrderFragment extends BaseFragment {
    LinearLayout emptyLl;
    ImageView emtryIv;
    TextView emtryTv;

    public MyOrderFragment() {
    }

    public MyOrderFragment(int orderstate) {
        this.orderstate = orderstate;
    }

    private OrderListActivity orderListActivity = null;
    private boolean isloading = false;//是否在加载中
    private int page = 1;
    private int num = 5;
    private int tolcount = 0;
    private int orderstate;//0全部1待付款2待发货3待收货4待评价 6.退款
//    private LinearLayout no_order;
    private ListView order_list;
    private CommonAdapter<OrderResponse.OrderListBean> orderAdapter;
    private List<OrderResponse.OrderListBean> olist = new ArrayList<OrderResponse.OrderListBean>();
    private PtrClassicFrameLayout pull_to_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        orderListActivity = (OrderListActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);
        initView(rootView);
        initViewData();
        initEvent();
        return rootView;
    }

    private void initView(View rootView) {
        order_list = V.f(rootView, R.id.order_list);
        emptyLl = V.f(rootView, R.id.emtry_ll);
        emtryIv = V.f(rootView, R.id.emtry_iv);
        emtryTv = V.f(rootView, R.id.emtry_tv);
        pull_to_refresh = V.f(rootView, R.id.pull_to_refresh);
    }


    private OrderResponse.OrderListBean delOrder;

    //初始化listview
    private void initViewData() {
        orderAdapter = new CommonAdapter<OrderResponse.OrderListBean>(orderListActivity, olist, R.layout.item_my_order) {
            @Override
            public void convert(ViewHolder helper, final OrderResponse.OrderListBean item) {
                LinearLayout btn_sqth = helper.getView(R.id.btn_sqth);//申请退货
                LinearLayout btn_ckwl = helper.getView(R.id.btn_ckwl);//查看物流
                LinearLayout btn_msfk = helper.getView(R.id.btn_msfk);//马上付款
                LinearLayout btn_txfh = helper.getView(R.id.btn_txfh);//提醒发货
                LinearLayout btn_qrsh = helper.getView(R.id.btn_qrsh);//确认收货
                LinearLayout btn_pj = helper.getView(R.id.btn_pj);//评价
                LinearLayout btn_zxs = helper.getView(R.id.btn_zxs);//找相似
                LinearLayout btn_qkqx = helper.getView(R.id.btn_qkqx);//钱款去向
                LinearLayout btn_qxdd = helper.getView(R.id.btn_qxdd);//取消订单

                helper.setText(R.id.store_nam, item.getShopName());
//                helper.setText(R.id.all_num, "共" + item.get() + "件商品");
                helper.getView(R.id.all_num).setVisibility(View.GONE);
                helper.setText(R.id.all_money, "￥" + item.getRealPrice());
//                helper.setText(R.id.dy_price, "(含运费￥" + item.getd() + ")");
                switch (item.getStatus() + "") {
                    case "1":
                        btn_qxdd.setVisibility(View.VISIBLE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.VISIBLE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "等待买家付款");
                        break;
                    case "2":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.VISIBLE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "买家已付款");
                        break;
                    case "3":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.VISIBLE);
                        btn_ckwl.setVisibility(View.VISIBLE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.VISIBLE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "待收货");
                        break;
                    case "4":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.VISIBLE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.VISIBLE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "交易成功");
                        break;
                    case "6":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        String str = "";
                        switch (item.getRefundStatus()) {
                            case "1":
                                str = "待处理";
                                break;
                            case "2":
                                str = "退款成功";
                                break;
                            case "3":
                                str = "退款拒绝";
                                break;
                        }
                        helper.setText(R.id.state, str);
                        break;
                }
                CommonAdapter<OrderResponse.OrderListBean.OrderContentListBean> goodsCommonAdapter
                        = new CommonAdapter<OrderResponse.OrderListBean.OrderContentListBean>(orderListActivity
                        , item.getOrderContentList(), R.layout.item_myorder_goods) {
                    @Override
                    public void convert(ViewHolder helper, OrderResponse.OrderListBean.OrderContentListBean mItem) {
                        if (GeneralUtils.isNotNullOrZeroLenght(mItem.getPicUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(orderListActivity, mItem.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(getActivity(), mItem.getPicUrlRequestUrl(), img, R.drawable.bg_image_classification);
                        }
                        helper.setText(R.id.goods_info, mItem.getContentName());
                        helper.setText(R.id.goods_price, "￥" + mItem.getRealPrice());
                        TextView or_price = helper.getView(R.id.or_price);
                        if (mItem.getOriginalPrice() == null || mItem.getOriginalPrice().equals("")) {
                            or_price.setVisibility(View.GONE);
                        } else {
                            or_price.setVisibility(View.VISIBLE);
                            or_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            or_price.setText("￥" + mItem.getOriginalPrice());
                        }
                        if (mItem.getColor() != null && !mItem.getColor().equals("")) {
                            helper.setText(R.id.goods_type, "分类:" + mItem.getStyle() + "、" + mItem.getColor());
                        } else {
                            helper.setText(R.id.goods_type, "分类:" + mItem.getStyle());
                        }
                        helper.setText(R.id.goods_num_x, "X" + mItem.getCount());
                        helper.getView(R.id.good_ll).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item.getStatus() == 5) return;
                                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                                intent.putExtra(IntentCode.ORDER_GOODS_LIST, (Serializable) item);
                                intent.putExtra(IntentCode.ORDER_STATE, item.getStatus() + "");
                                intent.putExtra(IntentCode.C_ORDER_ID, item.getId());
                                startActivity(intent);
                            }
                        });
                    }
                };
                ListView myitemlist = helper.getView(R.id.myitemlist);
                myitemlist.setAdapter(goodsCommonAdapter);
                GeneralUtils.setListViewHeightBasedOnChildrenExtend(myitemlist);
                btn_ckwl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LogisticsActivity.class);
                        intent.putExtra("orderID", item.getId());
                        intent.putExtra("headPic", item.getOrderContentList().get(0).getPicUrlRequestUrl());
                        startActivity(intent);
                    }
                });
                btn_msfk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
                        StoreGoodsBean storeGoodsBean = new StoreGoodsBean();
                        StoreBean storeBean = new StoreBean(item.getShopID(), item.getShopName(), false, false);
                        storeGoodsBean.setStoreBean(storeBean);
                        List<GoodsBean> goodsBeens = new ArrayList<GoodsBean>();
                        for (OrderResponse.OrderListBean.OrderContentListBean it : item.getOrderContentList()) {
                            GoodsBean goodsBean = new GoodsBean(it.getCreateTime(), Global.getUserId() + "", it.getPicUrlRequestUrl(), it.getRealPrice(),
                                    it.getStyle(), it.getCount(), item.getShopID(),
                                    it.getContentName(), item.getShopName(),
                                    it.getId(), it.getContentID(),
                                    it.getColor() + "", GoodsBean.STATUS_VALID, false, false);
                            goodsBeens.add(goodsBean);
                        }
                        storeGoodsBean.setGoodsBeanList(goodsBeens);
                        shopList.add(storeGoodsBean);
                        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                        intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(shopList));
                        intent.putExtra(IntentCode.ORDER_STATE, "1");//0 新生成订单，代付款订单 传订单号
                        startActivity(intent);

                    }
                });
                btn_pj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PublicCommentActy.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                        intent.putExtra(IntentCode.C_ORDER_ID, item.getId());
                        Global.saveOrderId(item.getId());
                        startActivity(intent);
                    }
                });
                btn_qrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().upDateOrder(item.getId(), "1", UpdataOrderResponse.class.getName());
                    }
                });
                btn_sqth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), RefundActy.class);
                        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                        intent.putExtra(IntentCode.C_ORDER_ID, item.getId());
                        Global.saveOrderId(item.getId());
                        //最多退款金额
                        Global.saveRefundMoney(item.getRealPrice() + "");
                        startActivity(intent);
                    }
                });
                btn_qxdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delOrder = item;
                        DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定要删除该订单吗？", "取消", "确定"
                                , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
                    }
                });
                btn_txfh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //提醒发货
                        UserServiceImpl.instance().REMINDDELIVER(item.getId(), RemindDeliverResponse.class.getName());
                    }
                });
                helper.getView(R.id.tol_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getStatus() == 5) return;
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra(IntentCode.ORDER_GOODS_LIST, (Serializable) item);
                        intent.putExtra(IntentCode.ORDER_STATE, item.getStatus() + "");
                        intent.putExtra(IntentCode.C_ORDER_ID, item.getId());
                        startActivity(intent);
                    }
                });
            }

        };
        order_list.setAdapter(orderAdapter);
        order_list.setEmptyView(emptyLl);
        switch (orderstate) {//0全部1待付款2待发货3待收货4待评价 6.退款
            case 0:
                emtryIv.setImageResource(R.drawable.bg_icon_order);
                emtryTv.setText("暂时还没有订单");
                break;
            case 1:
                emtryIv.setImageResource(R.drawable.bg_icon_pendingpayment);
                emtryTv.setText("没有要付款的商品");
                break;
            case 2:
                emtryIv.setImageResource(R.drawable.bg_icon_shipmentpending);
                emtryTv.setText("没有要发货的商品");
                break;
            case 3:
                emtryIv.setImageResource(R.drawable.bg_icon_shipmentpending);
                emtryTv.setText("没有待收货的商品");
                break;
            case 4:
                emtryIv.setImageResource(R.drawable.bg_icon_evaluate);
                emtryTv.setText("暂无待评价商品");
                break;
            case 6:
                emtryIv.setImageResource(R.drawable.bg_icon_refund);
                emtryTv.setText("暂时还没有退款/售后商品");
                break;
        }
    }

    private int lastVisibileItem;

    private void initEvent() {
        pull_to_refresh.setLastUpdateTimeRelateObject(this);
        pull_to_refresh.setResistance(1.7f);
        pull_to_refresh.setRatioOfHeaderHeightToRefresh(1.2f);
        pull_to_refresh.setDurationToClose(200);
        pull_to_refresh.setDurationToCloseHeader(1000);
        // default is false
        pull_to_refresh.setPullToRefresh(false);
        // default is true
        pull_to_refresh.setKeepHeaderWhenRefresh(true);

        pull_to_refresh.disableWhenHorizontalMove(true);

        pull_to_refresh.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isloading) {
                    isloading = true;
                    page = 1;
                    pull_to_refresh.autoRefresh();
                    getOrderList("");
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, order_list, header);
            }
        });
        order_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == order_list.getCount()) {
                    if (!isloading) {
                        if (tolcount > page * num) {
                            isloading = true;
                            page++;
                            getOrderList("");
                        } else {
                            ToastUtil.makeText(getActivity(), "当前是最后一页");
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
//        order_list.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // 当不滚动时
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    //判断是否滚动到底部
//                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                        if (!isloading) {
//                            isloading=true;
//                            if (tolcount > page * num) {
//                                page++;
//                                getOrderList("");
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            page = 1;
//            pull_to_refresh.autoRefresh();
            getOrderList("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            page = 1;
//            pull_to_refresh.autoRefresh();
            getOrderList("");
        }
    }

    //请求订单列表
    private void getOrderList(String keyword) {
        //正式访问
//        NetLoadingDialog.getInstance().loading(orderListActivity);
        UserServiceImpl.instance().getOrderList(orderstate, keyword, page, num, OrderResponse.class.getName());

//        if (page == 1) {
//            olist.clear();
//        }
//        for (int i = 1; i < 6; i++) {
//            List<OrderResult.Goods> goodslist = new ArrayList<OrderResult.Goods>();
//            for (int j = 0; j < 2; j++) {
//                goodslist.add(new OrderResult.Goods("" + j, "商品" + j, "100", "200", "分类:一段", "" + j
//                        , "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3704122693,1924714915&fm=21&gp=0.jpg"));
//            }
//            OrderResult o = null;
//            if (orderstate == 0) {
//                o = new OrderResult("" + i, "1", "" + i, "200", "商店名称" + i, "0.0", goodslist);
//            } else {
//                o = new OrderResult("" + i, "1", "" + orderstate, "200", "商店名称" + i, "0.0", goodslist);
//            }
//            tolcount = 10;
//            olist.add(o);
//            orderAdapter.setData(olist);
//            orderAdapter.notifyDataSetChanged();
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                isloading = false;
//                pull_to_refresh.onHeaderRefreshComplete();
//                pull_to_refresh.onFooterRefreshComplete();
//            }
//        }, 2000);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                NetLoadingDialog.getInstance().loading(getActivity());
                UserServiceImpl.instance().DelOrder(delOrder.getId(), DelOrderResponse.class.getName());
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(OrderResponse.class.getName())) {
                if (cache.equals(NetResponseEvent.Cache.isCache)) {
                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
                    //网络数据(一般不用去做处理)
                }
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    Log.e("sub", "result=" + result);
                    OrderResponse orderResponse = GsonHelper.toType(result, OrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderResponse.getResultCode())) {
                        if (page == 1) {
                            olist.clear();
                        }
                        tolcount = orderResponse.getTotalCount();
                        olist.addAll(orderResponse.getOrderList());
                        orderAdapter.setData(olist);
                        orderAdapter.notifyDataSetChanged();
                        isloading = false;
                    } else {
                        ErrorCode.doCode(orderListActivity, orderResponse.getResultCode(), orderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(orderListActivity);
                }
                pull_to_refresh.refreshComplete();
            }
            if (tag.equals(UpdataOrderResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UpdataOrderResponse updataOrderResponse = GsonHelper.toType(result, UpdataOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(updataOrderResponse.getResultCode())) {
                        page = 1;
                        getOrderList("");
                    } else {
                        ErrorCode.doCode(getActivity(), updataOrderResponse.getResultCode(), updataOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(DelOrderResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    DelOrderResponse delOrderResponse = GsonHelper.toType(result, DelOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(delOrderResponse.getResultCode())) {
                        olist.remove(delOrder);
                        orderAdapter.setData(olist);
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(getActivity(), delOrderResponse.getResultCode(), delOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(RemindDeliverResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    RemindDeliverResponse remindDeliverResponse = GsonHelper.toType(result, RemindDeliverResponse.class);
                    if (Constants.SUCESS_CODE.equals(remindDeliverResponse.getResultCode())) {
                        ToastUtils.showToast(getActivity(), "提醒发货成功!");
                    } else {
                        ErrorCode.doCode(getActivity(), remindDeliverResponse.getResultCode(), remindDeliverResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
