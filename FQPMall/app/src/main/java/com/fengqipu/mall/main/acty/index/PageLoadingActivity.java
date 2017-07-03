package com.fengqipu.mall.main.acty.index;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.index.AppInitInfoListBean;
import com.fengqipu.mall.bean.index.InitAppResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.main.acty.MainActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.SharePref;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <首页loading页>
 * <功能详细描述>
 *
 * @author huqing
 */
public class PageLoadingActivity extends BaseActivity
{


    private Timer timer;

    private List<AppInitInfoListBean> appInitInfoList = new ArrayList<>();

    /**
     * 展示图片的ImageView
     */
    private ImageView ivLoad;

    private int imageNum = 1;

    private int imageShowIndex = 0;

    //    private int picShowDelay = 2000;
    private TimerTask task;

    private int picShowDelay = 3000;

    private RelativeLayout rl_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_pageloading);
        ivLoad = (ImageView) findViewById(R.id.load_iv);
        /**
         * 获取上次应用初始化中保存的正确的数据
         */
        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.lastInitInfo, "")))
        {
            InitAppResponse mInitAppResponse = GsonHelper.toType(SharePref.getString(Constants.lastInitInfo, ""), InitAppResponse.class);
            appInitInfoList = mInitAppResponse.getAppInitInfoList();
            if (appInitInfoList.size() > 0)
            {
                rl_loading.setVisibility(View.GONE);
                imageNum = appInitInfoList.size();
                //还得改下图片加载的，加载中不显示
                Glide.with(mContext)
                        .load(appInitInfoList.get(imageShowIndex).getFirstPic())
                        .placeholder(Color.WHITE)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存转换后的资源
                        .into(ivLoad);

                imageShowIndex++;
            }
            else
            {
                rl_loading.setVisibility(View.VISIBLE);
            }
        }
        startTime();
    }

    @Override
    public void initView()
    {

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                if (imageShowIndex < imageNum && appInitInfoList.size() > 0)
                {
                    Glide.with(mContext)
                            .load(appInitInfoList.get(imageShowIndex).getFirstPic())
                            .placeholder(Color.WHITE)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存转换后的资源
                            .into(ivLoad);
                    ivLoad.startAnimation(AnimationUtils.loadAnimation(mContext,
                            R.anim.fade_ins_long));
                    imageShowIndex++;
                }
                else
                {
                    Intent intent = new Intent(PageLoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    timer.cancel();
                    finish();
                }
            }
        }
    };

    private void startTime()
    {
        task = new TimerTask()
        {
            public void run()
            {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer = new Timer(true);
        timer.schedule(task, picShowDelay, picShowDelay);
    }

    @Override
    protected void onDestroy()
    {
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }


}
