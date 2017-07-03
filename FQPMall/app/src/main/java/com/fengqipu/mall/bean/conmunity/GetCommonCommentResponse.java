package com.fengqipu.mall.bean.conmunity;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class GetCommonCommentResponse extends BaseResponse {

    private int totalCount;

    private List<CommentListBean> commentList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

}
