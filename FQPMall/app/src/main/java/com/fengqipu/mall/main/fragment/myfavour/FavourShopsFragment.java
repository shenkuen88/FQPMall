package com.fengqipu.mall.main.fragment.myfavour;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.DelFavourResponse;
import com.fengqipu.mall.bean.mine.ShopFavourResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.main.acty.enterprise.EnterpriseNewActivity;
import com.fengqipu.mall.main.acty.mine.NewMyFavourActivity;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CommonMethod;
import com.fengqipu.mall.tools.DisplayUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.MySwipeMenuListView;
import com.fengqipu.mall.view.swipemenulist.SwipeMenu;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuCreator;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuItem;
import com.fengqipu.mall.view.swipemenulist.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/6/13.
 */
public class FavourShopsFragment extends BaseFragment implements View.OnClickListener {
    private static NewMyFavourActivity newMyFavourActivity;
    @Bind(R.id.my_listview)
    MySwipeMenuListView myListview;
    @Bind(R.id.emtry_ll)
    LinearLayout emtryLl;
//    @Bind(R.id.refreshLayout)
//    PtrClassicFrameLayout refreshLayout;

    private CommonAdapter<ShopFavourResponse.FavoriteListBean> lAdapter;
    private List<ShopFavourResponse.FavoriteListBean> goodsList = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 10;
    int totalCount = 0;
    int lastVisibileItem = 0;

    public FavourShopsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newMyFavourActivity = (NewMyFavourActivity) getActivity();
        View v = LayoutInflater.from(newMyFavourActivity).inflate(R.layout.fragment_favourgoods, null);
        setWindow();
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
//        refreshLayout.setLastUpdateTimeRelateObject(this);
//        refreshLayout.setResistance(1.7f);
//        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        refreshLayout.setDurationToClose(200);
//        refreshLayout.setDurationToCloseHeader(1000);
//        // default is false
//        refreshLayout.setPullToRefresh(false);
//        // default is true
//        refreshLayout.setKeepHeaderWhenRefresh(true);
//
//        refreshLayout.disableWhenHorizontalMove(true);
//
//        refreshLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                initData();
//            }
//
//
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, myListview, header);
//
//            }
//        });
        lAdapter = new CommonAdapter<ShopFavourResponse.FavoriteListBean>(newMyFavourActivity, goodsList, R.layout.item_shops) {
            @Override
            public void convert(ViewHolder helper, final ShopFavourResponse.FavoriteListBean item) {
                TextView comment_name_tv = helper.getView(R.id.comment_name_tv);
                comment_name_tv.setText(item.getObjectName());
                ImageView comment_head_iv = helper.getView(R.id.comment_head_iv);
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrlRequestUrl())) {
                    GeneralUtils.setImageViewWithUrl(newMyFavourActivity, item.getPicUrlRequestUrl(),
                            comment_head_iv,
                            R.drawable.default_bg);
                }
                if (GeneralUtils.isNotNullOrZeroLenght(item.getShopType())&& item.getShopType().equals("1")){
                    helper.getView(R.id.qy_icon).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.qy_icon).setVisibility(View.GONE);
                }
                GridView gridView = helper.getView(R.id.my_grid_view);
                LinearLayout image_ll = helper.getView(R.id.image_ll);
                if (item.getAdvPicUrlList() != null && item.getAdvPicUrlList().size() > 0) {
                    image_ll.setVisibility(View.VISIBLE);
                    CommonAdapter<String> gadapter = new CommonAdapter<String>(newMyFavourActivity, item.getAdvPicUrlList(), R.layout.item_pic) {
                        @Override
                        public void convert(ViewHolder helper, String item) {
                            ImageView iv_pic = helper.getView(R.id.iv_pic);
                            if (GeneralUtils.isNotNullOrZeroLenght(item)) {
                                GeneralUtils.setImageViewWithUrl(newMyFavourActivity, item,
                                        iv_pic,
                                        R.drawable.default_bg);
                            }
                        }
                    };
                    gridView.setAdapter(gadapter);
                    CommonMethod.setListViewHeightBasedOnChildren(gridView);
                } else {
                    image_ll.setVisibility(View.GONE);
                }
                comment_name_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(newMyFavourActivity, EnterpriseNewActivity.class);
                        intent.putExtra("sid", item.getObjectID());
                        startActivity(intent);
                    }
                });
                comment_head_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(newMyFavourActivity, EnterpriseNewActivity.class);
                        intent.putExtra("sid", item.getObjectID());
                        startActivity(intent);
                    }
                });
            }
        };
