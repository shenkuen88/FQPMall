package com.fengqipu.mall.main.acty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.DelHistoryGoodsResponse;
import com.fengqipu.mall.bean.mine.HistoryGoodsResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.goods.GoodsDetailActivity;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.RefreshListView;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create jwei by 2016/7/10
 * 浏览历史
 */
public class HistoryGoodsActivity extends BaseActivity {
    @Bind(R.id.top_line)
    View topLine;
    @Bind(R.id.id_cb_select_all)
    CheckBox idCbSelectAll;
    @Bind(R.id.id_tv_delete_all)
    TextView idTvDeleteAll;
    @Bind(R.id.btn_edit)
    TextView btnEdit;
    @Bind(R.id.id_rl_foot)
    RelativeLayout idRlFoot;
    @Bind(R.id.all_ck_ll)
    LinearLayout allCkLl;
    private HeadView headView;
    private LinearLayout no_history;//空页面
    //    private ListView his_goods_list;
    private RefreshListView his_goods_list;
    private CommonAdapter<HistoryGoodsResponse.UserOperationListBean> hisgoodsAdapter;
    private List<HistoryGoodsResponse.UserOperationListBean> hglist = new ArrayList<HistoryGoodsResponse.UserOperationListBean>();
    private int page = 1;
    private String num = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_goods);
        ButterKnife.bind(this);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        his_goods_list = V.f(this, R.id.his_goods_list);
        initEmtyView();
    }

    private List<String> ck_list = new ArrayList<>();

    @Override
    public void initViewData() {
        idCbSelectAll.setChecked(false);
//        hisgoodsAdapter=new CommonAdapter<HistoryGoodsResult>(mContext,hglist,R.layout.his_goods_item) {
//            @Override
//            public void convert(ViewHolder helper, HistoryGoodsResult item) {
//                helper.setText(R.id.title, item.getName());
//                CommonAdapter<HistoryGoodsResult.Goods> mAdapter =
//                        new CommonAdapter<HistoryGoodsResult.Goods>(mContext,item.getGoods(),R.layout.his_g_item) {
//                            @Override
//                            public void convert(ViewHolder helper, HistoryGoodsResult.Goods item) {
//                                helper.setText(R.id.goods_info,item.getName());
//                                helper.setText(R.id.goods_price,"￥"+item.getPrice());
//                                if(GeneralUtils.isNotNullOrZeroLenght(item.getPic())){
//                                    ImageView img=helper.getView(R.id.img);
//                                    ImageLoaderUtil.getInstance().initImage(mContext, item.getPic(), img, Constants.DEFAULT_IMAGE_F_LOAD);
//                                }
//                            }
//                        };
//                MySwipeMenuListView my_sub_list=helper.getView(R.id.my_sub_list);
//                my_sub_list.setAdapter(mAdapter);
//                GeneralUtils.setListViewHeightBasedOnChildrenExtend(my_sub_list);
//                initLeftSlideList(my_sub_list);
//            }
//        };
        hisgoodsAdapter =
                new CommonAdapter<HistoryGoodsResponse.UserOperationListBean>(mContext, hglist, R.layout.item_his_history) {
                    @Override
                    public void convert(ViewHolder helper, final HistoryGoodsResponse.UserOperationListBean item) {
                        final LinearLayout btn_ck_ll=helper.getView(R.id.btn_ck_ll);
                        final CheckBox btn_ck = helper.getView(R.id.btn_ck);
                        if (isedit == 1) {
                            btn_ck_ll.setVisibility(View.VISIBLE);
                        } else {
                            btn_ck_ll.setVisibility(View.GONE);
                        }
                        boolean ischeck=false;
                        for(String s:ck_list){
                            if (s.equals(item.getId())){
                                ischeck=true;
                            }
                        }
                        Log.e("sub","ischeck="+ischeck);
                        btn_ck.setChecked(ischeck);
                        helper.setText(R.id.goods_info, item.getContentName());
                        helper.setText(R.id.goods_price, "￥" + item.getPrice());
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrlRequestUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrlRequestUrl(), img, R.drawable.default_head);
                        }
                        btn_ck_ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btn_ck.isChecked()) {
                                    btn_ck.setChecked(false);
                                    ck_list.remove(item.getId());
                                } else {
                                    btn_ck.setChecked(true);
                                    ck_list.add(item.getId());
                                }
                                if (ck_list.size() == hglist.size()) {
                                    idCbSelectAll.setChecked(true);
                                } else {
                                    idCbSelectAll.setChecked(false);
                                }
                            }
                        });
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(isedit == 1){
                                    if (btn_ck.isChecked()) {
                                        btn_ck.setChecked(false);
                                        ck_list.remove(item.getId());
                                    } else {
                                        btn_ck.setChecked(true);
                                        ck_list.add(item.getId());
                                    }
                                    if (ck_list.size() == hglist.size()) {
                                        idCbSelectAll.setChecked(true);
                                    } else {
                                        idCbSelectAll.setChecked(false);
                                    }
                                }else {
                                    Intent intent = new Intent(HistoryGoodsActivity.this, GoodsDetailActivity.class);
                                    intent.putExtra("contentID", item.getId());
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                };
        his_goods_list.setAdapter(hisgoodsAdapter);
        his_goods_list.setEmptyView(no_history);
