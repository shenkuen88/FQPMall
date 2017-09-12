package com.fengqipu.mall.view.photopicker.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.conmunity.ImageBean;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommunityImageZoomActivity extends BaseActivity {

    private ViewTreeObserver viewTreeObserver = null;

    private ViewPager pager;

    private MyPageAdapter adapter;

    private int currentPosition;

    private List<ImageBean> mDataList = new ArrayList<ImageBean>();

    private HeadView headView;

    private int window_width;

    private int window_height;
    private RelativeLayout layout_rl;
    private LinearLayout my_dots;
    /**
     * 这里重写handleMessage方法，接受到子线程数据后更新UI
     **/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //关闭
                    GeneralUtils.saveImageToGallery(mContext, bitmap, mDataList.get(currentPosition).getUrl());
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.makeText(mContext, "保存成功");
                    break;
            }
        }
    };

    private Bitmap bitmap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_show);

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);

        my_dots=(LinearLayout)findViewById(R.id.my_dots);
        layout_rl=(RelativeLayout) findViewById(R.id.layout_rl);
        layout_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
        adapter = new MyPageAdapter(mDataList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentPosition);

        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.app_save_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralUtils.isNotNullOrZeroLenght(mDataList.get(currentPosition).getUrl())) {
                    NetLoadingDialog.getInstance().loading(mContext);
                    //启动一个后台线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //这里下载数据
                            try {
                                URL url = new URL(mDataList.get(currentPosition).getUrl());
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);
                                conn.connect();
                                InputStream inputStream = conn.getInputStream();
                                bitmap = BitmapFactory.decodeStream(inputStream);
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
                else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.makeText(mContext, "无法获取图片地址");
                }
            }
        });
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }

    }

    @Override
    public void initView() {
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private int cutpos=0;
    private void initData() {
        currentPosition = getIntent().getIntExtra(
                IntentCode.EXTRA_CURRENT_IMG_POSITION, 0);
        mDataList = (List<ImageBean>) getIntent().getSerializableExtra(IntentCode.COMMUNITY_IMAGE_DATA);
        my_dots.removeAllViews();
        mPointViews.clear();
        for (int count = 0; count < mDataList.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(this);
            pointView.setPadding(5, 0, 5, 0);
            if (cutpos==count)
                pointView.setImageResource(R.mipmap.ic_page_indicator_focused);
            else
                pointView.setImageResource(R.mipmap.ic_page_indicator);
            mPointViews.add(pointView);
            my_dots.addView(pointView);
        }
    }

    private void removeImgs() {
        mDataList.clear();
    }

    private void removeImg(int location) {
        if (location + 1 <= mDataList.size()) {
            mDataList.remove(location);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            currentPosition = arg0;
            cutpos=arg0;
            Log.e("sub","arg0="+arg0);
            for(ImageView imageView:mPointViews){
                imageView.setImageResource(R.mipmap.ic_page_indicator);
            }
            mPointViews.get(cutpos).setImageResource(R.mipmap.ic_page_indicator_focused);}

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };

    class MyPageAdapter extends PagerAdapter {
        private List<ImageBean> dataList = new ArrayList<ImageBean>();

        private ArrayList<PhotoView> mViews = new ArrayList<PhotoView>();
//        private ArrayList<DragImageView> mViews = new ArrayList<DragImageView>();

        public MyPageAdapter(List<ImageBean> dataList) {
            this.dataList = dataList;
            int size = dataList.size();
            for (int i = 0; i != size; i++) {


                PhotoView iv = new PhotoView(CommunityImageZoomActivity.this);
                iv.setBackgroundColor(Color.parseColor("#404040"));
                Glide.with(mContext)
                        .load(dataList.get(i).getUrl())
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存转换后的资源
                        .into(iv);

                iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                mViews.add(iv);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Object instantiateItem(View arg0, int arg1) {
            PhotoView iv = mViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews.size() >= arg1 + 1) {
                ((ViewPager) arg0).removeView(mViews.get(arg1));
            }
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        public void removeView(int position) {
            if (position + 1 <= mViews.size()) {
                mViews.remove(position);
            }
        }

    }
}
