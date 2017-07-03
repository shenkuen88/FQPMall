package com.fengqipu.mall.main.fragment.category;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.category.CategoryResponse;
import com.fengqipu.mall.bean.category.LeftBean;
import com.fengqipu.mall.bean.category.RightBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.acty.index.ColumnListActy;
import com.fengqipu.mall.main.acty.index.SearchActy;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 分類
 * jwei create by 2016/7/13
 */
public class CategoryFragment extends BaseFragment {
    private static CategoryFragment instance;
    View headView;
    ImageView ivBanner;
    private MainActivity mainActivity;

    public static CategoryFragment newInstance() {
        if (instance == null) {
            instance = new CategoryFragment();
        }
        return instance;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    private View fview;
    private ListView left_list, right_list;
    private CommonAdapter<LeftBean> leftAdapter;
    private CommonAdapter<RightBean> rightAdapter;
    private List<LeftBean> lList = new ArrayList<LeftBean>();
    private List<RightBean> rightList = new ArrayList<RightBean>();
    private String leftSelId = "";

    private LinearLayout search_layout;
    private int t_position = 0;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
//            initViewData();
            getLeftData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && Global.getNowIndex().equals(this.getClass().getName())) {
//            initViewData();
            NetLoadingDialog.getInstance().loading(mainActivity);
            getLeftData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity) getActivity();
        fview = inflater.inflate(R.layout.fragment_fen_lei, container, false);
        headView = inflater.inflate(R.layout.header, null);
        ivBanner= V.f(headView,R.id.iv_banner);
        initAll();
        ButterKnife.bind(this, fview);
        return fview;
    }

    private void initAll() {
        initView();
        initViewData();
        initEvent();
    }

    private void initView() {
        left_list = V.f(fview, R.id.left_list);
        right_list = V.f(fview, R.id.right_list);
        search_layout = V.f(fview, R.id.search_layout);
        View topView = V.f(fview, R.id.topView);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ViewGroup.LayoutParams params = topView.getLayoutParams();
//            params.height += BaseApplication.statusBarHeight;
//            topView.setLayoutParams(params);
//            topView.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//        }
    }

    private void initViewData() {
        leftAdapter = new CommonAdapter<LeftBean>(mainActivity, lList, R.layout.fen_lei_left) {
            @Override
            public void convert(ViewHolder helper, LeftBean item) {
                TextView title = helper.getView(R.id.title);
                if (!item.getTitle().equals("")) {
                    title.setVisibility(View.VISIBLE);
                    title.setText(item.getTitle());
                } else {
                    title.setVisibility(View.GONE);
                }
                TextView name = helper.getView(R.id.name);
                if (leftSelId.equals("")) {
                    if (helper.getPosition() == 0) {
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.white_color));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.bg_col));
                    }
                } else {
                    if (item.getId().equals(leftSelId)) {
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.white_color));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.bg_col));
                    }
                }
                name.setText(item.getName());
            }
        };
        left_list.setAdapter(leftAdapter);
