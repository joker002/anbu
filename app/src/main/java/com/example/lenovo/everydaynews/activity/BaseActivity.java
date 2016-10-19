package com.example.lenovo.everydaynews.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.everydaynews.R;

/**
 * 基类
 * Created by lenovo on 2016/9/27.
 */
public abstract class BaseActivity extends FragmentActivity{
    RelativeLayout mRelativelayout;
    LayoutInflater mInflater;

    ImageView mIvLift;
    TextView mTvRightOne;
    ImageView mIvRightTwo;
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRelativelayout= (RelativeLayout) findViewById(R.id.rl_base);

        mIvLift= (ImageView) findViewById(R.id.iv_base_lift);
        mTvRightOne= (TextView) findViewById(R.id.tv_base_right_one);
        mIvRightTwo= (ImageView) findViewById(R.id.iv_base_right_two);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mInflater=getLayoutInflater();
    }

    public void setContentViews(int LayoutResId){
        mInflater.inflate(LayoutResId, mRelativelayout);
        initView();
    }
    public abstract void initView();



}
