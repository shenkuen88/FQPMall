package com.fengqipu.mall.main.acty.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.UploadFileResponse;
import com.fengqipu.mall.bean.shop.OneButtonShopResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.ChooseLocationActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.BitmapUtil;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.FileSystemManager;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;
import com.fengqipu.mall.view.wheel.cascade.activity.LocationBaseActivity;
import com.fengqipu.mall.view.wheel.widget.OnWheelChangedListener;
import com.fengqipu.mall.view.wheel.widget.WheelView;
import com.fengqipu.mall.view.wheel.widget.adapters.ArrayWheelAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_introduction)
    EditText etIntroduction;
    @Bind(R.id.et_card_user)
    EditText etCardUser;
    @Bind(R.id.et_card_num)
    EditText etCardNum;
    @Bind(R.id.et_card_bank)
    EditText etCardBank;
    @Bind(R.id.iv_shop_logo)
    ImageView ivShopLogo;
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
    @Bind(R.id.ll_2)
    LinearLayout ll2;
    @Bind(R.id.ll_3)
    LinearLayout ll3;
    @Bind(R.id.iv_location)
    ImageView ivLocation;
    private void fileOperation()
    {
        photoSavePath = FileSystemManager.getImgPath(mContext);
        photoSaveName = System.currentTimeMillis() + ".png";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button_shop);
        ButterKnife.bind(this);
        fileOperation();
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
    protected void onResume() {
        super.onResume();
        if(!SharePref.getString("TEMPLOC","").equals("")){
            etAdressDetail.setText(SharePref.getString("TEMPLOC",""));
            SharePref.saveString("TEMPLOC","");
        }
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
        tvAddress.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        idProvince.addChangingListener(this);
        idCity.addChangingListener(this);
        idDistrict.addChangingListener(this);
        ivYyzz.setOnClickListener(this);
        ivShopLogo.setOnClickListener(this);
        ivSfz1.setOnClickListener(this);
        ivSfz2.setOnClickListener(this);
        btnLjkd.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
    }

    private void setUpData() {
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
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == idProvince) {
            updateCities();
        } else if (wheel == idCity) {
            updateAreas();
        } else if (wheel == idDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void updateAreas() {
        int pCurrent = idCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        idDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        idDistrict.setCurrentItem(0);
    }

    private void updateCities() {
        int pCurrent = idProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        idCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        idCity.setCurrentItem(0);
        updateAreas();
    }

    int posType = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_location:
//                startLocation();
                startActivity(new Intent(OneButtonShopActivity.this, ChooseLocationActivity.class));
                break;
            case R.id.btn_ljkd:

                if (etShopname.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写商店名称!");
                    return;
                }
                if (tvAddress.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请选择省市区!");
                    return;
                }
                if (etAdressDetail.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写详细地址!");
                    return;
                }
                if (etName.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写法人/负责人名称");
                    return;
                }
                if (etPhone.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写联系电话");
                    return;
                }
                if (etIntroduction.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写企业介绍");
                    return;
                }
                if (etCardBank.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写持卡人");
                    return;
                }
                if (etCardNum.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写卡号");
                    return;
                }
                if (etCardBank.getText().toString().equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请填写开户行");
                    return;
                }
                if (shopLogo.equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请上传企业logo!");
                    return;
                }
                if (yyzzPic.equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请上传营业执照!");
                    return;
                }
                if (sfz1.equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请上传身份证正面照!");
                    return;
                }
                if (sfz2.equals("")) {
                    ToastUtils.showToast(OneButtonShopActivity.this, "请上传身份证反面照!");
                    return;
                }

                //马上开店
                List<File> files = new ArrayList<>();
                files.add(new File(yyzzPic));
                files.add(new File(sfz1));
                files.add(new File(sfz2));
                files.add(new File(shopLogo));
                NetLoadingDialog.getInstance().loading(OneButtonShopActivity.this);
                UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
                break;
            case R.id.btn_confirm:
                tvAddress.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName);
                bottomLl.setVisibility(View.GONE);
                break;
            case R.id.btn_cancel:
                bottomLl.setVisibility(View.GONE);
                break;
            case R.id.tv_address:
                Log.e("sub","tv_address");
                bottomLl.setVisibility(View.VISIBLE);
                hideKeyboardd();
                break;
            case R.id.iv_yyzz:
                posType = 0;
                //权限
                checkPermission(new CheckPermListener() {
                                    @Override
                                    public void superPermission() {
                                        new MyPopupWindows(mContext, ll2);
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case R.id.iv_shop_logo:
                posType = 3;
                //权限
                checkPermission(new CheckPermListener() {
                                    @Override
                                    public void superPermission() {
                                        new MyPopupWindows(mContext, ll2);
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case R.id.iv_sfz1:
                posType = 1;
                //权限
                checkPermission(new CheckPermListener() {
                                    @Override
                                    public void superPermission() {
                                        new MyPopupWindows(mContext, ll3);
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                break;
            case R.id.iv_sfz2:
                posType = 2;
                //权限
                checkPermission(new CheckPermListener() {
                                    @Override
                                    public void superPermission() {
                                        new MyPopupWindows(mContext, ll3);
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

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

    private static final int TAKE_PICTURE = 0x000000;

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(FileSystemManager.getImgPath(mContext), String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
//        path = vFile.getPath();
//        Uri cameraUri = Uri.fromFile(vFile);
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);

        Uri imageUri = FileProvider.getUriForFile(OneButtonShopActivity.this, "com.fengqipu.mall.fileprovider", vFile);//通过FileProvider创建一个content类型的Uri
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
//                    openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public class MyPopupWindows extends PopupWindow {

        public MyPopupWindows(final Context mContext, View parent) {

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
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                    Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(openAlbumIntent, PHOTOZOOM);
                }
            });
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                    File file = new File(photoSavePath + photoSaveName);
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    Uri imageUri = FileProvider.getUriForFile(OneButtonShopActivity.this, "com.fengqipu.mall.fileprovider", file);//通过FileProvider创建一个content类型的Uri
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
//                    openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, PHOTOTAKE);
                    dismiss();
                }
            });
        }
    }

    //拍照上传
    public static final int PHOTOZOOM = 0; // 相册

    public static final int PHOTOTAKE = 1; //拍照

    public static final int IMAGE_COMPLETE = 2; // 结果

    private String photoSavePath;//保存路径

    private String photoSaveName;//图片名

    private String path;//图片全路径，也是上传的图片路径

    private String yyzzPic = "";
    private String sfz1 = "";
    private String sfz2 = "";
    private String shopLogo = "";

    /**
     * 图片选择及拍照结果
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Log.e("sub","requestCode="+requestCode);
        Uri uri = null;
        switch (requestCode) {
            case PHOTOZOOM://相册
                if (data == null) {
                    return;
                }
                uri = data.getData();
                path = BitmapUtil.getPath(mContext, uri);
                if (!GeneralUtils.isNetworkConnected(mContext)) {
                    ToastUtil.showError(mContext);
                    return;
                }
                switch (posType) {
                    case 0:
                        yyzzPic = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivYyzz, R.mipmap.btn_pic1);
                        break;
                    case 1:
                        sfz1 = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivSfz1, R.mipmap.btn_pic2);
                        break;
                    case 2:
                        sfz2 = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivSfz2, R.mipmap.btn_pic3);
                        break;
                    case 3:
                        shopLogo = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivShopLogo, R.mipmap.shop_logo);
                        break;
                }
                break;
            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                if (!GeneralUtils.isNetworkConnected(mContext)) {
                    ToastUtil.showError(mContext);
                    return;
                }
                Log.e("sub","requestCode="+path);
                switch (posType) {
                    case 0:
                        yyzzPic = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivYyzz, R.mipmap.btn_pic1);
                        break;
                    case 1:
                        sfz1 = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivSfz1, R.mipmap.btn_pic2);
                        break;
                    case 2:
                        sfz2 = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivSfz2, R.mipmap.btn_pic3);
                        break;
                    case 3:
                        shopLogo = path;
                        GeneralUtils.setImageViewWithUrl(OneButtonShopActivity.this, path, ivShopLogo, R.mipmap.shop_logo);
                        break;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private LocationClient mLocationClient;//定位SDK的核心类

    private void startLocation() {
        if (((BaseApplication) getApplication()).mLocationClient != null) {
            mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
            InitLocation();//初始化
            mLocationClient.start();
        }
    }

    /**
     * 定位初始化设置
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            if (tag.equals(NotiTag.TAG_LOCATION_SUCCESS)&& BaseApplication.currentActivity.equals(this.getClass().getName())) {
                Log.e("sub","OBSHOP TAG_LOCATION_SUCCESS");
                if (mLocationClient != null) {
                    mLocationClient.stop();
                }
                String addressDetail = ((NoticeEvent) event).getText();
                etAdressDetail.setText(addressDetail);
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(UploadFileResponse.class.getName())) {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode())) {
                        String url1 = "", url2 = "", url3 = "",url4="";
                        for (int i = 0; i < uploadFileResponse.getUrlList().size(); i++) {
                            if (i == 0) {
                                url1 = uploadFileResponse.getUrlList().get(i);
                            } else if (i == 1) {
                                url2 = uploadFileResponse.getUrlList().get(i);
                            } else if (i == 2) {
                                url3 = uploadFileResponse.getUrlList().get(i);
                            } else if (i == 3) {
                                url4 = uploadFileResponse.getUrlList().get(i);
                            }
                        }
                        NetLoadingDialog.getInstance().loading(OneButtonShopActivity.this);
                        UserServiceImpl.instance().addShop(etName.getText().toString(),
                                etPhone.getText().toString(),
                                etCardUser.getText().toString(),
                                etCardBank.getText().toString(),
                                etCardNum.getText().toString(),
                                etIntroduction.getText().toString(),
                                etShopname.getText().toString()
                                , mCurrentProviceName, mCurrentCityName, mCurrentDistrictName
                                , etAdressDetail.getText().toString(), url1, url2, url3,url4, OneButtonShopResponse.class.getName());
                    } else {
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(OneButtonShopResponse.class.getName())) {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    OneButtonShopResponse oneButtonShopResponse = GsonHelper.toType(result, OneButtonShopResponse.class);
                    if (Constants.SUCESS_CODE.equals(oneButtonShopResponse.getResultCode())) {
                        ToastUtils.showToast(mContext, "申请已成功提交");
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, oneButtonShopResponse.getResultCode(), oneButtonShopResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


}
