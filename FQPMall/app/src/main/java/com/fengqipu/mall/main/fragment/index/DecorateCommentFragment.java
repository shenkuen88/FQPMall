package com.fengqipu.mall.main.fragment.index;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.NetResponseEvent;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.index.AddProductCommentResponse;
import com.fengqipu.mall.bean.index.AppraiseListBean;
import com.fengqipu.mall.bean.index.ProductCommentResponse;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.ErrorCode;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.main.acty.mine.LoginActy;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.main.base.BaseFragment;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.network.UserServiceImpl;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.DialogUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.NetLoadingDialog;
import com.fengqipu.mall.tools.ToastUtil;
import com.fengqipu.mall.view.CircleImageView;
import com.fengqipu.mall.view.MyListView;

import java.util.ArrayList;

/**
 * 装修 评论列表
 */
public class DecorateCommentFragment extends BaseFragment implements View.OnClickListener {

    private static TextView etDlgInput;
    /**
     * 提交评论
     */
    private static Button bnDlgSubmit;
    private View mView;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private int pageSize = Constants.LIST_NUM;

    /**
     * 适配器
     */
    private CommonAdapter<AppraiseListBean> adapter;
    /**
     * 存放数据记录
     */
    private ArrayList<AppraiseListBean> datas = new ArrayList<>();
    /**
     * 加载更多 数据
     */
    private ArrayList<AppraiseListBean> datasMore;
    private boolean isLoadingMoreData;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private ScrollView scrollView;
    private int pageTotal = 0;
    private MyListView lvComment;
    /**
     * 输入
     */
    private TextView tvInput;
    private View llTopInput;
    private String contentID = "1";
    private Dialog dialog;
    private int lastVisibileItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_decorate_comment, null);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            llLoading.setVisibility(View.GONE);
            //获取评论列表
            if (tag.equals(ProductCommentResponse.class.getName())) {
                llLoading.setVisibility(View.GONE);
                tvLoadMore.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ProductCommentResponse mProductCommentResponse = GsonHelper.toType(result, ProductCommentResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mProductCommentResponse.getResultCode())) {
                        pageTotal = mProductCommentResponse.getTotalCount();
                        if (loadPageNo == 1) {
                            datas = new ArrayList<AppraiseListBean>();
                            datasMore = new ArrayList<AppraiseListBean>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<AppraiseListBean>) mProductCommentResponse.getAppraiseList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<AppraiseListBean>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            lvComment.setVisibility(View.GONE);
                        } else {//有记录
                            adapter.setData(datas);
                            adapter.notifyDataSetChanged();
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                                llLoading.setVisibility(View.GONE);
                                isLoadingMoreData = false;
                            }
                        }
                    } else {
                        ErrorCode.doCode(getActivity(), mProductCommentResponse.getResultCode(), mProductCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }

            //添加评论
            if (tag.equals(AddProductCommentResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    dialog.dismiss();
                    AddProductCommentResponse mAddProductCommentResponse = GsonHelper.toType(result, AddProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddProductCommentResponse.getResultCode())) {
                        ToastUtil.makeText(getActivity(), "恭喜，评论成功");
                        if (datas.size() == 0) {
                            pageNo = 1;
                            loadPageNo = 1;
                            initCommentList();
                        } else {
                            AppraiseListBean bean = new AppraiseListBean();
                            bean.setText(etDlgInput.getText().toString());
                            bean.setUserPortrait(Global.getUserHeadUrl());
                            bean.setUserNickName(Global.getNickName());
                            datas.add(0, bean);//加到头部   datas.add(bean);//在列表最末尾
                            adapter.notifyDataSetChanged();
                            scrollView.scrollTo(0, 0);

                        }
                        etDlgInput.setText("");
                        Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_LONG).show();
                    } else {
                        ErrorCode.doCode(getActivity(), mAddProductCommentResponse.getResultCode(), mAddProductCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_tv:
                if (GeneralUtils.isLogin()) {
                    commentDialog(getActivity());
                } else {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.loading_test_ll:
                break;
        }
    }


    private void initCommentList() {
//        UserServiceImpl.instance().getProductCommentList(contentID, loadPageNo, ProductCommentResponse.class.getName());
    }

    private void initView() {
        lvComment = (MyListView) mView.findViewById(R.id.comment_lv);
        llTopInput = LayoutInflater.from(getActivity()).inflate(R.layout.lv_head_comment, null);
        lvComment.addHeaderView(llTopInput);
        tvInput = (TextView) llTopInput.findViewById(R.id.input_tv);//输入
        CircleImageView ivUserHead = (CircleImageView) llTopInput.findViewById(R.id.head_iv);//用户头像
        if (GeneralUtils.isNotNullOrZeroLenght(Global.getUserHeadUrl())) {
//            ImageLoaderUtil.getInstance().initImage(getActivity(), Global.getUserHeadUrl(), ivUserHead, Constants.DEFAULT_USER_HEAD);
            GeneralUtils.setImageViewWithUrl(getActivity(), Global.getUserHeadUrl(), ivUserHead, R.drawable.default_head);
        }
        tvInput.setOnClickListener(this);
        llLoading = (LinearLayout) mView.findViewById(R.id.loading_test_ll);//正在加载
        tvLoadMore = (TextView) mView.findViewById(R.id.load_more_tv);//加载更多
        llLoading.setVisibility(View.VISIBLE);
        tvLoadMore.setVisibility(View.GONE);
        lvComment.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == lvComment.getCount())
                    loadMore();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });
        initAdapter();
        initCommentList();
    }

    private void loadMore() {
        if (pageTotal != 0 && pageNo < pageTotal && tvLoadMore.getText().equals("加载更多")) {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
            initCommentList();
        } else {
            tvLoadMore.setText("已加载完毕");
        }
    }

    private void initAdapter() {
        adapter = new CommonAdapter<AppraiseListBean>(getActivity(), datas, R.layout.item_community_reply) {
            @Override
            public void convert(ViewHolder helper, final AppraiseListBean item) {
                ImageView imageView = (ImageView) helper.getView(R.id.head_iv);
                helper.setText(R.id.name_tv, item.getUserNickName());
                helper.setText(R.id.content_tv, item.getText());
                helper.setText(R.id.time_tv, item.getCreateTime());
                GeneralUtils.setRoundImageViewWithUrl(getActivity(), item.getUserPortrait(), imageView, R.drawable.default_head);
            }
        };
        lvComment.setAdapter(adapter);
    }

    /**
     * 评论
     */
    public void commentDialog(final Context context) {

        if (dialog == null) {
            dialog = DialogUtil.initDialog(context, R.layout.dialog_comment);

            etDlgInput = (TextView) dialog.findViewById(R.id.comment_et);
            bnDlgSubmit = (Button) dialog.findViewById(R.id.comment_bn);
            dialog.setCanceledOnTouchOutside(false);
            bnDlgSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //提交评论
                    if (GeneralUtils.isNotNullOrZeroLenght(etDlgInput.getText().toString())) {
                        UserServiceImpl.instance().addProductComment(contentID, etDlgInput.getText().toString(), AddProductCommentResponse.class.getName());
                    } else {
                        ToastUtil.makeText(getActivity(), "请输入内容!");
                    }
                }
            });
            dialog.show();

        } else {
            etDlgInput.setText("");
            dialog.show();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                etDlgInput.setFocusable(true);
                etDlgInput.setFocusableInTouchMode(true);
                etDlgInput.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) etDlgInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etDlgInput, 0);
            }
        }, 400);
    }

}
