package com.example.lenovo.everydaynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.everydaynews.R;


/**
 * 加载界面
 * Created by cc on 2016/9/27.
 */
public class LoadingActivity extends BaseActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.setClass(LoadingActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("aaa", "onCreate: ");

        setContentViews(R.layout.activity_loading);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_base);
        ll.setVisibility(View.GONE);
    }

    @Override
    public void initView() {

//        CountDownTimer timer=new CountDownTimer(1500,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//
//            @Override
//            public void onFinish() {
//                Intent intent = new Intent();
//                intent.setClass(LoadingActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }.start();


        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
