package com.fengqipu.mall.main.acty.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.category.CategoryResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class SearchCategoryActivity extends BaseActivity {

    @Bind(R.id.my_listview)
    ListView myListview;
    private CommonAdapter<CategoryResponse.Category> mAdapter;
    private List<CategoryResponse.Category> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        ButterKnife.bind(this);
        initAll();
    }

    private String selid = "";

    @Override
    public void initView() {
        initTitle();
        mAdapter = new CommonAdapter<CategoryResponse.Category>(SearchCategoryActivity.this, mlist, R.layout.item_search_category) {
            @Override
            public void convert(ViewHolder helper, CategoryResponse.Category item) {
                TextView tv_name = helper.getView(R.id.tv_name);
                tv_name.setText(item.getCategoryName());
                ImageView iv_pic = helper.getView(R.id.iv_pic);
                ListView sub_listview = helper.getView(R.id.sub_listview);
                List<CategoryResponse.Category> sitems = categoryResponse.getSubCategoryListMap().get(item.getId());
                if (sitems != null) {
                    CommonAdapter<CategoryResponse.Category> subAdpater = new CommonAdapter<CategoryResponse.Category>(SearchCategoryActivity.this, sitems, R.layout.item_search_category_sub) {
                        @Override
                        public void convert(ViewHolder helper, CategoryResponse.Category item) {
                            helper.setText(R.id.tv_name, item.getCategoryName());
                        }
                    };
                    sub_listview.setAdapter(subAdpater);
                    CommonMethod.setListViewHeightBasedOnChildren(sub_listview);
                    sub_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CategoryResponse.Category item = (CategoryResponse.Category) adapterView.getItemAtPosition(i);
                            EventBus.getDefault().post(new NoticeEvent("SearchCategory",item.getId()));
                            finish();

                        }
                    });
                }
                sub_listview.setVisibility(View.GONE);
                iv_pic.setImageResource(R.mipmap.arrow_down);
                if (selid.equals(item.getId())&&sitems!=null) {
                    sub_listview.setVisibility(View.VISIBLE);
                    iv_pic.setImageResource(R.mipmap.arrow_up);
                }
            }
        };
        myListview.setAdapter(mAdapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryResponse.Category item = (CategoryResponse.Category) adapterView.getItemAtPosition(i);
                if (selid.equals(item.getId())) {
                    selid = "";
                } else {
                    selid = item.getId();
                }
                myListview.setAdapter(mAdapter);
            }
        });
        View mHeaderView = LayoutInflater.from(SearchCategoryActivity.this).inflate(R.layout.item_search_category, null);
        TextView tv_name = V.f(mHeaderView, R.id.tv_name);
        tv_name.setText("全部分类");
        ImageView iv_pic = V.f(mHeaderView, R.id.iv_pic);
        iv_pic.setVisibility(View.GONE);
        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new NoticeEvent("SearchCategory",""));
                finish();
            }
        });
        myListview.addHeaderView(mHeaderView);
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("分类选择");
        headView.setHiddenRight();
    }

    @Override
    public void initViewData() {
        UserServiceImpl.instance().getCategoryList(SearchCategoryActivity.this, "1", CategoryResponse.class.getName());
    }

    @Override
    public void initEvent() {

    }

    CategoryResponse categoryResponse;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(CategoryResponse.class.getName())) {
                categoryResponse = GsonHelper.toType(result, CategoryResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(categoryResponse.getResultCode())) {
                        mlist.clear();
                        mlist.addAll(categoryResponse.getParentCategoryList());
                        mAdapter.setData(mlist);
                        mAdapter.notifyDataSetChanged();
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
