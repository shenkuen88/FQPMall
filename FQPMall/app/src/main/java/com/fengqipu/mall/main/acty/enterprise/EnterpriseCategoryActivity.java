package com.fengqipu.mall.main.acty.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.category.Category;
import com.fengqipu.mall.bean.category.CategoryResponse;
import com.fengqipu.mall.bean.category.LeftBean;
import com.fengqipu.mall.bean.category.RightBean;
import com.fengqipu.mall.bean.search.SearchGoodsResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.acty.index.zfb.NoticeListActivity;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.acty.search.NewSearchActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MyGridView;
import com.fengqipu.mall.view.MyScrollView1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EnterpriseCategoryActivity extends BaseActivity {

    @Bind(R.id.index_banner)
    ImageView indexBanner;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ed_ss)
    TextView edSs;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.btn_info)
    ImageView btnInfo;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.left_list)
    ListView leftList;
    @Bind(R.id.title_grid_view)
    MyGridView titleGridView;
    @Bind(R.id.my_grid_view)
    MyGridView myGridView;
    @Bind(R.id.my_scrollview)
    MyScrollView1 myScrollview;
    private String categorytype = "2";//1.企业 2.商铺
    private String leftSelId = "";
    private String sonLeftSelId = "";
    private String rightTileId = "";

    private CommonAdapter<LeftBean> leftAdapter;
    private List<LeftBean> lList = new ArrayList<LeftBean>();
    private List<RightBean> rList = new ArrayList<RightBean>();
    private CommonAdapter<SearchGoodsResponse.ContentListBean> gAdapter;
    private List<SearchGoodsResponse.ContentListBean> goodsList = new ArrayList<>();
    private List<CategoryResponse.ContentType> titleNames = new ArrayList<>();
    private CommonAdapter<CategoryResponse.ContentType> titleAdapter;
    private List<CategoryResponse.ContentType> rightLists = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 10;
    private boolean isloading = false;
    int totalCount = 0;
    private String contentType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_category);
        ButterKnife.bind(this);
        categorytype = getIntent().getStringExtra("categorytype");
        initAll();
    }

    @Override
    public void initView() {
        if (categorytype.equals("1")) {
            edSs.setHint("搜你想要的企业");
        } else {
            edSs.setHint("搜你想要的商铺");
        }


    }

    @Override
    public void initViewData() {
        titleAdapter = new CommonAdapter<CategoryResponse.ContentType>(this, titleNames, R.layout.new_fen_lei_right_title) {
            @Override
            public void convert(ViewHolder helper, CategoryResponse.ContentType item) {
                LinearLayout ll_name = helper.getView(R.id.ll_name);
                TextView name = helper.getView(R.id.name);
                name.setText(item.getTypeName());
                if (rightTileId.equals("")) {
                    if (helper.getPosition() == 0) {
                        rightTileId=item.getId();
                        contentType="";
                        pageNum=1;
                        initBtmList();
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        ll_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_rec_4));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        ll_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_rec_5));
                    }
                } else {
                    if (item.getId().equals(rightTileId)) {
                        contentType=item.getId();
                        pageNum=1;
                        initBtmList();
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        ll_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_rec_4));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        ll_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_rec_5));
                    }
                }

            }
        };
        titleGridView.setAdapter(titleAdapter);
        titleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryResponse.ContentType item = (CategoryResponse.ContentType) adapterView.getItemAtPosition(i);
                rightTileId = item.getId();
                titleAdapter.notifyDataSetChanged();

            }
        });
        gAdapter = new CommonAdapter<SearchGoodsResponse.ContentListBean>(this, goodsList, R.layout.index_btm_grid2) {
            @Override
            public void convert(ViewHolder helper, SearchGoodsResponse.ContentListBean item) {
                ImageView img = helper.getView(R.id.img);
                TextView title = helper.getView(R.id.title);
                TextView location = helper.getView(R.id.location);
                TextView xl = helper.getView(R.id.xl);
                TextView price = helper.getView(R.id.price);
//                TextView hpd=helper.getView(R.id.hpd);
                if (item.getPicUrl1RequestUrl() != null && !item.getPicUrl1RequestUrl().equals("")) {
                    GeneralUtils.setImageViewWithUrl(EnterpriseCategoryActivity.this, item.getPicUrl1RequestUrl(), img, R.drawable.bg_image_classification);
                }
                title.setText("" + item.getContentName());
//                location.setText("" + item.getShopProvince() + " " + item.getShopCity());
                location.setVisibility(View.GONE);
                if (item.getSales() != null && !item.getSales().equals("")) {
                    xl.setText("" + item.getMonthSales() + "人已付款");
                } else {
                    xl.setText("0人已付款");
                }
                price.setText("￥" + item.getPrice());
            }
        };
        myGridView.setAdapter(gAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchGoodsResponse.ContentListBean item = (SearchGoodsResponse.ContentListBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(EnterpriseCategoryActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentID", item.getId());
                startActivity(intent);
            }
        });

        leftAdapter = new CommonAdapter<LeftBean>(this, lList, R.layout.new_fen_lei_left) {
            @Override
            public void convert(ViewHolder helper, LeftBean item) {
                View sel_line = helper.getView(R.id.sel_line);
                ImageView icon_iv = helper.getView(R.id.icon_iv);
                TextView name = helper.getView(R.id.name);
                ListView my_list_view = helper.getView(R.id.my_list_view);
                getSonData();
                final CommonAdapter sonAdapter = new CommonAdapter<RightBean>(EnterpriseCategoryActivity.this, rList, R.layout.new_fen_lei_left_son) {
                    @Override
                    public void convert(ViewHolder helper, RightBean item) {
                        TextView name = helper.getView(R.id.name);
                        name.setText(item.getName());
                        if (sonLeftSelId.equals("")) {
                            if (helper.getPosition() == 0) {
                                sonLeftSelId = item.getId();
                                name.setTextColor(getResources().getColor(R.color.app_color));
                            } else {
                                name.setTextColor(getResources().getColor(R.color.txt_col));
                            }
                        } else {
                            if (item.getId().equals(sonLeftSelId)) {
                                name.setTextColor(getResources().getColor(R.color.app_color));
                            } else {
                                name.setTextColor(getResources().getColor(R.color.txt_col));
                            }
                        }
                        getRightData();
                        titleNames.clear();
                        for (CategoryResponse.ContentType it : rightLists) {
                            titleNames.add(it);
                        }
                        titleAdapter.setData(titleNames);
                        titleAdapter.notifyDataSetChanged();
                    }
                };
                if (leftSelId.equals("")) {
                    if (helper.getPosition() == 0) {
                        leftSelId = item.getId();
                        sel_line.setVisibility(View.VISIBLE);
                        icon_iv.setImageResource(R.mipmap.i_up);
                        my_list_view.setAdapter(sonAdapter);
                    } else {
                        sel_line.setVisibility(View.GONE);
                        icon_iv.setImageResource(R.mipmap.i_down);
                        my_list_view.setAdapter(null);
                    }
                } else {
                    if (item.getId().equals(leftSelId)) {
                        sel_line.setVisibility(View.VISIBLE);
                        icon_iv.setImageResource(R.mipmap.i_up);
                        my_list_view.setAdapter(sonAdapter);
                    } else {
                        sel_line.setVisibility(View.GONE);
                        icon_iv.setImageResource(R.mipmap.i_down);
                        my_list_view.setAdapter(null);
                    }
                }
                name.setText(item.getName());
                my_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        RightBean item = (RightBean) adapterView.getItemAtPosition(i);
                        sonLeftSelId = item.getId();
                        sonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        leftList.setAdapter(leftAdapter);
        getLeftData();
        myScrollview.setScrollViewListener(new MyScrollView1.IScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                if (isloading) return;
                if (pageNum * pageSize >= totalCount) return;
                isloading = true;
                pageNum = pageNum + 1;
                initBtmList();
            }

            @Override
            public void onScrolledToTop() {

            }
        });

    }
    private void initBtmList() {
        if(contentType.equals("-1")){
            contentType="";
        }
        UserServiceImpl.instance().getSearchGList(categorytype,contentType,sonLeftSelId, pageNum, pageSize, SearchGoodsResponse.class.getName());
    }

    @Override
    public void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterpriseCategoryActivity.this, NewSearchActivity.class);
                if (categorytype.equals("1")) {
                    intent.putExtra("searchtype", 0);
                } else {
                    intent.putExtra("searchtype", 1);
                }
                startActivity(intent);
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GeneralUtils.isLogin()){
                    startActivity(new Intent(mContext, NoticeListActivity.class));
                }else {
                    startActivity(new Intent(mContext,LoginActy.class));
                }
            }
        });
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LeftBean left = (LeftBean) parent.getItemAtPosition(position);
                t_position = position;
                leftSelId = left.getId();
                cid = left.getId();
                leftAdapter.notifyDataSetChanged();
                String img = left.getPic();
                final String openType = left.getOpenType();
                final String sid = left.getSid();
                final String cid = left.getCid();
                final String link = left.getLink();
                final String name = left.getName();
                try {
                    if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                        GeneralUtils.setImageViewWithUrl(EnterpriseCategoryActivity.this, img, indexBanner, R.drawable.bg_banner_classification);
                        indexBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (openType) {
                                    case "1":
                                        try {
                                            Intent intent = new Intent(EnterpriseCategoryActivity.this, EnterpriseNewActivity.class);
                                            intent.putExtra("sid", sid);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "2":
                                        try {
                                            Intent intent = new Intent(EnterpriseCategoryActivity.this, GoodsDetailActivity.class);
                                            intent.putExtra("contentID", cid);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "3":
                                    case "4":
                                        try {
                                            Intent intentExplain = new Intent(EnterpriseCategoryActivity.this, CommonWebViewActivity.class);
                                            intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, name);
                                            intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, link);
                                            startActivity(intentExplain);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSonData() {
        rList.clear();
        if (categoryResponse.getSubCategoryListMap().get(cid) != null) {
            for (int i = 0; i < categoryResponse.getSubCategoryListMap().get(cid).size(); i++) {
                List<RightBean.SubRightBean> relist = new ArrayList<RightBean.SubRightBean>();
                Category item = categoryResponse.getSubCategoryListMap().get(cid).get(i);
                if (item != null) {
                    List<CategoryResponse.Shop> subitems = categoryResponse.getShopListMap().get(item.getId());
                    if (subitems != null) {
                        for (CategoryResponse.Shop sitem : subitems) {
                            relist.add(new RightBean.SubRightBean(sitem.getId(), sitem.getShopName(), sitem.getCategory2(), sitem.getPicUrlRequestUrl(), sitem.getAdvPicUrlList()));
                        }
                    }
                    rList.add(new RightBean(item.getId(), item.getCategoryName(), "", relist));
                }
            }
        }
    }

    private void getRightData() {
        rightLists.clear();
        CategoryResponse.ContentType item=new CategoryResponse.ContentType();
        item.setId("-1");
        item.setTypeName("全部");
        rightLists.add(item);
        try {
            if (categoryResponse.getContentTypeListMap().get(sonLeftSelId) != null) {
                rightLists.addAll(categoryResponse.getContentTypeListMap().get(sonLeftSelId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLeftData() {
        NetLoadingDialog.getInstance().loading(EnterpriseCategoryActivity.this);
        UserServiceImpl.instance().getCategoryList(this, "1", CategoryResponse.class.getName());
    }

    private CategoryResponse categoryResponse;
    private int t_position = 0;
    private String cid = "";

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(CategoryResponse.class.getName())) {
                if (cache.equals(NetResponseEvent.Cache.isCache)) {
                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
                    //网络数据(一般不用去做处理)
                }
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    Log.e("HTTP", result);
                    categoryResponse = GsonHelper.toType(result, CategoryResponse.class);
                    if (Constants.SUCESS_CODE.equals(categoryResponse.getResultCode())) {
                        List<LeftBean> tolLeftList = new ArrayList<LeftBean>();
                        for (Category item : categoryResponse.getParentCategoryList()) {
                            LeftBean leftBean = new LeftBean("", item.getId(), item.getCategoryName(), item.getCoverRequestUrl(), item.getLink(), item.getOpenType() + "", item.getToShopID() + "", item.getToContentID() + "");
                            tolLeftList.add(leftBean);
                        }
                        leftAdapter.setData(tolLeftList);
                        if (GeneralUtils.isNotNullOrZeroSize(tolLeftList)) {
                            cid = tolLeftList.get(t_position).getId();
                            String img = tolLeftList.get(t_position).getPic();
                            final String openType = tolLeftList.get(t_position).getOpenType();
                            final String sid = tolLeftList.get(t_position).getSid();
                            final String cid = tolLeftList.get(t_position).getCid();
                            final String link = tolLeftList.get(t_position).getLink();
                            final String name = tolLeftList.get(t_position).getName();
                            try {
                                if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                                    GeneralUtils.setImageViewWithUrl(EnterpriseCategoryActivity.this, img, indexBanner, R.drawable.bg_banner_classification);
                                    indexBanner.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            switch (openType) {
                                                case "1":
                                                    try {
                                                        Intent intent = new Intent(EnterpriseCategoryActivity.this, EnterpriseNewActivity.class);
                                                        intent.putExtra("sid", sid);
                                                        startActivity(intent);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "2":
                                                    try {
                                                        Intent intent = new Intent(EnterpriseCategoryActivity.this, GoodsDetailActivity.class);
                                                        intent.putExtra("contentID", cid);
                                                        startActivity(intent);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "3":
                                                case "4":
                                                    try {
                                                        Intent intentExplain = new Intent(EnterpriseCategoryActivity.this, CommonWebViewActivity.class);
                                                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, name);
                                                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, link);
                                                        startActivity(intentExplain);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        leftAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(this, categoryResponse.getResultCode(), categoryResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(SearchGoodsResponse.class.getName())) {
                SearchGoodsResponse searchGoodsResponse = GsonHelper.toType(result, SearchGoodsResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(searchGoodsResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        isloading = false;
                        totalCount = searchGoodsResponse.getTotalCount();
                        if (searchGoodsResponse.getContentList() != null && searchGoodsResponse.getContentList().size() > 0) {
                            goodsList.addAll(searchGoodsResponse.getContentList());
                        }
                        gAdapter.setData(goodsList);
                        gAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(this, searchGoodsResponse.getResultCode(), searchGoodsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }
}
