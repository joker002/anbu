package com.example.lenovo.everydaynews.entity;

import java.util.ArrayList;

/**
 * 数据列表
 * Created by Administrator on 2016/9/27.
 */
public class NewsArray {
    String message;
    int status;
    public  ArrayList<News> data;

    @Override
    public String toString() {
        return "NewsArray{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
