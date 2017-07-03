package com.fengqipu.mall.main.acty.goods;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.goods.GoodsCommentResponse;
import com.fengqipu.mall.bean.goods.GoodsDetailResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.dialog.GuiGeBtmDialog;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.mTabs)
    TabLayout mTabs;
    @Bind(R.id.mContainer)
    ViewPager mContainer;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_info)
    ImageView ivInfo;

    private String contentID="";
    //sel_btm_layout  gwc_canshu_item gwc_cs_item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        contentID=getIntent().getStringExtra("contentID");
        initAll();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initViewData() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(1);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        initData();
        getProComment();
    }
    public int pageNum=1;
    public int pageSize=10;
    public void initData() {
        UserServiceImpl.instance().getGoodsDetial(this,contentID,GoodsDetailResponse.class.getName());
    }
    public void getProComment(){
        UserServiceImpl.instance().getProductCommentList(contentID,pageNum,pageSize,GoodsCommentResponse.class.getName());
    }
    @Override
    public void initEvent() {
        ivBack.setOnClickListener(this);
        ivInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void chang2Comment() {
        mContainer.setCurrentItem(2);
    }

    public class SectionsPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
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
    public void showGuiGeDialog(){
        if(guiGeBtmDialog==null) {
            guiGeBtmDialog = new GuiGeBtmDialog(GoodsDetailActivity.this, goodsDetailResponse);
        }
        guiGeBtmDialog.show();
    }
    private void hideGuiGeDialog(){
        if(guiGeBtmDialog!=null&&guiGeBtmDialog.isShowing()){
            guiGeBtmDialog.dismiss();
        }
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
                    if (Constants.SUCESS_CODE.equals(goodsDetailResponse.getResultCode())) {
                        EventBus.getDefault().post(new NoticeEvent("REFRESH"));
                    } else {
                        ErrorCode.doCode(this, goodsDetailResponse.getResultCode(), goodsDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }

        }
    }
}
