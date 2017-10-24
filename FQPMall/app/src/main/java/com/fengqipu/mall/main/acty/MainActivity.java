package com.fengqipu.mall.main.acty;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.NoticeInfoEvent;
import com.fengqipu.mall.bean.index.AppInitInfoListBean;
import com.fengqipu.mall.bean.index.GetUploadUrlResponse;
import com.fengqipu.mall.bean.index.InitAppResponse;
import com.fengqipu.mall.bean.mine.AddressBean;
import com.fengqipu.mall.bean.mine.AddressListResponse;
import com.fengqipu.mall.bean.mine.LoginResponse;
import com.fengqipu.mall.bean.update.CodeBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.fragment.NewIndexFragment;
import com.fengqipu.mall.main.fragment.NewUserCenterFragment;
import com.fengqipu.mall.main.fragment.cart.CartFragment;
import com.fengqipu.mall.main.fragment.category.CategoryFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UpdateUtils;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.StringEncrypt;
import com.fengqipu.mall.tools.ToastUtil;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 首页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private FragmentManager fragmentManager;
    private String[] tags;
    private List<String> otherTags = new ArrayList<>();
    private String modelName;
    private ImageView tab4, tab3, tab2, tab1;
    private TextView tab4_txt, tab3_txt, tab2_txt, tab1_txt;
    /**
     * 上次退出的时间
     */
    private long downTime;

    private LocationClient mLocationClient;//定位SDK的核心类
    private InitAppResponse mInitAppResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_indicator);
        initAll();
        doLogin();

        checkPermission(new CheckPermListener() {
                            @Override
                            public void superPermission() {
                                startLocation();
                            }
                        }, R.string.need_loaction_permission,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
        UserServiceImpl.instance().getReceiveAddressList(AddressListResponse.class.getName());
    }


    private void startLocation() {
        if (((BaseApplication) getApplication()).mLocationClient != null) {
            mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
            InitLocation();//初始化
            mLocationClient.start();
        }
    }


    private void doLogin() {
        if (GeneralUtils.isNotNullOrZeroLenght(Global.getPassword()) && GeneralUtils.isNotNullOrZeroLenght(Global.getLoginName())) {
            UserServiceImpl.instance().login(Global.getLoginName(), StringEncrypt.Encrypt(Global.getPassword()),
                    LoginResponse.class.getName());
        }
    }

    public static void getUpLoadImageUrl() {
        UserServiceImpl.instance().getUploadUrl(GetUploadUrlResponse.class.getName());
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (tag.equals(NotiTag.TAG_LOCATION_SUCCESS)&& BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (mLocationClient != null) {
                    mLocationClient.stop();
                }
                //应用初始化
                UserServiceImpl.instance().initAPP(InitAppResponse.class.getName());
            } else if (tag.equals(NotiTag.TAG_USER_EXIT_APP)) {
                if (mInitAppResponse.getAppVersionInfo().getType() == 1) {
                    ((BaseApplication) getApplication()).onTerminate();
                }
            }
        }
        if (event instanceof NoticeInfoEvent) {
            String tag = ((NoticeInfoEvent) event).getTag();
            if (tag.equals(NotiTag.TAG_INDEX_CHANGE_FRAGMENT)) {
                changeFragment(((NoticeInfoEvent) event).getText());
                otherTags.add(((NoticeInfoEvent) event).getText());
            }
        }
        if (event instanceof NetResponseEvent) {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName())) {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                    } else {
                        Global.loginOut(mContext);
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT));
//                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT));
//                    ToastUtil.showError(this);
                }
            } else if (tag.equals(InitAppResponse.class.getName())) {
                //应用初始化，包含升级操作
                mInitAppResponse = GsonHelper.toType(result, InitAppResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    try {
                        if (Constants.SUCESS_CODE.equals(mInitAppResponse.getResultCode())) {
                            CMLog.e(Constants.HTTP_TAG, result);
                            SharePref.saveString(Constants.lastUpdateTime, mInitAppResponse.getServerTime());
                            List<AppInitInfoListBean> appInitInfoList = mInitAppResponse.getAppInitInfoList();
                            if (appInitInfoList.size() > 0) {
                                SharePref.saveString(Constants.lastInitInfo, result.trim());
                            }
//                            if (GeneralUtils.isNotNullOrZeroLenght(mInitAppResponse.getCommunity().getName())){
//                                SharePref.saveString(Constants.initGetZoneName,mInitAppResponse.getCommunity().getName());
//                                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_APP_INIT_SUCCESS));
//                            }
                            InitAppResponse.AppVersionInfoBean versionBean = mInitAppResponse.getAppVersionInfo();
                            int versionCode = mInitAppResponse.getAppVersionInfo().getCodeVersion();
                            CMLog.e(Constants.HTTP_TAG, versionCode + "   " + Constants.VERSION_NAME);
                            if (versionCode>Constants.VERSION_NAME) {
                                UpdateUtils updateUtils = new UpdateUtils();
                                CodeBean codeBean = new CodeBean(versionBean.getCodeVersion() + "",
                                        versionBean.getShowVersion() + "",
                                        versionBean.getDescription(),
                                        versionBean.getUrl());
                                updateUtils.showDialog(mContext, codeBean);
                            }
                        } else {
                            ErrorCode.doCode(this, mInitAppResponse.getResultCode(), mInitAppResponse.getDesc());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showError(this);
                }
            } else if (tag.equals(GetUploadUrlResponse.class.getName())) {//上传图片的token
                GetUploadUrlResponse mGetUploadUrlResponse = GsonHelper.toType(result, GetUploadUrlResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mGetUploadUrlResponse.getResultCode())) {
                        Global.saveToken(mGetUploadUrlResponse.getUpToken());
                    } else {
//                        ErrorCode.doCode(this, mGetUploadUrlResponse.getResultCode(), mGetUploadUrlResponse.getDesc());
                    }
                }
            } else {
//                ToastUtil.showError(this);
            }
            if (tag.equals(AddressListResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    AddressListResponse mAddressListResponse = GsonHelper.toType(result, AddressListResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddressListResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.ADDRESS_LIST, result);
                        Gson gson=new Gson();
                        AddressBean it=null;
                        for(AddressBean item: mAddressListResponse.getUserAddressList()){
                            if(item.getIsDefault()!=null&&item.getIsDefault().equals("1")){
                                it=item;
                            }
                        }
                        if(it!=null) {
                            SharePref.saveString(Constants.CHOOSE_ADDRESS, gson.toJson(it));
                        }
                    }
                }
            }
        }

    }

    @Override
    public void initView() {
        tab1 = (ImageView) findViewById(R.id.tab1);
        tab2 = (ImageView) findViewById(R.id.tab2);
        tab3 = (ImageView) findViewById(R.id.tab3);
        tab4 = (ImageView) findViewById(R.id.tab4);
        tab1_txt = (TextView) findViewById(R.id.tab1_txt);
        tab2_txt = (TextView) findViewById(R.id.tab2_txt);
        tab3_txt = (TextView) findViewById(R.id.tab3_txt);
        tab4_txt = (TextView) findViewById(R.id.tab4_txt);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab1_txt.setOnClickListener(this);
        tab2_txt.setOnClickListener(this);
        tab3_txt.setOnClickListener(this);
        tab4_txt.setOnClickListener(this);
    }

    @Override
    public void initViewData() {
        tags = new String[]{NewIndexFragment.class.getName(), CategoryFragment.class.getName(), CartFragment.class.getName(), NewUserCenterFragment.class.getName()};
        modelName = tags[0];
        fragmentManager = this.getSupportFragmentManager();
        viewChange(modelName);
    }

    @Override
    public void initEvent() {

    }

    private void changeFragment(int position) {
        if (!modelName.equals(tags[position])) {
            modelName = tags[position];
            Global.saveNowIndex(modelName);
            viewChange(modelName);
        }

    }

    private void changeFragment(String fragmentName) {
        if (!modelName.equals(fragmentName)) {
            modelName = fragmentName;
            Global.saveNowIndex(modelName);
            viewChange(modelName);
        }
    }

    private void viewChange(String tag) {
        detachView(tag);//移除其他Fragment
        Fragment mFragment = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mFragment != null) {
            if (mFragment.isHidden()) {
                ft.show(mFragment);
            }
        } else {
            mFragment = Fragment.instantiate(this, tag);
            ft.add(R.id.main_content_frame, mFragment, tag);
        }
        ft.commit();
    }

    /**
     * 移除非类名为tag的Fragment,其中还应该考虑到fragment跳转Fragment的场合
     *
     * @param tag
     */
    private void detachView(String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment mFragment = null;
        for (int i = 0; i < tags.length; i++) {
            if (!tag.equals(tags[i])) {
                mFragment = fragmentManager.findFragmentByTag(tags[i]);
                if (mFragment != null) {
                    ft.hide(mFragment);
                }
            }
        }
        for (String otherTag : otherTags) {
            if (!tag.equals(otherTag)) {
                mFragment = fragmentManager.findFragmentByTag(otherTag);
                if (mFragment != null) {
                    ft.hide(mFragment);
                }
            }
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1_txt:
            case R.id.tab1:
                initDefaultGray();
                tab1_txt.setTextColor(getResources().getColor(R.color.sandybrown));
                tab1.setImageResource(R.mipmap.bql_btn_sy_h);
                changeFragment(0);
                break;
            case R.id.tab2_txt:
            case R.id.tab2:
                initDefaultGray();
                tab2_txt.setTextColor(getResources().getColor(R.color.sandybrown));
                tab2.setImageResource(R.mipmap.bql_btn_fl_h);
                changeFragment(1);
                break;
            case R.id.tab3_txt:
            case R.id.tab3:
                if (GeneralUtils.isLogin()) {
                    initDefaultGray();
                    tab3_txt.setTextColor(getResources().getColor(R.color.sandybrown));
                    tab3.setImageResource(R.mipmap.bql_btn_gwc_h);
                    changeFragment(2);
                } else {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
            case R.id.tab4_txt:
            case R.id.tab4:
//                if (GeneralUtils.isLogin()) {
                    initDefaultGray();
                    tab4_txt.setTextColor(getResources().getColor(R.color.sandybrown));
                    tab4.setImageResource(R.mipmap.bql_btn_grzx_h);
                    changeFragment(3);
//                } else {
//                    startActivity(new Intent(mContext, LoginActy.class));
//                }
                break;
        }
    }

    /**
     * 底部图标设为默认的黑色
     */
    private void initDefaultGray() {
        tab1.setImageResource(R.mipmap.bql_btn_sy_n);
        tab2.setImageResource(R.mipmap.bql_btn_fl_n);
        tab3.setImageResource(R.mipmap.bql_btn_gwc_n);
        tab4.setImageResource(R.mipmap.bql_btn_grzx_n);
        tab1_txt.setTextColor(getResources().getColor(R.color.textGrey3));
        tab2_txt.setTextColor(getResources().getColor(R.color.textGrey3));
        tab3_txt.setTextColor(getResources().getColor(R.color.textGrey3));
        tab4_txt.setTextColor(getResources().getColor(R.color.textGrey3));
    }


    /**
     * 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getDownTime() - downTime <= 2000) {
                ChatClient.getInstance().logout(true, new Callback(){
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
                BaseApplication.getInstance().onTerminate();
            } else {
                ToastUtil.makeText(this, getResources().getString(R.string.exit_tips));
                downTime = event.getDownTime();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 定位初始化设置
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onStop() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        super.onStop();
    }


}
