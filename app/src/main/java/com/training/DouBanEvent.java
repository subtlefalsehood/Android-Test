package com.training;

/**
 * Created by chenqiuyi on 17/8/17.
 */

public class DouBanEvent {
    public static final int TOP250_MOVIES_START = 1;
    public static final int TOP250_MOVIES_SUCCESS = 2;
    public static final int TOP250_MOVIES_ERROR = 3;

    private Object data;
    private int message;

    public DouBanEvent(int message,Object data) {
        this.data = data;
        this.message = message;
    }

    public DouBanEvent(Object data) {
        this.data = data;
    }

    public DouBanEvent(int  message) {
        this.message = message;
    }

    public DouBanEvent() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
