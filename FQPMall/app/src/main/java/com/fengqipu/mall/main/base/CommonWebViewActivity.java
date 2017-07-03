package com.fengqipu.mall.main.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.constant.URLUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.WebViewUtil;


public class CommonWebViewActivity extends BaseActivity
{
    private WebView webView;

    private String title;

    private String url;

    private String mTag;

    private View viewError;

    private boolean showError;

    private String instruction;

    private boolean clear;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_web_view);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
//        NetLoadingDialog.getInstance().loading(this);
        webView = (WebView) findViewById(R.id.common_web_view);
//        WebViewUtil.initWebView(this, webView, "https://www.baidu.com/?tn=47018152_dg");
        WebViewUtil.initWebView(this, webView, url);
        viewError = findViewById(R.id.error_view);
        viewError.setVisibility(View.GONE);
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    private void initTitle()
    {
        title = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_TITLE);
        url = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL);
        instruction = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL_INSTRUCTION);
        mTag = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL_TAG);

        if (GeneralUtils.isNullOrZeroLenght(title))
        {
            title = getResources().getString(R.string.app_name);
        }
        if (GeneralUtils.isNullOrZeroLenght(url))
        {
            url = URLUtil.DEFAULT_WEB_URL;
        }

        View view = findViewById(R.id.common_back);
//        setImmerseLayout(view);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//        {
////            view.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//        }
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText(title);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        BaseApplication.urlTag = url;
    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()) && BaseApplication.urlTag.equals(url))
            {
                if (webView.canGoBack())
                {
                    webView.goBack();
                }
                else
                {
                    finish();
                }
            }
            if (NotiTag.equalsTags(this, tag, NotiTag.TAG_DO_RIGHT))
            {
                if ("HOME".equals(mTag))
                {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    intent.putExtra(Intent.EXTRA_TEXT, instruction + " " + url);
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
                }


            }
            if (NotiTag.TAG_WEB_VIEW_ERROR.equals(tag))
            {
                showError = true;
                viewError.setVisibility(View.VISIBLE);
                NetLoadingDialog.getInstance().dismissDialog();
            }
            if (NotiTag.TAG_WEB_VIEW_START.equals(tag))
            {
                NetLoadingDialog.getInstance().dismissDialog();
                NetLoadingDialog.getInstance().loading(this);
            }
            if (NotiTag.TAG_WEB_VIEW_FINISH.equals(tag))
            {
                if (!showError)
                {
                    viewError.setVisibility(View.GONE);
                }
                showError = false;
                NetLoadingDialog.getInstance().dismissDialog();
                if (clear)
                {
                    webView.clearHistory();
                    clear = false;
                }
            }
            if (NotiTag.TAG_WEB_VIEW_REFRESH.equals(tag) || NotiTag.TAG_ERROR_VIEW.equals(tag))
            {
                clear = true;
                if (BaseApplication.cookieStore != null)
                {
                    WebViewUtil.synCookies(this, url);
                }
                webView.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        webView.clearHistory();
                        webView.loadUrl(url);// 载入网页
                    }
                }, 500);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (webView.canGoBack())
        {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
