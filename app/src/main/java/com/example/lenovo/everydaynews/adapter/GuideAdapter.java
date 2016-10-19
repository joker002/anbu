package com.example.lenovo.everydaynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.everydaynews.activity.GuideActivity;
import com.example.lenovo.everydaynews.activity.LoadingActivity;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by lenovo on 2016/9/27.
 */
public class GuideAdapter extends PagerAdapter {
    ArrayList<ImageView> mList;

    public GuideAdapter( ArrayList<ImageView> mList){
        this.mList=mList;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //Log.e("aaa", "isViewFromObject: "+view+"----Object"+object );
        return view== object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.e("aaa", "instantiateItem: "+position);
        ImageView img=mList.get(position);
        container.addView(img);
//        if(position==2){
//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
