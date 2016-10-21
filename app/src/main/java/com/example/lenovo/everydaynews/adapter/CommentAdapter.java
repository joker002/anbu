package com.example.lenovo.everydaynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.entity.Comment;

import java.util.ArrayList;

/**
 * 评论页面适配器
 * Created by lenovo on 2016/10/18.
 */
public class CommentAdapter extends MyBaseAdapter<Comment> {

    public CommentAdapter(Context mContext, ArrayList<Comment> mList, int LayoutRecId) {
        super(mContext, mList, LayoutRecId);
    }

    @Override
    public void putView(Holder holder, View convertView, int position) {
        Comment com = mList.get(position);
        //评论者头像
        ImageView cid = (ImageView) convertView.findViewById(R.id.iv_comment_portrait);
        Glide.with(mContext).load(com.portrait).into(cid);

        //cid.setImageResource();
        //评论者姓名
        TextView name = (TextView) convertView.findViewById(R.id.tv_comment_name);
        name.setText(com.uid);
        Log.e("aaa", "putView: "+com.uid );

        //评论时间
        TextView time = (TextView) convertView.findViewById(R.id.tv_comment_time);
        time.setText(com.stamp);
        //评论内容
        TextView comment = (TextView) convertView.findViewById(R.id.tv_comment_comment);
        comment.setText(com.content);
    }
}
