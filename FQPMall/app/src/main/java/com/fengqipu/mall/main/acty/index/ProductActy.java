package com.fengqipu.mall.main.acty.index;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.bean.cart.StoreBean;
import com.fengqipu.mall.bean.cart.StoreGoodsBean;
import com.fengqipu.mall.bean.index.AddToBuyCarResponse;
import com.fengqipu.mall.bean.index.CollectContentResponse;
import com.fengqipu.mall.bean.index.ContentDetailResponse;
import com.fengqipu.mall.bean.index.ContentListBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.fragment.index.ProductFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyScrollView;
import com.fengqipu.mall.view.MyWebView;
import com.fengqipu.mall.view.VerticalViewPager;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * Created by dell on 2016/5/23.
 */
public class ProductActy extends BaseActivity implements View.OnClickListener {

    private VerticalViewPager verticalViewPager;
    private LinearLayout rlBottom;
    /**
     * 加入购物车
     */
    private Button addBuy_bn;
    /**
     * 购买
     */
    private Button buy_bn;
    /**
     * 收藏
     */
    private TextView collect_tv;
    /**
     * 客服
     */
    private TextView service_tv;

    private String contentID = "";

    private ContentDetailResponse mContentDetailResponse;
    private ContentListBean productContent;
    private int buyNum = 1;
    private String buyStyle="";
    private boolean collectFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initWindow();

        setContentView(R.layout.activity_product);
//        StatusBarCompat1.translucentStatusBar(this);
        contentID = getIntent().getStringExtra(IntentCode.contentID);
        CMLog.e(Constants.LOCAL_TAG, contentID + "  --");
        if (contentID == null) {
            ToastUtil.makeText(mContext, "无contentID");
            finish();
        }
        initView();
        initEvent();
        addListener();
    }


    private void addListener() {
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                MyScrollView.page = position;
                MyWebView.pageView = position;
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.equalsTags(this, NotiTag.TAG_CLOSE_ACTIVITY, tag)) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {

            } else if (NotiTag.TAG_CHANGE_BUY_NUM.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                buyNum = ((NoticeEvent) event).getPosition();
            } else if (NotiTag.TAG_CHANGE_STYLE.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                buyStyle = ((NoticeEvent) event).getText();
            } else if (NotiTag.TAG_ADD_CAR.equals(tag)) {
                int num = ((NoticeEvent) event).getPosition();
                String style = ((NoticeEvent) event).getText();
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().addToBuyCar(contentID, num, style, "",
                        AddToBuyCarResponse.class.getName());
            } else if (NotiTag.equalsTags(mContext, NotiTag.TAG_BUY_NOW, tag)) {
                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(getGoods()));
                intent.putExtra(IntentCode.ORDER_STATE, "0");//0 新生成订单，代付款订单 传订单号
                startActivity(intent);
            }
        } else if (event instanceof NetResponseEvent) {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            //添加到购物车
            if (NotiTag.equalsTags(mContext, tag, AddToBuyCarResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    AddToBuyCarResponse mAddToBuyCarResponse = GsonHelper.toType(result, AddToBuyCarResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddToBuyCarResponse.getResultCode())) {
                        ToastUtil.makeText(mContext, "添加成功");
                    } else {
                        ErrorCode.doCode(mContext, mAddToBuyCarResponse.getResultCode(), mAddToBuyCarResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            //收藏
            if (NotiTag.equalsTags(mContext, tag, CollectContentResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CollectContentResponse mCollectContentResponse = GsonHelper.toType(result, CollectContentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCollectContentResponse.getResultCode())) {
                        Drawable drawable = getResources().getDrawable(R.mipmap.product_collected);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                        collect_tv.setCompoundDrawables(null, drawable, null, null);//画在上方
                        collectFlag = true;
                        ToastUtil.makeText(mContext, getString(R.string.collect_success));
                    } else {
                        ErrorCode.doCode(mContext, mCollectContentResponse.getResultCode(), mCollectContentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(ContentDetailResponse.class.getName())) {
                mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode())) {
                        productContent = mContentDetailResponse.getContent();
                        buyStyle = mContentDetailResponse.getContentStyleList().get(0).getStyle();
                    } else {
                        ErrorCode.doCode(mContext, mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    public void initView() {
        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);
        verticalViewPager.setAdapter(new ProjectAdapter(getSupportFragmentManager()));
        addBuy_bn = (Button) findViewById(R.id.addBuy_bn);
        collect_tv = (TextView) findViewById(R.id.collect_tv);
        service_tv = (TextView) findViewById(R.id.service_tv);
        buy_bn = (Button) findViewById(R.id.buy_bn);
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        addBuy_bn.setOnClickListener(this);
        collect_tv.setOnClickListener(this);
        service_tv.setOnClickListener(this);
        buy_bn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBuy_bn:
                // && GeneralUtils.isNotNullOrZeroLenght(buyStyle)
                if (GeneralUtils.isLogin()) {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().addToBuyCar(contentID, 1, buyStyle, "",
                            AddToBuyCarResponse.class.getName());
                } else {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
            case R.id.buy_bn:
                if (GeneralUtils.isLogin()) {
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_BUY_NOW));
                } else {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
            case R.id.collect_tv:
                if (collectFlag) {
                    break;
                } else if (GeneralUtils.isLogin()) {
                    UserServiceImpl.instance().collectContent(contentID,
                            CollectContentResponse.class.getName());
                } else {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
        }
    }

    class ProjectAdapter extends FragmentPagerAdapter {

        public ProjectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = ProductFragment.newInstance(contentID);
            } else {
//                fragment = ProductWebFragment.newInstance("1");

            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    @Override
    protected void onDestroy() {
        MyScrollView.page = 0;
        MyWebView.pageView = 0;
        super.onDestroy();
    }


    public ArrayList<StoreGoodsBean> getGoods() {
        ArrayList<StoreGoodsBean> slist = new ArrayList<StoreGoodsBean>();

        StoreGoodsBean storeGoodsBean = new StoreGoodsBean();
        storeGoodsBean.setStoreBean(null);
        //商店实体
        StoreBean storeBean = new StoreBean(mContentDetailResponse.getContent().getShopID(),
                mContentDetailResponse.getContent().getShopName(), false, false);
//        StoreBean storeBean = new StoreBean("", "中巢", false, false);
        storeGoodsBean.setStoreBean(storeBean);

        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setContentID(mContentDetailResponse.getContent().getContentID());
        goodsBean.setPicUrl(mContentDetailResponse.getContent().getPicUrl1());
        goodsBean.setObjectName(mContentDetailResponse.getContent().getContentName());
        goodsBean.setCount(buyNum);
        goodsBean.setStyle(buyStyle);
        if (GeneralUtils.isNotNullOrZeroLenght(mContentDetailResponse.getContent().getPrice())) {
            goodsBean.setPrice(Double.parseDouble(mContentDetailResponse.getContent().getPrice()));
        } else {
            goodsBean.setPrice(0);
        }

        storeGoodsBean.getGoodsBeanList().add(goodsBean);
        if (storeGoodsBean.getStoreBean() != null) {
            slist.add(storeGoodsBean);
        }
        return slist;
    }

}
