package com.fengqipu.mall.main.acty.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
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
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderSearchActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.finish_iv)
    ImageView finishIv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @Bind(R.id.btn_ss)
    TextView btnSs;
    @Bind(R.id.my_list_view)
    RefreshListView myListView;

    private int page = 1;
    private int num = 5;
    private int tolcount = 0;
    private CommonAdapter<OrderResponse.OrderListBean> orderAdapter;
    private List<OrderResponse.OrderListBean> olist = new ArrayList<OrderResponse.OrderListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    public void initView() {
        orderAdapter = new CommonAdapter<OrderResponse.OrderListBean>(this, olist, R.layout.item_my_order) {
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
                LinearLayout btn_qxdd= helper.getView(R.id.btn_qxdd);//取消订单

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
                        String str="";
                        switch (item.getRefundStatus()){
                            case "1":
                                str="待处理";
                                break;
                            case "2":
                                str="退款成功";
                                break;
                            case "3":
                                str="退款拒绝";
                                break;
                        }
                        helper.setText(R.id.state, str);
                        break;
                }
                CommonAdapter<OrderResponse.OrderListBean.OrderContentListBean> goodsCommonAdapter
                        = new CommonAdapter<OrderResponse.OrderListBean.OrderContentListBean>(OrderSearchActivity.this
                        , item.getOrderContentList(), R.layout.item_myorder_goods) {
                    @Override
                    public void convert(ViewHolder helper, OrderResponse.OrderListBean.OrderContentListBean mItem) {
                        if (GeneralUtils.isNotNullOrZeroLenght(mItem.getPicUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(OrderSearchActivity.this, mItem.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(OrderSearchActivity.this, mItem.getPicUrlRequestUrl(), img, R.drawable.bg_image_classification);
                        }
                        helper.setText(R.id.goods_info, mItem.getContentName());
                        helper.setText(R.id.goods_price, "￥" + mItem.getRealPrice());
                        TextView or_price = helper.getView(R.id.or_price);
                        if(mItem.getOriginalPrice()==null||mItem.getOriginalPrice().equals("")){
                            or_price.setVisibility(View.GONE);
                        }else {
                            or_price.setVisibility(View.VISIBLE);
                            or_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            or_price.setText("￥" + mItem.getOriginalPrice());
                        }
                        if(mItem.getColor()!=null&&!mItem.getColor().equals("")){
                            helper.setText(R.id.goods_type, "分类:"+mItem.getStyle()+"、"+mItem.getColor());
                        }else {
                            helper.setText(R.id.goods_type, "分类:" + mItem.getStyle());
                        }
                        helper.setText(R.id.goods_num_x, "X" + mItem.getCount());
                        helper.getView(R.id.good_ll).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(item.getStatus()==5)return;
                                Intent intent=new Intent(OrderSearchActivity.this, OrderDetailActivity.class);
                                intent.putExtra(IntentCode.ORDER_GOODS_LIST,(Serializable) item);
                                intent.putExtra(IntentCode.ORDER_STATE,item.getStatus()+"");
                                intent.putExtra(IntentCode.C_ORDER_ID,item.getId());
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
                        Intent intent = new Intent(OrderSearchActivity.this, LogisticsActivity.class);
                        intent.putExtra("orderID", item.getId());
                        intent.putExtra("headPic", item.getOrderContentList().get(0).getPicUrlRequestUrl());
                        startActivity(intent);
                    }
                });
                btn_msfk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
                        StoreGoodsBean storeGoodsBean=new StoreGoodsBean();
                        StoreBean storeBean=new StoreBean(item.getShopID(),item.getShopName(),false,false);
                        storeGoodsBean.setStoreBean(storeBean);
                        List<GoodsBean> goodsBeens=new ArrayList<GoodsBean>();
                        for(OrderResponse.OrderListBean.OrderContentListBean it:item.getOrderContentList()){
                            GoodsBean goodsBean = new GoodsBean(it.getCreateTime(), Global.getUserId()+"", it.getPicUrlRequestUrl(), it.getRealPrice(),
                                    it.getStyle(), it.getCount(), item.getShopID(),
                                    it.getContentName(), item.getShopName(),
                                    it.getId(), it.getContentID(),
                                    it.getColor()+"", GoodsBean.STATUS_VALID, false, false);
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
                        Intent intent = new Intent(OrderSearchActivity.this, PublicCommentActy.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                        intent.putExtra(IntentCode.C_ORDER_ID,item.getId());
                        Global.saveOrderId(item.getId());
                        startActivity(intent);
                    }
                });
                btn_qrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().upDateOrder(item.getId(),"1", UpdataOrderResponse.class.getName());
                    }
                });
                btn_sqth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(OrderSearchActivity.this, RefundActy.class);
                        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                        intent.putExtra(IntentCode.C_ORDER_ID,item.getId());
                        Global.saveOrderId(item.getId());
                        //最多退款金额
                        Global.saveRefundMoney(item.getRealPrice()+"");
                        startActivity(intent);
                    }
                });
                btn_qxdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delOrder=item;
                        DialogUtil.showNoTipTwoBnttonDialog(mContext,"确定要删除该订单吗？","取消","确定"
                                , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
                    }
                });
                btn_txfh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //提醒发货
                        UserServiceImpl.instance().REMINDDELIVER(item.getId(),RemindDeliverResponse.class.getName());
                    }
                });
                helper.getView(R.id.tol_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.getStatus()==5)return;
                        Intent intent=new Intent(OrderSearchActivity.this, OrderDetailActivity.class);
                        intent.putExtra(IntentCode.ORDER_GOODS_LIST,(Serializable) item);
                        intent.putExtra(IntentCode.ORDER_STATE,item.getStatus()+"");
                        intent.putExtra(IntentCode.C_ORDER_ID,item.getId());
                        startActivity(intent);
                    }
                });
            }

        };
        myListView.setAdapter(orderAdapter);
    }

    @Override
    public void initViewData() {

    }
    private boolean isloading = false;//是否在加载中
    private int lastVisibileItem=0;
    @Override
    public void initEvent() {
        finishIv.setOnClickListener(this);
        ivSearchClear.setOnClickListener(this);
        btnSs.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyWord();
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    ivSearchClear.setVisibility(View.VISIBLE);
                } else {
                    ivSearchClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == myListView.getCount()) {
                    if (!isloading) {
                        if (tolcount > page * num) {
                            isloading = true;
                            page++;
                            getOrderList("");
                        }else {
                            ToastUtil.makeText(OrderSearchActivity.this,"当前是最后一页");
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.finish_iv:
                finish();
                break;
            case R.id.btn_ss:
                searchKeyWord();
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;
        }
    }
    private void searchKeyWord() {
        if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString())) {
            NetLoadingDialog.getInstance().loading(OrderSearchActivity.this);
            initData();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch,InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0); //强制隐藏键盘
        } else {
            ToastUtil.makeText(mContext, "请输入搜索内容");
        }
    }

    private void initData() {
        page=1;
        getOrderList(etSearch.getText().toString());
    }
    //请求订单列表
    private void getOrderList(String keyword) {
        //正式访问
//        NetLoadingDialog.getInstance().loading(orderListActivity);
        UserServiceImpl.instance().getOrderList(0, keyword, page, num, OrderResponse.class.getName());
    }
    private OrderResponse.OrderListBean delOrder;
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                NetLoadingDialog.getInstance().loading(OrderSearchActivity.this);
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
                    Log.e("sub","result="+result);
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
                        ErrorCode.doCode(OrderSearchActivity.this, orderResponse.getResultCode(), orderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(OrderSearchActivity.this);
                }
            }
            if (tag.equals(UpdataOrderResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UpdataOrderResponse updataOrderResponse = GsonHelper.toType(result, UpdataOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(updataOrderResponse.getResultCode())) {
                        page=1;
                        getOrderList("");
                    } else {
                        ErrorCode.doCode(OrderSearchActivity.this, updataOrderResponse.getResultCode(), updataOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(OrderSearchActivity.this);
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
                        ErrorCode.doCode(OrderSearchActivity.this, delOrderResponse.getResultCode(), delOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(OrderSearchActivity.this);
                }
            }
            if (tag.equals(RemindDeliverResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    RemindDeliverResponse remindDeliverResponse = GsonHelper.toType(result, RemindDeliverResponse.class);
                    if (Constants.SUCESS_CODE.equals(remindDeliverResponse.getResultCode())) {
                        ToastUtils.showToast(OrderSearchActivity.this,"提醒发货成功!");
                    } else {
                        ErrorCode.doCode(OrderSearchActivity.this, remindDeliverResponse.getResultCode(), remindDeliverResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(OrderSearchActivity.this);
                }
            }
        }
    }
}
