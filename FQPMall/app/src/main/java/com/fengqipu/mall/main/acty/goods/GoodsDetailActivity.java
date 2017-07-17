package com.fengqipu.mall.main.acty.goods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.bean.cart.StoreBean;
import com.fengqipu.mall.bean.cart.StoreGoodsBean;
import com.fengqipu.mall.bean.goods.AddGWCResponse;
import com.fengqipu.mall.bean.goods.AddGoodsFavourResponse;
import com.fengqipu.mall.bean.goods.GoodsCommentResponse;
import com.fengqipu.mall.bean.goods.GoodsDetailResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.dialog.GuiGeBtmDialog;
import com.fengqipu.mall.dialog.SucDialog;
import com.fengqipu.mall.main.acty.index.ConfirmOrderActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.fragment.goods.CommentFragment;
import com.fengqipu.mall.main.fragment.goods.GoodsFragment;
import com.fengqipu.mall.main.fragment.goods.ProductWebFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.fengqipu.mall.R.id.collect_tv;


public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.mTabs)
    TabLayout mTabs;
    @Bind(R.id.mContainer)
    ViewPager mContainer;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.shop_tv)
    TextView shopTv;
    @Bind(collect_tv)
    TextView collectTv;
    @Bind(R.id.service_tv)
    TextView serviceTv;
    @Bind(R.id.btn_addgwc)
    Button btnAddgwc;
    @Bind(R.id.btn_buy)
    Button btnBuy;

    private String contentID = "";
    //sel_btm_layout  gwc_canshu_item gwc_cs_item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        contentID = getIntent().getStringExtra("contentID");
        initAll();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initViewData() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(3);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        initData();
        getProComment();
    }

    public int pageNum = 1;
    public int pageSize = 10;

    public void initData() {
        UserServiceImpl.instance().getGoodsDetial(this, contentID, GoodsDetailResponse.class.getName());
    }

    public void getProComment() {
        UserServiceImpl.instance().getProductCommentList(contentID, pageNum, pageSize, GoodsCommentResponse.class.getName());
    }

    @Override
    public void initEvent() {
        ivBack.setOnClickListener(this);
        ivInfo.setOnClickListener(this);
        shopTv.setOnClickListener(this);
        collectTv.setOnClickListener(this);
        serviceTv.setOnClickListener(this);
        btnAddgwc.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_info:break;
            case R.id.shop_tv:

                break;
            case R.id.search_tv:

                break;
            case R.id.service_tv:
                Intent intent=new IntentBuilder(GoodsDetailActivity.this)
                .setServiceIMNumber("kefuchannelimid_021199") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                        .setTitleName("客服聊天")
                    .build();
                startActivity(intent);
                break;
            case collect_tv:
                UserServiceImpl.instance().addFavour(this,"1",contentID, AddGoodsFavourResponse.class.getName());
                break;
            case R.id.btn_addgwc:
                if(GeneralUtils.isLogin()){
                    UserServiceImpl.instance().addToBuyCar(contentID,num,style,color,AddGWCResponse.class.getName());
                }else {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
                break;
            case R.id.btn_buy:
                if(GeneralUtils.isLogin()){
                    change2Buy();
                }else {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
                break;
        }
    }
    public String style="";
    public String color="";
    public int num=1;
    public double curprice=0;

    public void change2Buy(){
        ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
        StoreGoodsBean storeGoodsBean=new StoreGoodsBean();
        StoreBean storeBean=new StoreBean(goodsDetailResponse.getContent().getShopID(),goodsDetailResponse.getContent().getShopName(),false,false);
        storeGoodsBean.setStoreBean(storeBean);
        List<GoodsBean> goodsBeens=new ArrayList<GoodsBean>();
        GoodsBean goodsBean = new GoodsBean(goodsDetailResponse.getContent().getCreateTime(), Global.getUserId()+"", goodsDetailResponse.getContent().getPicUrl1RequestUrl(), curprice,
                style, num, goodsDetailResponse.getContent().getShopID(),
                goodsDetailResponse.getContent().getContentName(), goodsDetailResponse.getContent().getShopName(),
                goodsDetailResponse.getContent().getId(), goodsDetailResponse.getContent().getId(),
                color+"", GoodsBean.STATUS_VALID, false, false);
        goodsBeens.add(goodsBean);
        storeGoodsBean.setGoodsBeanList(goodsBeens);
        shopList.add(storeGoodsBean);
        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
        intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(shopList));
        intent.putExtra(IntentCode.ORDER_STATE, "0");//0 新生成订单，代付款订单 传订单号
        startActivity(intent);
    }

    public void chang2Comment() {
        mContainer.setCurrentItem(2);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GoodsFragment();
                case 1:
                    return new ProductWebFragment();
                case 2:
                    return new CommentFragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "商品";
                case 1:
                    return "详情";
                case 2:
                    return "评价";
            }
            return null;
        }
    }

    public GuiGeBtmDialog guiGeBtmDialog;

    public void showGuiGeDialog() {
        if (guiGeBtmDialog == null) {
            guiGeBtmDialog = new GuiGeBtmDialog(GoodsDetailActivity.this, goodsDetailResponse);
        }
        guiGeBtmDialog.setGwcClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GeneralUtils.isLogin()){
                    UserServiceImpl.instance().addToBuyCar(contentID,num,style,color,AddGWCResponse.class.getName());
                }else {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }

            }
        });
        guiGeBtmDialog.setBuyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GeneralUtils.isLogin()){
                    change2Buy();
                }else {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
            }
        });
        guiGeBtmDialog.show();
    }

    private void hideGuiGeDialog() {
        if (guiGeBtmDialog != null && guiGeBtmDialog.isShowing()) {
            guiGeBtmDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public GoodsDetailResponse goodsDetailResponse;
    public GoodsCommentResponse goodsCommentResponse;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(GoodsCommentResponse.class.getName())) {
                goodsCommentResponse = GsonHelper.toType(result, GoodsCommentResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(goodsCommentResponse.getResultCode())) {
                        EventBus.getDefault().post(new NoticeEvent("COMMENTREFRESH"));
                    } else {
                        ErrorCode.doCode(this, goodsCommentResponse.getResultCode(), goodsCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(GoodsDetailResponse.class.getName())) {
                goodsDetailResponse = GsonHelper.toType(result, GoodsDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    Log.e("sub",result);
                    if (Constants.SUCESS_CODE.equals(goodsDetailResponse.getResultCode())) {
                        EventBus.getDefault().post(new NoticeEvent("REFRESH"));
                        if(goodsDetailResponse.getIsFavorite()==1){
                            Drawable top = getResources().getDrawable(R.mipmap.star_chedked_new);
                            collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                        }else{
                            Drawable top = getResources().getDrawable(R.mipmap.star_check);
                            collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                        }
                    } else {
                        ErrorCode.doCode(this, goodsDetailResponse.getResultCode(), goodsDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(AddGoodsFavourResponse.class.getName())) {
                AddGoodsFavourResponse addGoodsFavourResponse = GsonHelper.toType(result, AddGoodsFavourResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(addGoodsFavourResponse.getResultCode())) {
                        SucDialog sucDialog=new SucDialog(this,"收藏成功");
                        sucDialog.show();
                        Drawable top = getResources().getDrawable(R.mipmap.star_chedked_new);
                        collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    } else {
                        ErrorCode.doCode(this, addGoodsFavourResponse.getResultCode(), addGoodsFavourResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(AddGWCResponse.class.getName())) {
                AddGWCResponse addGWCResponse = GsonHelper.toType(result, AddGWCResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(addGWCResponse.getResultCode())) {
                        SucDialog sucDialog=new SucDialog(this,"加入购物车成功");
                        sucDialog.show();
                    } else {
                        ErrorCode.doCode(this, addGWCResponse.getResultCode(), addGWCResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }
}
