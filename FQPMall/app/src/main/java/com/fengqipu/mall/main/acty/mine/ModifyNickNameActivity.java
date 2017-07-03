package com.fengqipu.mall.main.acty.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.EditUserInfoResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * 修改昵称
 */
public class ModifyNickNameActivity extends BaseActivity implements View.OnClickListener {


    private EditText etNickName;
    private String type;
    private String detail;
    private String editInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        etNickName = (EditText) findViewById(R.id.app_nickName_et);
        etNickName.setText(Global.getNickName());
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
                if (isOpen) {
                    try {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        finish();
                    }
                } else {
                    finish();
                }
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().editUserInfo(1,etNickName.getText().toString(),
                        EditUserInfoResponse.class.getName());
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(EditUserInfoResponse.class.getName())) {
                EditUserInfoResponse mEditInfoResponse = GsonHelper.toType(result, EditUserInfoResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG,result);
                    if (Constants.SUCESS_CODE.equals(mEditInfoResponse.getResultCode())) {
                        ToastUtil.makeText(mContext, "修改成功!");
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                        finish();
                    } else {
                        ErrorCode.doCode(this, mEditInfoResponse.getResultCode(), mEditInfoResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("保存");
        headView.setTitleText("修改");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
