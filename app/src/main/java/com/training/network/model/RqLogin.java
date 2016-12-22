package com.training.network.model;

import com.training.network.Constant;

public class RqLogin extends RequestObject {
    private String phoneNum;

    private String password;

    private String openId;

    public RqLogin() {
        super(Constant.LOGIN_URL);
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}