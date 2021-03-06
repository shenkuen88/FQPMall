package com.fengqipu.mall.main.acty.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.search.HistorySearchBean;
import com.fengqipu.mall.bean.search.HotSearchResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewSearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.finish_iv)
    ImageView finishIv;
    @Bind(R.id.search_tv)
    TextView searchTv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @Bind(R.id.btn_ss)
    TextView btnSs;
    @Bind(R.id.gridview_rmss)
    MyGridView gridviewRmss;
    @Bind(R.id.gridview_lsss)
    MyGridView gridviewLsss;
    @Bind(R.id.btn_qkls)
    LinearLayout btnQkls;

    private CommonAdapter<String> rmAdapter;
    private CommonAdapter<HistorySearchBean> lsAdapter;
    private List<String> rmList=new ArrayList<>();
    private List<HistorySearchBean> lsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search);
        ButterKnife.bind(this);
        searchtype=getIntent().getIntExtra("searchtype",0);
        initAll();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    etSearch.requestFocus();
                    imm.showSoftInput(etSearch, 0);
                }
            }
        }, 100);
    }

    @Override
    public void initView() {
        rmAdapter=new CommonAdapter<String>(this,rmList,R.layout.item_search_tag) {
            @Override
            public void convert(ViewHolder helper, String item) {
                TextView comment_name_tv=helper.getView(R.id.comment_name_tv);
                comment_name_tv.setText(item);
            }
        };
        gridviewRmss.setAdapter(rmAdapter);
        gridviewRmss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String) adapterView.getItemAtPosition(i);
                searchKeyWord(searchtype,item);
            }
        });
        lsAdapter=new CommonAdapter<HistorySearchBean>(this,lsList,R.layout.item_search_tag) {
            @Override
            public void convert(ViewHolder helper, HistorySearchBean item) {
                TextView comment_name_tv=helper.getView(R.id.comment_name_tv);
                comment_name_tv.setText(item.getKeyword());
            }
        };
        gridviewLsss.setAdapter(lsAdapter);
        gridviewLsss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistorySearchBean item=(HistorySearchBean)adapterView.getItemAtPosition(i);
                searchKeyWord(item.getSearchType(),item.getKeyword());
            }
        });
        gridviewLsss.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistorySearchBean item=(HistorySearchBean)adapterView.getItemAtPosition(i);
                delitem=item;
                DialogUtil.showNoTipTwoBnttonDialog(mContext
                        , "您确定要删除"+item.getKeyword()+"吗?"
                        , "取消"
                        , "确定"
                        , NotiTag.TAG_DEL_GOODS_CANCEL, "TAG_DEL_GOODS_OK_ITEM");
                return false;
            }
        });
    }
    HistorySearchBean delitem;
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                Global.delAllSearchHistory();
                initHistorySearch();
            }
            if ("TAG_DEL_GOODS_OK_ITEM".equals(tag)) {
                Log.e("sub","del="+delitem.getKeyword());
                if(delitem!=null){
                    Global.delSearchHistory(delitem.getKeyword());
                    initHistorySearch();
                }
            }
            if(NotiTag.TAG_DEL_GOODS_CANCEL.equals(tag)){
                delitem=null;
            }
        }else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(HotSearchResponse.class.getName())) {
                HotSearchResponse  hotSearchResponse= GsonHelper.toType(result, HotSearchResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(hotSearchResponse.getResultCode())) {
                        rmList.clear();
                        rmList.addAll(hotSearchResponse.getWordsList());
                        rmAdapter.setData(rmList);
                        rmAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(this, hotSearchResponse.getResultCode(), hotSearchResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }

    }
    @Override
    public void initViewData() {
        switch (searchtype){
            case 0:
                etSearch.setHint("搜您想要的企业");
                searchTv.setText("企业");
                break;
            case 1:
                etSearch.setHint("搜您想要的商铺");
                searchTv.setText("商铺");
                break;
            case 2:
                etSearch.setHint("搜您想要的商品");
                searchTv.setText("商品");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
//        SearchBean s1=new SearchBean();
//        SearchBean s2=new SearchBean();
//        SearchBean s3=new SearchBean();
//        SearchBean s4=new SearchBean();
//        rmList.add(s1);rmList.add(s2);rmList.add(s3);rmList.add(s4);
//        rmAdapter.notifyDataSetChanged();
        initHotSearch();
        initHistorySearch();
//        CommonMethod.setListViewHeightBasedOnChildren(gridviewRmss);
//        CommonMethod.setListViewHeightBasedOnChildren(gridviewLsss);
    }

    private void initHotSearch() {
        UserServiceImpl.instance().getHotKeywords((searchtype+1)+"",HotSearchResponse.class.getName());
    }

    private void initHistorySearch() {
        ArrayList<HistorySearchBean> searchHislist=Global.getSearchHistory();
        lsList.clear();
        lsList.addAll(searchHislist);
        lsAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
        searchTv.setOnClickListener(this);
        finishIv.setOnClickListener(this);
        ivSearchClear.setOnClickListener(this);
        btnSs.setOnClickListener(this);
        btnQkls.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyWord(searchtype,etSearch.getText().toString());
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    ivSearchClear.setVisibility(View.VISIBLE);
                }else{
                    ivSearchClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void searchKeyWord(int searchtype,String keyword){
        if (GeneralUtils.isNotNullOrZeroLenght(keyword)){
            Global.addSearchHistory(searchtype,keyword);
            if(searchtype==2){
                Intent intent = new Intent(mContext, SearchGoodsActivity.class);
                intent.putExtra(IntentCode.SEARCH_KEYORD,keyword);
                startActivity(intent);
            }else if(searchtype==1){
                Intent intent = new Intent(mContext, SearchShopsActivity.class);
                intent.putExtra(IntentCode.SEARCH_KEYORD,keyword);
                intent.putExtra("SearchType",searchtype);
                startActivity(intent);
            }else{
                Intent intent = new Intent(mContext, SearchShopsActivity.class);
                intent.putExtra(IntentCode.SEARCH_KEYORD,keyword);
                intent.putExtra("SearchType",searchtype);
                startActivity(intent);
            }
        }else {
            ToastUtil.makeText(mContext,"请输入搜索内容");
        }
    }
    int searchtype=0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_qkls:
                DialogUtil.showNoTipTwoBnttonDialog(mContext
                        , "您确定要清空历史记录吗?"
                        , "取消"
                        , "确定"
                        , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
                break;
            case R.id.btn_ss:
                searchKeyWord(searchtype,etSearch.getText().toString());
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;
            case R.id.finish_iv:
                finish();
                break;
            case R.id.search_tv:
                //弹出框
                showSSPW(searchTv);
                break;
            case R.id.pw_enterprise_tv:
                etSearch.setHint("搜您想要的企业");
                searchTv.setText("企业");
                searchtype=0;
                popupWindow.dismiss();
                initHotSearch();
                break;
            case R.id.pw_shop_tv:
                etSearch.setHint("搜您想要的商铺");
                searchTv.setText("商铺");
                searchtype=1;
                popupWindow.dismiss();
                initHotSearch();
                break;
            case R.id.pw_goods_tv:
                etSearch.setHint("搜您想要的商品");
                searchTv.setText("商品");
                searchtype=2;
                popupWindow.dismiss();
                initHotSearch();
                break;
        }
    }
    private PopupWindow popupWindow;

    private void showSSPW(TextView view) {
        // 一个自定义的布局，作为显示的内容
        if (popupWindow == null) {
            View contentView = LayoutInflater.from(mContext).inflate(
                    R.layout.popupwindow_ss, null);
            TextView pw_enterprise_tv = (TextView) contentView.findViewById(R.id.pw_enterprise_tv);
            TextView pw_shop_tv = (TextView) contentView.findViewById(R.id.pw_shop_tv);
            TextView pw_goods_tv = (TextView) contentView.findViewById(R.id.pw_goods_tv);
            pw_enterprise_tv.setOnClickListener(this);
            pw_shop_tv.setOnClickListener(this);
            pw_goods_tv.setOnClickListener(this);

            popupWindow = new PopupWindow(contentView,
                    (int) getResources().getDimension(R.dimen.pxtodp220), (int) getResources().getDimension(R.dimen.pxtodp208), true);

            popupWindow.setTouchable(true);

            popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.app_white));
        }
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
}
