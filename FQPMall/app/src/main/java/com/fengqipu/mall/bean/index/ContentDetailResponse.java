package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ContentDetailResponse extends BaseResponse {


    private ContentListBean content;

    private List<ContentStyleListBean> contentStyleList = new ArrayList<>();

    public ContentListBean getContent() {
        return content;
    }

    public void setContent(ContentListBean content) {
        this.content = content;
    }

    public List<ContentStyleListBean> getContentStyleList() {
        if (contentStyleList != null) {
            return contentStyleList;
        } else {
            contentStyleList = new ArrayList<>();
            return contentStyleList;
        }
    }

    public void setContentStyleList(List<ContentStyleListBean> contentStyleList) {
        this.contentStyleList = contentStyleList;
    }


}
