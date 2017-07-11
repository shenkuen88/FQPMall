package com.fengqipu.mall.main.acty.mine;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeDialogInfoEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.BirthDateResponse;
import com.fengqipu.mall.bean.mine.EditUserInfoResponse;
import com.fengqipu.mall.bean.mine.UploadFileResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.main.base.square_clip.CircleClipActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.BitmapUtil;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.FileSystemManager;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.birthdate.MyDayView;
import com.fengqipu.mall.view.birthdate.MyMonthView;
import com.fengqipu.mall.view.birthdate.MyYearView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqing on 2016/7/4.
 * 账号管理
 */
public class AccountManageActy extends BaseActivity implements View.OnClickListener
{

    private RelativeLayout rlHead;

    private RelativeLayout rlNick;

    private RelativeLayout rlSex;

    private RelativeLayout rlBirth;

    /**
     * 姓名
     */
    private TextView tvName;

    /**
     * 昵称
     */
    private TextView tvNick;

    /**
     * 性别
     */
    private TextView tvSex;

    /**
     * 出生日期
     */
    private TextView tvBirth;

    /**
     * 头像
     */
    private ImageView ivHead;

    /**
     * 日期对话框
     */
    private Dialog locationDialog;

    private MyYearView yearView;

    private MyMonthView monthView;

    private MyDayView dayView;

    private Integer year;

