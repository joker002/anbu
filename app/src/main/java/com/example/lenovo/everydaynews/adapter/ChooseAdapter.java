package com.example.lenovo.everydaynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.activity.ChooseActivity;

import java.util.ArrayList;

import static com.example.lenovo.everydaynews.activity.ChooseActivity.chooses;

/**
 * RecyclerView的适配器
 * Created by lenovo on 2016/10/10.
 */
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MyHolder> {
    ArrayList<String> mList;
    Context mContext;
    LayoutInflater mInflater;

    public ChooseAdapter(ArrayList<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_detail, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.mTvName.setText(mList.get(position));
        holder.mIvSelector.setSelected(ChooseActivity.chooses[position]);

        //Log.e("aaa", "onBindViewHolder: A" );
        holder.itemView.setOnClickListener(new MyClickClass(holder, position));
        //Log.e("aaa", "onBindViewHolder: B" );
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView mIvSelector;
        public TextView mTvName;
        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            mIvSelector = (ImageView) itemView.findViewById(R.id.iv_user_detail_selector);
            mTvName = (TextView) itemView.findViewById(R.id.tv_user_detail_name);
            this.itemView = itemView;
        }
    }


    public class MyClickClass implements View.OnClickListener {
        MyHolder holder;
        int position;

        public MyClickClass(MyHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //Log.e("aaa", "onBindViewHolder: C" );
            //响应
            mOnItemClickListener.onItemClick(holder, position);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        //Log.e("aaa", "onBindViewHolder: D" );
        this.mOnItemClickListener = mOnItemClickListener;
    }

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MyHolder holder, int position);
    }
}
