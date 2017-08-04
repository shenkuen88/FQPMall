package com.fengqipu.mall.main.acty.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.LoginResponse;
import com.fengqipu.mall.bean.mine.SearchUserResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.StringEncrypt;
import com.fengqipu.mall.tools.ToastUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;

public class LoginActy extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tab1)
    ImageView tab1;
    @Bind(R.id.tab1_txt)
    TextView tab1Txt;
    @Bind(R.id.tab2)
    ImageView tab2;
    @Bind(R.id.tab2_txt)
    TextView tab2Txt;
    private Button commitBn;
    private EditText nameET, psdET;
    private TextView forgetTv, registTv;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(mContext);
        setContentView(R.layout.activity_login_acty);
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("登录");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        initTitle();
        nameET = (EditText) findViewById(R.id.app_login_name_et);
        psdET = (EditText) findViewById(R.id.app_login_psd_et);
        commitBn = (Button) findViewById(R.id.app_login_bn);
        registTv = (TextView) findViewById(R.id.app_register_tv);
        forgetTv = (TextView) findViewById(R.id.app_forget_tv);

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        commitBn.setOnClickListener(this);
        registTv.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab1Txt.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab2Txt.setOnClickListener(this);

    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag)) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName())) {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        Global.savePassword(psdET.getText().toString());
                        Global.saveLoginName(nameET.getText().toString());
                        ToastUtil.makeText(mContext, "登录成功");
                        //发个通知，让其他页面知道已经退出了
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                        MainActivity.getUpLoadImageUrl();
                        if(ChatClient.getInstance().isLoggedInBefore()){
                            //已经登录，可以直接进入会话界面
                        }else{
                            //未登录，需要登录后，再进入会话界面
                            ChatClient.getInstance().createAccount(Global.getUserName(), Global.getUserName()
                                    , new Callback(){
                                        @Override
                                        public void onSuccess() {
                                            ChatClient.getInstance().login(Global.getUserName(), Global.getUserName(), new Callback(){
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
                                        }

                                        @Override
                                        public void onError(int i, String s) {
                                            if(i== Error.USER_ALREADY_EXIST){
                                                ChatClient.getInstance().login(Global.getUserName(), Global.getUserName(), new Callback(){
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
                                            }
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {
                                        }
                                    });
                        }
                        finish();
                    } else {
                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(SearchUserResponse.class.getName())) {
                Log.e("sub","SearchUserResponse="+result);
                SearchUserResponse searchUserResponse = GsonHelper.toType(result, SearchUserResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(searchUserResponse.getResultCode())) {
//                        if(searchUserResponse.getUserList()==null||searchUserResponse.getUserList().size()==0){
//
//                        }
                        if(searchUserResponse.getUser()==null){
                            Intent intent=new Intent(LoginActy.this,RegistCodeActy.class);
                            intent.putExtra("isThirdPart",sanUserID);
                            intent.putExtra("type",type);
                            intent.putExtra("nickName",sanNickName);
                            intent.putExtra("portrait",sanIconUri);
                            startActivity(intent);
                        }else{
                            UserServiceImpl.instance().login(searchUserResponse.getUser().getUserName(), StringEncrypt.Encrypt(searchUserResponse.getUser().getPassword()),
                                    LoginResponse.class.getName());
                        }
                    } else {
                        ErrorCode.doCode(this, searchUserResponse.getResultCode(), searchUserResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }

    }
    String type="";
    private String sanNickName="";
    private String sanIconUri="";
    private String sanUserID="";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
            case R.id.tab1_txt:
                Platform qq= ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.e("sub","qq onComplete");
                        type="1";
                        sanNickName= platform.getDb().getUserName();//获取用户名字
                        sanIconUri= platform.getDb().getUserIcon(); //获取用户头像
                        sanUserID =platform.getDb().getUserId();
                        String toJSLogin = "{" +
                                "  \"headimgurl\" : \"" +  sanIconUri+ "\","
                                + "  \"nickname\" : \"" + sanNickName + "\","
                                + "  \"sanUserID\" :  \""+ sanUserID + "\""
                                + "}";
                        Log.e("sub","qq="+toJSLogin);
                        UserServiceImpl.instance().searchUser("2",sanUserID,SearchUserResponse.class.getName());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        Log.e("sub","qqonError="+i);
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                qq.authorize();
                break;
            case R.id.tab2:
            case R.id.tab2_txt:
                Log.e("sub","wechat");
                Platform wechat= ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.e("sub","wechat onComplete");
                        type="2";
                        //获取资料
                        sanNickName= platform.getDb().getUserName();//获取用户名字
                        sanIconUri= platform.getDb().getUserIcon(); //获取用户头像
                        sanUserID =platform.getDb().getUserId();
                        String toJSLogin = "{" +
                                "  \"headimgurl\" : \"" +  sanIconUri+ "\","
                                + "  \"nickname\" : \"" + sanNickName + "\","
                                + "  \"sanUserID\" :  \""+ sanUserID + "\""
                                + "}";
                        Log.e("sub", "wx="+toJSLogin);
                        UserServiceImpl.instance().searchUser("2",sanUserID,SearchUserResponse.class.getName());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        Log.e("sub","wechatonError="+i);
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        Log.e("sub","wechatonCancel="+i);
                    }
                });
                wechat.authorize();
                break;
            case R.id.app_login_bn:
                if (GeneralUtils.isNotNullOrZeroLenght(psdET.getText().toString())) {
                    if (GeneralUtils.isNotNullOrZeroLenght(nameET.getText().toString())) {
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().login(nameET.getText().toString().trim(), StringEncrypt.Encrypt(psdET.getText().toString().trim()),
                                LoginResponse.class.getName());
                    } else {
                        ToastUtil.makeText(mContext, "请输入用户名");
                    }
                } else {
                    ToastUtil.makeText(mContext, "请输入密码");
                }
                break;
            case R.id.app_forget_tv:
                startActivity(new Intent(mContext, FindPasswordCodeActy.class));
                break;
            case R.id.app_register_tv:
                startActivity(new Intent(mContext, RegistCodeActy.class));
                break;
        }
    }


}
