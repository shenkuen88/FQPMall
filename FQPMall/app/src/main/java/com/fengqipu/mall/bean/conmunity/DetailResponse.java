package com.fengqipu.mall.bean.conmunity;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class DetailResponse extends BaseResponse
{

    private ArticleListBean article;

    private int totalCount;
  

    private List<CommentListBean> commentList;

    public ArticleListBean getArticle() {
        return article;
    }

    public void setArticle(ArticleListBean article) {
        this.article = article;
    }

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