//        getLeftData();
        rightAdapter = new CommonAdapter<RightBean>(mainActivity, rightList, R.layout.fen_lei_right) {
            @Override
            public void convert(ViewHolder helper, final RightBean item) {
                helper.setText(R.id.title, item.getName());
                MyGridView gridview = helper.getView(R.id.my_grid_view);
                CommonAdapter<RightBean.SubRightBean> gAdapter
                        = new CommonAdapter<RightBean.SubRightBean>(mainActivity, item.getList(), R.layout.item_pic_ll) {
                    @Override
                    public void convert(ViewHolder helper, RightBean.SubRightBean item) {
                        helper.setText(R.id.tv_name, item.getName());
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getPic())) {
                            ImageView img = helper.getView(R.id.iv_pic);
//                            ImageLoaderUtil.getInstance().initImage(mainActivity, item.getPic(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mainActivity, item.getPic(),
                                    img,
                                    R.drawable.default_bg);
                        }
                    }
                };
                gridview.setAdapter(gAdapter);
                TextView btn_more = helper.getView(R.id.btn_more);
                btn_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ColumnListActy.class);
                        intent.putExtra(IntentCode.SEARCH_KEYORD, item.getId());
                        intent.putExtra(IntentCode.SEARCH_TITLE, item.getName());
                        intent.putExtra(IntentCode.EXTRA_SERVICETYPE, item.getType());
                        getActivity().startActivity(intent);
                    }
                });
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RightBean.SubRightBean subRightBean = (RightBean.SubRightBean) parent.getItemAtPosition(position);

                    }
                });
            }
        };
        right_list.setAdapter(rightAdapter);
    }

    private String cid = "";

    private void getRightData() {
//        NetLoadingDialog.getInstance().loading(mainActivity);
//        UserServiceImpl.instance().getCategoryRight(mainActivity,"1", cid, CategoryRightResponse.class.getName());
        rightList.clear();
        if(categoryResponse.getSubCategoryListMap().get(cid)!=null) {
            for (int i = 0; i < categoryResponse.getSubCategoryListMap().get(cid).size(); i++) {
                List<RightBean.SubRightBean> relist = new ArrayList<RightBean.SubRightBean>();
                CategoryResponse.Category item = categoryResponse.getSubCategoryListMap().get(cid).get(i);
                if (item != null) {
                    List<CategoryResponse.ContentType> subitems = categoryResponse.getContentTypeListMap().get(item.getId());
                    if (subitems != null) {
                        for (CategoryResponse.ContentType sitem : subitems) {
                            relist.add(new RightBean.SubRightBean(sitem.getId(), sitem.getTypeName(), sitem.getCreateTime(), sitem.getPicUrlRequestUrl(), null));
                        }
                    }
                    rightList.add(new RightBean(item.getId(), item.getCategoryName(), "", relist));
                }

            }
        }
        rightAdapter.setData(rightList);
        rightAdapter.notifyDataSetChanged();
    }

    private void getLeftData() {
        UserServiceImpl.instance().getCategoryList(mainActivity, "1", CategoryResponse.class.getName());
    }

    private void initEvent() {
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActy.class));
            }
        });
        left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LeftBean left = (LeftBean) parent.getItemAtPosition(position);
                t_position = position;
                leftSelId = left.getId();
                leftAdapter.notifyDataSetChanged();
                cid = left.getId();
                String img = left.getPic();
                Log.e("sub","img="+img);
                try {
                    if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                        GeneralUtils.setImageViewWithUrl(mainActivity, img, ivBanner, R.drawable.default_head);
//                        ivBanner.setVisibility(View.VISIBLE);
//                        headView.setVisibility(View.VISIBLE);
                        right_list.removeHeaderView(headView);
                        right_list.addHeaderView(headView);
                        ivBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (left.getLink() != null && !left.getLink().equals("")) {
                                    Intent intentExplain = new Intent(getActivity(), CommonWebViewActivity.class);
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, left.getName());
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, left.getLink());
                                    getActivity().startActivity(intentExplain);
                                }
                            }
                        });
                    } else {
//                        ivBanner.setVisibility(View.GONE);
//                        headView.setVisibility(View.GONE);
                        right_list.removeView(headView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getRightData();
            }
        });
    }
    private CategoryResponse categoryResponse;
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(CategoryResponse.class.getName())) {
                if (cache.equals(NetResponseEvent.Cache.isCache)) {
                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
                    //网络数据(一般不用去做处理)
                }
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    Log.e("HTTP", result);
                    categoryResponse = GsonHelper.toType(result, CategoryResponse.class);
                    if (Constants.SUCESS_CODE.equals(categoryResponse.getResultCode())) {
                        List<LeftBean> tolLeftList = new ArrayList<LeftBean>();
                        for (CategoryResponse.Category item : categoryResponse.getParentCategoryList()) {
//
                            LeftBean leftBean = new LeftBean("", item.getId(), item.getCategoryName(),item.getCoverRequestUrl() , item.getLink());
                            tolLeftList.add(leftBean);
                        }
                        leftAdapter.setData(tolLeftList);
                        leftAdapter.notifyDataSetChanged();
                        if (GeneralUtils.isNotNullOrZeroSize(tolLeftList)) {
                            cid = tolLeftList.get(t_position).getId();
                            getRightData();
                            String img = tolLeftList.get(t_position).getPic();
                            try {
                                if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                                    GeneralUtils.setImageViewWithUrl(mainActivity, img, ivBanner, R.drawable.default_head);
//                                    ivBanner.setVisibility(View.VISIBLE);
//                                    headView.setVisibility(View.VISIBLE);
                                    right_list.removeHeaderView(headView);
                                    right_list.addHeaderView(headView);
                                } else {
                                    right_list.removeView(headView);
//                                    ivBanner.setVisibility(View.GONE);
//                                    headView.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ErrorCode.doCode(mainActivity, categoryResponse.getResultCode(), categoryResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mainActivity);
                }
            }
//            if (tag.equals(CategoryRightResponse.class.getName())) {
//                if (cache.equals(NetResponseEvent.Cache.isCache)) {
//                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
//                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
//                    //网络数据(一般不用去做处理)
//                    if (GeneralUtils.isNotNullOrZeroLenght(result)) {
//                        Log.e("HTTP", result);
//                        CategoryRightResponse crResponse = GsonHelper.toType(result, CategoryRightResponse.class);
//                        if (Constants.SUCESS_CODE.equals(crResponse.getResultCode())) {
//                            rightList.clear();
////                            for (int i = 0; i < crResponse.getContentList().size(); i++) {
////                                List<RightBean.SubRightBean> srblist = new ArrayList<RightBean.SubRightBean>();
////                                CategoryRightResponse.ge rightResult = new CategoryRightResponse.ShopListBean();
////                                rightList.add(new RightBean(rightResult.getId(), rightResult.getShopName(), "", srblist));
////                            }
//                            rightAdapter.setData(rightList);
//                            rightAdapter.notifyDataSetChanged();
//                        } else {
//                            ErrorCode.doCode(mainActivity, crResponse.getResultCode(), crResponse.getDesc());
//                        }
//                    }
//                } else {
//                    ToastUtil.showError(mainActivity);
//                }
//            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
