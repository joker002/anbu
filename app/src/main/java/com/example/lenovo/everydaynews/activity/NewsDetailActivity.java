package com.example.lenovo.everydaynews.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.entity.News;

import java.io.Serializable;
import java.net.URL;

/**
 * 网络加载页面 webView控件
 * Created by cc on 2016/9/29.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
    WebView mWebView;
    View mView;
    PopupWindow mPopupWindow;
    News mNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_news_detail);
    }

    @Override
    public void initView() {
        mIvLift.setImageResource(R.mipmap.back);
        mTvTitle.setText("资讯");
        mTvRightOne.setText("跟帖");
        mIvRightTwo.setImageResource(R.mipmap.news_menu);

        mIvLift.setOnClickListener(this);
        mTvRightOne.setOnClickListener(this);
        mIvRightTwo.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.web_news_detail);

        //Intent intent = getIntent();

        //对象bean= (对象bean) getIntent().getSerializableExtra("设置标记的key");
        mNews = (News) getIntent().getSerializableExtra("Extra");
        //String str = intent.getStringExtra("Extra");
        Log.e("aaa", "initView: " + mNews.link);
        String str = mNews.link;

        //1.加载连接
        //mWebView.loadUrl("https://www.baidu.com/");
        //Log.e("aaa", "initView: " + str);
        mWebView.loadUrl(str);

        //2.加载本地文件

        //使用当前应用作为Web视图的展示页面
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //必须加 否则无法加载下个网页
                mWebView.loadUrl(url);
                //返回值为true
                return true;
            }
        });

        //添加设置
        WebSettings settings = mWebView.getSettings();
        //支持JavaScript写的网页
        settings.setJavaScriptEnabled(true);

        //PopupWindow
        //PopupWindow这个类用来实现一个弹出框，可以使用任意布局的View作为其内容，这个弹出框是悬浮在当前activity之上的。
        mView = getLayoutInflater().inflate(R.layout.item_news_deatil_collect, null);
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mView.setOnClickListener(this);
    }

    //监听按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_base_lift:
                finish();
                break;
            case R.id.tv_base_right_one://跟帖
                Intent intent = new Intent(this, CommentActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_base_right_two://收藏
                mPopupWindow.showAsDropDown(mIvRightTwo, 0, 0);
                //mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
                //Intent intent=new Intent();
                break;

            case R.id.ll_item_news_detail_collect:

                Log.e("aaa", "onClick: " + mNews);

                Intent intent1 = new Intent(this, HomeActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("News", mNews);
                intent1.putExtras(mBundle);

                mPopupWindow.dismiss();
                Toast.makeText(this, "已加入收藏", Toast.LENGTH_LONG).show();
                break;

        }
    }
}
