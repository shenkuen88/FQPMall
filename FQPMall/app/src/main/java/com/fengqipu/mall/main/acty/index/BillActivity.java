package com.fengqipu.mall.main.acty.index;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.SharePref;

import de.greenrobot.event.EventBus;


/**
 * 发票
 * SharePref.saveString(Constants.TAG_SET_BILL,"1");  为1时需要发票
 */
public class BillActivity extends BaseActivity implements View.OnClickListener {


    private Button bnSave;
    private CheckBox cb1, cb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        bnSave = (Button) findViewById(R.id.app_save_bn);
        cb1 = (CheckBox) findViewById(R.id.bill_cb1);
        cb2 = (CheckBox) findViewById(R.id.bill_cb2);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()) {
                    SharePref.saveString(Constants.TAG_SET_BILL, "0");
                } else {
                    SharePref.saveString(Constants.TAG_SET_BILL, "1");
                }
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_SET_BILL));
                finish();
            }
        });
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                } else {
                    cb1.setChecked(false);
                    cb2.setChecked(true);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                } else {
                    cb1.setChecked(false);
                    cb2.setChecked(true);
                }
            }
        });
        if (SharePref.getString(Constants.TAG_SET_BILL, "").equals("1")) {
            cb2.setChecked(true);
            cb1.setChecked(false);
        } else {
            cb2.setChecked(false);
            cb1.setChecked(true);
        }
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("发票");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

}
