package com.joker.weixin.model;

/**
 * Created by Administrator on 2016/3/24.
 */
public class SuperButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
