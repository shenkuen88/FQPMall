package com.fengqipu.mall.bean.video;

import com.fengqipu.mall.bean.BaseResponse;

/**
 * Created by Administrator on 2016/8/6.
 */
public class VideoURLResponse extends BaseResponse {
    private String playUrl;

    public VideoURLResponse() {
    }

    public VideoURLResponse(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
