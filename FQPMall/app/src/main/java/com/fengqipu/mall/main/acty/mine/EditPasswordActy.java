package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.EditPasswordResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.StringEncrypt;
import com.fengqipu.mall.tools.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * 设置密码
 */
public class EditPasswordActy extends BaseActivity implements View.OnClickListener {


    private Button comfirmBn;
    private String phoneNum = "";
    private EditText etPsdComfirm;
    private EditText psdEt;
    /**
     * 旧密码
     */
    private EditText etOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password_reset);
        phoneNum = getIntent().getStringExtra(IntentCode.REGISTER_PHONE);
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("修改密码");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView() {
        initTitle();
        psdEt = (EditText) findViewById(R.id.app_phone_et);
        etOld = (EditText) findViewById(R.id.app_old_et);
        etPsdComfirm = (EditText) findViewById(R.id.app_code_et);
        comfirmBn = (Button) findViewById(R.id.app_register_next_bn);

        comfirmBn.setOnClickListener(this);
        etPsdComfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (GeneralUtils.isNotNullOrZeroLenght(etPsdComfirm.getText().toString())) {
                    comfirmBn.setEnabled(true);
                }
            }
        });

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
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(EditPasswordResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    EditPasswordResponse mCheck = GsonHelper.toType(result, EditPasswordResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCheck.getResultCode())) {
                        //密码修改成功
                        ToastUtil.makeText(mContext, "密码修改成功，请用新密码登录");
                        Global.loginOut(mContext);
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_OUT));
                        startActivity(new Intent(mContext,LoginActy.class));
                        finish();

                    } else {
                        ErrorCode.doCode(mContext, mCheck.getResultCode(), mCheck.getDesc());
                        NetLoadingDialog.getInstance().dismissDialog();
                    }
                } else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
            }

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_register_next_bn:
                if (GeneralUtils.isNullOrZeroLenght(etOld.getText().toString().trim())) {
                    ToastUtil.makeText(mContext, "请输入旧密码");
                    return;
                } else if (etPsdComfirm.getText().toString().trim().equals(psdEt.getText().toString().trim())) {
                    if (etPsdComfirm.getText().toString().trim().length() >= 6) {
                        UserServiceImpl.instance().editPassword("1", phoneNum, etOld.getText().toString().trim(), StringEncrypt.Encrypt(etPsdComfirm.getText().toString().trim()), EditPasswordResponse.class.getName());
                    } else {
                        ToastUtil.makeText(mContext, "请输入大于六位数的密码");
                    }
                } else {
                    ToastUtil.makeText(mContext, "两次密码输入不一致");
                }

                break;

        }
    }
}
