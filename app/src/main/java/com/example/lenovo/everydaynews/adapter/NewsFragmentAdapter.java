package com.example.lenovo.everydaynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.entity.News;

import java.util.ArrayList;

/**
 * 新闻碎片适配器
 * Created by lenovo on 2016/9/30.
 */
public class NewsFragmentAdapter extends MyBaseAdapter<News>{
    //public ArrayList<News> mList;

    public NewsFragmentAdapter(Context mContext, ArrayList<News> mList, int LayoutRecId) {
        super(mContext, mList, LayoutRecId);
    }

    @Override
    public void putView(Holder holder, View convertView, int position) {
        //ImageView mImageView=convertView.findViewById(R.id.)
        //Log.e("aaa", "putView: "+position );

        News news= mList.get(position);
        TextView tittle= (TextView) convertView.findViewById
                (R.id.tv_iten_fragment_title);

        tittle.setText(news.title);

        TextView summary= (TextView) convertView.findViewById
                (R.id.tv_iten_fragment_summary);
        summary.setText(news.summary);

        TextView stamp= (TextView) convertView.findViewById
                (R.id.tv_iten_fragment_stamp);
        stamp.setText(news.stamp);

        ImageView img= (ImageView) convertView.findViewById(R.id.iv_item_fragment);

        Glide.with(mContext).load(news.icon).into(img);
    }
}
