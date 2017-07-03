package com.fengqipu.mall.main.acty.index;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;


/**
 * 系统消息
 * item_ststem_notice
 */
public class SystemNoticeActivity extends BaseActivity implements View.OnClickListener {


    private Button bnSave;
    private CheckBox cb1, cb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notice);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.notice_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.toActyOtherwiseLogin(mContext, ProductNoticeActivity.class);
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
//            if (tag.equals(ComplainResponse.class.getName())) {
//                NetLoadingDialog.getInstance().dismissDialog();
//                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
//                    ComplainResponse mComplainResponse = GsonHelper.toType(result, ComplainResponse.class);
//                    if (Constants.SUCESS_CODE.equals(mComplainResponse.getStatus())) {
//                        ToastUtil.makeText(mContext, mComplainResponse.getMessage());
//                        finish();
//                    } else {
//                        ErrorCode.doCode(mContext, mComplainResponse.getStatus(), mComplainResponse.getMessage());
//                    }
//                } else {
//                    ToastUtil.showError(mContext);
//                }
//            }
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("消息");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

}
