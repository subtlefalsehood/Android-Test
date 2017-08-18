package com.training.network.model;

import com.subtlefalsehood.base.utils.StringUtil;
import com.training.network.consts.UrlConstant;

import java.io.Serializable;

public class RequestObject implements Serializable{
    private String cmd;
    private String mac;
    private String at;

    public RequestObject(String cmd) {
        this.cmd = cmd;
        if(!StringUtil.isBlank(UrlConstant.DEVICE_ID)){
            this.mac = UrlConstant.DEVICE_ID;
        }else {
            this.mac = "criwell-unget-device-id";
        }
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getId() {
        return at;
    }

    public void setId(String id) {
        this.at = id;
    }
}
