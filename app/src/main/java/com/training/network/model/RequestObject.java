package com.training.network.model;

import java.io.Serializable;

/**
 * Created by chenqiuyi on 16/12/8.
 */

public class RequestObject implements Serializable {
    private String mac = "eye-android";
    private String cmd;

    public RequestObject(String cmd) {
        this.cmd = cmd;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
