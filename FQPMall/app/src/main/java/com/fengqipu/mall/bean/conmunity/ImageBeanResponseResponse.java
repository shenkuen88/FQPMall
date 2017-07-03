package com.fengqipu.mall.bean.conmunity;



import java.util.ArrayList;
import java.util.List;

public class ImageBeanResponseResponse {
    private List<ImageBean> image = new ArrayList<>();

    public List<ImageBean> getImage() {
        return image;
    }

    public void setImage(List<ImageBean> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String logStr = "";
        for (int i = 0; i < image.size(); i++) {
            ImageBean bean = image.get(i);
            logStr += bean.getUrl()+"\n";
        }
        return logStr;
    }
}
