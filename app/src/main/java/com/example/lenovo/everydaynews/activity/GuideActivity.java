package com.example.lenovo.everydaynews.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.adapter.GuideAdapter;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 引导界面
 */
public class GuideActivity extends BaseActivity implements  View.OnClickListener {
    /**
     * ViewPager
     */
    ViewPager mViewpager;
    /**
     *
     */
    boolean mBoolean;
    /**
     *
     */
    public String AAA;
    /**
     * 存储方式
     */
    SharedPreferences mPreferences;
    /**
     * 装有图片ID的数组
     */
     int[] mPicture={R.mipmap.bd,R.mipmap.small,R.mipmap.wy,R.mipmap.welcome};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //加载视图
        setContentViews(R.layout.activity_guide);

        //gone掉BaseActivity的框架
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_base);
        ll.setVisibility(View.GONE);

        //判断是否第一次进入
        if(getDate()){
            Intent intent=new Intent(this,LoadingActivity.class);
            startActivity(intent);
            //加载了第一张图片没有销毁 需要finish()
            finish();
        }
    }

    public boolean getDate(){
        mPreferences=getSharedPreferences(POWER_SERVICE,MODE_APPEND);
        //PREFRENCE_NAME  MODE_PRIVATE
        mBoolean=mPreferences.getBoolean("first",false);
        return mBoolean;
    }

    @Override
    public void initView() {
        //找到ViewPager控件
        mViewpager= (ViewPager) findViewById(R.id.iv_guide);
        //装有图片的集合
        ArrayList<ImageView> mList = new ArrayList<>();

        for (int i = 0; i <mPicture.length ; i++) {
            ImageView img=new ImageView(this);
            img.setImageResource(mPicture[i]);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mList.add(img);
        }

        //给集合绑定点击事件
        mList.get(mPicture.length-1).setOnClickListener(this);

        GuideAdapter adapter=new GuideAdapter(mList);
        mViewpager.setAdapter(adapter);
        //mViewpager.addOnPageChangeListener(this);
        //mViewpager.setOnClickListener(this);

    }

//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

//    @Override
//    public void onPageSelected(int position) {
//        if(position==2){
 //           Log.e("aaa", "onPageSelected: "+position );
//            mRelativeLayout.setOnClickListener(this);
//            mBtn.setOnClickListener(this);
//        }
//    }

//    @Override
//    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,LoadingActivity.class);
        startActivity(intent);
        finish();
    }

    SharedPreferences.Editor edit;
    @Override
    protected void onStop() {
        super.onStop();
        edit=mPreferences.edit();
        edit.putBoolean("first",true);
        edit.commit();
    }
}
