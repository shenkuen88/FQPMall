package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.YZMResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;

/**
 * 注册
 */
public class RegistCodeActy extends BaseActivity implements View.OnClickListener {


    private Button registBn;
    private EditText etPhone;
    private String formerPhone;


    private TextView tvAggreement;
    private String isThirdPart="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        isThirdPart=getIntent().getStringExtra("isThirdPart");
        if(isThirdPart==null){
            isThirdPart="0";
        }
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        if(isThirdPart.equals("0")) {
            headView.setTitleText("手机快速注册");
        }else{
            headView.setTitleText("绑定手机号");
        }
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView() {
        initTitle();
        etPhone = (EditText) findViewById(R.id.app_phone_et);
        registBn = (Button) findViewById(R.id.app_register_next_bn);
        tvAggreement = (TextView) findViewById(R.id.app_aggrement_tv);
        tvAggreement.setOnClickListener(this);
        registBn.setOnClickListener(this);

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onEventMainThread(BaseResponse event) {
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
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(YZMResponse.class.getName())&&BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    YZMResponse mYZMResponse = GsonHelper.toType(result, YZMResponse.class);
                    if (Constants.SUCESS_CODE.equals(mYZMResponse.getResultCode())) {
                        //获取验证码成功后，跳转到注册页面
                        Intent intent = new Intent(mContext,RegistSetPasswordActy.class);
                        intent.putExtra(IntentCode.REGISTER_PHONE,formerPhone);
                        if(!isThirdPart.equals("0")) {
                            intent.putExtra("isThirdPart",isThirdPart);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mYZMResponse.getResultCode(), mYZMResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_register_next_bn:
                formerPhone = etPhone.getText().toString().trim();
                if (formerPhone.length() >0) {
                    NetLoadingDialog.getInstance().loading(this);
                    UserServiceImpl.instance().getYZMCode( "1", formerPhone, YZMResponse.class.getName());
                } else {
                    ToastUtil.makeText(mContext, getString(R.string.app_input_error));
                }
                break;
            case R.id.app_aggrement_tv:

        }
    }


}
