package com.fengqipu.mall.main.fragment.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.tools.NetLoadingDialog;

/**
 * 产品详情
 * community_zone_item 动态展示item
 * community_zone_item 动态展示item
 */
public class ProductBottomParamsFragment extends BaseFragment implements View.OnClickListener {


    private View mView;


    public static ProductBottomParamsFragment newInstance() {
        ProductBottomParamsFragment fragment = new ProductBottomParamsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.product_detai_top, null);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void initData(int mPageNo) {
        NetLoadingDialog.getInstance().loading(getActivity());
//        UserServiceImpl.instance().getCommunityList(getActivity(), communityId, mPageNo, pageSize, PublicCommunityResponse.class.getName());
    }

    private void initView() {


    }


}
