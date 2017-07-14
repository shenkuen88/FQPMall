package com.fengqipu.mall.main.acty.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.AddReceiveAddressResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.wheel.cascade.activity.LocationBaseActivity;
import com.fengqipu.mall.view.wheel.widget.OnWheelChangedListener;
import com.fengqipu.mall.view.wheel.widget.WheelView;
import com.fengqipu.mall.view.wheel.widget.adapters.ArrayWheelAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OneButtonShopActivity extends LocationBaseActivity implements View.OnClickListener, OnWheelChangedListener {
    @Bind(R.id.btn_ljkd)
    Button btnLjkd;
    @Bind(R.id.iv_yyzz)
    ImageView ivYyzz;
    @Bind(R.id.iv_sfz1)
    ImageView ivSfz1;
    @Bind(R.id.iv_sfz2)
    ImageView ivSfz2;
    @Bind(R.id.et_shopname)
    EditText etShopname;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_adress_detail)
    EditText etAdressDetail;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.btn_confirm)
    TextView btnConfirm;
    @Bind(R.id.id_province)
    WheelView idProvince;
    @Bind(R.id.id_city)
    WheelView idCity;
    @Bind(R.id.id_district)
    WheelView idDistrict;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button_shop);
        ButterKnife.bind(this);
        initAll();
    }

    private void initTitle() {
        View view = (View) findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("一键开店");
    }

    @Override
    public void initView() {
        initTitle();
    }

    @Override
    public void initViewData() {
        setUpData();
    }

    @Override
    public void initEvent() {
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        idProvince.addChangingListener(this);
        idCity.addChangingListener(this);
        idDistrict.addChangingListener(this);
    }
    private void setUpData()
    {
        initProvinceDatas();
        idProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext, mProvinceDatas));
        idProvince.setVisibleItems(7);
        idCity.setVisibleItems(7);
        idDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        idProvince.setCurrentItem(2);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue)
    {
        if (wheel == idProvince)
        {
            updateCities();
        }
        else if (wheel == idCity)
        {
            updateAreas();
        }
        else if (wheel == idDistrict)
        {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void updateAreas()
    {
        int pCurrent = idCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null)
        {
            areas = new String[]{""};
        }
        idDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        idDistrict.setCurrentItem(0);
    }

    private void updateCities()
    {
        int pCurrent = idProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null)
        {
            cities = new String[]{""};
        }
        idCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        idCity.setCurrentItem(0);
        updateAreas();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                tvAddress.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName);
                bottomLl.setVisibility(View.GONE);
                break;
            case R.id.btn_cancel:
                bottomLl.setVisibility(View.GONE);
                break;
            case R.id.tv_address:
                bottomLl.setVisibility(View.VISIBLE);
                hideKeyboardd();
                break;
            default:
                break;
        }
    }

    private void hideKeyboardd() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            try {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(AddReceiveAddressResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    AddReceiveAddressResponse mAddReceiveAddressResponse = GsonHelper.toType(result, AddReceiveAddressResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddReceiveAddressResponse.getResultCode())) {
                    } else {
                        ErrorCode.doCode(mContext, mAddReceiveAddressResponse.getResultCode(), mAddReceiveAddressResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


}
