package com.training.network.model;

import java.util.Comparator;

/**
 * Created by chenqiuyi on 16/12/12.
 */

public abstract class Request<T> implements Comparator<Request<T>> {

    public static enum HttpMethod {
        GET("GET"),
        PUT("PUT"),
        POST("POST"),
        delete("DELETE");

        HttpMethod(String method) {

        }
    }
}
