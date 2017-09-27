package com.fengqipu.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fengqipu.mall.R;


public class PWebView extends LinearLayout
{

    private final static int BAR_HEIGHT = 5;

    private ProgressBar mProgressBar;

    private WebView mWebView;

    private String mLoadUrl;

    private View mControllerView;

//    private ImageButton mGoBackBtn;
//    private ImageButton mGoForwardBtn;

    public PWebView(Context context) {
        this(context, null);
    }

    public PWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        initProgressBar();

        initWebView();

        initControllerView();
    }

    private void initProgressBar() {
        mProgressBar = (ProgressBar) LayoutInflater.from(getContext()).inflate(
                R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                        BAR_HEIGHT, getResources().getDisplayMetrics()));
    }

    private void initWebView() {
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);

        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        addView(mWebView, lps);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadUrl = url;
//                mGoBackBtn.setEnabled(mWebView.canGoBack());
//                mGoForwardBtn.setEnabled(mWebView.canGoForward());
            }

        });
    }


    private void initControllerView() {
//        mControllerView = LayoutInflater.from(getContext()).inflate(
//                R.layout.browser_controller, null);
//        mGoBackBtn = (ImageButton) mControllerView.findViewById(R.id.controller_back);
//        mGoForwardBtn = (ImageButton) mControllerView.findViewById(R.id.controller_forward);
//        ImageButton goBrowserBtn = (ImageButton) mControllerView.findViewById(R.id.controller_go);
//        ImageButton refreshBtn = (ImageButton) mControllerView.findViewById(R.id.controller_refresh);
//
//        mGoBackBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (canGoBack()) {
//                    goBack();
//                }
//            }
//        });
//
//        mGoForwardBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (canGoForward()) {
//                    goForward();
//                }
//            }
//        });
//
//        refreshBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                loadUrl(mLoadUrl);
//            }
//        });
//
//        goBrowserBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(mLoadUrl)) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(mLoadUrl));
//                    getContext().startActivity(intent);
//                }
//            }
//        });
//
//        addView(mControllerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public boolean canGoBack() {
        return null != mWebView && mWebView.canGoBack();
    }

    public boolean canGoForward() {
        return null != mWebView && mWebView.canGoForward();
    }

    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public void hideBrowserController() {
        mControllerView.setVisibility(View.GONE);
    }

    public void showBrowserController() {
        mControllerView.setVisibility(View.VISIBLE);
    }
}
