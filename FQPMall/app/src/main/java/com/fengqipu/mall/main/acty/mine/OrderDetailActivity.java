package com.fengqipu.mall.main.acty.mine;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
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
import com.fengqipu.mall.bean.mine.OrderDetailResponse;
import com.fengqipu.mall.bean.mine.RemindDeliverResponse;
import com.fengqipu.mall.bean.mine.UpdataOrderResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.enterprise.EnterpriseNewActivity;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.acty.index.ConfirmOrderActivity;
import com.fengqipu.mall.main.acty.logistics.LogisticsActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.ll_view_line)
    View llViewLine;
    @Bind(R.id.ll_view)
    LinearLayout llView;
    @Bind(R.id.iv_lxmj)
    ImageView ivLxmj;
    @Bind(R.id.tv_lxmj)
    TextView tvLxmj;
    @Bind(R.id.iv_bddh)
    ImageView ivBddh;
    @Bind(R.id.tv_bddh)
    TextView tvBddh;
    private String state = "1";
    private String orderId = "";
    LinearLayout btn_sqth;//申请退货
    LinearLayout btn_ckwl;//查看物流
    LinearLayout btn_msfk;//马上付款
    LinearLayout btn_txfh;//提醒发货
    LinearLayout btn_qrsh;//确认收货
    LinearLayout btn_pj;//评价
    LinearLayout btn_qxdd;//取消订单
    LinearLayout btn_sqsh;//申请售后
    private CommonAdapter<OrderDetailResponse.OrderBean> orderAdapter;
    private List<OrderDetailResponse.OrderBean> olist = new ArrayList<OrderDetailResponse.OrderBean>();
    private ListView my_list;
    private ImageView img;
    private TextView title, info;
    private LinearLayout wl_info;
    private TextView wl_info_txt, wl_info_time;
    private TextView sh_name, sh_phone, sh_address, order_id, order_jyh, order_createtime, order_fktime, order_fhtime,order_mjly;
    private TextView fz;
    public static boolean needclose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        state = getIntent().getStringExtra(IntentCode.ORDER_STATE);
        orderId = getIntent().getStringExtra(IntentCode.C_ORDER_ID);
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needclose) {
            finish();
            needclose = false;
        }
    }

    @Override
    public void initView() {
        initTitle();
        btn_sqth = V.f(this, R.id.btn_sqth);//申请退货
        btn_ckwl = V.f(this, R.id.btn_ckwl);//查看物流
        btn_msfk = V.f(this, R.id.btn_msfk);//马上付款
        btn_txfh = V.f(this, R.id.btn_txfh);//提醒发货
        btn_qrsh = V.f(this, R.id.btn_qrsh);//确认收货
        btn_pj = V.f(this, R.id.btn_pj);//评价
        btn_qxdd = V.f(this, R.id.btn_qxdd);//取消订单
        btn_sqsh = V.f(this, R.id.btn_sqsh);//申请售后
        order_mjly= V.f(this, R.id.order_mjly);//买家留言
        my_list = V.f(this, R.id.my_list);
        img = V.f(this, R.id.img);
        title = V.f(this, R.id.title);
        info = V.f(this, R.id.info);
        wl_info_txt = V.f(this, R.id.wl_info_txt);
        wl_info_time = V.f(this, R.id.wl_info_time);
        sh_name = V.f(this, R.id.sh_name);
        sh_phone = V.f(this, R.id.sh_phone);
        sh_address = V.f(this, R.id.sh_address);
        order_id = V.f(this, R.id.order_id);
        order_jyh = V.f(this, R.id.order_jyh);
        order_createtime = V.f(this, R.id.order_createtime);
        order_fktime = V.f(this, R.id.order_fktime);
        order_fhtime = V.f(this, R.id.order_fhtime);
        wl_info = V.f(this, R.id.wl_info);
        fz = V.f(this, R.id.fz);
    }

    @Override
    public void initViewData() {
        getOrderDetail();
        orderAdapter = new CommonAdapter<OrderDetailResponse.OrderBean>(mContext, olist, R.layout.item_my_order) {
            @Override
            public void convert(ViewHolder helper, final OrderDetailResponse.OrderBean item) {
                helper.getView(R.id.view_layout).setVisibility(View.GONE);
                helper.getView(R.id.state).setVisibility(View.GONE);
                helper.setText(R.id.store_nam, item.getShopName());
//                helper.setText(R.id.all_num, "共" + item.get() + "件商品");
                helper.getView(R.id.all_num).setVisibility(View.GONE);
                helper.setText(R.id.all_money, "￥" + item.getRealPrice());
//                helper.setText(R.id.dy_price, "(含运费￥" + item.getd() + ")");
                helper.getView(R.id.store_nam).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderDetailActivity.this, EnterpriseNewActivity.class);
                        intent.putExtra("sid", item.getShopID());
                        startActivity(intent);

                    }
                });
                CommonAdapter<OrderDetailResponse.OrderBean.OrderContentListBean> goodsCommonAdapter
                        = new CommonAdapter<OrderDetailResponse.OrderBean.OrderContentListBean>(mContext
                        , item.getOrderContentList(), R.layout.item_myorder_goods) {
                    @Override
                    public void convert(ViewHolder helper, final OrderDetailResponse.OrderBean.OrderContentListBean item) {
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrlRequestUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrlRequestUrl(), img, R.drawable.bg_image_classification);
                        }
                        helper.setText(R.id.goods_info, item.getContentName());
                        helper.setText(R.id.goods_price, "￥" + item.getRealPrice());
                        TextView or_price = helper.getView(R.id.or_price);
