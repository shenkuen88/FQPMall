package com.fengqipu.mall.bean.conmunity;


import com.fengqipu.mall.bean.BaseResponse;

public class CommentResponse extends BaseResponse
{

    /**
     * recordID : 1
     * userID : 1
     * StringType : 1
     * StringID : 1
     * text : 哦哦哦
     * createTime : null
     */

    private CommentListBean comment;

    public CommentListBean getComment() {
        return comment;
    }

    public void setComment(CommentListBean comment) {
        this.comment = comment;
    }


}
