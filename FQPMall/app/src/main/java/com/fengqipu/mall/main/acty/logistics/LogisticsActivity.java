package com.fengqipu.mall.main.acty.logistics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.logistics.LogisticsResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.base.BaseActivity;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.HeadView;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.tools.V;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogisticsActivity extends BaseActivity {
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.emtry_ll)
    LinearLayout emtryLl;
    private HeadView headView;
    private String orderID = "1";
    private ListView my_listview;
    private CommonAdapter<LogisticsResponse.DeliveryRecordListBean> mAdapter;
    private List<LogisticsResponse.DeliveryRecordListBean> llist = new ArrayList<LogisticsResponse.DeliveryRecordListBean>();
    private TextView wl_state, wl_ly, wl_id, wl_phone;
    String headPic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);
        orderID = getIntent().getStringExtra("orderID");
        headPic = getIntent().getStringExtra("headPic");
        initAll();
    }

    //初始化标题
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("查看物流");
        headView.setHiddenRight();
        my_listview = V.f(this, R.id.my_listview);
        wl_state = V.f(this, R.id.wl_state);
        wl_ly = V.f(this, R.id.wl_ly);
        wl_id = V.f(this, R.id.wl_id);
        wl_phone = V.f(this, R.id.wl_phone);
    }

    @Override
    public void initView() {
        if (!headPic.equals("")) {
            GeneralUtils.setImageViewWithUrl(this, headPic, imgHead, R.drawable.default_bg);
        }
        initTitle();
    }

    @Override
    public void initViewData() {
        mAdapter = new CommonAdapter<LogisticsResponse.DeliveryRecordListBean>(mContext, llist, R.layout.item_logistics) {
            @Override
            public void convert(ViewHolder helper, LogisticsResponse.DeliveryRecordListBean item) {
                TextView context = helper.getView(R.id.context);
                TextView time = helper.getView(R.id.time);
                if (helper.getPosition() == 0) {
                    context.setTextColor(getResources().getColor(R.color.app_color));
                    time.setTextColor(getResources().getColor(R.color.app_color));
                    helper.getView(R.id.img).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img2).setVisibility(View.GONE);
                } else {
                    context.setTextColor(getResources().getColor(R.color.txt_nol_col));
                    time.setTextColor(getResources().getColor(R.color.txt_nol_col));
                    helper.getView(R.id.img).setVisibility(View.GONE);
                    helper.getView(R.id.img2).setVisibility(View.VISIBLE);
                }
                context.setText(item.getContext());
                time.setText(item.getTime());
            }
        };
        my_listview.setAdapter(mAdapter);
        my_listview.setEmptyView(emtryLl);
        getData();
    }

    @Override
    public void initEvent() {

    }

    private void getData() {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getLogisticsInfo(mContext, orderID, LogisticsResponse.class.getName());
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            Log.e("sub", "result==" + result);
            if (tag.equals(LogisticsResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    LogisticsResponse logisticsResponse = GsonHelper.toType(result, LogisticsResponse.class);
                    if (Constants.SUCESS_CODE.equals(logisticsResponse.getResultCode())) {
                        llist.clear();
                        if (logisticsResponse.getDeliveryRecordList() != null && logisticsResponse.getDeliveryRecordList().size() > 0) {
                            llist.addAll(logisticsResponse.getDeliveryRecordList());
                        }
                        mAdapter.setData(llist);
                        mAdapter.notifyDataSetChanged();
//                        switch (logisticsResponse.getDelivery().getStatus()){
//                            case 1:wl_state.setText("未发货");break;
//                            case 2:wl_state.setText("在途中");break;
//                            case 3:wl_state.setText("完成");break;
//                            default:wl_state.setText("暂无状态");break;
//                        }


                        wl_state.setText(logisticsResponse.getDelivery().getStateDesc());
                        wl_ly.setText(logisticsResponse.getDelivery().getDeliveryAddress());
                        wl_id.setText(logisticsResponse.getDelivery().getOrderID());
                        wl_phone.setText(logisticsResponse.getDelivery().getDeliveryNum());
                    } else {
                        ErrorCode.doCode(mContext, logisticsResponse.getResultCode(), logisticsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
}
