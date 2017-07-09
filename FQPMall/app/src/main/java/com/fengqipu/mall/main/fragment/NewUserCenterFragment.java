package com.fengqipu.mall.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.TuiJianResponse;
import com.fengqipu.mall.bean.mine.UserCountResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.acty.mine.AccountManageActy;
import com.fengqipu.mall.main.acty.mine.HistoryGoodsActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.acty.mine.NewMyFavourActivity;
import com.fengqipu.mall.main.acty.mine.OrderListActivity;
import com.fengqipu.mall.main.acty.mine.SettingActy;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.ScrollBottomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.fengqipu.mall.R.id.spgz_num;


/**
 * Created by Administrator on 2016/6/13.
 */
public class NewUserCenterFragment extends BaseFragment implements View.OnClickListener {
    private static MainActivity mainActivity;
    @Bind(R.id.scrollView)
    ScrollBottomScrollView scrollView;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.ll_top_bg)
    LinearLayout llTopBg;
    @Bind(R.id.my_grid_view)
    GridView myGridView;
    @Bind(R.id.head_small)
    ImageView headSmall;
    @Bind(R.id.usercenter_title)
    TextView usercenterTitle;
    @Bind(R.id.btn_setting)
    ImageView btnSetting;
    @Bind(R.id.tv_allorder)
    TextView tvAllorder;
    @Bind(R.id.iv_allorder)
    ImageView ivAllorder;
    @Bind(R.id.dfk)
    LinearLayout dfk;
    @Bind(R.id.dfh)
    LinearLayout dfh;
    @Bind(R.id.dsh)
    LinearLayout dsh;
    @Bind(R.id.dpj)
    LinearLayout dpj;
    @Bind(R.id.tk)
    LinearLayout tk;
    @Bind(R.id.headbig)
    ImageView headbig;
    @Bind(R.id.top_index_ll)
    LinearLayout topIndexLl;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.ll_spgz)
    LinearLayout llSpgz;
    @Bind(R.id.ll_qygz)
    LinearLayout llQygz;
    @Bind(R.id.ll_wdzj)
    LinearLayout llWdzj;
    @Bind(R.id.btm_ll)
    LinearLayout btmLl;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.huiyuan_iv)
    ImageView huiyuanIv;
    @Bind(R.id.huiyuan_tv)
    TextView huiyuanTv;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.nologin_ll)
    LinearLayout nologinLl;
    @Bind(spgz_num)
    TextView spgzNum;
    @Bind(R.id.qygz_num)
    TextView qygzNum;
    @Bind(R.id.wdzj_num)
    TextView wdzjNum;

    private CommonAdapter<TuiJianResponse.ContentListBean> mAdapter;
    private List<TuiJianResponse.ContentListBean> goodsList = new ArrayList<>();

    public NewUserCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            if (scrollView != null) {
                scrollView.scrollTo(0, 0);
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.fragment_usercenter, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }


    private void initView() {
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setResistance(1.7f);
        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshLayout.setDurationToClose(200);
        refreshLayout.setDurationToCloseHeader(1000);
        // default is false
        refreshLayout.setPullToRefresh(false);
        // default is true
        refreshLayout.setKeepHeaderWhenRefresh(true);

        refreshLayout.disableWhenHorizontalMove(true);

        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mAdapter = new CommonAdapter<TuiJianResponse.ContentListBean>(mainActivity, goodsList, R.layout.index_btm_grid) {
            @Override
            public void convert(ViewHolder helper, TuiJianResponse.ContentListBean item) {
                ImageView img=helper.getView(R.id.img);
                TextView title=helper.getView(R.id.title);
                TextView location=helper.getView(R.id.location);
                TextView xl=helper.getView(R.id.xl);
                TextView price=helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(mainActivity, item.getPicUrl1RequestUrl(), img, R.drawable.default_bg);
                }
                title.setText(""+item.getContentName());
                location.setText(""+item.getShopProvince()+" "+item.getShopCity());
                if(item.getMonthSales()!=null&&!item.getMonthSales().equals("")) {
                    xl.setText("月销量" + item.getMonthSales() + "笔");
                }else{
                    xl.setText("月销量0笔");
                }
                price.setText("￥"+item.getPrice());
//                hpd.setText("好评度"+item.getAppraiseCount());
            }
        };
        myGridView.setAdapter(mAdapter);
