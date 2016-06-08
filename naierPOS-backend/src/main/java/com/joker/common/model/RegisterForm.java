package com.joker.common.model;

/**
 * Created by Administrator on 2015/4/30.
 */
public class RegisterForm {
    private String kaptcha;
    private User user;

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
