package com.example.lenovo.everydaynews.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.adapter.NewsFragmentAdapter;
import com.example.lenovo.everydaynews.entity.News;

import java.util.ArrayList;

import static android.content.Intent.getIntent;

/**
 * 收藏碎片
 * Created by lenovo on 2016/10/9.
 */
public class CollectFragment extends Fragment {
    Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.one_fragment, container, false);
        mActivity = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // ListView mListView = (ListView) view.findViewById(R.id.lv_fragment_collect);

        //数据源
//        News mNews = (News) mActivity.getIntent().getSerializableExtra("News");
//        Log.e("aaa", "onViewCreated: "+mNews );
//
//        ArrayList<News> arrayList=new ArrayList<>();
//        arrayList.add(mNews);
//        Log.e("aaa", "onViewCreated: "+arrayList );
//
//        //适配器
//        NewsFragmentAdapter mNewsFragmentAdapter = new NewsFragmentAdapter(mActivity, null, R.layout.item_fragment);
//        mListView.setAdapter(mNewsFragmentAdapter);

//        mNewsFragmentAdapter.mList.addAll(arrayList);
//        mNewsFragmentAdapter.notifyDataSetChanged();

    }

}
