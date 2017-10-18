package com.fengqipu.mall.main.acty.goods;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.fengqipu.mall.main.acty.enterprise.EnterpriseActivity;
import com.fengqipu.mall.main.acty.index.ConfirmOrderActivity;
import com.fengqipu.mall.main.acty.index.zfb.NoticeListActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.fragment.goods.CommentFragment;
import com.fengqipu.mall.main.fragment.goods.ProductVPFragment;
import com.fengqipu.mall.main.fragment.goods.ProductWebFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.fengqipu.mall.R.id.collect_tv;


public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.mTabs)
    TabLayout mTabs;

    @Bind(R.id.mContainer)
    ViewPager mContainer;

    @Bind(R.id.iv_back)
    ImageView ivBack;

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

    @Bind(R.id.btn_info)
    ImageView btnInfo;

    @Bind(R.id.empty_ll)
    LinearLayout llEmpty;


    private String contentID = "";
    //sel_btm_layout  gwc_canshu_item gwc_cs_item

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        contentID = getIntent().getStringExtra("contentID");
        initAll();
    }

    @Override
    public void initView()
    {
    }

    @Override
    public void initViewData()
    {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(3);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        mContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (position == 0 && productVPFragment.fPosition == 1)
                {
                    showTab(false);
                }
                else
                {
                    showTab(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        initData();
        getProComment();
    }

    public void showTab(boolean show)
    {
        if (show)
        {
            findViewById(R.id.mTabs).setVisibility(View.VISIBLE);
            findViewById(R.id.title_tv).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.mTabs).setVisibility(View.GONE);
            findViewById(R.id.title_tv).setVisibility(View.VISIBLE);
        }
    }

    public int pageNum = 1;

    public int pageSize = 10;

    public void initData()
    {
        UserServiceImpl.instance().getGoodsDetial(this, contentID, GoodsDetailResponse.class.getName());
    }

    public void getProComment()
    {
        UserServiceImpl.instance().getProductCommentList(contentID, pageNum, pageSize, GoodsCommentResponse.class.getName());
    }

    @Override
    public void initEvent()
    {
        ivBack.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        shopTv.setOnClickListener(this);
        collectTv.setOnClickListener(this);
        serviceTv.setOnClickListener(this);
        btnAddgwc.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_info:
//                startActivity(new Intent(GoodsDetailActivity.this, ConversationListActivity.class));
                startActivity(new Intent(GoodsDetailActivity.this, NoticeListActivity.class));

                break;
            case R.id.shop_tv:
                try
                {
                    Intent intent2 = new Intent(GoodsDetailActivity.this, EnterpriseActivity.class);
                    intent2.putExtra("sid", goodsDetailResponse.getContent().getShopID());
                    startActivity(intent2);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.search_tv:

                break;
            case R.id.service_tv:
//                if (GeneralUtils.isLogin() && null != goodsDetailResponse)
//                {
//                    try
//                    {
//                        List<String> strs = new ArrayList<>();
//                        strs.add(goodsDetailResponse.getContent().getContentName());
//                        strs.add(goodsDetailResponse.getContent().getPrice() + "");
//                        strs.add(goodsDetailResponse.getContent().getDescription() + "");
//                        strs.add(goodsDetailResponse.getContent().getPicUrl1RequestUrl() + "");
//                        strs.add(goodsDetailResponse.getContent().getDescriptionLink() + "");
//                        Intent intent = new IntentBuilder(GoodsDetailActivity.this)
//                                .setServiceIMNumber("kefuchannelimid_021199") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
//                                .setTitleName(GsonHelper.toJson(strs))
//                                .setVisitorInfo(ContentFactory.createVisitorInfo(null)
//                                        .companyName("")
//                                        .email(Global.getEmail())
//                                        .qq("")
//                                        .name(Global.getUserName())
//                                        .nickName(Global.getNickName())
//                                        .phone(Global.getPhone()))
//                                .setShowUserNick(true)
//                                .build();
//                        startActivity(intent);
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                {
//                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
//                }
                if (null != goodsDetailResponse)
                {
                    String phone = goodsDetailResponse.getShop().getPhone();
                    if (phone != null && !phone.equals("")&&GeneralUtils.isLogin())
                    {
                        call(phone);
                    }else {
                        startActivity(new Intent(mContext,LoginActy.class));
                    }
                }
                break;
            case collect_tv:
                if(GeneralUtils.isLogin()){
                    UserServiceImpl.instance().addFavour(this, "1", contentID, AddGoodsFavourResponse.class.getName());
                }else {
                    startActivity(new Intent(mContext,LoginActy.class));
                }
                break;
            case R.id.btn_addgwc:
                if (GeneralUtils.isLogin())
                {
                    if (GUIGEERROR)
                    {
                        ToastUtils.showToast(this, "规格选择不正确!");
                        showGuiGeDialog();
                        return;
                    }
                    UserServiceImpl.instance().addToBuyCar(contentID, num, style, color, AddGWCResponse.class.getName());
                }
                else
                {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
                break;
            case R.id.btn_buy:
                if (GeneralUtils.isLogin())
                {
                    if (GUIGEERROR)
                    {
                        ToastUtils.showToast(this, "规格选择不正确!");
                        showGuiGeDialog();
                        return;
                    }
                    change2Buy();
                }
                else
                {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
                break;
        }
    }

    public String style = "";

    public String color = "";

    public int num = 1;

    public double curprice = 0;

    public boolean GUIGEERROR = false;

    public void change2Buy()
    {
        ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
        StoreGoodsBean storeGoodsBean = new StoreGoodsBean();
        StoreBean storeBean = new StoreBean(goodsDetailResponse.getContent().getShopID(), goodsDetailResponse.getContent().getShopName(), false, false);
        storeGoodsBean.setStoreBean(storeBean);
        List<GoodsBean> goodsBeens = new ArrayList<GoodsBean>();
        GoodsBean goodsBean = new GoodsBean(goodsDetailResponse.getContent().getCreateTime(), Global.getUserId() + "", goodsDetailResponse.getContent().getPicUrl1RequestUrl(), curprice,
                style, num, goodsDetailResponse.getContent().getShopID(),
                goodsDetailResponse.getContent().getContentName(), goodsDetailResponse.getContent().getShopName(),
                goodsDetailResponse.getContent().getId(), goodsDetailResponse.getContent().getId(),
                color + "", GoodsBean.STATUS_VALID, false, false);
        goodsBeens.add(goodsBean);
        storeGoodsBean.setGoodsBeanList(goodsBeens);
        shopList.add(storeGoodsBean);
        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
        intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(shopList));
        intent.putExtra(IntentCode.ORDER_STATE, "0");//0 新生成订单，代付款订单 传订单号
        startActivity(intent);
    }

    public void chang2Comment()
    {
        mContainer.setCurrentItem(2);
    }

    ProductVPFragment productVPFragment = new ProductVPFragment();

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:

                    return productVPFragment;
                case 1:
                    return new ProductWebFragment();
                case 2:
                    return new CommentFragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
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

    public void changePager(int postion)
    {
        mContainer.setCurrentItem(postion);
    }

    public GuiGeBtmDialog guiGeBtmDialog;

    public void showGuiGeDialog()
    {
        if (guiGeBtmDialog == null)
        {
            guiGeBtmDialog = new GuiGeBtmDialog(GoodsDetailActivity.this, goodsDetailResponse);
        }
        guiGeBtmDialog.setGwcClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (GeneralUtils.isLogin())
                {
                    if (GUIGEERROR)
                    {
                        ToastUtils.showToast(GoodsDetailActivity.this, "规格选择不正确!");
                        return;
                    }
                    UserServiceImpl.instance().addToBuyCar(contentID, num, style, color, AddGWCResponse.class.getName());
                }
                else
                {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }

            }
        });
        guiGeBtmDialog.setBuyClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (GeneralUtils.isLogin())
                {
                    if (GUIGEERROR)
                    {
                        ToastUtils.showToast(GoodsDetailActivity.this, "规格选择不正确!");
                        return;
                    }
                    change2Buy();
                }
                else
                {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActy.class));
                }
            }
        });
        guiGeBtmDialog.show();
    }

    private void hideGuiGeDialog()
    {
        if (guiGeBtmDialog != null && guiGeBtmDialog.isShowing())
        {
            guiGeBtmDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public GoodsDetailResponse goodsDetailResponse;

    public GoodsCommentResponse goodsCommentResponse;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
            }
            if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag))
            {
                initData();
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(GoodsCommentResponse.class.getName()))
            {
                goodsCommentResponse = GsonHelper.toType(result, GoodsCommentResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(goodsCommentResponse.getResultCode()))
                    {
                        EventBus.getDefault().post(new NoticeEvent("COMMENTREFRESH"));
                    }
                    else
                    {
                        ErrorCode.doCode(this, goodsCommentResponse.getResultCode(), goodsCommentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(GoodsDetailResponse.class.getName()))
            {
                goodsDetailResponse = GsonHelper.toType(result, GoodsDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    Log.e("sub", result);
                    if (Constants.SUCESS_CODE.equals(goodsDetailResponse.getResultCode()))
                    {
                        EventBus.getDefault().post(new NoticeEvent("REFRESH"));
                        if (goodsDetailResponse.getIsFavorite() == 1)
                        {
                            Drawable top = getResources().getDrawable(R.mipmap.star_chedked_new);
                            collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                        }
                        else
                        {
                            Drawable top = getResources().getDrawable(R.mipmap.star_check);
                            collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                        }
                        llEmpty.setVisibility(View.GONE);
                    }
                    else
                    {
                        ErrorCode.doCode(this, goodsDetailResponse.getResultCode(), goodsDetailResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(AddGoodsFavourResponse.class.getName()))
            {
                AddGoodsFavourResponse addGoodsFavourResponse = GsonHelper.toType(result, AddGoodsFavourResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(addGoodsFavourResponse.getResultCode()))
                    {
                        SucDialog sucDialog = new SucDialog(this, "收藏成功");
                        sucDialog.show();
                        Drawable top = getResources().getDrawable(R.mipmap.star_chedked_new);
                        collectTv.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    }
                    else
                    {
                        ErrorCode.doCode(this, addGoodsFavourResponse.getResultCode(), addGoodsFavourResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(AddGWCResponse.class.getName()))
            {
                AddGWCResponse addGWCResponse = GsonHelper.toType(result, AddGWCResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(addGWCResponse.getResultCode()))
                    {
                        SucDialog sucDialog = new SucDialog(this, "加入购物车成功");
                        sucDialog.show();
                    }
                    else
                    {
                        ErrorCode.doCode(this, addGWCResponse.getResultCode(), addGWCResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
        }
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private String curphone = "";

    private void call(String phone)
    {
        curphone = phone;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == 111)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //代表用户同意了打电话的请求
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + curphone));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
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
            }
            else
            {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
