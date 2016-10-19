package com.example.lenovo.everydaynews.net;

/**
 * Created by lenovo on 2016/9/22.
 */
public interface OnResultFinishListener {
    void success(Response response);
    void failed(Response response);
}
