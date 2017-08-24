package com.fengqipu.mall.main.acty.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.ConversationListActivity;
import com.fengqipu.mall.main.acty.index.SearchActy;
import com.fengqipu.mall.main.acty.search.NewSearchActivity;
import com.fengqipu.mall.main.acty.search.SearchShopsActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.CommonWebViewActivity;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.fengqipu.mall.R.id.left_list;
import static com.fengqipu.mall.R.id.right_list;
import static com.fengqipu.mall.R.id.search_layout;

public class EnterpriseListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.topView)
    View topView;
    @Bind(R.id.ed_ss)
    TextView edSs;
    @Bind(search_layout)
    LinearLayout searchLayout;
    @Bind(left_list)
    ListView leftList;
    @Bind(right_list)
    ListView rightList;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    View headView;
    ImageView ivBanner;
    @Bind(R.id.btn_info)
    ImageView btnInfo;

    private CommonAdapter<LeftBean> leftAdapter;
    private CommonAdapter<RightBean> rightAdapter;
    private List<LeftBean> lList = new ArrayList<LeftBean>();
    private List<RightBean> rList = new ArrayList<RightBean>();
    private String leftSelId = "";
    private int t_position = 0;
    private String categorytype = "2";//1.企业 2.商铺

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_list);
        ButterKnife.bind(this);
        categorytype = getIntent().getStringExtra("categorytype");
        initAll();
        getLeftData();
    }

    @Override
    public void initView() {
        if (categorytype.equals("2")) {
            edSs.setHint("搜你想要的企业");
        } else {
            edSs.setHint("搜你想要的商铺");
        }
        headView = getLayoutInflater().inflate(R.layout.header, null);
        ivBanner = V.f(headView, R.id.iv_banner);
    }

    @Override
    public void initViewData() {
        leftAdapter = new CommonAdapter<LeftBean>(this, lList, R.layout.fen_lei_left) {
            @Override
            public void convert(ViewHolder helper, LeftBean item) {
                TextView title = helper.getView(R.id.title);
                if (!item.getTitle().equals("")) {
                    title.setVisibility(View.VISIBLE);
                    title.setText(item.getTitle());
                } else {
                    title.setVisibility(View.GONE);
                }
                TextView name = helper.getView(R.id.name);
                if (leftSelId.equals("")) {
                    if (helper.getPosition() == 0) {
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.white_color));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.bg_col));
                    }
                } else {
                    if (item.getId().equals(leftSelId)) {
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.white_color));
                    } else {
                        name.setTextColor(getResources().getColor(R.color.txt_col));
                        helper.getView(R.id.txt_bg).setBackgroundColor(getResources().getColor(R.color.bg_col));
                    }
                }
                name.setText(item.getName());
            }
        };
        leftList.setAdapter(leftAdapter);