//        initLeftSlideList(his_goods_list);
        page = 1;
        getHistoryGoods();
    }


    //获取浏览历史
    private void getHistoryGoods() {
//        hglist.clear();
//        for(int i=0;i<2;i++){
//            List<HistoryGoodsResult.Goods> templist=new ArrayList<HistoryGoodsResult.Goods>();
//            for(int j=0;j<4;j++){
//                HistoryGoodsResult.Goods goods=new HistoryGoodsResult.Goods(""+j,"视频"+j,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3704122693,1924714915&fm=21&gp=0.jpg","100");
//                templist.add(goods);
//            }
//            hglist.add(new HistoryGoodsResult(templist,""+i,"商品"+i));
//            hisgoodsAdapter.setData(hglist);
//            hisgoodsAdapter.notifyDataSetChanged();
//        }
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getGoodsHistory("5", page + "", num, HistoryGoodsResponse.class.getName());
    }

    private boolean isloading = false;
    private int count = 0;
    int isedit = 0;//0.编辑 1.完成

    @Override
    public void initEvent() {
        idCbSelectAll.setClickable(false);
        allCkLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idCbSelectAll.isChecked()){
                    idCbSelectAll.setChecked(false);
                    ck_list.clear();
                }else{
                    idCbSelectAll.setChecked(true);
                    for(HistoryGoodsResponse.UserOperationListBean item:hglist){
                        ck_list.add(item.getId());
                    }
                }
                hisgoodsAdapter.notifyDataSetChanged();
            }
        });
        idTvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> operationIDs=new ArrayList<>();
                if(tempDellist.size()>0)return;
                tempDellist.addAll(hglist);
                for(HistoryGoodsResponse.UserOperationListBean g:hglist){
                    if(ck_list.contains(g.getId())){
                        tempDellist.remove(g);
                        operationIDs.add(g.getId());
                    }
                }
                if(operationIDs.size()==0){
                    tempDellist.clear();
                    ToastUtils.showToast(HistoryGoodsActivity.this,"请选择您要删除的足迹!");
                }
                UserServiceImpl.instance().DelHistoryGoods(operationIDs, "5", DelHistoryGoodsResponse.class.getName());
            }
        });
//        idCbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                } else {
//                    ck_list.clear();
//                }
//                hisgoodsAdapter.notifyDataSetChanged();
//            }
//        });
        his_goods_list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (!isloading) {
                            isloading = true;
                            if (count > page * Integer.valueOf(num)) {
                                page++;
                                getHistoryGoods();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });
        his_goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HistoryGoodsResponse.UserOperationListBean item = (HistoryGoodsResponse.UserOperationListBean) parent.getItemAtPosition(position);
