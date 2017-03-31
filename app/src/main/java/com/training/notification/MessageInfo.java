package com.training.notification;

import java.util.ArrayList;

/**
 * Created by chenqiuyi on 17/3/14.
 */

public class MessageInfo {
    private String pkgName;
    private ArrayList<String> msgList = new ArrayList<>();

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public ArrayList<String> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<String> msgList) {
        this.msgList = msgList;
    }
}
