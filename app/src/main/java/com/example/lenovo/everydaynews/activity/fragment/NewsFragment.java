package com.example.lenovo.everydaynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.activity.NewsDetailActivity;
import com.example.lenovo.everydaynews.adapter.NewsFragmentAdapter;
import com.example.lenovo.everydaynews.entity.News;
import com.example.lenovo.everydaynews.entity.NewsArray;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;
import com.example.lenovo.everydaynews.view.XListView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 新闻碎片
 * Created by cc on 2016/9/29.
 */
public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    /**
     * 自定义Activity
     */
    FragmentActivity activity;

    XListView mListView;

    View mView;
    /**
     * 方向
     */
    int dir = 1;
    /**
     * 新闻ID
     */
    int nid = 1;
    /**
     * 适配器
     */
    NewsFragmentAdapter mAdapter;
    /**
     * 格式化日期类
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //得到View
        mView = getView();
        //得到FragmentActivity
        activity = getActivity();
        //找到mListView控件
        mListView = (XListView) mView.findViewById(R.id.lv_fragment_home);
        //加载适配器
        mAdapter = new NewsFragmentAdapter(activity, null, R.layout.item_fragment);

        mListView.setAdapter(mAdapter);

        //可以进行上拉加载
        mListView.setPullLoadEnable(true);
        //可以进行下拉刷新
        mListView.setPullRefreshEnable(true);

        mListView.setXListViewListener(this);

        //mListView点击事件
        mListView.setOnItemClickListener(this);

        getHttp();
    }

    /**
     * 调接口 拿数据
     */
    public void getHttp() {

        //带参数的键值对
        Map<String, String> params = new HashMap<>();
        params.put("ver", "0000000");
        params.put("subid", "1");
        params.put("dir", "" + dir);
        params.put("nid", "" + nid);
        params.put("stamp", "20140321000000");
        params.put("cnt", "18");

        MyHttp.get(activity, "http://118.244.212.82:9092/newsClient/news_list", params, new OnResultFinishListener() {

            @Override
            public void success(Response response) {
                //Log.e("aaa", "success: "+response.result );
                //Gson解析
                Gson gson = new Gson();
                NewsArray array = gson.fromJson(response.result.toString(), NewsArray.class);
                //Log.e("aaa", "success: "+array.data+"--------"+mAdapter+"--------"+mAdapter.mList);

                //将数据写入适配器的数据源中
                //mAdapter.mList.addAll(array.data);
                // mAdapter.notifyDataSetChanged();

                /**
                 * 有数据  进行添加并刷新
                 */
                if (array.data != null && array.data.size() > 0) {
                    mAdapter.mList.addAll(array.data);
                    mAdapter.notifyDataSetChanged();
                }
                //1：用户下滑界面主动刷新，服务器返回较新的评论
                //2： 用户上拉界面主动刷新，服务器返回较早的评论；

                if (dir == 1) {
                    Date date = new Date();

                    mListView.setRefreshTime(format.format(date));
                }
                //关闭上拉加载以及下拉刷新框
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }

            @Override
            public void failed(Response response) {
                //Log.e("aaa", "failed: " + response);
            }
        });
    }

    @Override
    public void onRefresh() {
        //进行下拉刷新操作
        dir = 1;
        //清空之前
        mAdapter.mList.clear();
        getHttp();
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        dir = 2;
        //拿到最后一条的id
        if (mAdapter.mList.size() > 0) {
            News news = mAdapter.mList.get(mAdapter.mList.size() - 1);
            nid = news.nid;
        }
        getHttp();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(activity, NewsDetailActivity.class);

        //News news=mAdapter.mList.get(position-1);
        //Log.e("aaa", "onItemClick: 222222222222" );
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("Extra", mAdapter.mList.get(position - 1));
        //intent.putExtra("Extra", mAdapter.mList.get(position-1));
        //intent.putExtra(mBundle);
        intent.putExtras(mBundle);

        //Log.e("aaa", "onItemClick: 1111111111111" + String.valueOf(mAdapter.mList.get(position - 1)));
        startActivity(intent);
    }
}

