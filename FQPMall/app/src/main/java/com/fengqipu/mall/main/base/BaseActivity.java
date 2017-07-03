package com.fengqipu.mall.main.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.tools.ACache;
import com.fengqipu.mall.tools.DisplayUtil;
import com.fengqipu.mall.tools.FileSystemManager;
import com.fengqipu.mall.tools.SystemBarTintManager;
import com.fengqipu.mall.tools.permission.PermissionActivity;

import de.greenrobot.event.EventBus;

/**
 * <基础activity>
 *
 * @author wangtao
 */
public abstract class BaseActivity extends PermissionActivity {
    public Context mContext;

    private SystemBarTintManager tintManager;
    protected InputMethodManager inputManager;
    public ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        mCache = ACache.get(this);//网路返回值缓存
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        StatusBarUtil.myStatusBar(BaseActivity.this);
        FileSystemManager.getCacheFilePathAll(mContext);
    }


    public void initAll(){
//        setColor(this, R.color.status_bar);
        initView();
        initViewData();
        initEvent();
    }
    public abstract void initView();

    public abstract void initViewData();

    public abstract void initEvent();

    //http://blog.csdn.net/eclothy/article/details/41912445
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    protected void setImmerseLayout(View view) {
        if (!DisplayUtil.checkDeviceHasNavigationBar(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
            }
        }
    }


    public void initWindow()
    {
        if (!DisplayUtil.checkDeviceHasNavigationBar(this))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
                tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.status_bar));
                tintManager.setStatusBarTintEnabled(true);
            }
            else
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                Window window = getWindow();
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        int statusBarHeight = DisplayUtil.getStatusBarHeight(this.getBaseContext());
        BaseApplication.statusBarHeight = statusBarHeight;
    }


    public void onEvent(BaseResponse event) {
    }

    public  void onEventMainThread(BaseResponse event) throws Exception {
    }

    public void onEventBackgroundThread(BaseResponse event) {
    }

    public void onEventAsync(BaseResponse event) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.currentActivity = this.getClass().getName();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getInstance().deleteActivity(this);
        EventBus.getDefault().unregister(this);
//        ViewGroup root = (ViewGroup) this.getWindow().getDecorView();  //获取本Activity下的获取最外层控件
//        BitmapUtil.destoryViewImage(root);
        super.onDestroy();
    }
    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if ( getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if ( getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */

    public static void setColor(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // 设置状态栏透明

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 生成一个状态栏大小的矩形

            View statusView = createStatusView(activity, color);

            // 添加 statusView 到布局中

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

            decorView.addView(statusView);

            // 设置根布局的参数

            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            rootView.setFitsSystemWindows(true);

            rootView.setClipToPadding(true);

        }

    }
    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity

     * @param color    状态栏颜色值

     * @return 状态栏矩形条

     */

    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;

    }

}
