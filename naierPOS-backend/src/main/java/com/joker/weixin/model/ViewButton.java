package com.joker.weixin.model;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ViewButton extends Button {
    private String type;
    private String url;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
