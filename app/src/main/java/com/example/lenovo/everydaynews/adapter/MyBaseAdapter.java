package com.example.lenovo.everydaynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/9/27.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{

    Context mContext;
    public ArrayList<T> mList;

    LayoutInflater mInflater;
    int LayoutRecId;
    public MyBaseAdapter(Context mContext,ArrayList<T> mList,int LayoutRecId){
        this.mContext=mContext;

        this.mList=mList;
        if(mList==null){
            this.mList=new ArrayList<>();
        }

        this.LayoutRecId=LayoutRecId;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=mInflater.inflate(LayoutRecId,null);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        putView(holder,convertView,position);
        return convertView;
    }

    public abstract void putView(Holder holder,View convertView,int position) ;



    class Holder{

    }
}
