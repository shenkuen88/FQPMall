package com.fengqipu.mall.main.acty.index;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.bean.cart.StoreBean;
import com.fengqipu.mall.bean.cart.StoreGoodsBean;
import com.fengqipu.mall.bean.index.AddOrderResponse;
import com.fengqipu.mall.bean.index.OrderContent;
import com.fengqipu.mall.bean.mine.AddressBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.index.zfb.AuthResult;
import com.fengqipu.mall.main.acty.index.zfb.PayResult;
import com.fengqipu.mall.main.acty.index.zfb.util.OrderInfoUtil2_0;
import com.fengqipu.mall.main.acty.mine.RecieveAddressListActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyListView;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.fengqipu.mall.main.acty.index.zfb.PayDemoActivity.APPID;
import static com.fengqipu.mall.main.acty.index.zfb.PayDemoActivity.RSA2_PRIVATE;
import static com.fengqipu.mall.main.acty.index.zfb.PayDemoActivity.RSA_PRIVATE;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvNoReceiver;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout llReceiver;
    private CheckBox cbWX, cbZFB;
    /**
     * 发票
     */
    private RelativeLayout rlBill;
    private TextView tvBill;
    /**
     * 订单列表
     * 对应的item是 item_confirm_order
     * 同一个商店中的商品对应的布局 item_confirm_order_same_shop
     */
    private MyListView lvOrder;
    /**
     * 收货地址
     */
    private RelativeLayout rlReceiver;
    private ArrayList<GoodsBean> goodslist = new ArrayList<GoodsBean>();
    private ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
    private AddressBean addressBean;
    private CommonAdapter<StoreGoodsBean> adapter;
    private TextView tvShouldPay;
    /**
     * 提交订单
     */
    private Button bnSubmit;
    /**
     * 构造的订单
     */
    private ArrayList<OrderContent> orderList = new ArrayList<>();
    private int payType;//支付方式
    //  应付金额
    private double totalPay = 0;
    private OrderContent orderContent;
    private AddressBean bean;
    private String orderState = 0 + "";
    private AddOrderResponse mComplainResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        orderState = getIntent().getStringExtra(IntentCode.ORDER_STATE);
        initAll();
    }


    private void initAdapter() {
        Log.e("sub","initAdapter");
        adapter = new CommonAdapter<StoreGoodsBean>(mContext, shopList, R.layout.item_confirm_order) {
            @Override
            public void convert(ViewHolder helper, final StoreGoodsBean item) {
                StoreBean shopBean = item.getStoreBean();
                helper.setText(R.id.title_tv, shopBean.getName());//店名
                MyListView lvGood = helper.getView(R.id.good_lv);
                helper.setText(R.id.yf_tv, "0");//运费
                //订单金额
                TextView tvOrderPay = helper.getView(R.id.order_pay_tv);
                //应付金额
                TextView tvPay = helper.getView(R.id.total_tv);
                CommonAdapter<GoodsBean> gAdapter
                        = new CommonAdapter<GoodsBean>(mContext, item.getGoodsBeanList(), R.layout.item_confirm_order_same_shop) {
                    @Override
                    public void convert(ViewHolder helper, GoodsBean item) {
                        try {
                            orderContent = new OrderContent(item.getContentID()+"", item.getCount() + "", item.getStyle()+"");
                            if (orderList.size() > 0) {
                                for (int i = 0; i < orderList.size(); i++) {
                                    OrderContent bean = orderList.get(i);
                                    if (//orderList中无该orderContent
                                            !(orderContent.getContentID().equals(bean.getContentID())
                                                    && orderContent.getStyle().equals(bean.getStyle())
                                                    && orderContent.getCount().equals(orderContent.getCount()))) {
                                        orderList.add(orderContent);
                                    }
                                }
                            } else {
                                orderList.add(orderContent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //图标
                        try {
                            ImageView iv = helper.getView(R.id.good_iv);
                            GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl(), iv,  R.drawable.default_bg);
                        } catch (Exception e) {
                        }
                        //数量
                        try {
                            helper.setText(R.id.num_tv, "×" + item.getCount());
                        } catch (Exception e) {
                        }
                        //价格
                        try {
                            helper.setText(R.id.price_tv, "¥" + item.getPrice());
                        } catch (Exception e) {
                        }
                        //名称
                        try {
                            helper.setText(R.id.name, item.getObjectName());
                        } catch (Exception e) {
                        }
                        //样式
                        try {
                            if(item.getColor()!=null&&!item.getColor().equals("")){
                                helper.setText(R.id.classify_tv, "分类:"+item.getStyle()+"、"+item.getColor());
                            }else{
                                helper.setText(R.id.classify_tv, "分类:"+item.getStyle());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        totalPay = item.getCount() * item.getPrice();
                    }
                };
                lvGood.setAdapter(gAdapter);
                try {
                    tvOrderPay.setText("¥" + totalPay);
                    tvPay.setText("¥" + totalPay);
                    tvShouldPay.setText(totalPay + "元");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        lvOrder.setAdapter(adapter);
    }

    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.zfb_rl).setOnClickListener(this);
        findViewById(R.id.wx_rl).setOnClickListener(this);
        rlReceiver = (RelativeLayout) findViewById(R.id.receiver_rl);
        rlReceiver.setOnClickListener(this);
        tvNoReceiver = (TextView) findViewById(R.id.no_receiver_tv);
        tvShouldPay = (TextView) findViewById(R.id.should_pay_tv);
        bnSubmit = (Button) findViewById(R.id.submit_order_bn);
        bnSubmit.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.name_tv);
        tvPhone = (TextView) findViewById(R.id.phone_tv);
        tvBill = (TextView) findViewById(R.id.bill_info_tv);
        tvAddress = (TextView) findViewById(R.id.address_tv);
        llReceiver = (LinearLayout) findViewById(R.id.receiver_ll);
        lvOrder = (MyListView) findViewById(R.id.order_list);
        rlBill = (RelativeLayout) findViewById(R.id.bill_rl);
        cbZFB = (CheckBox) findViewById(R.id.zhb_cb);
        cbWX = (CheckBox) findViewById(R.id.wx_cb);
        tvNoReceiver.setVisibility(View.VISIBLE);
        llReceiver.setVisibility(View.GONE);
        receiveAddressShow();
        cbWX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWX.setChecked(true);
                    cbZFB.setChecked(false);
                } else {
                    cbWX.setChecked(false);
                    cbZFB.setChecked(true);
                }
            }
        });
        cbZFB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbWX.setChecked(true);
                    cbZFB.setChecked(false);
                } else {
                    cbWX.setChecked(false);
                    cbZFB.setChecked(true);
                }
            }
        });

    }


    private void receiveAddressShow() {
        String addStr = SharePref.getString(Constants.CHOOSE_ADDRESS, "");
        if (GeneralUtils.isNotNullOrZeroLenght(addStr)) {
            bean = GsonHelper.toType(addStr, AddressBean.class);
            tvNoReceiver.setVisibility(View.GONE);
            llReceiver.setVisibility(View.VISIBLE);
            tvName.setText("收货人：" + bean.getDeliveryUser());
            tvPhone.setText("手机号码：" + bean.getPhone());
            tvAddress.setText("收货地址：" + bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail());
        } else {
            tvNoReceiver.setVisibility(View.VISIBLE);
            llReceiver.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewData() {
        initAdapter();
        Type type = new TypeToken<ArrayList<StoreGoodsBean>>() {
        }.getType();
        shopList = GsonHelper.fromJson(getIntent().getStringExtra(IntentCode.ORDER_GOODS_LIST), type);
        adapter.setData(shopList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
        rlBill.setOnClickListener(this);
    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_SET_BILL.equals(tag)) {
                if (SharePref.getString(Constants.TAG_SET_BILL, "").equals("1")) {
                    tvBill.setText("需要发票");
                } else {
                    tvBill.setText("不需要发票");
                }
            } else if (NotiTag.TAG_CHOOSE_ADDRESS_OK.equals(tag)) {
                receiveAddressShow();
            } else if (NotiTag.TAG_PAY_SUCCESS.equals(tag)) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(AddOrderResponse.class.getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                     mComplainResponse = GsonHelper.toType(result, AddOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(mComplainResponse.getResultCode())) {
                        if (payType == 2) {
//                            Intent zfbIntent = new Intent(mContext, ZFBPayActivity.class);
//                            zfbIntent.putExtra(IntentCode.ZFB_RESULT, result);
//                            startActivity(zfbIntent);
                            payV2(mComplainResponse.getOrderCode(),mComplainResponse.getTotalPrice());
                        } else {
//                            Intent zfbIntent = new Intent(mContext, PayActivity.class);
//                            zfbIntent.putExtra(IntentCode.ZFB_RESULT, result);
//                            startActivity(zfbIntent);
                            IWXAPI api = WXAPIFactory.createWXAPI(this, com.fengqipu.mall.main.acty.index.wx.Constants.APP_ID);
                            AddOrderResponse mc = GsonHelper.toType(result, AddOrderResponse.class);
                            if(null != mc){

                                PayReq req = new PayReq();
                                req.appId			= mc.getAppID();//
                                req.partnerId		= mc.getPartnerID();
                                req.prepayId		= mc.getPrepay_id();
                                req.nonceStr		= mc.getNonce_str();
                                req.timeStamp		= mc.getTimestamp();
                                req.packageValue	= "Sign=WXpay";
//                                req.sign			= mc.getSign();
                                req.extData			= mc.getTimestamp(); // optional
                                List<NameValuePair> signParams = new LinkedList<NameValuePair>();
                                signParams.add(new BasicNameValuePair("appid", req.appId));
                                signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
                                signParams.add(new BasicNameValuePair("package", req.packageValue));
                                signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
                                signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
                                signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
                                req.sign = genAppSign(signParams);
                                Log.e("sub",mc.getSign()+"==="+req.sign);
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(com.fengqipu.mall.main.acty.index.wx.Constants.APP_ID);
                                boolean bl=api.sendReq(req);
                                Log.e("sub","bl"+bl);
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mComplainResponse.getResultCode(), mComplainResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
    // 随机串 防重发
    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("ningbozhongchaowenhuachuanmei123");

        String appSign = getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
//        Log.e("orion", appSign);
        return appSign;
    }
    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("确认订单");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    private String genTimeStamp() {
        return System.currentTimeMillis() / 1000+"";
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //确认订单
            case R.id.submit_order_bn:
                if (GeneralUtils.isNullOrZeroLenght(tvAddress.getText().toString())) {
                    ToastUtil.makeText(mContext, "请确认收货信息");
                } else {
                    if (cbZFB.isChecked()) {
                        payType = 2;
                    } else {
                        payType = 3;
                    }
                    NetLoadingDialog.getInstance().loading(mContext);

                    //新添加订单
                    if (orderState.equals("0")) {
                        UserServiceImpl.instance().addOrder(orderList, payType, bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail(),
                                bean.getDeliveryUser(), bean.getPhone(),
                                AddOrderResponse.class.getName());
                    } else {
                        UserServiceImpl.instance().addOrder(orderState, payType, bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail(),
                                bean.getDeliveryUser(), bean.getPhone(),
                                AddOrderResponse.class.getName());
                    }
                }
                break;
            case R.id.bill_rl:
                startActivity(new Intent(mContext, BillActivity.class));
                break;
            case R.id.wx_rl:
                cbWX.setChecked(true);
                cbZFB.setChecked(false);
                break;
            case R.id.zfb_rl:
                cbWX.setChecked(false);
                cbZFB.setChecked(true);
                break;
            case R.id.receiver_rl:
                Intent intent = new Intent(mContext, RecieveAddressListActy.class);
                if (addressBean != null && GeneralUtils.isNotNullOrZeroLenght(addressBean.getPhone())) {
                    intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, addressBean.getPhone());
                    intent.putExtra(IntentCode.EDIT_ADDRESS_BEAN_DETAIL, addressBean.getDetail());
                } else {
                    intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, "1");
                }
                startActivity(intent);
                break;
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(ConfirmOrderActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(ConfirmOrderActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * 支付宝支付业务
     *
     */
    public void payV2(String orderID,double price) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,orderID,price);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
