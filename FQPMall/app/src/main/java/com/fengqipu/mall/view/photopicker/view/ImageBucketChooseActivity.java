package com.fengqipu.mall.view.photopicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.PublicCommentActy;
import com.fengqipu.mall.main.acty.mine.RefundActy;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.view.photopicker.adapter.ImageBucketAdapter;
import com.fengqipu.mall.view.photopicker.model.ImageBucket;
import com.fengqipu.mall.view.photopicker.util.ImageFetcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 选择相册
 */

public class ImageBucketChooseActivity extends BaseActivity
{
    private ImageFetcher mHelper;

    private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();

    private ListView mListView;

    private ImageBucketAdapter mAdapter;

    private int availableSize;

    private HeadView headView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket_choose);

        mHelper = ImageFetcher.getInstance(getApplicationContext());
        initData();
        initView();
        initTitle();
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
                back();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                back();
            }
        }

    }

    private void back()
    {
        String className = SharePref.getString(Constants.PUBLIC_NEED_IMG_ACTY, "");
        Intent intent = new Intent();
        if (className.equals(PublishActivity.class.getName()))
        {//发帖
            intent.setClass(mContext, PublishActivity.class);
        }
        else if (className.equals(PublicCommentActy.class.getName()))
        {//评论
            intent.setClass(mContext, PublicCommentActy.class);
        }
        else if (className.equals(RefundActy.class.getName()))
        {//退款
            intent.setClass(mContext, RefundActy.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "0");
        startActivity(intent);
        finish();
    }

    /**
     * 按返回键
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0)
        {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData()
    {
        mDataList = mHelper.getImagesBucketList(false);
        availableSize = getIntent().getIntExtra(
                IntentCode.EXTRA_CAN_ADD_IMAGE_SIZE,
                Constants.MAX_IMAGE_SIZE);
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setRightText("取消");
        headView.setTitleText("相册");
        headView.setLeftImage(R.mipmap.app_title_back);
    }

    public void initView()
    {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ImageBucketAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

                selectOne(position);

                Intent intent = new Intent(ImageBucketChooseActivity.this,
                        ImageChooseActivity.class);
                intent.putExtra(IntentCode.EXTRA_IMAGE_LIST,
                        (Serializable) mDataList.get(position).imageList);
                intent.putExtra(IntentCode.EXTRA_BUCKET_NAME,
                        mDataList.get(position).bucketName);
                intent.putExtra(IntentCode.EXTRA_CAN_ADD_IMAGE_SIZE,
                        availableSize);

                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    private void selectOne(int position)
    {
        int size = mDataList.size();
        for (int i = 0; i != size; i++)
        {
            if (i == position)
            {
                mDataList.get(i).selected = true;
            }
            else
            {
                mDataList.get(i).selected = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
