package com.training.network.model;

import com.training.network.Constant;

public class RqLogin extends RequestObject {
    private String phoneNum;

    private String password;

    public RqLogin(String cmd) {
        super(Constant.HTTP_URL + cmd);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}