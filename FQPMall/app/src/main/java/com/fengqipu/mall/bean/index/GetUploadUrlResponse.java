package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

public class GetUploadUrlResponse extends BaseResponse {

    /**
     * upToken : FA7eDLBorOJQo93WXY2w2Bukp1QuMNLjptkfsL4D:033zgEIMfRBha0sEIC1uPQuSDrg=:eyJzY29wZSI6Inpob25nY2hhby10ZXN0IiwiZGVhZGxpbmUiOjE0NzA2MzA4ODh9
     * deadline : 20160808123448
     */

    private String upToken;
    private String deadline;

    public String getUpToken() {
        return upToken;
    }

    public void setUpToken(String upToken) {
        this.upToken = upToken;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
