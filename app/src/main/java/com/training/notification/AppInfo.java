package com.training.notification;

class AppInfo {
    private String pkg_name;
    private String app_name;
    private int verCode;
    private String verName;
    private boolean isListen = false;

    public String getPkgName() {
        return pkg_name;
    }

    public void setPkgName(String name) {
        this.pkg_name = name;
    }

    boolean isListen() {
        return isListen;
    }

    void setListen(boolean listen) {
        isListen = listen;
    }

    public void toggleSwitch() {
        isListen = !isListen;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }
}