//        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ShopFavourResponse.FavoriteListBean item=(ShopFavourResponse.FavoriteListBean)adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(newMyFavourActivity, EnterpriseActivity.class);
//                intent.putExtra("sid", item.getId());
//                startActivity(intent);
//            }
//        });
        myListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == myListview.getCount())
                    if (pageNum * pageSize >= totalCount) return;
                pageNum = pageNum + 1;
                initBtmList();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        myListview.setAdapter(lAdapter);
        myListview.setEmptyView(emtryLl);
        initLeftSlideList(myListview);
        initData();
    }

    private void initData() {
        //请求底部列表接口
        pageNum = 1;
        initBtmList();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    refreshLayout.refreshComplete();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 2000);
    }

    private void initBtmList() {
        //        myLoading.setVisibility(View.GONE);
//        GoodsBean g1 = new GoodsBean();
//        GoodsBean g2 = new GoodsBean();
//        GoodsBean g3 = new GoodsBean();
//        GoodsBean g4 = new GoodsBean();
//        GoodsBean g5 = new GoodsBean();
//        goodsList.add(g1);
//        goodsList.add(g2);
//        goodsList.add(g3);
//        goodsList.add(g4);
//        goodsList.add(g5);
//        lAdapter.notifyDataSetChanged();
        UserServiceImpl.instance().getFavourList(newMyFavourActivity, "2", pageNum + "", pageSize + "", ShopFavourResponse.class.getName());
    }

    public float scaleWidth;
    public float scaleHeight;
    public int windowWidth = 0;
    public int windowHeight = 0;

    public void setWindow() {
        if (windowWidth > 0 && windowHeight > 0) {
            return;
        }
        try {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            windowWidth = dm.widthPixels;
            windowHeight = dm.heightPixels;
            scaleWidth = (float) windowWidth / 720f;
            scaleHeight = (float) windowHeight / 1280f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //初始化向左滑动出现删除按钮
    private void initLeftSlideList(MySwipeMenuListView listview) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        newMyFavourActivity.getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.app_color)));
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(newMyFavourActivity, 60));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu

                menu.addMenuItem(openItem);

            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //删除
                        delitem = goodsList.get(position);
                        UserServiceImpl.instance().DelFavour(delitem.getId(), delitem.getObjectType() + "", delitem.getObjectID(), DelFavourResponse.class.getName());
                    }
                }, 300);
                return false;
            }
        });
    }

    private ShopFavourResponse.FavoriteListBean delitem;

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(ShopFavourResponse.class.getName())) {
                ShopFavourResponse favourResponse = GsonHelper.toType(result, ShopFavourResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(favourResponse.getResultCode())) {
                        if (pageNum == 1) {
                            goodsList.clear();
                        }
                        totalCount = favourResponse.getTotalCount();
                        if (favourResponse.getFavoriteList() != null && favourResponse.getFavoriteList().size() > 0) {
                            goodsList.addAll(favourResponse.getFavoriteList());
                        }
                        lAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(newMyFavourActivity, favourResponse.getResultCode(), favourResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(newMyFavourActivity);
                }
            }
            if (tag.equals(DelFavourResponse.class.getName())) {
                DelFavourResponse delFavourResponse = GsonHelper.toType(result, DelFavourResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(delFavourResponse.getResultCode())) {
                        if (delitem != null) {
                            goodsList.remove(delitem);
                            lAdapter.setData(goodsList);
                            lAdapter.notifyDataSetChanged();
                        } else {
                            pageNum = 1;
                            initBtmList();
                        }
                    } else {
                        ErrorCode.doCode(newMyFavourActivity, delFavourResponse.getResultCode(), delFavourResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(newMyFavourActivity);
                }
            }
        }
    }
}
