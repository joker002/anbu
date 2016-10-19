package com.example.lenovo.everydaynews.entity;

import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * 数据详情
 * Created by lenovo on 2016/9/30.
 */
public class News implements Serializable{
    public String summary;
    public String icon;
    public String stamp;
    public String title;
    public int nid;
    public String link;
    public int type;
}
