package com.training.storage.model;

/**
 * Created by chenqiuyi on 16/11/21.
 */

public class SQLiteInfo {
    private String id;
    private String entryid;
    private String title;
    private String cotent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }
}
