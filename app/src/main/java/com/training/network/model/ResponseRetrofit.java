package com.training.network.model;

/**
 * Created by chenqiuyi on 16/12/19.
 */

public class ResponseRetrofit<T> {
    /**
     * status : 1
     * info : success
     */

    private String status;
    private String info;
    private int pageTotal;
    private T endata;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getEndata() {
        return endata;
    }

    public void setEndata(T endata) {
        this.endata = endata;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
