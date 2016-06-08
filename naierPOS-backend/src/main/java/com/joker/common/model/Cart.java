package com.joker.common.model;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Cart {
    private String userId;
    private Business business;
    private int num;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
