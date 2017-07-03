package com.fengqipu.mall.main.acty.index;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.HotWordsResponse;
import com.fengqipu.mall.bean.index.SearchResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.XCFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面
 */
public class SearchActy extends BaseActivity implements View.OnClickListener {

    private ImageView ivClearSearch;
    private EditText etSearch;
    private XCFlowLayout flowLayout;
    //关闭页面
    private ImageView ivFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initTitle();
        initAll();
        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.SEARCH_HOT_WORDS, ""))) {
            HotWordsResponse mEditUserInfoResponse = GsonHelper.toType(SharePref.getString(Constants.SEARCH_HOT_WORDS, ""), HotWordsResponse.class);
            List<String> hotList = mEditUserInfoResponse.getWordList();
            initChildViews((String[]) hotList.toArray(new String[hotList.size()]));
        }
    }

    private void initChildViews(String hotWords[]) {
        flowLayout.removeAllViews();
        final List<TextView> tvList = new ArrayList<>();

        MarginLayoutParams lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < hotWords.length; i++) {
            final TextView view = new TextView(this);
            if (GeneralUtils.isNotNullOrZeroLenght(hotWords[i])) {
                view.setText(hotWords[i]);
                view.setTextColor(Color.GRAY);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
                tvList.add(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearOther(tvList);
                        view.setTextColor(getResources().getColor(R.color.app_color));
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_pink_bg));
                        Intent intent = new Intent(mContext, com.fengqipu.mall.main.acty.index.baby.SearchActy.class);
                        intent.putExtra(IntentCode.SEARCH_KEYORD, view.getText().toString());
                        startActivity(intent);
                    }
                });
                flowLayout.addView(view, lp);
            }
        }

    }

    private void clearOther(List<TextView> tvList) {
        for (int i = 0; i < tvList.size(); i++) {
            tvList.get(i).setTextColor(Color.GRAY);
            tvList.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
        }
    }


    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
            } else if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag)) {//修改成功相当于登录后修改消息
                initViewData();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(HotWordsResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    HotWordsResponse mEditUserInfoResponse = GsonHelper.toType(result, HotWordsResponse.class);
                    if (Constants.SUCESS_CODE.equals(mEditUserInfoResponse.getResultCode())) {
                        List<String> hotList = mEditUserInfoResponse.getWordList();
                        if (hotList != null && hotList.size() != 0) {
                            SharePref.saveString(Constants.SEARCH_HOT_WORDS, result);
                            initChildViews((String[]) hotList.toArray(new String[hotList.size()]));
                        }
                    } else {
                        ErrorCode.doCode(mContext, mEditUserInfoResponse.getResultCode(), mEditUserInfoResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(SearchResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    SearchResponse mSearchResponse = GsonHelper.toType(result, SearchResponse.class);
                    if (Constants.SUCESS_CODE.equals(mSearchResponse.getResultCode())) {

                    } else {
                        ErrorCode.doCode(mContext, mSearchResponse.getResultCode(), mSearchResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


    private void initTitle() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;

            case R.id.finish_iv:
                finish();
                break;

        }
    }

    @Override
    public void initView() {
        etSearch = (EditText) findViewById(R.id.et_search);
        ivClearSearch = (ImageView) findViewById(R.id.iv_search_clear);
        ivFinish = (ImageView) findViewById(R.id.finish_iv);
        ivFinish.setOnClickListener(this);
        flowLayout = (XCFlowLayout) findViewById(R.id.search_flowlayout);
        ivClearSearch.setOnClickListener(this);
//        UserServiceImpl.instance().search(etSearch.getText().toString(), 1, SearchResponse.class.getName());
        etSearch.setOnEditorActionListener(new OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                 if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString())){
                     Intent intent = new Intent(mContext, com.fengqipu.mall.main.acty.index.baby.SearchActy.class);
                     intent.putExtra(IntentCode.SEARCH_KEYORD,etSearch.getText().toString());
                     startActivity(intent);
                 }else {
                     ToastUtil.makeText(mContext,"请输入搜索内容");
                 }
                }
                return false;
            }

        });
    }

    @Override
    public void initViewData() {
        UserServiceImpl.instance().getHotWords(mContext,
                HotWordsResponse.class.getName());
    }

    @Override
    public void initEvent() {

    }


}
