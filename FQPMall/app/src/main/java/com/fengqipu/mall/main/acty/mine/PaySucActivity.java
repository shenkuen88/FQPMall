package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.AddressBean;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.SharePref;

import de.greenrobot.event.EventBus;

public class PaySucActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_suc);
        initAll();
        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_PAY_SUCCESS));
    }

    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.check_order_tv).setOnClickListener(this);
        findViewById(R.id.back_main_tv).setOnClickListener(this);
        //每次支付前选中的地址都会保存
        String addStr = SharePref.getString(Constants.CHOOSE_ADDRESS, "");
        AddressBean bean = GsonHelper.toType(addStr, AddressBean.class);
        TextView tvName = (TextView) findViewById(R.id.receiver_tv);
        TextView tvAddress = (TextView) findViewById(R.id.address_tv);
        TextView tvTel = (TextView) findViewById(R.id.tel_tv);
        tvName.setText(bean.getDeliveryUser());
        tvAddress.setText(bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail());
        tvTel.setText(bean.getPhone());

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("支付成功");
        headView.setHiddenRight();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_order_tv:
                Intent intent = new Intent(this, OrderListActivity.class);
                intent.putExtra("orderstate", 2);
                startActivity(intent);
                finish();
                break;
            case R.id.back_main_tv:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
        }
    }
}
