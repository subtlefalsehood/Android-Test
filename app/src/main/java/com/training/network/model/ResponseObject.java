package com.training.network.model;

import java.io.Serializable;

/**
 * Created by chenqiuyi on 16/12/8.
 */

public class ResponseObject implements Serializable {
    private int status;
    private String info;
    private int pageTotal;
    private String endata;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getEndata() {
        return endata;
    }

    public void setEndata(String endata) {
        this.endata = endata;
    }
}
