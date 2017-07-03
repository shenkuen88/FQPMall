package com.fengqipu.mall.main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.tools.ACache;
import com.fengqipu.mall.tools.BitmapUtil;

import de.greenrobot.event.EventBus;

/**
 * 
 * <基础Fragment>
 * <功能详细描述>
 * 
 * @author  hq
 * @version  [版本�? Apr 21, 2014]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseFragment extends Fragment
{

    public ACache mCache;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mCache = ACache.get(getActivity());
    }
    private ViewGroup root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onEvent(BaseResponse event)
    {
    }
    
    public void onEventMainThread(BaseResponse event) throws Exception {
    }
    
    public void onEventBackgroundThread(BaseResponse event)
    {
    }
    
    public void onEventAsync(BaseResponse event)
    {
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BitmapUtil.destoryViewImage(root);
    }

}
