package com.fengqipu.mall.main.acty.search;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.cart.GoodsBean;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.fengqipu.mall.R.id.btn_sx;

public class SearchShopsActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.finish_iv)
    ImageView finishIv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @Bind(R.id.btn_ss)
    TextView btnSs;
    @Bind(R.id.btn_zh)
    TextView btnZh;
    @Bind(R.id.btn_xl)
    TextView btnXl;
    @Bind(R.id.btn_xy)
    TextView btnXy;
    @Bind(R.id.btn_sx)
    TextView btnSx;
    @Bind(R.id.my_listview)
    RefreshListView myListview;
    private CommonAdapter<GoodsBean> lAdapter;
    private List<GoodsBean> goodsList = new ArrayList<>();
    String keyword = "";
    int searchType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shops);
        ButterKnife.bind(this);
        keyword = getIntent().getStringExtra(IntentCode.SEARCH_KEYORD);
        searchType = getIntent().getIntExtra("SearchType", 0);
        initAll();
    }

    @Override
    public void initView() {

    }

    private void initData() {
        initBtmList();
    }

    private void initBtmList() {
//        myLoading.setVisibility(View.GONE);
        myListview.loadComplete();
        GoodsBean g1 = new GoodsBean();
        GoodsBean g2 = new GoodsBean();
        GoodsBean g3 = new GoodsBean();
        GoodsBean g4 = new GoodsBean();
        GoodsBean g5 = new GoodsBean();
        goodsList.add(g1);
        goodsList.add(g2);
        goodsList.add(g3);
        goodsList.add(g4);
        goodsList.add(g5);
        lAdapter.notifyDataSetChanged();
    }

    @Override
    public void initViewData() {
        lAdapter = new CommonAdapter<GoodsBean>(this, goodsList, R.layout.item_shops) {
            @Override
            public void convert(ViewHolder helper, GoodsBean item) {
                helper.getView(R.id.btn_jd).setVisibility(View.VISIBLE);
                List<String> pics = new ArrayList<>();
                pics.add("");
                pics.add("");
                pics.add("");
                CommonAdapter adapter = new CommonAdapter(SearchShopsActivity.this, pics, R.layout.item_pic) {
                    @Override
                    public void convert(ViewHolder helper, Object item) {

                    }
                };
                GridView myGridView = helper.getView(R.id.my_grid_view);
                myGridView.setAdapter(adapter);
                CommonMethod.setListViewHeightBasedOnChildren(myGridView);
            }
        };
        myListview.setAdapter(lAdapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        if (keyword != null) {
            etSearch.setText(keyword);
            ivSearchClear.setVisibility(View.VISIBLE);
        }
        if (searchType == 0) {
            etSearch.setHint("搜您想要的企业");
        } else {
            etSearch.setHint("搜您想要的商铺");
        }
        initData();
    }

    @Override
    public void initEvent() {
        btnZh.setOnClickListener(this);
        btnXl.setOnClickListener(this);
        btnXy.setOnClickListener(this);
        btnSx.setOnClickListener(this);
        finishIv.setOnClickListener(this);
        ivSearchClear.setOnClickListener(this);
        btnSs.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyWord();
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
                if (!charSequence.toString().equals("")) {
                    ivSearchClear.setVisibility(View.VISIBLE);
                } else {
                    ivSearchClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchKeyWord() {
        if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString())) {
        } else {
            ToastUtil.makeText(mContext, "请输入搜索内容");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_iv:
                finish();
                break;
            case R.id.btn_ss:
                searchKeyWord();
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;
            case R.id.btn_zh:
                initTopBtn();
                btnZh.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw3 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw3.setBounds(0, 0, arraw3.getMinimumWidth(), arraw3.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw3, null);
                break;
            case R.id.btn_xl:
                initTopBtn();
                btnXl.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw2 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw2.setBounds(0, 0, arraw2.getMinimumWidth(), arraw2.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw2, null);
                break;
            case R.id.btn_xy:
                initTopBtn();
                btnXy.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw1 = getResources().getDrawable(R.mipmap.icon_arrow_down);
                arraw1.setBounds(0, 0, arraw1.getMinimumWidth(), arraw1.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw1, null);
                break;
            case btn_sx://筛选
                initTopBtn();
                btnSx.setTextColor(getResources().getColor(R.color.app_color));
                Drawable arraw = getResources().getDrawable(R.mipmap.icon_arrow_red);
                arraw.setBounds(0, 0, arraw.getMinimumWidth(), arraw.getMinimumHeight());
                btnSx.setCompoundDrawables(null, null, arraw, null);
                break;
        }
    }

    private void initTopBtn() {
        btnZh.setTextColor(Color.parseColor("#4A4A4A"));
        btnXl.setTextColor(Color.parseColor("#4A4A4A"));
        btnXy.setTextColor(Color.parseColor("#4A4A4A"));
        btnSx.setTextColor(Color.parseColor("#4A4A4A"));
    }
}
