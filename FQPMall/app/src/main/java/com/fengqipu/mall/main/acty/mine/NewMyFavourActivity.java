package com.fengqipu.mall.main.acty.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.main.fragment.myfavour.FavourGoodsFragment;
import com.fengqipu.mall.main.fragment.myfavour.FavourShopsFragment;
import com.fengqipu.mall.view.NoScrollViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *收藏
 */
public class NewMyFavourActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.mTabs)
    TabLayout mTabs;
    @Bind(R.id.mContainer)
    NoScrollViewPager mContainer;

    private String type="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favour);
        type=getIntent().getStringExtra("type");
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
    }

    @Override
    public void initViewData() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mContainer.setAdapter(sectionsPagerAdapter);
        mContainer.setOffscreenPageLimit(1);
        mTabs.setupWithViewPager(mContainer);
        mTabs.setVisibility(View.VISIBLE);
        if(type.equals("0")){
            mContainer.setCurrentItem(0);
        }else{
            mContainer.setCurrentItem(1);
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
        } else if (event instanceof NetResponseEvent) {
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("我的收藏");
        headView.setHiddenRight();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FavourShopsFragment();
                case 1:
                    return new FavourGoodsFragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "商铺";
                case 1:
                    return "商品";
            }
            return null;
        }
    }
}