    private String headUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        MainActivity.getUpLoadImageUrl();
        initTitle();
        fileOperation();
        initAll();
    }


    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag))
            {
            }
            else if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag))
            {//修改成功相当于登录后修改消息
                initViewData();
            }
            else if (NotiTag.TAG_USER_HEAD_CHOOSE.equals(tag))
            {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().editUserInfo(2, headUrl, EditUserInfoResponse.class.getName());
            }
            else if (NotiTag.TAG_CHOOSE_TIME_OK.equals(tag))
            {
                String time = ((NoticeEvent) event).getText();
                CMLog.e(Constants.HTTP_TAG, time);
                UserServiceImpl.instance().editUserInfo(5, ((NoticeEvent) event).getText().trim(),
                        EditUserInfoResponse.class.getName());
                NetLoadingDialog.getInstance().loading(mContext);
            }
        }
        else if (event instanceof NoticeDialogInfoEvent)
        {
            String tag = ((NoticeDialogInfoEvent) event).getTag();
            if (NotiTag.TAG_CHOOSE_CITY_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                //初始化地址选择对话框
                initDateDialog((NoticeDialogInfoEvent) event);
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(UploadFileResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode()))
                    {
                        UserServiceImpl.instance().editUserInfo(2, uploadFileResponse.getUrl(), EditUserInfoResponse.class.getName());
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(EditUserInfoResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    EditUserInfoResponse mEditUserInfoResponse = GsonHelper.toType(result, EditUserInfoResponse.class);
                    if (Constants.SUCESS_CODE.equals(mEditUserInfoResponse.getResultCode()))
                    {
                        Global.saveLoginUserData(mContext, mEditUserInfoResponse.getUser());
                        initViewData();
//                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                        ToastUtil.makeText(mContext, "修改成功！");
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mEditUserInfoResponse.getResultCode(), mEditUserInfoResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void initDateDialog(NoticeDialogInfoEvent event)
    {
        locationDialog = event.getmDialog();
        WindowManager.LayoutParams lp = locationDialog.getWindow().getAttributes();
        lp.alpha = 0.98f;
        locationDialog.getWindow().setAttributes(lp);
        yearView = event.getMyYearView();
        monthView = event.getMyMonthView();
        dayView = event.getMyDayView();
        locationDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {//关闭对话框 并旋转箭头方向
                    hideDialog();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
        initWheel();
//        setUpData();
    }

    private void initWheel()
    {
        if (monthView.getTime().equals("1月") || monthView.getTime().equals("3月")
                || monthView.getTime().equals("5月") || monthView.getTime().equals("7月")
                || monthView.getTime().equals("8月") || monthView.getTime().equals("10月") || monthView.getTime().equals("12月"))
        {
            dayView.initData(31);
        }
        else
        {
            dayView.initData(30);
            if (monthView.getTime().equals("2月"))
            {
                String stringYear = yearView.getTime();
                year = Integer.valueOf(stringYear.substring(0, stringYear.length() - 1));
                if (year % 4 == 0)
                {
                    dayView.initData(29);
                }
                else
                {
                    dayView.initData(28);
                }
            }
        }
    }

    /**
     * 关闭dialog 改变箭头
     */
    private void hideDialog()
    {
        if (locationDialog != null && locationDialog.isShowing())
        {
            locationDialog.dismiss();
        }
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("账户管理");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.nick_rl:
                startActivity(new Intent(mContext, ModifyNickNameActivity.class));
                break;
            case R.id.head_rl:
                if (!GeneralUtils.isNetworkConnected(mContext))
                {
                    ToastUtil.showError(mContext);
                    return;
                }
                //权限
                checkPermission(new CheckPermListener()
                                {
                                    @Override
                                    public void superPermission()
                                    {
                                        new MyPopupWindows(mContext, rlHead);
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case R.id.sex_rl:
                new SexPopupWindows(mContext, rlSex);
                break;
            case R.id.birth_rl:
                DialogUtil.chooseDateDialog(mContext, BirthDateResponse.class.getName());
                break;

        }
    }

    @Override
    public void initView()
    {
        ivHead = (ImageView) findViewById(R.id.my_head_iv);
        rlHead = (RelativeLayout) findViewById(R.id.head_rl);
        rlNick = (RelativeLayout) findViewById(R.id.nick_rl);
        rlSex = (RelativeLayout) findViewById(R.id.sex_rl);
        rlBirth = (RelativeLayout) findViewById(R.id.birth_rl);

        tvName = (TextView) findViewById(R.id.user_name_tv);
        tvNick = (TextView) findViewById(R.id.user_nick_tv);
        tvSex = (TextView) findViewById(R.id.user_sex_tv);
        tvBirth = (TextView) findViewById(R.id.user_birth_tv);

        rlNick.setOnClickListener(this);
        rlHead.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlBirth.setOnClickListener(this);


    }

    @Override
    public void initViewData()
    {
        Log.e("sub","head="+Global.getUserHeadUrl());
        GeneralUtils.setRoundImageViewWithUrl(mContext, Global.getUserHeadUrl(), ivHead, R.drawable.default_head);
        tvName.setText(Global.getUserName());
        tvNick.setText(Global.getNickName());
        if (Global.getGender().equals("1"))
        {
            tvSex.setText("男");
        }
        else
        {
            tvSex.setText("女");
        }
        tvBirth.setText(Global.getBirthday());
    }

    @Override
    public void initEvent()
    {

    }


    public class MyPopupWindows extends PopupWindow
    {

        public MyPopupWindows(final Context mContext, View parent)
        {

            View view = View.inflate(mContext, R.layout.item_popupwindow_img, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                    Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(openAlbumIntent, PHOTOZOOM);
                }
            });
            bt1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                    photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                    File file=new File(photoSavePath+photoSaveName);
                    if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                    Uri imageUri = FileProvider.getUriForFile(AccountManageActy.this, "com.fengqipu.mall.fileprovider", file);//通过FileProvider创建一个content类型的Uri
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
//                    openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, PHOTOTAKE);
                }
            });
        }
    }

    public class SexPopupWindows extends PopupWindow
    {

        public SexPopupWindows(final Context mContext, View parent)
        {

            View view = View.inflate(mContext, R.layout.item_popupwindow_img, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            bt1.setText("男");
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            bt2.setText("女");
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                    tvSex.setText("女");
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().editUserInfo(3, "2",
                            EditUserInfoResponse.class.getName());

                }
            });
            bt1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                    tvSex.setText("男");
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().editUserInfo(3, "1",
                            EditUserInfoResponse.class.getName());
                }
            });
        }
    }

    /**
     * 图片选择及拍照结果
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK)
        {
            return;
        }
        Uri uri = null;
        switch (requestCode)
        {
            case PHOTOZOOM://相册
                if (data == null)
                {
                    return;
                }
                uri = data.getData();
                path = BitmapUtil.getPath(mContext, uri);
                Intent intent3 = new Intent(mContext, CircleClipActivity.class);
                intent3.putExtra("path", path);
                startActivityForResult(intent3, IMAGE_COMPLETE);
//                ImageLoaderUtil.getInstance().displayFromSDCard(path, ivHead);
//                NetLoadingDialog.getInstance().loading(mContext);
//                Bitmap headBit = GeneralUtils.getLoacalBitmap(path);
//                UserServiceImpl.instance().doUpLoadHeadImg(GeneralUtils.getFromPathString(headBit), EditUserHeadResponse.class.getName());
                break;
            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                Intent intent2 = new Intent(mContext, CircleClipActivity.class);
                intent2.putExtra("path", path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;
            case IMAGE_COMPLETE:
                final String temppath = data.getStringExtra("path");
                GeneralUtils.setRoundImageViewWithUrl(mContext, temppath, ivHead, R.drawable.default_bg);
                Bitmap headBit1 = BitmapUtil.getLoacalBitmap(temppath);
                if (!GeneralUtils.isNetworkConnected(mContext))
                {
                    ToastUtil.showError(mContext);
                    return;
                }
                //上传图片start
                NetLoadingDialog.getInstance().loading(mContext);
                File file = new File(temppath);
                Log.e("sub","file="+file.exists()+",temppath="+temppath);
                List<File> files=new ArrayList<>();
                files.add(file);
                UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
//                final UploadManager uploadManager = new UploadManager();
//                new Thread()
//                {
//                    public void run()
//                    {
//                        // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
//                        uploadManager.put(temppath, "portrait_" + Global.getUserId() + "_" + java.util.UUID.randomUUID().toString() + ".png", Global.getToken(),
//                                new UpCompletionHandler()
//                                {
//                                    @Override
//                                    public void complete(String key, ResponseInfo info, JSONObject res)
//                                    {
//                                        try
//                                        {
//                                            headUrl = res.getString("key");
//                                            EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_HEAD_CHOOSE));
//                                        } catch (JSONException e)
//                                        {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }
//                                , null);
//
//                    }
//                }.start();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //拍照上传
    private LayoutInflater layoutInflater;

    private TextView photograph, albums;

    private LinearLayout cancel;

    public static final int PHOTOZOOM = 0; // 相册

    public static final int PHOTOTAKE = 1; //拍照

    public static final int IMAGE_COMPLETE = 2; // 结果

    public static final int CROPREQCODE = 3; // 截取

    private String photoSavePath;//保存路径

    private String photoSaveName;//图片名

    private String path;//图片全路径，也是上传的图片路径

    private void fileOperation()
    {
        photoSavePath = FileSystemManager.getImgPath(mContext);
        photoSaveName = System.currentTimeMillis() + ".png";
    }
}
