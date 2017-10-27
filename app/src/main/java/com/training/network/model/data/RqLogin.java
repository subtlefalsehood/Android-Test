package com.training.network.model.data;

import com.training.network.consts.UrlConstant;
import com.training.network.model.RequestObject;

public class RqLogin extends RequestObject {
    private String phoneNum;

    private String password;

    private String openId;

    public RqLogin() {
        super(UrlConstant.LOGIN_URL);
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