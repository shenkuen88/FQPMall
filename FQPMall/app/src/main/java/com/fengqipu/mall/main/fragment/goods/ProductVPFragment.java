package com.fengqipu.mall.main.fragment.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.R;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.view.VerticalViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huqing on 2017/9/26.
 */

public class ProductVPFragment extends BaseFragment
{

    @Bind(R.id.vertical_viewpager)
    VerticalViewPager verticalViewPager;
    public  int fPosition = 0;
    private GoodsDetailActivity goodsDetailActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        goodsDetailActivity = (GoodsDetailActivity) getActivity();
        View v = LayoutInflater.from(goodsDetailActivity).inflate(R.layout.fragment_good_vp, null);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView()
    {
        verticalViewPager.setAdapter(new ProjectAdapter(goodsDetailActivity.getSupportFragmentManager()));
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (position==0){
                    fPosition =0;
                    goodsDetailActivity.showTab(true);
                }else {
                    fPosition=1;
                    goodsDetailActivity.showTab(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    class ProjectAdapter extends FragmentPagerAdapter
    {

        public ProjectAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;
            if (position == 0)
            {
                fragment = new GoodsFragment();
            }
            else
            {
                fragment = new ProductWebFragment();

            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return "";
        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