//                        if(item.getOriginalPrice()==null||item.getOriginalPrice().equals("")){
                        or_price.setVisibility(View.GONE);
//                        }else {
//                            or_price.setVisibility(View.VISIBLE);
//                            or_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                            or_price.setText("￥" + item.getOriginalPrice());
//                        }
                        if (item.getColor() != null && !item.getColor().equals("")) {
                            helper.setText(R.id.goods_type, "规格:" + item.getStyle() + "、" + item.getColor());
                        } else {
                            helper.setText(R.id.goods_type, "规格:" + item.getStyle());
                        }
                        if (GeneralUtils.isNullOrZeroLenght(item.getStyle())&&GeneralUtils.isNullOrZeroLenght(item.getColor())){
                            helper.getView(R.id.goods_type).setVisibility(View.GONE);
                        }else {
                            helper.getView(R.id.goods_type).setVisibility(View.VISIBLE);
                        }
                        helper.setText(R.id.goods_num_x, "X" + item.getCount());
                        helper.getView(R.id.good_ll).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到详情页
                                Intent intent = new Intent(OrderDetailActivity.this, GoodsDetailActivity.class);
                                intent.putExtra("contentID", item.getContentID());
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
                        Intent intent = new Intent(mContext, LogisticsActivity.class);
                        intent.putExtra("orderID", item.getId());
                        intent.putExtra("headPic", item.getOrderContentList().get(0).getPicUrlRequestUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        my_list.setAdapter(orderAdapter);
        initState();
    }

    private void initState() {
        switch (state) {

            case "1":
                btn_sqth.setVisibility(View.GONE);
                btn_ckwl.setVisibility(View.GONE);
                btn_msfk.setVisibility(View.VISIBLE);
                btn_txfh.setVisibility(View.GONE);
                btn_qrsh.setVisibility(View.GONE);
                btn_pj.setVisibility(View.GONE);
                btn_qxdd.setVisibility(View.VISIBLE);
                ((TextView) btn_qxdd.getChildAt(0)).setText("取消订单");
                btn_sqsh.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.pic8);
                title.setText("等待买家付款");
                break;
            case "2":
                btn_sqth.setVisibility(View.VISIBLE);
                btn_ckwl.setVisibility(View.GONE);
                btn_msfk.setVisibility(View.GONE);
                btn_txfh.setVisibility(View.VISIBLE);
                btn_qrsh.setVisibility(View.GONE);
                btn_pj.setVisibility(View.GONE);
                btn_qxdd.setVisibility(View.GONE);
                btn_sqsh.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.pic5);
                title.setText("等待卖家发货");
                break;
            case "3":
                btn_sqth.setVisibility(View.VISIBLE);
                btn_ckwl.setVisibility(View.VISIBLE);
                btn_msfk.setVisibility(View.GONE);
                btn_txfh.setVisibility(View.GONE);
                btn_qrsh.setVisibility(View.VISIBLE);
                btn_pj.setVisibility(View.GONE);
                btn_qxdd.setVisibility(View.GONE);
                btn_sqsh.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.pic9);
                title.setText("卖家已发货");
                break;
            case "4":
                btn_sqth.setVisibility(View.VISIBLE);
                btn_ckwl.setVisibility(View.VISIBLE);
                btn_msfk.setVisibility(View.GONE);
                btn_txfh.setVisibility(View.GONE);
                btn_qrsh.setVisibility(View.GONE);
                btn_pj.setVisibility(View.VISIBLE);
                btn_qxdd.setVisibility(View.VISIBLE);
                ((TextView) btn_qxdd.getChildAt(0)).setText("删除订单");
                btn_sqsh.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.pic4);
                title.setText("交易成功");
                break;
            case "6":
                btn_sqth.setVisibility(View.GONE);
                btn_ckwl.setVisibility(View.GONE);
                btn_msfk.setVisibility(View.GONE);
                btn_txfh.setVisibility(View.GONE);
                btn_qrsh.setVisibility(View.GONE);
                btn_pj.setVisibility(View.GONE);
                btn_qxdd.setVisibility(View.VISIBLE);
                btn_sqsh.setVisibility(View.GONE);
                title.setText("交易成功");
                break;
        }
        if (btn_sqth.getVisibility() == View.GONE && btn_sqsh.getVisibility() == View.GONE) {
            llView.setVisibility(View.GONE);
            llViewLine.setVisibility(View.GONE);
        } else {
            llView.setVisibility(View.VISIBLE);
            llViewLine.setVisibility(View.VISIBLE);
        }
    }

    private void getOrderDetail() {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getOrderDetail(orderId, OrderDetailResponse.class.getName());
    }


    @Override
    public void initEvent() {
        btn_txfh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //提醒发货
                UserServiceImpl.instance().REMINDDELIVER(item.getId(), RemindDeliverResponse.class.getName());
            }
        });
        btn_ckwl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra("orderID", orderId);
                intent.putExtra("headPic", item.getOrderContentList().get(0).getPicUrlRequestUrl());
                startActivity(intent);
            }
        });
        btn_msfk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item == null) return;
                ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
                StoreGoodsBean storeGoodsBean = new StoreGoodsBean();
                StoreBean storeBean = new StoreBean(item.getShopID(), item.getShopName(), false, false);
                storeGoodsBean.setStoreBean(storeBean);
                List<GoodsBean> goodsBeens = new ArrayList<GoodsBean>();
                for (OrderDetailResponse.OrderBean.OrderContentListBean it : item.getOrderContentList()) {
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
                Intent intent = new Intent(mContext, PublicCommentActy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                intent.putExtra(IntentCode.C_ORDER_ID, orderId);
                Global.saveOrderId(orderId);
                startActivity(intent);
            }
        });
        btn_qrsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().upDateOrder(orderId, "1", UpdataOrderResponse.class.getName());
            }
        });
        btn_sqth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item == null) return;
                Intent intent = new Intent(mContext, RefundActy.class);
                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                intent.putExtra(IntentCode.C_ORDER_ID, orderId);
                Global.saveOrderId(orderId);
                //最多退款金额
                Global.saveRefundMoney(item.getRealPrice() + "");
                startActivity(intent);
            }
        });
        btn_qxdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定要删除该订单吗？", "取消", "确定"
                        , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
            }
        });
        ivLxmj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GeneralUtils.isLogin()) {
                    Intent intent = new IntentBuilder(OrderDetailActivity.this)
                            .setServiceIMNumber("kefuchannelimid_563950") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                            .setTitleName("")
                            .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                    .companyName("")
                                    .email(Global.getEmail())
                                    .qq("")
                                    .name(Global.getUserName())
                                    .nickName(Global.getNickName())
                                    .phone(Global.getPhone()))
                            .setShowUserNick(true)
                            .build();
                    startActivity(intent);
                } else {
                    startActivity(new Intent(OrderDetailActivity.this, LoginActy.class));
                }
            }
        });
        tvLxmj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GeneralUtils.isLogin()) {
                    Intent intent = new IntentBuilder(OrderDetailActivity.this)
                            .setServiceIMNumber("kefuchannelimid_563950") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                            .setTitleName("")
                            .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                    .companyName("")
                                    .email(Global.getEmail())
                                    .qq("")
                                    .name(Global.getUserName())
                                    .nickName(Global.getNickName())
                                    .phone(Global.getPhone()))
                            .setShowUserNick(true)
                            .build();
                    startActivity(intent);
                } else {
                    startActivity(new Intent(OrderDetailActivity.this, LoginActy.class));
                }
            }
        });
        ivBddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone =orderDetailResponse.getShop().getPhone();
                if(phone!=null&&!phone.equals("")){
                    call(phone);
                }

            }
        });
        tvBddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone =orderDetailResponse.getShop().getPhone();
                if(phone!=null&&!phone.equals("")){
                    call(phone);
                }
            }
        });
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private String curphone = "";

    private void call(String phone) {
        curphone = phone;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + curphone));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //代表用户同意了打电话的请求
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + curphone));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }else{
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("订单详情");
        headView.setHiddenRight();
    }

    private OrderDetailResponse.OrderBean item;
    OrderDetailResponse orderDetailResponse;
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().DelOrder(orderId, DelOrderResponse.class.getName());
            }
        }

        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(OrderDetailResponse.class.getName())) {
                CMLog.e(Constants.HTTP_TAG, result);
                if (cache.equals(NetResponseEvent.Cache.isCache)) {
                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
                    //网络数据(一般不用去做处理)
                }
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    orderDetailResponse = GsonHelper.toType(result, OrderDetailResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderDetailResponse.getResultCode())) {
                        if (orderDetailResponse.getOrder() != null) {
                            olist.clear();
                            olist.add(orderDetailResponse.getOrder());
                            orderAdapter.setData(olist);
                            orderAdapter.notifyDataSetChanged();
                        }
                        item = orderDetailResponse.getOrder();
                        fz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager cmb = (ClipboardManager) mContext
                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                cmb.setText(orderDetailResponse.getOrder().getId());
                                ToastUtil.makeText(mContext, "复制成功!");
                            }
                        });
                        state = orderDetailResponse.getOrder().getStatus() + "";
                        initState();
                        switch (state) {
                            case "1":
                                info.setText("还有" + orderDetailResponse.getOrder().getEndTimeShow() + "自动关闭");
                                break;
                            case "2":
                                info.setText("还有" + orderDetailResponse.getOrder().getEndTimeShow() + "自动确认");
                                break;
                            case "3":
                                info.setText("还有" + orderDetailResponse.getOrder().getEndTimeShow() + "自动确认");
                                break;
                            case "4":
                                info.setText("还有" + orderDetailResponse.getOrder().getEndTimeShow() + "自动评价");
                                break;
                            case "5":
                                break;
                        }
                        if (orderDetailResponse.getDeliveryRecordList() != null && orderDetailResponse.getDeliveryRecordList().size() > 0) {
                            wl_info.setVisibility(View.VISIBLE);
                            wl_info_txt.setText(orderDetailResponse.getDeliveryRecordList().get(orderDetailResponse.getDeliveryRecordList().size() - 1).getContext());
                            wl_info_time.setText(orderDetailResponse.getDeliveryRecordList().get(orderDetailResponse.getDeliveryRecordList().size() - 1).getTime());
                        } else {
                            wl_info.setVisibility(View.GONE);
                        }
                        try {
                            sh_name.setText(orderDetailResponse.getDelivery().getDeliveryUser());
                            sh_phone.setText(orderDetailResponse.getDelivery().getDeliveryNum());
                            sh_address.setText(orderDetailResponse.getDelivery().getDeliveryAddress());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        order_id.setText("订单编号:" + orderDetailResponse.getOrder().getOrderCode());
                        order_jyh.setText("订单交易号:" + orderDetailResponse.getOrder().getOuterCode());
                        order_createtime.setText("创建时间:" + orderDetailResponse.getOrder().getCreateTimeShow());
                        order_fktime.setText("付款时间:" + orderDetailResponse.getOrder().getPayTimeShow());
                        order_fhtime.setText("发货时间:" + orderDetailResponse.getOrder().getDeliveryTimeShow());
                        order_mjly.setText(""+orderDetailResponse.getOrder().getRemark());
                    } else {
                        ErrorCode.doCode(mContext, orderDetailResponse.getResultCode(), orderDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(RemindDeliverResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    RemindDeliverResponse remindDeliverResponse = GsonHelper.toType(result, RemindDeliverResponse.class);
                    if (Constants.SUCESS_CODE.equals(remindDeliverResponse.getResultCode())) {
                        ToastUtils.showToast(mContext, "提醒发货成功!");
                    } else {
                        ErrorCode.doCode(mContext, remindDeliverResponse.getResultCode(), remindDeliverResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(UpdataOrderResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UpdataOrderResponse updataOrderResponse = GsonHelper.toType(result, UpdataOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(updataOrderResponse.getResultCode())) {
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, updataOrderResponse.getResultCode(), updataOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(DelOrderResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    DelOrderResponse delOrderResponse = GsonHelper.toType(result, DelOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(delOrderResponse.getResultCode())) {
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, delOrderResponse.getResultCode(), delOrderResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
}