//                Intent intent = new Intent();
//                if (item.getOperationType() == 1) {
////                    intent.setClass(mContext, EduOlineVideoActivity.class);
//                } else if (item.getOperationType() == 2) {
//                    intent.setClass(mContext, ProductActy.class);
//                } else if (item.getOperationType() == 3) {
////                    intent.setClass(mContext, DecorateActy.class);
//                }
//                intent.putExtra(IntentCode.contentID, item.getOperationID());
//                startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnEdit.getText().equals("编辑")) {
                    isedit = 1;
                    headView.setRightText("");
                    topLine.setVisibility(View.GONE);
                    btnEdit.setText("完成");
                    idRlFoot.setVisibility(View.VISIBLE);
                    hisgoodsAdapter.notifyDataSetChanged();
                } else {
                    isedit = 0;
                    headView.setRightText("清空");
                    topLine.setVisibility(View.VISIBLE);
                    btnEdit.setText("编辑");
                    idRlFoot.setVisibility(View.GONE);
                    hisgoodsAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    //初始化标题
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("我的足迹");
        headView.setRightText("清空");
    }

    //初始化空View
    private void initEmtyView() {
        no_history = V.f(this, R.id.no_history);
        ImageView tips_pic = V.f(this, R.id.tips_pic);
        tips_pic.setImageResource(R.mipmap.collectx);
        TextView tips = V.f(this, R.id.tips);
        tips.setText("暂无足迹~");
    }
    private List<HistoryGoodsResponse.UserOperationListBean> tempDellist=new ArrayList<>();
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
                DialogUtil.showNoTipTwoBnttonDialog(mContext
                        , "你确定要清空足迹吗?"
                        , "取消"
                        , "确定"
                        , NotiTag.TAG_DEL_GOODS_CANCEL, NotiTag.TAG_DEL_GOODS_OK);
            }
            if (NotiTag.TAG_DEL_GOODS_OK.equals(tag)) {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().DelHistoryGoods(null, "5", DelHistoryGoodsResponse.class.getName());
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(HistoryGoodsResponse.class.getName())) {
                isloading = false;
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    Log.e("sub",result);
                    HistoryGoodsResponse historyGoodsResponse = GsonHelper.toType(result, HistoryGoodsResponse.class);
                    if (Constants.SUCESS_CODE.equals(historyGoodsResponse.getResultCode())) {
                        count = historyGoodsResponse.getTotalCount();
                        if (page == 1) {
                            hglist.clear();
                        }
                        if (historyGoodsResponse.getUserOperationList() != null && historyGoodsResponse.getUserOperationList().size() > 0) {
                            hglist.addAll(historyGoodsResponse.getUserOperationList());
                        }
                        hisgoodsAdapter.setData(hglist);
                        hisgoodsAdapter.notifyDataSetChanged();

                    } else {
                        ErrorCode.doCode(mContext, historyGoodsResponse.getResultCode(), historyGoodsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(DelHistoryGoodsResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    DelHistoryGoodsResponse delHistoryGoodsResponse = GsonHelper.toType(result, DelHistoryGoodsResponse.class);
                    if (Constants.SUCESS_CODE.equals(delHistoryGoodsResponse.getResultCode())) {
                        if (tempDellist.size()>0) {
                            hglist.clear();
                            hglist.addAll(tempDellist);
                            tempDellist.clear();
                            hisgoodsAdapter.setData(hglist);
                            hisgoodsAdapter.notifyDataSetChanged();
                        } else {
                            page = 1;
                            getHistoryGoods();
                        }
                    } else {
                        ErrorCode.doCode(mContext, delHistoryGoodsResponse.getResultCode(), delHistoryGoodsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
}
