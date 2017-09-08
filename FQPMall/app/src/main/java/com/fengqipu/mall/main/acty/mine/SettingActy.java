package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.LoginResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.AboutActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.FileSystemManager;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.switchbn.SwitchButton;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huqing on 2016/7/4.
 * app设置页面
 */
public class SettingActy extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.address_rl)
    RelativeLayout addressRl;
    @Bind(R.id.yijian_rl)
    RelativeLayout yijianRl;
    @Bind(R.id.aboutus_rl)
    RelativeLayout aboutusRl;
    /**
     * 关闭提示音
     */
    private SwitchButton soundBn;
    /**
     * 修改密码
     */
    private RelativeLayout editPsdRl;
    /**
     * 清空缓存
     */
    private RelativeLayout clearRl;
    /**
     * 退出
     */
    private TextView exitTv;
    private TextView clearTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_acty);
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
//                startActivity(new Intent(mContext, AddRecieveAddressActy.class));
            } else if (NotiTag.TAG_USER_EXIT.equals(tag)) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(LoginResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                    } else {
                        ErrorCode.doCode(mContext, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    @Override
    public void initView() {
        initTitle();
        soundBn = (SwitchButton) findViewById(R.id.open_sound_bn);
        editPsdRl = (RelativeLayout) findViewById(R.id.edit_password_rl);
        clearRl = (RelativeLayout) findViewById(R.id.clear_rl);
        exitTv = (TextView) findViewById(R.id.exit_tv);
        clearTv = (TextView) findViewById(R.id.clear_tv);

        editPsdRl.setOnClickListener(this);
        clearRl.setOnClickListener(this);
        exitTv.setOnClickListener(this);
        addressRl.setOnClickListener(this);
        yijianRl.setOnClickListener(this);
        aboutusRl.setOnClickListener(this);
        soundBn.setChecked(SharePref.getBoolean(Constants.SOUND_TIP, false));
        //提示音 仅本地保存
        soundBn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePref.saveBoolean(Constants.SOUND_TIP, isChecked);
            }
        });
        clearTv.setText(FileSystemManager.getFormatSize(FileSystemManager.getFolderSize(new File(FileSystemManager.getCacheFilePath(mContext)))));

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
        headView.setHiddenRight();
        headView.setTitleText("设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutus_rl:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.address_rl:
                Intent intent = new Intent(this, RecieveAddressListActy.class);
                intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, "");
                startActivity(intent);
                break;
            case R.id.yijian_rl:
                GeneralUtils.toActyOtherwiseLogin(this, FeedBackActivity.class);
                break;
            case R.id.edit_password_rl:
                startActivity(new Intent(mContext, EditPasswordActy.class));
                break;
            case R.id.clear_rl:
                FileSystemManager.deleteFolderFile(FileSystemManager.getCacheFilePath(mContext), true);
                ToastUtil.makeText(mContext, getString(R.string.app_clear_finish));
                String size = FileSystemManager.getFormatSize(FileSystemManager.getFolderSize(new File(FileSystemManager.getCacheFilePath(mContext))));
                CMLog.e(Constants.HTTP_TAG, size);
                clearTv.setText(size);

                break;
            case R.id.exit_tv:
                DialogUtil.exitAccountDialog(mContext);
                break;
        }
    }
}
