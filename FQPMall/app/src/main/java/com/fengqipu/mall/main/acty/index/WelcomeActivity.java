package com.fengqipu.mall.main.acty.index;

import android.content.Intent;
import android.os.Bundle;

import com.fengqipu.mall.R;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.main.base.BaseActivity;


/**
 * 
 * <判断是否是新用户>
 * <功能详细描述>
 * 
 * @author  huqing
 */
public class WelcomeActivity extends BaseActivity
{
    
    private int isNewsOrOldUser = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_welcome);
        //判断是否是新用户
        isNewsUser();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    /**
     * 
     * <判断是否是新用户>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    private void isNewsUser()
    {
        isNewsOrOldUser = Global.getUserGuide();
        //0是老用户跳到欢迎页面，-1是新用户跳到Guide页面
        if (isNewsOrOldUser < Constants.GUIDE_VERSION_CODE)
        {
            Intent intent = new Intent();
            intent.setClass(this, GuideActy.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent load = new Intent();
            load.setClass(this, PageLoadingActivity.class);
            startActivity(load);
            finish();
        }
    }
    
}