//        getLeftData();
        rightAdapter = new CommonAdapter<RightBean>(EnterpriseListActivity.this, rList, R.layout.fen_lei_right2) {
            @Override
            public void convert(ViewHolder helper, final RightBean item) {
                helper.setText(R.id.title, item.getName());
                ListView listView = helper.getView(R.id.my_list_view);
                CommonAdapter<RightBean.SubRightBean> gAdapter
                        = new CommonAdapter<RightBean.SubRightBean>(EnterpriseListActivity.this, item.getList(), R.layout.item_shops) {
                    @Override
                    public void convert(ViewHolder helper, final RightBean.SubRightBean item) {
                        helper.setText(R.id.comment_name_tv, item.getName());
                        helper.setText(R.id.comment_content_tv, item.getInfo());
                        ImageView comment_head_iv = helper.getView(R.id.comment_head_iv);
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getPic())) {
                            GeneralUtils.setImageViewWithUrl(EnterpriseListActivity.this, item.getPic(),
                                    comment_head_iv,
                                    R.drawable.bg_image_classification);
                        }
                        GridView gridView = helper.getView(R.id.my_grid_view);
                        CommonAdapter<String> gadapter = new CommonAdapter<String>(EnterpriseListActivity.this, item.getPics(), R.layout.item_pic) {
                            @Override
                            public void convert(ViewHolder helper, String item) {
                                ImageView iv_pic = helper.getView(R.id.iv_pic);
                                if (GeneralUtils.isNotNullOrZeroLenght(item)) {
                                    GeneralUtils.setImageViewWithUrl(EnterpriseListActivity.this, item,
                                            iv_pic,
                                            R.drawable.bg_image_classification);
                                }
                            }
                        };
                        gridView.setAdapter(gadapter);
                        CommonMethod.setListViewHeightBasedOnChildren(gridView);
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(EnterpriseListActivity.this, EnterpriseActivity.class);
                                intent.putExtra("sid", item.getId());
                                startActivity(intent);
                            }
                        });
                    }
                };
                listView.setAdapter(gAdapter);
                CommonMethod.setListViewHeightBasedOnChildren(listView);
                TextView btn_more = helper.getView(R.id.btn_more);
                btn_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EnterpriseListActivity.this, SearchShopsActivity.class);
                        intent.putExtra(IntentCode.SEARCH_KEYORD, item.getName());
                        intent.putExtra("SearchType", (Integer.valueOf(categorytype)-1));
                        EnterpriseListActivity.this.startActivity(intent);
                    }
                });
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        RightBean.SubRightBean subRightBean = (RightBean.SubRightBean) parent.getItemAtPosition(position);
//                        Intent intent = new Intent(EnterpriseListActivity.this, EnterpriseActivity.class);
//                        intent.putExtra("sid", subRightBean.getId());
//                        startActivity(intent);
//
//                    }
//                });
            }
        };
        rightList.addHeaderView(headView);
        rightList.setAdapter(rightAdapter);
    }

    @Override
    public void initEvent() {
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterpriseListActivity.this, NewSearchActivity.class);
                if (categorytype.equals("2")) {
                    intent.putExtra("searchtype", 0);
                } else {
                    intent.putExtra("searchtype", 1);
                }
                startActivity(intent);
            }
        });
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LeftBean left = (LeftBean) parent.getItemAtPosition(position);
                t_position = position;
                leftSelId = left.getId();
                leftAdapter.notifyDataSetChanged();
                cid = left.getId();
                String img = left.getPic();
                try {
                    if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                        GeneralUtils.setImageViewWithUrl(EnterpriseListActivity.this, img, ivBanner, R.drawable.bg_banner_classification);
//                        ivBanner.setVisibility(View.VISIBLE);
//                        headView.setVisibility(View.VISIBLE);
                        rightList.removeHeaderView(headView);
                        rightList.addHeaderView(headView);
                        ivBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (left.getLink() != null && !left.getLink().equals("")) {
                                    Intent intentExplain = new Intent(EnterpriseListActivity.this, CommonWebViewActivity.class);
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, left.getName());
                                    intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, left.getLink());
                                    startActivity(intentExplain);
                                }
                            }
                        });
                    } else {
//                        ivBanner.setVisibility(View.GONE);
//                        headView.setVisibility(View.GONE);
                        rightList.removeHeaderView(headView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getRightData();
            }
        });
        ivBack.setOnClickListener(this);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnterpriseListActivity.this, ConversationListActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_layout:
                startActivity(new Intent(EnterpriseListActivity.this, SearchActy.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private String cid = "";

    private void getRightData() {
//        NetLoadingDialog.getInstance().loading(mainActivity);
//        UserServiceImpl.instance().getCategoryRight(mainActivity,"1", cid, CategoryRightResponse.class.getName());
        rList.clear();
        if (categoryResponse.getSubCategoryListMap().get(cid) != null) {
            for (int i = 0; i < categoryResponse.getSubCategoryListMap().get(cid).size(); i++) {
                List<RightBean.SubRightBean> relist = new ArrayList<RightBean.SubRightBean>();
                Category item = categoryResponse.getSubCategoryListMap().get(cid).get(i);
                if (item != null) {
                    List<CategoryResponse.Shop> subitems = categoryResponse.getShopListMap().get(item.getId());
                    if (subitems != null) {
                        for (CategoryResponse.Shop sitem : subitems) {
                            relist.add(new RightBean.SubRightBean(sitem.getId(), sitem.getShopName(), sitem.getDescription(), sitem.getPicUrlRequestUrl(), sitem.getAdvPicUrlList()));
                        }
                    }
                    rList.add(new RightBean(item.getId(), item.getCategoryName(), "", relist));
                }
            }
        }
        rightAdapter.setData(rList);
        rightAdapter.notifyDataSetChanged();
    }

    private void getLeftData() {
        UserServiceImpl.instance().getCategoryList(this, categorytype, CategoryResponse.class.getName());
    }

    private CategoryResponse categoryResponse;

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
                            LeftBean leftBean = new LeftBean("", item.getId(), item.getCategoryName(), item.getCoverRequestUrl(), item.getLink());
                            tolLeftList.add(leftBean);
                        }
                        leftAdapter.setData(tolLeftList);
                        leftAdapter.notifyDataSetChanged();
                        if (GeneralUtils.isNotNullOrZeroSize(tolLeftList)) {
                            cid = tolLeftList.get(t_position).getId();
                            getRightData();
                            String img = tolLeftList.get(t_position).getPic();
                            try {
                                if (GeneralUtils.isNotNullOrZeroLenght(img)) {
                                    GeneralUtils.setImageViewWithUrl(this, img, ivBanner, R.drawable.bg_banner_classification);
//                                    ivBanner.setVisibility(View.VISIBLE);
//                                    headView.setVisibility(View.VISIBLE);
                                    rightList.removeHeaderView(headView);
                                    rightList.addHeaderView(headView);
                                } else {
//                                    ivBanner.setVisibility(View.GONE);
//                                    headView.setVisibility(View.GONE);
                                    rightList.removeHeaderView(headView);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ErrorCode.doCode(this, categoryResponse.getResultCode(), categoryResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }
    }


}