//        scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
//            @Override
//            public void scrollBottom() {
//                initBtmList();
//            }
//        });
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TuiJianResponse.ContentListBean item=(TuiJianResponse.ContentListBean)adapterView.getItemAtPosition(i);
                Intent intent=new Intent(mainActivity, GoodsDetailActivity.class);
                intent.putExtra("contentID",item.getId());
                startActivity(intent);
            }
        });

        scrollView.setOnScrollChangeListener(new ScrollBottomScrollView.OnScrollChangeListener() {
            @Override
            public void scrollChange(int y) {

                float f = y / 300f;
                if (f >= 1) {
                    headSmall.setVisibility(View.VISIBLE);
                    usercenterTitle.setVisibility(View.VISIBLE);
                    btnSetting.setImageResource(R.mipmap.tool2);
                } else {
                    headSmall.setVisibility(View.INVISIBLE);
                    usercenterTitle.setVisibility(View.INVISIBLE);
                    btnSetting.setImageResource(R.mipmap.tool);
                }
                llTopBg.setAlpha(f);
            }
        });
        tvAllorder.setOnClickListener(this);
        ivAllorder.setOnClickListener(this);
        dfk.setOnClickListener(this);
        dfh.setOnClickListener(this);
        dsh.setOnClickListener(this);
        dpj.setOnClickListener(this);
        tk.setOnClickListener(this);
        headbig.setOnClickListener(this);
        headSmall.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        llSpgz.setOnClickListener(this);
        llQygz.setOnClickListener(this);
        llWdzj.setOnClickListener(this);
        nologinLl.setOnClickListener(this);
        initData();
    }

    private void initBtmList() {
        UserServiceImpl.instance().getUserCount(mainActivity,UserCountResponse.class.getName());
        UserServiceImpl.instance().getTuiJianList(mainActivity,"2",TuiJianResponse.class.getName());
    }

    private void changLoginOrLoginOut() {
        if (GeneralUtils.isLogin()) {
            btmLl.setVisibility(View.VISIBLE);
            loginLl.setVisibility(View.VISIBLE);
            nologinLl.setVisibility(View.GONE);
            if(Global.getNickName().equals("")){
                userName.setText(Global.getUserName());
            }else{
                userName.setText(Global.getNickName());
            }
            GeneralUtils.setRoundImageViewWithUrl(getActivity(), Global.getUserHeadUrl(), headbig, R.drawable.default_head);
            GeneralUtils.setRoundImageViewWithUrl(getActivity(), Global.getUserHeadUrl(), headSmall, R.drawable.default_head);
        } else {
            btmLl.setVisibility(View.GONE);
            loginLl.setVisibility(View.GONE);
            nologinLl.setVisibility(View.VISIBLE);
            spgzNum.setText("0");
            qygzNum.setText("0");
            wdzjNum.setText("0");
            headSmall.setImageResource(R.drawable.default_head);
        }
    }

    private void initData() {
        changLoginOrLoginOut();
        initBtmList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    refreshLayout.refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }


    public float scaleWidth;
    public float scaleHeight;
    public int windowWidth = 0;
    public int windowHeight = 0;

    public void setWindow() {
        if (windowWidth > 0 && windowHeight > 0) {
            return;
        }
        try {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            windowWidth = dm.widthPixels;
            windowHeight = dm.heightPixels;
            scaleWidth = (float) windowWidth / 720f;
            scaleHeight = (float) windowHeight / 1280f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nologin_ll:
                startActivity(new Intent(getActivity(), LoginActy.class));
                break;
            case R.id.ll_wdzj:
                GeneralUtils.toActyOtherwiseLogin(getActivity(), HistoryGoodsActivity.class);
                break;
            case R.id.ll_spgz:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), NewMyFavourActivity.class);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.ll_qygz:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), NewMyFavourActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.head_small:
            case R.id.headbig:
                GeneralUtils.toActyOtherwiseLogin(getActivity(), AccountManageActy.class);
                break;
            case R.id.tv_allorder:
            case R.id.iv_allorder:
                GeneralUtils.toActyOtherwiseLogin(getActivity(), OrderListActivity.class);
                break;
            case R.id.btn_setting:
                GeneralUtils.toActyOtherwiseLogin(getActivity(), SettingActy.class);
                break;
            case R.id.dfk:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 1);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dfh:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 2);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dsh:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 3);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dpj:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 4);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.tk:
                if (GeneralUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 5);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (tag.equals(NotiTag.TAG_LOGIN_SUCCESS)) {
                Log.e("sub", "login");
                initData();
            } else if (tag.equals(NotiTag.TAG_LOGIN_OUT)) {
                Log.e("sub", "loginout");
                changLoginOrLoginOut();
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(TuiJianResponse.class.getName())) {
                TuiJianResponse tuiJianResponse = GsonHelper.toType(result, TuiJianResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(tuiJianResponse.getResultCode())) {
                        if(tuiJianResponse.getContentList()!=null&&tuiJianResponse.getContentList().size()>0) {
                            goodsList.clear();
                            goodsList.addAll(tuiJianResponse.getContentList());
                            mAdapter.setData(goodsList);
                            mAdapter.notifyDataSetChanged();
                            CommonMethod.setListViewHeightBasedOnChildren(myGridView);
                        }
                    } else {
                        ErrorCode.doCode(mainActivity, tuiJianResponse.getResultCode(), tuiJianResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mainActivity);
                }
            }
            if (tag.equals(UserCountResponse.class.getName())) {
                UserCountResponse userCountResponse = GsonHelper.toType(result, UserCountResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(userCountResponse.getResultCode())) {
                        spgzNum.setText(userCountResponse.getContentFavoriteCount()+"");
                        qygzNum.setText((userCountResponse.getShop1FavoriteCount()+userCountResponse.getShop2FavoriteCount())+"");
                        wdzjNum.setText(userCountResponse.getViewContentCount()+"");
                    } else {
                        ErrorCode.doCode(mainActivity, userCountResponse.getResultCode(), userCountResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mainActivity);
                }
            }
        }

    }
}
