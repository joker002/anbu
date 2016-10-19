package com.example.lenovo.everydaynews.net;

import java.util.Map;

/**
 * 请求所用数据
 * Created by lenovo on 2016/9/22.
 */
public class Request {
    /**
     * 链接
     */
    public String url;

    /**
     *参数
     */
    public Map<String,String> params;
    /**
     * 连接的方式
     */
    public int type;
}
